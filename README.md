# Weather
Application provide possibility to search weather forecast for specific city based on data from [OpenWeatherMap](https://openweathermap.org)

## How to use
Project already has gradle wrapper (so you don't need to have it installed). 
All other dependencies will be downloaded by it.

Web UI page using [OpenWeatherMap](https://openweathermap.org) API for retrieving weather forecast data,
so, please, make sure you have your own key [generated](https://openweathermap.org/api) and put it inside
`application.properties` as `owm.api.key` parameter
 
### Execution
Execute gradle build (It may take a while in the first run):
```sh
$ ./gradlew build
```
After the message of BUILD SUCCESS, the application is ready to run:
```sh
$ ./gradlew bootRun
```

Application will be available on http://localhost:8787 (This one can be also changed in `application.properties`).
REST API documentation will be available at http://localhost:8787/swagger-ui.html

## Tech stack
- Backend
    - Java 8
    - Spring Boot
        - Web
	    - Test
    - Lombok
    - SpringFox Swagger UI
- Gradle

