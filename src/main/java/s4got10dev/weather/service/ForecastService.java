package s4got10dev.weather.service;

import s4got10dev.weather.domain.ForecastResponse;

public interface ForecastService {

    ForecastResponse get3DayForecast(String city);

}
