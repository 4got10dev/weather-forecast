package s4got10dev.weather.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DayForecast {

    @JsonFormat(pattern="dd.MM.yyyy", timezone = "UTC")
    private Instant date;
    @JsonProperty("avg_day_temperature")
    private double dayTemp;
    @JsonProperty("avg_night_temperature")
    private double nightTemp;
    @JsonProperty("avg_pressure")
    private double pressure;

}
