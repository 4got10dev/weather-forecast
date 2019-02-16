package s4got10dev.weather.service.owm;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
class OwmCityDTO {

    @JsonProperty("id")
    public long id;
    @JsonProperty("name")
    public String name;
    @JsonProperty("country")
    public String country;

}
