package nyc.insideout.weathervane.data;

import android.support.v4.util.ArrayMap;

import nyc.insideout.weathervane.data.mapper.WeatherDataMapper;
import nyc.insideout.weathervane.data.service.WeatherService;

import nyc.insideout.weathervane.data.database.model.WeatherData;
import nyc.insideout.weathervane.data.preferences.WeatherPreferences;
import nyc.insideout.weathervane.data.service.model.ApiWeatherData;
import nyc.insideout.weathervane.domain.interactor.GetForecastDetailsUseCase;
import nyc.insideout.weathervane.domain.interactor.GetForecastUseCase.RequestResult;
import nyc.insideout.weathervane.domain.model.ForecastDetail;
import nyc.insideout.weathervane.domain.repository.WeatherRepository;


public class WeatherRepositoryImpl implements WeatherRepository {

    private WeatherService mWeatherService;
    private WeatherPreferences mWeatherPreferences;
    private WeatherDataMapper mWeatherDataMapper;
    private ArrayMap<Long, WeatherData> mWeatherDataCache;

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
        final String lastLocation;

        // if location value is null then retrieve the last known search location from weather preferences
        if(location == null){
            lastLocation = mWeatherPreferences.getLastSearchLocation();
        }else{
            lastLocation = location;
        }

        // if in-memory cache has data then return those values, otherwise make a call to service
        if(mWeatherDataCache != null){
            RequestResult requestResult =
                    new RequestResult(lastLocation, mWeatherDataMapper.cacheToDomain(mWeatherDataCache.values()));
            callback.onDataLoaded(requestResult);
        }
        else{
            mWeatherService.fetchForecast(lastLocation, new WeatherService.WeatherServiceCallback<ApiWeatherData>() {
                @Override
                public void onDataLoaded(ApiWeatherData result) {
                    mWeatherDataCache = mWeatherDataMapper.apiToCache(result);
                    RequestResult requestResult =
                            new RequestResult(lastLocation, mWeatherDataMapper.cacheToDomain(mWeatherDataCache.values()));
                    callback.onDataLoaded(requestResult);
                }

                @Override
                public void onError(Throwable e) {

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
