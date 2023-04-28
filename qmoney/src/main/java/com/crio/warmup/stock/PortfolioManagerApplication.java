
package com.crio.warmup.stock;


import com.crio.warmup.stock.dto.*;
import com.crio.warmup.stock.log.UncaughtExceptionHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
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
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.web.client.RestTemplate;


public class PortfolioManagerApplication {
  static String TOKEN="KDHMQ4C28QFQXOA1";
  static String URL="https://api.tiingo.com/tiingo/daily/$SYMBOL/prices?startDate=$STARTDATE&endDate=$ENDDATE&token=$APIKEY";
  public static RestTemplate restTemplate=new RestTemplate();
  public static PortfolioManager portfolioManager=PortfolioManagerFactory.getPortfolioManager(restTemplate);
  // TODO: CRIO_TASK_MODULE_JSON_PARSING
  //  Task:
  //       - Read the json file provided in the argument[0], The file is available in the classpath.
  //       - Go through all of the trades in the given file,
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
    String file=args[0];
    String content=readFileAsString(file);
    ObjectMapper objectMapper=getObjectMapper();
    PortfolioTrade[] names=objectMapper.readValue(content, PortfolioTrade[].class); 
     return Stream.of(names).map(PortfolioTrade::getSymbol).collect(Collectors.toList());
  }

  public static String getToken(){
    return TOKEN;
  }



  // TODO: CRIO_TASK_MODULE_CALCULATIONS
  //  Now that you have the list of PortfolioTrade and their data, calculate annualized returns
  //  for the stocks provided in the Json.
  //  Use the function you just wrote #calculateAnnualizedReturns.
  //  Return the list of AnnualizedReturns sorted by annualizedReturns in descending order.

  // Note:
  // 1. You may need to copy relevant code from #mainReadQuotes to parse the Json.
  // 2. Remember to get the latest quotes from Tiingo API.














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

  private static String readFileAsString(String filename)throws URISyntaxException,IOException{
    return new String(Files.readAllBytes(resolveFileFromResources(filename).toPath()),
    "UTF-8");
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
     String resultOfResolveFilePathArgs0 = "/home/crio-user/workspace/amalatrasky15-ME_QMONEY_V2/qmoney/bin/main/trades.json";
     String toStringOfObjectMapper = "com.fasterxml.jackson.databind.ObjectMapper@7c6908d7";
     String functionNameFromTestFileInStackTrace = "String[1]@23/mainReadFile";
     String lineNumberFromTestFileInStackTrace = "29:1";


    return Arrays.asList(new String[]{valueOfArgument0, resultOfResolveFilePathArgs0,
        toStringOfObjectMapper, functionNameFromTestFileInStackTrace,
        lineNumberFromTestFileInStackTrace});
  }


  // Note:
  // Remember to confirm that you are getting same results for annualized returns as in Module 3.
  public static List<String> mainReadQuotes(String[] args) throws IOException, URISyntaxException {
    LocalDate endDate=LocalDate.parse(args[1]);
    PortfolioTrade[] portfolioTrades = getProfolioTradesFrom(args[0]);
    return Arrays.stream(portfolioTrades).map(trade ->{
      TiingoCandle[] tiingoCandles = getTiingoCandles(endDate, trade);
      return new TotalReturnsDto(trade.getSymbol(), Stream.of(tiingoCandles)
      .filter(Candle->Candle.getDate().equals(endDate))
      .findFirst().get().getClose());
    }).sorted(Comparator.comparing(TotalReturnsDto::getClosingPrice)).map(TotalReturnsDto::getSymbol)
    .collect(Collectors.toList());
  }

private static TiingoCandle[] getTiingoCandles(LocalDate endDate, PortfolioTrade trade) {
  String url=buildTiingoUrl(endDate, trade, URL, TOKEN);
  TiingoCandle[] tiingoCandles=new RestTemplate().getForObject(url,TiingoCandle[].class);
  return tiingoCandles;
}
private static PortfolioTrade[] getProfolioTradesFrom(String file)
    throws URISyntaxException, IOException, JsonProcessingException, JsonMappingException {
  String content=readFileAsString(file);
  ObjectMapper objectmapper=getObjectMapper();
  return objectmapper.readValue(content, PortfolioTrade[].class);
}
private static String buildTiingoUrl(LocalDate endDate, PortfolioTrade trade, String uri,
    String token) {
  return uri.replace("$APIKEY",token).replace("$SYMBOL",trade.getSymbol()).
  replace("$STARTDATE", trade.getPurchaseDate().toString())
  .replace("$ENDDATE",endDate.toString());
}
  public static List<PortfolioTrade> readTradesFromJson(String filename) throws IOException, URISyntaxException {
    ObjectMapper objectMapper=getObjectMapper();
     List<PortfolioTrade> trades=Arrays.asList(objectMapper.readValue(resolveFileFromResources(filename),PortfolioTrade[].class));
    return trades;

 }
  public static List<TotalReturnsDto> mainReadQuotesHelper(String[] args,List<PortfolioTrade> trades)throws IOException,URISyntaxException{
    RestTemplate restTemplate=new RestTemplate();
    List<TotalReturnsDto> tests=new ArrayList<TotalReturnsDto>();
    for(PortfolioTrade t:trades){
      String uri="https://api.tiingo.com/tiingo/daily/"+t.getSymbol()+"/prices?startDate="+
      t.getPurchaseDate().toString()+"&endDate="+args[1]
      +"&token=3b5b84139609519f80f0b4bbe8249a89089ccaf8";
      TiingoCandle[] results=restTemplate.getForObject(uri,TiingoCandle[].class);
      if(results!=null){
        tests.add(new TotalReturnsDto(t.getSymbol(), results[results.length-1].getClose()));
      }
    }
    return tests;
  }
 
  // TODO:
  //  After refactor, make sure that the tests pass by using these two commands
  //  ./gradlew test --tests PortfolioManagerApplicationTest.readTradesFromJson
  //  ./gradlew test --tests PortfolioManagerApplicationTest.mainReadFile
 


  // TODO:
  //  Build the Url using given parameters and use this function in your code to cann the API.
  public static String prepareUrl(PortfolioTrade trade, LocalDate endDate, String token) {
    String uri="https://api.tiingo.com/tiingo/daily/"+trade.getSymbol()+"/prices?startDate="+
      trade.getPurchaseDate().toString()+"&endDate="+endDate
      +"&token="+token;
     return uri;
  }
  // TODO:
  //  Ensure all tests are passing using below command
  //  ./gradlew test --tests ModuleThreeRefactorTest
  static Double getOpeningPriceOnStartDate(List<Candle> candles) { 
    return candles.get(0).getOpen();
  }

  public static Double getClosingPriceOnEndDate(List<Candle> candles) {
     return candles.get(candles.size()-1).getClose();
  }


  public static List<Candle> fetchCandles(PortfolioTrade trade, LocalDate endDate, String token) throws JsonMappingException, JsonProcessingException {
    RestTemplate restTemplate=new RestTemplate();
    ObjectMapper objectMapper=new ObjectMapper();
    List<TotalReturnsDto> tests=new ArrayList<TotalReturnsDto>();
    List<Candle> can=new ArrayList<Candle>();
    String uri="https://api.tiingo.com/tiingo/daily/"+trade.getSymbol()+"/prices?startDate="+
      trade.getPurchaseDate().toString()+"&endDate="+endDate
      +"&token="+token;
      TiingoCandle[] results=restTemplate.getForObject(uri,TiingoCandle[].class);
      for(TiingoCandle t:results){
        can.add(t);
      }
      return can;
  }

  public static List<AnnualizedReturn> mainCalculateSingleReturn(String[] args)
      throws IOException, URISyntaxException {
        LocalDate endDate=LocalDate.parse(args[1]);
        PortfolioTrade[] portfolioTrades = getProfolioTradesFrom(args[0]);
        return Arrays.stream(portfolioTrades).map(trade ->{
        TiingoCandle[] tiingoCandles = getTiingoCandles(endDate, trade);
          double openprice=tiingoCandles[0].getOpen();
          double closeprice=tiingoCandles[tiingoCandles.length-1].getClose();
          AnnualizedReturn returnobj=calculateAnnualizedReturns(endDate,trade,openprice,closeprice); 
        return returnobj;
       }).sorted(Comparator.comparing(AnnualizedReturn::getAnnualizedReturn).reversed())
    .collect(Collectors.toList());
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
        double totalreturn=(sellPrice-buyPrice)/buyPrice;
        double numyear=ChronoUnit.DAYS.between(trade.getPurchaseDate(), endDate)/365.24;
        double annualized_return=Math.pow((1+totalreturn), (1/numyear))-1;
      return new AnnualizedReturn(trade.getSymbol(),annualized_return, totalreturn);
  }

















  // TODO: CRIO_TASK_MODULE_REFACTOR
  //  Once you are done with the implementation inside PortfolioManagerImpl and
  //  PortfolioManagerFactory, create PortfolioManager using PortfolioManagerFactory.
  //  Refer to the code from previous modules to get the List<PortfolioTrades> and endDate, and
  //  call the newly implemented method in PortfolioManager to calculate the annualized returns.

  // Note:
  // Remember to confirm that you are getting same results for annualized returns as in Module 3.

  public static List<AnnualizedReturn> mainCalculateReturnsAfterRefactor(String[] args)
      throws Exception {
       String file = args[0];
       LocalDate endDate = LocalDate.parse(args[1]);
       String contents = readFileAsString(file);
       ObjectMapper objectMapper = getObjectMapper();
       PortfolioTrade[] portfolioTrades=objectMapper.readValue(contents,PortfolioTrade[].class);
       return portfolioManager.calculateAnnualizedReturn(Arrays.asList(portfolioTrades), endDate);
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

