package s4got10dev.weather.service.owm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
class DataLoader {

    @Value("${owm.api.endpoint}")
    private String url;

    @Value("${owm.api.key}")
    private String apiKey;

    @Value("${owm.api.units}")
    private String units;

    OwmForecastResponseDTO loadForecastFromOWM(String city) {
        final String forecastUrl = String.format("%s?q=%s&units=%s&timezone=utc&APPID=%s", url, city, units, apiKey);
        try {
            return new RestTemplate().getForObject(forecastUrl, OwmForecastResponseDTO.class);
        } catch (HttpClientErrorException ex) {
            return OwmForecastResponseDTO.builder().cod("404").message("No data found for specified request").build();
        }
    }

}
