package nyc.insideout.weathervane.domain.interactor;


import java.util.List;

import nyc.insideout.weathervane.domain.model.Forecast;
import nyc.insideout.weathervane.domain.repository.WeatherRepository;
import nyc.insideout.weathervane.domain.repository.WeatherRepository.DataRequestCallback;

/**
 * This UseCase is responsible for fetching the list of Forecast items to be displayed in a list.
 */

public class GetForecastUseCase implements UseCase<GetForecastUseCase.RequestParam,
        GetForecastUseCase.RequestResult> {

    WeatherRepository mWeatherRepository;

    public GetForecastUseCase(WeatherRepository weatherRepository){
        mWeatherRepository = weatherRepository;
    }

    @Override
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


    /**
     * This class is used to wrap the request parameters sent to this UseCase
     */
    public static final class RequestParam implements UseCase.RequestParam{

        private final String location;

        public RequestParam(final String location){
            this.location = location;
        }

        public String getLocation(){
            return location;
        }
    }

    /**
     * This class is used to wrap the results produced by execution of this UseCase
     */
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
