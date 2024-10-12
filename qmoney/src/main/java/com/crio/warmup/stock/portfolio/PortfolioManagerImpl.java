
package com.crio.warmup.stock.portfolio;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.SECONDS;

import com.crio.warmup.stock.dto.AnnualizedReturn;
import com.crio.warmup.stock.dto.Candle;
import com.crio.warmup.stock.dto.PortfolioTrade;
import com.crio.warmup.stock.dto.TiingoCandle;
import com.crio.warmup.stock.exception.StockQuoteServiceException;
import com.crio.warmup.stock.quotes.StockQuotesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.springframework.web.client.RestTemplate;

public class PortfolioManagerImpl implements PortfolioManager {

  // private RestTemplate restTemplate;
  private StockQuotesService stockQuotesService;
  private RestTemplate restTemplate;

  PortfolioManagerImpl(StockQuotesService stockQuotesService){
    this.stockQuotesService = stockQuotesService;
  }

  // Caution: Do not delete or modify the constructor, or else your build will break!
  // This is absolutely necessary for backward compatibility
  protected PortfolioManagerImpl(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }


  //TODO: CRIO_TASK_MODULE_REFACTOR
  // 1. Now we want to convert our code into a module, so we will not call it from main anymore.
  //    Copy your code from Module#3 PortfolioManagerApplication#calculateAnnualizedReturn
  //    into #calculateAnnualizedReturn function here and ensure it follows the method signature.
  // 2. Logic to read Json file and convert them into Objects will not be required further as our
  //    clients will take care of it, going forward.

  // Note:
  // Make sure to exercise the tests inside PortfolioManagerTest using command below:
  // ./gradlew test --tests PortfolioManagerTest

  //CHECKSTYLE:OFF




  private Comparator<AnnualizedReturn> getComparator() {
    return Comparator.comparing(AnnualizedReturn::getAnnualizedReturn).reversed();
  }

  


  //CHECKSTYLE:OFF

  // TODO: CRIO_TASK_MODULE_REFACTOR
  //  Extract the logic to call Tiingo third-party APIs to a separate function.
  //  Remember to fill out the buildUri function and use that.


  public List<Candle> getStockQuote(String symbol, LocalDate from, LocalDate to)
      throws JsonProcessingException, StockQuoteServiceException {
        // if(from.compareTo(to) >= 0){
        //   throw new RuntimeException();
        // }

        // String tiingoURL = buildUri(symbol, from, to);
        // TiingoCandle[] tiingoCandleArray = restTemplate.getForObject(tiingoURL, TiingoCandle[].class);

        // if(tiingoCandleArray == null){
        //   return new ArrayList<Candle>();
        // }else{
        //   List<Candle> stockList = Arrays.asList(tiingoCandleArray);
        //   return stockList;
        // }

        try{
          return stockQuotesService.getStockQuote(symbol, from, to);

        }catch(JsonProcessingException e){
          e.printStackTrace();
          return new ArrayList<>();
        }


  }

  protected String buildUri(String symbol, LocalDate startDate, LocalDate endDate) {
    String APIKEY = "48eb5677021cf0daa4040abb4250ffd8a78015e5";

    String uriTemplate = "https:api.tiingo.com/tiingo/daily/$SYMBOL/prices?"
            + "startDate=$STARTDATE&endDate=$ENDDATE&token=$APIKEY";
    String url = uriTemplate.replace("$APIKEY", APIKEY).replace("$SYMBOL", symbol)
    .replace("$STARTDATE", startDate.toString()).replace("$ENDDATE", endDate.toString());
      return url;
  }


  @Override
  public List<AnnualizedReturn> calculateAnnualizedReturn(List<PortfolioTrade> portfolioTrades,
      LocalDate endDate) throws StockQuoteServiceException {
    // TODO Auto-generated method stub
  AnnualizedReturn annualizedReturn;
  List<AnnualizedReturn> annualizedReturns = new ArrayList<>();

  for(int i =0; i< portfolioTrades.size(); i++){

    annualizedReturn = getAnnualizedReturn(portfolioTrades.get(i), endDate);

    annualizedReturns.add(annualizedReturn);
  }
    Collections.sort(annualizedReturns, getComparator());
    return annualizedReturns;
  }


  private AnnualizedReturn getAnnualizedReturn(PortfolioTrade trade, LocalDate endDate) throws StockQuoteServiceException {
    AnnualizedReturn annualizedReturn;

    String symbol = trade.getSymbol();
    LocalDate StartDate = trade.getPurchaseDate();

    try{
      List<Candle> stocks;

      stocks = getStockQuote(symbol, StartDate, endDate);

      Candle stockStart = stocks.get(0);
      Candle stockLast = stocks.get(stocks.size()-1);

      Double buyPrice = stockStart.getOpen();
      Double sellPrice = stockLast.getClose();

      Double totalReturn = (sellPrice - buyPrice) / buyPrice;

      double NumYears = ChronoUnit.DAYS.between(StartDate, endDate) / 365.24;

      double annualizedReturns = Math.pow( (1 + totalReturn) , (1 / NumYears))-1; 

      annualizedReturn =  new AnnualizedReturn(symbol, annualizedReturns, totalReturn );
    }catch (JsonProcessingException e){
      annualizedReturn = new AnnualizedReturn(symbol, Double.NaN, Double.NaN);
    }

    return annualizedReturn;
  }


  // ¶TODO: CRIO_TASK_MODULE_ADDITIONAL_REFACTOR
  //  Modify the function #getStockQuote and start delegating to calls to
  //  stockQuoteService provided via newly added constructor of the class.
  //  You also have a liberty to completely get rid of that function itself, however, make sure
  //  that you do not delete the #getStockQuote function.

}
