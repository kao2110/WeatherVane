package nyc.insideout.weathervane.domain.repository;


import nyc.insideout.weathervane.domain.interactor.GetForecastDetailsUseCase;
import nyc.insideout.weathervane.domain.interactor.GetForecastUseCase;

public interface WeatherRepository {

    void getForecast(String location, DataRequestCallback<GetForecastUseCase.RequestResult> callback);

    void getForecastDetails(long date, DataRequestCallback<GetForecastDetailsUseCase.RequestResult> callback);

    /* Interface used to communicate results for data requests made to repository */
    public interface DataRequestCallback<R>{

        void onDataLoaded(R result);

        void onError(Throwable e);
    }
}
