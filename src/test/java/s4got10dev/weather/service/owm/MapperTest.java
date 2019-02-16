package s4got10dev.weather.service.owm;

import org.junit.Before;
import org.junit.Test;
import s4got10dev.weather.domain.DayForecast;
import s4got10dev.weather.domain.ForecastResponse;

import java.time.Instant;
import java.util.Collections;

import static java.time.temporal.ChronoUnit.*;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class MapperTest {

    private Mapper mapper;
    private OwmForecastResponseDTO testResponse;

    @Before
    public void setUp() throws Exception {
        mapper = new Mapper();
    }

    @Test
    public void testNotFoundResponse() {
        String message = "location not found";
        OwmForecastResponseDTO notFound = OwmForecastResponseDTO.builder().cod("404").message(message).build();

        ForecastResponse expected = new ForecastResponse();
        expected.setCode(404);
        expected.setMessage(message);

        assertThat(mapper.map(notFound)).isEqualToComparingFieldByField(expected);
    }

    @Test
    public void testSuccessfulResponse() {
        OwmForecastResponseDTO testData = OwmForecastResponseDTO.builder().cod("200").message("")
                .city(OwmCityDTO.builder().name("Berlin").id(1L).country("DE").build())
                .list(asList(
                        OwmForecastDTO.builder()
                                .dt(createInstant(1, 8, 30))
                                .main(OwmMainDTO.builder().pressure(3000.30d).temp(20d).build()).build(),
                        OwmForecastDTO.builder()
                                .dt(createInstant(1, 16, 30))
                                .main(OwmMainDTO.builder().pressure(2000.20d).temp(10d).build()).build(),
                        OwmForecastDTO.builder()
                                .dt(createInstant( 1, 1, 0))
                                .main(OwmMainDTO.builder().pressure(1800.00d).temp(1d).build()).build(),
                        OwmForecastDTO.builder()
                                .dt(createInstant(1, 3, 0))
                                .main(OwmMainDTO.builder().pressure(1000.00d).temp(5d).build()).build(),
                        OwmForecastDTO.builder()
                                .dt(createInstant(2, 8, 30))
                                .main(OwmMainDTO.builder().pressure(5000.99d).temp(30d).build()).build(),
                        OwmForecastDTO.builder()
                                .dt(createInstant(2, 23, 30))
                                .main(OwmMainDTO.builder().pressure(6000.99d).temp(30d).build()).build(),
                        OwmForecastDTO.builder()
                                .dt(createInstant(5, 1, 0))
                                .main(OwmMainDTO.builder().pressure(1800d).temp(1d).build()).build(),
                        OwmForecastDTO.builder()
                                .dt(createInstant(6, 3, 0))
                                .main(OwmMainDTO.builder().pressure(1000d).temp(5d).build()).build()
                ))
                .build();

        ForecastResponse result = mapper.map(testData);
        assertThat(result).isNotNull().hasFieldOrPropertyWithValue("location", "Berlin, DE");
        assertThat(result.getDailyForecast()).isNotNull().hasSize(2).contains(
                DayForecast.builder().date(createInstant(1, 0, 0))
                        .dayTemp(15d).nightTemp(3d).pressure(1950.13d).build(),
                DayForecast.builder().date(createInstant(2, 0, 0))
                        .dayTemp(30d).nightTemp(30d).pressure(5500.99d).build()
        );
    }
    
    private static Instant createInstant(int days, int hours, int minutes) {
        return Instant.now().truncatedTo(DAYS).plus(days, DAYS).plus(hours, HOURS).plus(minutes, MINUTES);
    }
}

