package s4got10dev.weather.service.owm;

import com.google.common.base.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import s4got10dev.weather.domain.DayForecast;
import s4got10dev.weather.domain.ForecastResponse;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

import static com.google.common.base.MoreObjects.firstNonNull;
import static java.lang.Integer.valueOf;
import static java.time.ZoneOffset.UTC;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Stream.of;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.resolve;

@Component
class Mapper {


    ForecastResponse map(OwmForecastResponseDTO response) {
        ForecastResponse forecast = new ForecastResponse();

        HttpStatus code = resolve(valueOf(response.getCod()));
        forecast.setCode(firstNonNull(code, BAD_REQUEST).value());
        if (code != HttpStatus.OK) {
            forecast.setMessage(response.getMessage());
        } else {
            forecast.setLocation(ofNullable(response.getCity())
                    .map(city -> of(city.getName(), city.getCountry()).map(Strings::nullToEmpty).collect(joining(", ")))
                    .orElse("unknown"));

            forecast.setDailyForecast(mapNextNDays(response.getList(), 3));
        }
        return forecast;
    }

    List<DayForecast> mapNextNDays(List<OwmForecastDTO> forecastList, int days) {
        if (CollectionUtils.isEmpty(forecastList))
            return Collections.emptyList();
        return mapSpecificTimeRange(forecastList, inNDays(1), inNDays(1 + days));
    }

    private List<DayForecast> mapSpecificTimeRange(List<OwmForecastDTO> forecastList, Instant rangeStart, Instant rangeEnd) {
        final Map<Instant, List<OwmForecastDTO>> grouped = forecastList.stream()
                .filter(f -> f.getDt().isAfter(rangeStart.minusSeconds(1)) && f.getDt().isBefore(rangeEnd))
                .collect(groupingBy(f -> f.getDt().truncatedTo(DAYS)));
        return grouped.entrySet().stream().map(Mapper::map).collect(Collectors.toList());
    }

    private static DayForecast map(Map.Entry<Instant, List<OwmForecastDTO>> entry) {
        return DayForecast.builder().date(entry.getKey())
                .pressure(average(entry.getValue(), Mapper::dayAndNight, OwmMainDTO::getPressure))
                .dayTemp(average(entry.getValue(), Mapper::day, OwmMainDTO::getTemp))
                .nightTemp(average(entry.getValue(), Mapper::night, OwmMainDTO::getTemp)).build();
    }

    private static Instant inNDays(int days) {
        return Instant.now().truncatedTo(DAYS).plus(days, DAYS);
    }

    private static double average(List<OwmForecastDTO> entry, Predicate<OwmForecastDTO> filter, ToDoubleFunction<OwmMainDTO> extractor) {
        return BigDecimal.valueOf(entry.stream()
                .filter(filter)
                .map(OwmForecastDTO::getMain)
                .mapToDouble(extractor)
                .average()
                .orElse(0))
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    private static boolean dayAndNight(OwmForecastDTO forecast) {
        return true;
    }

    private static boolean day(OwmForecastDTO forecast) {
        int hour = forecast.getDt().atZone(UTC).getHour();
        return hour > 6 && hour < 18;
    }

    private static boolean night(OwmForecastDTO forecast) {
        return !day(forecast);
    }

}
