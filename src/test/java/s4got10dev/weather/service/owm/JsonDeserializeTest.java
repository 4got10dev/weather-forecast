package s4got10dev.weather.service.owm;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;

import static java.nio.file.Files.readAllBytes;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@JsonTest
public class JsonDeserializeTest {

    @Autowired
    private JacksonTester<OwmForecastResponseDTO> json;

    @Test
    public void testNotFoundResponse() throws IOException {
        File file = ResourceUtils.getFile("classpath:response-not-found.json");
        OwmForecastResponseDTO forecastResponse = json.parseObject(new String(readAllBytes(file.toPath())));
        assertThat(forecastResponse).isNotNull();

        assertThat(forecastResponse.getCod()).isEqualTo("404");

        assertThat(forecastResponse.getCity()).isNull();
        assertThat(forecastResponse.getList()).isNull();
    }

    @Test
    public void testSuccessfulResponse() throws IOException {
        File file = ResourceUtils.getFile("classpath:response-small.json");
        OwmForecastResponseDTO forecastResponse = json.parseObject(new String(readAllBytes(file.toPath())));

        assertThat(forecastResponse).isNotNull();

        assertThat(forecastResponse.getCod()).isEqualTo("200");

        assertThat(forecastResponse.getCity())
                .isNotNull().hasFieldOrPropertyWithValue("name", "London");

        assertThat(forecastResponse.getList()).hasSize(3)
                .extracting(f -> f.getMain().getPressure()).contains(1028.81d, 1027.66d, 1027.26d);

    }
}
