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

    public WeatherRepositoryImpl(WeatherService weatherService,
                                 WeatherPreferences weatherPreferences,
                                 WeatherDataMapper weatherDataMapper){
        mWeatherService = weatherService;
        mWeatherPreferences = weatherPreferences;
        mWeatherDataMapper = weatherDataMapper;
    }

    @Override
    public void getForecast(final String location, final DataRequestCallback<RequestResult> callback){

        if(mWeatherDataCache != null){
            RequestResult requestResult =
                    new RequestResult(location, mWeatherDataMapper.cacheToDomain(mWeatherDataCache.values()));
            callback.onDataLoaded(requestResult);
        }else{
            mWeatherService.fetchForecast(location, new WeatherService.WeatherServiceCallback<ApiWeatherData>() {
                @Override
                public void onDataLoaded(ApiWeatherData result) {
                    mWeatherDataCache = mWeatherDataMapper.apiToCache(result);
                    RequestResult requestResult =
                            new RequestResult(location, mWeatherDataMapper.cacheToDomain(mWeatherDataCache.values()));
                    callback.onDataLoaded(requestResult);
                }

                @Override
                public void onError(Throwable e) {

                }
            });
        }
    }

    @Override
    public void getForecastDetails(long date, DataRequestCallback<GetForecastDetailsUseCase.RequestResult> callback){
        if(mWeatherDataCache != null){
            ForecastDetail forecastDetail = mWeatherDataMapper.cacheToDomainDetail(mWeatherDataCache.get(date));
            GetForecastDetailsUseCase.RequestResult result = new GetForecastDetailsUseCase.RequestResult(forecastDetail);
            callback.onDataLoaded(result);
        }
    }
}
