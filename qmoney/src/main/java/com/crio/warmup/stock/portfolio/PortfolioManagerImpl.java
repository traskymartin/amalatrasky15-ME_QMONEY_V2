
package com.crio.warmup.stock.portfolio;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.SECONDS;

import com.crio.warmup.stock.dto.AnnualizedReturn;
import com.crio.warmup.stock.dto.Candle;
import com.crio.warmup.stock.dto.PortfolioTrade;
import com.crio.warmup.stock.dto.TiingoCandle;
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


  private RestTemplate restTemplate;
  private StockQuotesService stockQuotesService;
  PortfolioManagerImpl(StockQuotesService stockQuotesService){
  this.stockQuotesService=stockQuotesService;
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








  

  //CHECKSTYLE:OFF

  // TODO: CRIO_TASK_MODULE_REFACTOR
  //  Extract the logic to call Tiingo third-party APIs to a separate function.
  //  Remember to fill out the buildUri function and use that.

  public List<Candle> getStockQuote(String symbol, LocalDate from, LocalDate to)
      throws JsonProcessingException {
      return stockQuotesService.getStockQuote(symbol,from,to);
  }

  protected String buildUri(String symbol, LocalDate startDate, LocalDate endDate) {
    String token="a92bb455a97449ca9eb46a9af9868b54aa8bed9c";
       String uriTemplate = "https:api.tiingo.com/tiingo/daily/$SYMBOL/prices?"
            + "startDate=$STARTDATE&endDate=$ENDDATE&token=$APIKEY";
            String uri=uriTemplate.replace("$APIKEY", token).replace("$SYMBOL", symbol)
            .replace("$STARTDATE", startDate.toString())
            .replace("$ENDDATE",endDate.toString());
      return uri;
  }


  @Override
  public List<AnnualizedReturn> calculateAnnualizedReturn(List<PortfolioTrade> portfolioTrades,
      LocalDate endDate) {
        AnnualizedReturn annualizedreturn;
        List<AnnualizedReturn> annualizedReturns=new ArrayList<AnnualizedReturn>();
        for(int i=0;i<portfolioTrades.size();i++){
          annualizedreturn=getAnnualizedReturn(portfolioTrades.get(i),endDate);
          annualizedReturns.add(annualizedreturn);
        }
        Comparator<AnnualizedReturn> sortedbyanreturn=Comparator.comparing(AnnualizedReturn::getAnnualizedReturn).reversed();
        Collections.sort(annualizedReturns,sortedbyanreturn);
        return annualizedReturns;
  }


  private AnnualizedReturn getAnnualizedReturn(PortfolioTrade portfolioTrade, LocalDate endDate) {
    AnnualizedReturn annualizedReturn;
    String symbol=portfolioTrade.getSymbol();
    LocalDate startLocalDate=portfolioTrade.getPurchaseDate();
    try {
      List<Candle> stocksStartToEndDate;
      stocksStartToEndDate=getStockQuote(symbol,startLocalDate, endDate);
      Candle stockStartDate=stocksStartToEndDate.get(0);
      Candle stockLatest=stocksStartToEndDate.get(stocksStartToEndDate.size()-1);
      double buyprice=stockStartDate.getOpen();
      double sellPrice=stockLatest.getClose();
      double totalReturn=(sellPrice-buyprice)/buyprice;
      double numYears=(double)ChronoUnit.DAYS.between(startLocalDate,endDate)/365;
      double annualizedReturns=Math.pow((1+totalReturn),(1/numYears))-1;
      annualizedReturn =new AnnualizedReturn(symbol, annualizedReturns , totalReturn);
    } catch (Exception e) {
      //TODO: handle exception
      annualizedReturn=new AnnualizedReturn(symbol,Double.NaN,Double.NaN);
    }
    return annualizedReturn;
  }
  private Comparator<AnnualizedReturn> getComparator() {
    return Comparator.comparing(AnnualizedReturn::getAnnualizedReturn).reversed();
  }



  // TODO: CRIO_TASK_MODULE_ADDITIONAL_REFACTOR
  //  Modify the function #getStockQuote and start delegating to calls to
  //  stockQuoteService provided via newly added constructor of the class.
  //  You also have a liberty to completely get rid of that function itself, however, make sure
  //  that you do not delete the #getStockQuote function.

}
