package nyc.insideout.weathervane.data;

import nyc.insideout.weathervane.data.preferences.WeatherPreferences;
import nyc.insideout.weathervane.data.service.WeatherService;
import nyc.insideout.weathervane.domain.interactor.GetForecastDetailsUseCase;
import nyc.insideout.weathervane.domain.interactor.GetForecastUseCase;
import nyc.insideout.weathervane.domain.repository.WeatherRepository;


public class WeatherRepositoryImpl implements WeatherRepository {

    WeatherService mWeatherService;
    WeatherPreferences mWeatherPreferences;

    public WeatherRepositoryImpl(WeatherService weatherService,
                                 WeatherPreferences weatherPreferences){
        this.mWeatherService = weatherService;
        this.mWeatherPreferences = weatherPreferences;
    }

    @Override
    public void getForecast(String location, DataRequestCallback<GetForecastUseCase.RequestResult> callback){

    }

    @Override
    public void getForecastDetails(long date, DataRequestCallback<GetForecastDetailsUseCase.RequestResult> callback){

    }

    private void setRecentSearchLocation(String location){

    }

    private String getRecentSearchLocation(){
        return null;
    }
}
