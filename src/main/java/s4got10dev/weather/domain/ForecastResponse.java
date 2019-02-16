package s4got10dev.weather.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ForecastResponse {

    @JsonIgnore
    private int code;
    private String message;

    private String location;
    private List<DayForecast> dailyForecast;
}
