package s4got10dev.weather.rest;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import s4got10dev.weather.service.ForecastService;
import s4got10dev.weather.domain.ForecastResponse;

@RestController
@RequestMapping("/data")
public class ForecastController {

    private ForecastService forecastService;

    public ForecastController(ForecastService forecastService) {
        this.forecastService = forecastService;
    }

    @ApiOperation("Returns average day and night temperature (in Celsius) and pressure of the next 3 days")
    @GetMapping
    public ResponseEntity<ForecastResponse> get3DayForecast(@RequestParam(value = "city") String city) {
        ForecastResponse response = forecastService.get3DayForecast(city);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }


}
