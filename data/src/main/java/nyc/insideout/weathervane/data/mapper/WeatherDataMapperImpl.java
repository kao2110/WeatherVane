package nyc.insideout.weathervane.data.mapper;


import android.support.v4.util.ArrayMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import nyc.insideout.weathervane.data.database.model.WeatherData;
import nyc.insideout.weathervane.data.service.model.ApiCity;
import nyc.insideout.weathervane.data.service.model.ApiForecastItem;
import nyc.insideout.weathervane.data.service.model.ApiWeatherData;
import nyc.insideout.weathervane.domain.model.Forecast;
import nyc.insideout.weathervane.domain.model.ForecastDetail;

public class WeatherDataMapperImpl implements WeatherDataMapper{


    // takes the bundled data retrieved from the api and map it to a list of individual items
    // to prepare it for the UseCase or to be stored in a local database
    @Override
    public ArrayMap<Long, WeatherData> apiToCache(ApiWeatherData apiWeatherData) {
        ArrayMap<Long, WeatherData> arrayMap = new ArrayMap<>();
        for(int i = 0; i < apiWeatherData.getApiForecastList().size(); i++){
            ApiForecastItem forecastItem = apiWeatherData.getApiForecastList().get(i);
            arrayMap.put(forecastItem.getDate(), apiToWeatherData(apiWeatherData.getApiCity(), forecastItem));
        }
        return arrayMap;
    }

    // takes a collection of WeatherData items and returns a list modules used by the UseCases
    @Override
    public List<Forecast> cacheToDomain(Collection<WeatherData> data) {
        List<Forecast> forecastList = new ArrayList<>();
        Iterator<WeatherData> iterator = data.iterator();

        while(iterator.hasNext()){
            WeatherData weatherData = iterator.next();
            Forecast forecast = new Forecast(weatherData.getDate(), weatherData.getTempMax(),
                    weatherData.getTempMin(), weatherData.getForecastId(),
                    weatherData.getForecastDesc(), weatherData.getForecastIconId());
            forecastList.add(forecast);
        }

        return forecastList;
    }

    // takes a single data model and maps it to a domain module
    @Override
    public ForecastDetail cacheToDomainDetail(WeatherData weatherData) {
        return new ForecastDetail(weatherData.getDate(), weatherData.getTempMax(),
                weatherData.getTempMin(), weatherData.getForecastId(), weatherData.getForecastDesc(),
                weatherData.getHumidity(), weatherData.getForecastDescDetail(), weatherData.getForecastIconId());
    }

    private WeatherData apiToWeatherData(ApiCity apiCity, ApiForecastItem apiForecastItem){

        return new WeatherData(apiCity.getId(), apiCity.getName(), apiForecastItem.getDate(),
                apiForecastItem.getHumidity(), apiForecastItem.getApiTemperature().getMax(),
                apiForecastItem.getApiTemperature().getMin(), apiForecastItem.getPressure(),
                apiForecastItem.getDeg(), apiForecastItem.getSpeed(), apiForecastItem.getDescription().get(0).getMain(),
                apiForecastItem.getDescription().get(0).getDescription(),
                apiForecastItem.getDescription().get(0).getId(), apiForecastItem.getDescription().get(0).getIcon());
    }
}