package nyc.insideout.weathervane.data.mapper;


import android.support.v4.util.ArrayMap;

import java.util.Collection;
import java.util.List;

import nyc.insideout.weathervane.data.database.model.WeatherData;
import nyc.insideout.weathervane.data.service.model.ApiWeatherData;
import nyc.insideout.weathervane.domain.model.Forecast;
import nyc.insideout.weathervane.domain.model.ForecastDetail;

/**
 * Interface to be implemented by class used to map data from api to database and to domain
 */
public interface WeatherDataMapper {


    ArrayMap<Long,WeatherData> apiToCache(ApiWeatherData apiWeatherData);

    List<Forecast> cacheToDomain(Collection<WeatherData> data);

    ForecastDetail cacheToDomainDetail(WeatherData data);
}
