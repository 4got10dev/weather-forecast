package s4got10dev.weather.service.owm;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Collections;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
class OwmForecastResponseDTO {

    @JsonProperty("cod")
    public String cod;
    @JsonProperty("message")
    public String message;
    @JsonProperty("list")
    public List<OwmForecastDTO> list = null;
    @JsonProperty("city")
    public OwmCityDTO city;

}
