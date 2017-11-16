package nyc.insidout.weathervane.domain.interactor;


import java.util.List;

import nyc.insidout.weathervane.domain.model.Forecast;
import nyc.insidout.weathervane.domain.repository.WeatherRepository;
import nyc.insidout.weathervane.domain.repository.WeatherRepository.DataRequestCallback;

public class GetForecastUseCase implements UseCase<GetForecastUseCase.RequestParam,
        GetForecastUseCase.RequestResult> {

    WeatherRepository mWeatherRepository;

    public GetForecastUseCase(WeatherRepository weatherRepository){
        mWeatherRepository = weatherRepository;
    }

    public void execute(RequestParam params, final UseCaseCallback<RequestResult> callback){
        mWeatherRepository.getForecast(params.location, new DataRequestCallback<RequestResult>() {
            @Override
            public void onDataLoaded(RequestResult result) {
               callback.onComplete(result);
            }

            @Override
            public void onError(Throwable e) {
                callback.onError(e);
            }
        });
    }

    public static final class RequestParam implements UseCase.RequestParam{

        private final String location;

        public RequestParam(final String location){
            this.location = location;
        }

        public String getLocation(){
            return location;
        }
    }

    public static final class RequestResult implements UseCase.RequestResult{

        private final List<Forecast> forecastList;
        private final String location;

        public RequestResult(final String location, final List<Forecast> items){
            this.location = location;
            this.forecastList = items;
        }

        public String getLocation(){
            return location;
        }

        public List<Forecast> getForecastList(){
            return forecastList;
        }
    }
}
