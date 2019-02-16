package s4got10dev.weather.service.owm;

import org.springframework.stereotype.Service;
import s4got10dev.weather.service.ForecastService;
import s4got10dev.weather.domain.ForecastResponse;

@Service
public class ForecastServiceOwm implements ForecastService {

    private DataLoader dataLoader;
    private Mapper mapper;

    public ForecastServiceOwm(DataLoader dataLoader, Mapper mapper) {
        this.dataLoader = dataLoader;
        this.mapper = mapper;
    }

    @Override
    public ForecastResponse get3DayForecast(String city) {
        return mapper.map(dataLoader.loadForecastFromOWM(city));
    }

}
