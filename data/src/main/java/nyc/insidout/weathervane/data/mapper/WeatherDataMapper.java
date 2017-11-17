package nyc.insidout.weathervane.data.mapper;


import android.support.v4.util.ArrayMap;

import java.util.Collection;
import java.util.List;

import nyc.insidout.weathervane.data.database.model.WeatherData;
import nyc.insidout.weathervane.data.service.model.ApiWeatherData;
import nyc.insidout.weathervane.domain.model.Forecast;
import nyc.insidout.weathervane.domain.model.ForecastDetail;

/**
 * Interface to be implemented by class used to map data from api to database and to domain
 */
public interface WeatherDataMapper {


    ArrayMap<Long,WeatherData> apiToCache(ApiWeatherData apiWeatherData);

    List<Forecast> cacheToDomain(Collection<WeatherData> data);

    ForecastDetail cacheToDomainDetail(WeatherData data);
}
