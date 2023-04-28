
package com.crio.warmup.stock.quotes;

import com.crio.warmup.stock.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.*;

import org.springframework.web.client.RestTemplate;

public class TiingoService implements StockQuotesService {
public static final String Token="a92bb455a97449ca9eb46a9af9868b54aa8bed9c";
  private RestTemplate restTemplate;
  protected TiingoService(RestTemplate restTemplate){
  this.restTemplate=restTemplate;
  }

  // TODO: CRIO_TASK_MODULE_ADDITIONAL_REFACTOR
  //  Implement getStockQuote method below that was also declared in the interface.

  // Note:
  // 1. You can move the code from PortfolioManagerImpl#getStockQuote inside newly created method.
  // 2. Run the tests using command below and make sure it passes.
  //    ./gradlew test --tests TiingoServiceTest


  //CHECKSTYLE:OFF

  // TODO: CRIO_TASK_MODULE_ADDITIONAL_REFACTOR
  //  Write a method to create appropriate url to call the Tiingo API.
  @Override
  public List<Candle> getStockQuote(String symbol, LocalDate from, LocalDate to)
  throws JsonProcessingException {
    List<Candle> stackStarttoend;
    if(from.compareTo(to)>=0){
      throw new RuntimeException();
    }
    String url=buildUri(symbol,from,to);
    String stcoks=restTemplate.getForObject(url,String.class);
    ObjectMapper objectMapper=getObjectMapper();
    TiingoCandle[] stocksStrattoEndArray=objectMapper.readValue(stcoks,TiingoCandle[].class);
    if(stocksStrattoEndArray!=null){
      stackStarttoend=Arrays.asList(stocksStrattoEndArray);
    }else{
      stackStarttoend=Arrays.asList(new TiingoCandle[0]);
        }
    return stackStarttoend;
}

private static ObjectMapper getObjectMapper() {
  ObjectMapper objectMapper = new ObjectMapper();
  objectMapper.registerModule(new JavaTimeModule());
  return objectMapper;
}

protected String buildUri(String symbo,LocalDate from,LocalDate to) {
   String uriTemplate =String.format("https://api.tiingo.com/tiingo/daily/%s/prices?"+"startDate=%s&endDate=%s&token=%s",symbo,from,to,Token);
        String uri=uriTemplate.replace("$APIKEY", Token).replace("$SYMBOL",symbo).replace("$FROM",from.toString())
        .replace("$END",to.toString());
  return uri;
}

}
