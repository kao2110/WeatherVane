package nyc.insidout.weathervane.data;

import nyc.insidout.weathervane.domain.interactor.GetForecastDetailsUseCase;
import nyc.insidout.weathervane.domain.interactor.GetForecastUseCase;
import nyc.insidout.weathervane.domain.repository.WeatherRepository;


public class WeatherRepositoryImpl implements WeatherRepository {

    @Override
    public void getForecast(String location, DataRequestCallback<GetForecastUseCase.RequestResult> callback){

    }

    @Override
    public void getForecastDetails(long date, DataRequestCallback<GetForecastDetailsUseCase.RequestResult> callback){

    }

    @Override
    public void setRecentSearchLocation(String location){

    }

    @Override
    public String getRecentSearchLocation(){
        return null;
    }
}
