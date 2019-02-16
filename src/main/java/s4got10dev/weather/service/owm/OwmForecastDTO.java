package s4got10dev.weather.service.owm;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
class OwmForecastDTO {

    @JsonProperty("dt")
    public Instant dt;
    @JsonProperty("main")
    public OwmMainDTO main;

}
