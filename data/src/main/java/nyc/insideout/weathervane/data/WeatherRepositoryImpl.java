package nyc.insideout.weathervane.data;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import nyc.insideout.weathervane.data.mapper.WeatherDataMapper;
import nyc.insideout.weathervane.data.service.WeatherService;

import nyc.insideout.weathervane.data.database.model.WeatherData;
import nyc.insideout.weathervane.data.preferences.WeatherPreferences;
import nyc.insideout.weathervane.data.service.WeatherServiceImpl;
import nyc.insideout.weathervane.data.service.model.ApiWeatherData;
import nyc.insideout.weathervane.domain.interactor.GetForecastDetailsUseCase;
import nyc.insideout.weathervane.domain.interactor.GetForecastUseCase.RequestResult;
import nyc.insideout.weathervane.domain.model.ForecastDetail;
import nyc.insideout.weathervane.domain.repository.WeatherRepository;


/**
 * Class which serves as data repository for application.
 * It uses a Map to store recent query results retrieved from the api call which uses the forecast
 * date as the key for each downloaded forecast item.
 * With more time on-disk caching would be implemented so query data would be available off-line.
 * Also with more time would handle if user was performing very first search.
 */
public class WeatherRepositoryImpl implements WeatherRepository {

    private static final Logger LOGGER = Logger.getLogger(WeatherServiceImpl.class.getName());

    private WeatherService<ApiWeatherData> mWeatherService;
    private WeatherPreferences mWeatherPreferences;
    private WeatherDataMapper mWeatherDataMapper;
    private Map<Long, WeatherData> mWeatherDataCache;

    public WeatherRepositoryImpl(WeatherService weatherService, WeatherPreferences weatherPreferences,
                                 WeatherDataMapper weatherDataMapper){
        mWeatherService = weatherService;
        mWeatherPreferences = weatherPreferences;
        mWeatherDataMapper = weatherDataMapper;
    }

    /*
    * Method to retrieve list of Forecast data from either local cache or remote service.
    */
    @Override
    public void getForecast(String location, final DataRequestCallback<RequestResult> callback){
        final String lastLocation = mWeatherPreferences.getLastSearchLocation();

        // if location value is null then set location to last known search location from weather preferences
        if(location == null) location = lastLocation;

        // if in-memory cache has data and search location is a repeat then return those values,
        // otherwise make a call to service
        if(mWeatherDataCache != null && lastLocation.equalsIgnoreCase(location)){
            LOGGER.log(Level.INFO, "Data retrieved from in-memory cache");
            RequestResult requestResult =
                    new RequestResult(lastLocation, mWeatherDataMapper.cacheToDomain(mWeatherDataCache.values()));
            callback.onDataLoaded(requestResult);

        } else{
            LOGGER.log(Level.INFO, "Data retrieved from network service");

            mWeatherService.fetchForecast(location, new WeatherService.WeatherServiceCallback<ApiWeatherData>() {
                @Override
                public void onDataLoaded(ApiWeatherData result) {
                    mWeatherDataCache = mWeatherDataMapper.apiToCache(result);
                    mWeatherPreferences.setLastSearchLocation(result.getApiCity().getName());
                    RequestResult requestResult =
                            new RequestResult(lastLocation, mWeatherDataMapper.cacheToDomain(mWeatherDataCache.values()));
                    callback.onDataLoaded(requestResult);
                }

                @Override
                public void onError(Throwable e) {
                    callback.onError(e);
                }
            });
        }
    }

    /*
    * Method to retrieve forecast details from cached items.
    */
    @Override
    public void getForecastDetails(long date, DataRequestCallback<GetForecastDetailsUseCase.RequestResult> callback){
        if(mWeatherDataCache != null){
            ForecastDetail forecastDetail = mWeatherDataMapper.cacheToDomainDetail(mWeatherDataCache.get(date));
            GetForecastDetailsUseCase.RequestResult result = new GetForecastDetailsUseCase.RequestResult(forecastDetail);
            callback.onDataLoaded(result);
        }
    }
}
