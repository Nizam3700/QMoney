
package com.crio.warmup.stock;


import com.crio.warmup.stock.dto.*;
import com.crio.warmup.stock.log.UncaughtExceptionHandler;
import com.crio.warmup.stock.portfolio.PortfolioManager;
import com.crio.warmup.stock.portfolio.PortfolioManagerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.http.ResponseEntity;
// import org.springframework.http.codec.multipart.MultipartParser.Token;
// import org.springframework.http.codec.multipart.MultipartParser.Token;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.type.TypeReference;

import org.springframework.web.client.RestTemplate;


public class PortfolioManagerApplication {

  // TODO: CRIO_TASK_MODULE_JSON_PARSING
  //  Task:
  //       - Read the json file provided in the argument[0], The file is available in the classpath.
  //       - Prepare the list of all symbols a portfolio has.
  //       - if "trades.json" has trades like
  //         [{ "symbol": "MSFT"}, { "symbol": "AAPL"}, { "symbol": "GOOGL"}]
  //         Then you should return ["MSFT", "AAPL", "GOOGL"]
  //  Hints:
  //    1. Go through two functions provided - #resolveFileFromResources() and #getObjectMapper
  //       Check if they are of any help to you.
  //    2. Return the list of all symbols in the same order as provided in json.

  //  Note:
  //  1. There can be few unused imports, you will need to fix them to make the build pass.
  //  2. You can use "./gradlew build" to check if your code builds successfully.



  public static List<String> mainReadFile(String[] args) throws IOException, URISyntaxException {
    //  create the list 
    List<String> user = new ArrayList<>();

    // Check if arguments are provided
    if (args == null || args.length == 0) {
      System.out.println("No filename provided.");
      return user;
    }

    // read Json file 
    List<PortfolioTrade> trades = getObjectMapper().readValue(resolveFileFromResources(args[0]), new TypeReference<List<PortfolioTrade>>() {});

    // Extract symbols 
   for(PortfolioTrade port : trades){
      user.add(port.getSymbol());
   }

     return user;
  }




  //  Now that you have the list of PortfolioTrade and their data, calculate annualized returns
  //  for the stocks provided in the Json.
  //  Use the function you just wrote #calculateAnnualizedReturns.
  //  Return the list of AnnualizedReturns sorted by annualizedReturns in descending order.

  // Note:
  // 1. You may need to copy relevant code from #mainReadQuotes to parse the Json.





  // TODO: CRIO_TASK_MODULE_REST_API
  //  Find out the closing price of each stock on the end_date and return the list
  //  of all symbols in ascending order by its close value on end date.

  // Note:
  // 1. You may have to register on Tiingo to get the api_token.
  // 2. Look at args parameter and the module instructions carefully.
  // 2. You can copy relevant code from #mainReadFile to parse the Json.
  // 3. Use RestTemplate#getForObject in order to call the API,
  //    and deserialize the results in List<Candle>



  private static void printJsonObject(Object object) throws IOException {
    Logger logger = Logger.getLogger(PortfolioManagerApplication.class.getCanonicalName());
    ObjectMapper mapper = new ObjectMapper();
    logger.info(mapper.writeValueAsString(object));
  }

  private static File resolveFileFromResources(String filename) throws URISyntaxException {
    return Paths.get(
        Thread.currentThread().getContextClassLoader().getResource(filename).toURI()).toFile();
  }

  private static ObjectMapper getObjectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    return objectMapper;
  }


  // TODO: CRIO_TASK_MODULE_JSON_PARSING
  //  Follow the instructions provided in the task documentation and fill up the correct values for
  //  the variables provided. First value is provided for your reference.
  //  A. Put a breakpoint on the first line inside mainReadFile() which says
  //    return Collections.emptyList();
  //  B. Then Debug the test #mainReadFile provided in PortfoliomanagerApplicationTest.java
  //  following the instructions to run the test.
  //  Once you are able to run the test, perform following tasks and record the output as a
  //  String in the function below.
  //  Use this link to see how to evaluate expressions -
  //  https://code.visualstudio.com/docs/editor/debugging#_data-inspection
  //  1. evaluate the value of "args[0]" and set the value
  //     to the variable named valueOfArgument0 (This is implemented for your reference.)
  //  2. In the same window, evaluate the value of expression below and set it
  //  to resultOfResolveFilePathArgs0
  //     expression ==> resolveFileFromResources(args[0])
  //  3. In the same window, evaluate the value of expression below and set it
  //  to toStringOfObjectMapper.
  //  You might see some garbage numbers in the output. Dont worry, its expected.
  //    expression ==> getObjectMapper().toString()
  //  4. Now Go to the debug window and open stack trace. Put the name of the function you see at
  //  second place from top to variable functionNameFromTestFileInStackTrace
  //  5. In the same window, you will see the line number of the function in the stack trace window.
  //  assign the same to lineNumberFromTestFileInStackTrace
  //  Once you are done with above, just run the corresponding test and
  //  make sure its working as expected. use below command to do the same.
  //  ./gradlew test --tests PortfolioManagerApplicationTest.testDebugValues

  public static List<String> debugOutputs() {

     String valueOfArgument0 = "trades.json";
     String resultOfResolveFilePathArgs0 = "trades.json";
     String toStringOfObjectMapper = "ObjectMapper";
     String functionNameFromTestFileInStackTrace = "mainReadFile";
     String lineNumberFromTestFileInStackTrace = "";


    return Arrays.asList(new String[]{valueOfArgument0, resultOfResolveFilePathArgs0,
        toStringOfObjectMapper, functionNameFromTestFileInStackTrace,
        lineNumberFromTestFileInStackTrace});
  }


  // Note:
    final static String tiingoToken = "48eb5677021cf0daa4040abb4250ffd8a78015e5";
  // Remember to confirm that you are getting same results for annualized returns as in Module 3.
   public static List<String> mainReadQuotes(String[] args) throws IOException, URISyntaxException {

    LocalDate endDate = LocalDate.parse(args[1]);

    List<PortfolioTrade> portfolioTrades = readTradesFromJson(args[0]);

    RestTemplate restTemplate = new RestTemplate();

    List<TotalReturnsDto> totalReturnsDtos = new ArrayList<>();

    List<String> listOfSortSymbolsOnClosingPrice = new ArrayList<>();

    for (PortfolioTrade portfolioTrade : portfolioTrades) {
      TiingoCandle[] tiingoCandleArray = extracted(endDate, restTemplate, portfolioTrade);
      totalReturnsDtos.add(new TotalReturnsDto(portfolioTrade.getSymbol(),
          tiingoCandleArray[tiingoCandleArray.length - 1].getClose()));
    }

    Collections.sort(totalReturnsDtos,
        (a, b) -> Double.compare(a.getClosingPrice(), b.getClosingPrice()));
    for (TotalReturnsDto totalReturnsDto : totalReturnsDtos) {
      listOfSortSymbolsOnClosingPrice.add(totalReturnsDto.getSymbol());
    }

    // Check if arguments are provided
    if (args == null || args.length < 3) {
      System.out.println("Insufficient arguments provided.");
      return listOfSortSymbolsOnClosingPrice;
    }

    return listOfSortSymbolsOnClosingPrice;
  }




  private static TiingoCandle[] extracted(LocalDate endDate, RestTemplate restTemplate,
    PortfolioTrade portfolioTrade) {
    String tiingoURL = prepareUrl(portfolioTrade, endDate, tiingoToken);
    TiingoCandle[] tiingoCandleArray = restTemplate.getForObject(tiingoURL, TiingoCandle[].class);
    return tiingoCandleArray;
  }


  // TODO:
  //  After refactor, make sure that the tests pass by using these two commands
  //  ./gradlew test --tests PortfolioManagerApplicationTest.readTradesFromJson
  //  ./gradlew test --tests PortfolioManagerApplicationTest.mainReadFile
  public static List<PortfolioTrade> readTradesFromJson(String filename) throws IOException, URISyntaxException {
    File file = resolveFileFromResources(filename);
    ObjectMapper objectMapper = getObjectMapper();
    PortfolioTrade[] trades = objectMapper.readValue(file, PortfolioTrade[].class);
     return Arrays.asList(trades);
  }


  // TODO:
  //  Build the Url using given parameters and use this function in your code to cann the API.
  public static String prepareUrl(PortfolioTrade trade, LocalDate endDate, String Token) {
    return String.format("https://api.tiingo.com/tiingo/daily/%s/prices?startDate=%s&endDate=%s&token=%s",
    trade.getSymbol(),trade.getPurchaseDate(),endDate.toString(), Token);


    /*
    "https://api.tiingo.com/tiingo/daily/AAPL/prices?startDate=2010-01-01&endDate=2010-01-10&token=abcd"
     expected: <https://api.tiingo.com/tiingo/daily/AAPL/prices?endDate=2010-01-10&token=abcd>
     but was: <https://api.tiingo.com/tiingo/daily/AAPL/prices?startDate=2010-01-01&endDate=2010-01-10&token=abcd>
     */
  }

  public static String getToken(){
    // String token = "8a4d7e79d91c961e17d11b7df441697287b2c7ea";
    String token = "48eb5677021cf0daa4040abb4250ffd8a78015e5";
    return token;
  }

  // TODO:
  //  Ensure all tests are passing using below command
  //  ./gradlew test --tests ModuleThreeRefactorTest
  static Double getOpeningPriceOnStartDate(List<Candle> tiingoCandleArray) {
    if(tiingoCandleArray.isEmpty()){
      return null;
    }

    double OpeningPrice = tiingoCandleArray.get(0).getOpen();

    return OpeningPrice;
  }


  public static Double getClosingPriceOnEndDate(List<Candle> candles) {
    if(candles.isEmpty()){
      return null;
    }

    double ClosingPrice = candles.get(candles.size()-1).getClose();
     return ClosingPrice;
  }


  public static List<Candle> fetchCandles(PortfolioTrade trade, LocalDate endDate, String token) {
     String tiingoUrl = prepareUrl(trade, endDate, getToken());

     RestTemplate restTemplate = new RestTemplate();

     TiingoCandle[] tiingoCandleArray = restTemplate.getForObject(tiingoUrl, TiingoCandle[].class);

     if( tiingoCandleArray != null){
        return Arrays.asList(tiingoCandleArray);
     }else{
        return Collections.emptyList();
     }

  }

  public static List<AnnualizedReturn> mainCalculateSingleReturn(String[] args)
      throws IOException, URISyntaxException {
    LocalDate endDate = LocalDate.parse(args[1]);

    List<PortfolioTrade> portfolioTrades = readTradesFromJson(args[0]);

    RestTemplate restTemplate = new RestTemplate();

    List<AnnualizedReturn> totalReturnsDtos = new ArrayList<>();

    // List<String> listOfSortSymbolsOnClosingPrice = new ArrayList<>();

    for (PortfolioTrade portfolioTrade : portfolioTrades) {
      TiingoCandle[] tiingoCandleArray = extracted(endDate, restTemplate, portfolioTrade);
      // totalReturnsDtos.add(new TotalReturnsDto(portfolioTrade.getSymbol(),
      //     tiingoCandleArray[tiingoCandleArray.length - 1].getClose()));

      double OpenPrice = tiingoCandleArray[0].getOpen();
      double closePrice = tiingoCandleArray[tiingoCandleArray.length-1].getClose();

      AnnualizedReturn ReturnObj = calculateAnnualizedReturns(endDate, portfolioTrade, OpenPrice, closePrice);
        totalReturnsDtos.add(ReturnObj);
    }

     totalReturnsDtos.sort((a,b)-> Double.compare(b.getAnnualizedReturn(), a.getAnnualizedReturn()));
     return totalReturnsDtos;
  }

  // TODO: CRIO_TASK_MODULE_CALCULATIONS
  //  Return the populated list of AnnualizedReturn for all stocks.
  //  Annualized returns should be calculated in two steps:
  //   1. Calculate totalReturn = (sell_value - buy_value) / buy_value.
  //      1.1 Store the same as totalReturns
  //   2. Calculate extrapolated annualized returns by scaling the same in years span.
  //      The formula is:
  //      annualized_returns = (1 + total_returns) ^ (1 / total_num_years) - 1
  //      2.1 Store the same as annualized_returns
  //  Test the same using below specified command. The build should be successful.
  //     ./gradlew test --tests PortfolioManagerApplicationTest.testCalculateAnnualizedReturn

  public static AnnualizedReturn calculateAnnualizedReturns(LocalDate endDate,
      PortfolioTrade trade, Double buyPrice, Double sellPrice) {
        // Calculate totalReturn 
        double totalReturn = (sellPrice - buyPrice) / buyPrice;
        double NumYears = ChronoUnit.DAYS.between(trade.getPurchaseDate(), endDate) / 365.24;

        // Calculate Annualized Return
        double annualReturn = Math.pow( (1 + totalReturn) , (1 / NumYears))-1; 

        return new AnnualizedReturn(trade.getSymbol(), annualReturn, totalReturn );
  }





  // TODO: CRIO_TASK_MODULE_REFACTOR
  //  Once you are done with the implementation inside PortfolioManagerImpl and
  //  PortfolioManagerFactory, create PortfolioManager using PortfolioManagerFactory.
  //  Refer to the code from previous modules to get the List<PortfolioTrades> and endDate, and
  //  call the newly implemented method in PortfolioManager to calculate the annualized returns.
  public static RestTemplate restTemplate = new RestTemplate();
  public static PortfolioManager portfolioManager = PortfolioManagerFactory.getPortfolioManager(restTemplate);
  // Note:
  // Remember to confirm that you are getting same results for annualized returns as in Module 3.

  public static List<AnnualizedReturn> mainCalculateReturnsAfterRefactor(String[] args)
      throws Exception {
      //  String file = args[0];
       LocalDate endDate = LocalDate.parse(args[1]);
       String contents = readFileAsString(args[0]);
       ObjectMapper objectMapper = getObjectMapper();
       PortfolioTrade[] portfolioTrades = objectMapper.readValue(contents, PortfolioTrade[].class);
       
       return portfolioManager.calculateAnnualizedReturn(Arrays.asList(portfolioTrades), endDate);
  }


  private static String readFileAsString(String string) {
    return null;
  }




  public static void main(String[] args) throws Exception {
    Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler());
    ThreadContext.put("runId", UUID.randomUUID().toString());

    printJsonObject(mainReadFile(args));

    printJsonObject(mainReadQuotes(args));

    printJsonObject(mainCalculateSingleReturn(args));

    printJsonObject(mainCalculateReturnsAfterRefactor(args));
  }

}

