import java.util.Set;

// TODO: CRIO_TASK_MODULE_ADDITIONAL_REFACTOR
//  Implement the Candle interface in such a way that it matches the parameters returned
//  inside Json response from Alphavantage service.

 // Reference - https:www.baeldung.com/jackson-ignore-properties-on-serialization
//  Reference - https:www.baeldung.com/jackson-name-of-property
@JsonIgnoreProperties(ignoreUnKnown=true)
public class AlphavantageCandle implements Candle {
  @JsonProperty("1. open")
  private Double open;
  @JsonProperty("2. high")
  private Double high;
  @JsonProperty("3. low")
  private Double low;
  @JsonProperty("4. close")
  private Double close;
  private LocalDate date;

  @Override
  public Double getOpen(){
    return open;
  }
  @Override
  public void setOpen(Double open){
    this.open=open;
  }
  @Override
  public Double getClose(){
    return close;
  }
  @Override
  public void setClose(Double close){
    this.close=close;
  }
}

