package nyc.insideout.weathervane.domain.interactor;

import nyc.insideout.weathervane.domain.model.ForecastDetail;
import nyc.insideout.weathervane.domain.repository.WeatherRepository;
import nyc.insideout.weathervane.domain.repository.WeatherRepository.DataRequestCallback;

/**
 * This UseCase is responsible for fetching the Forecast details be displayed.
 */

public class GetForecastDetailsUseCase implements UseCase<GetForecastDetailsUseCase.RequestParam,
        GetForecastDetailsUseCase.RequestResult> {

    WeatherRepository mWeatherRepository;

    public GetForecastDetailsUseCase(WeatherRepository weatherRepository){
        this.mWeatherRepository = weatherRepository;
    }

    @Override
    public void execute(RequestParam param, final UseCaseCallback<RequestResult> callback){

        mWeatherRepository.getForecastDetails(param.getDate(), new DataRequestCallback<RequestResult>() {
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
        private final long date;

        public RequestParam(final long date){
            this.date = date;
        }

        public long getDate(){
            return date;
        }
    }

    /**
     * This class is used to wrap the results produced by execution of this UseCase
     */
    public static final class RequestResult implements UseCase.RequestResult{
        private final ForecastDetail forecastDetail;

        public RequestResult(final ForecastDetail forecastDetail){
            this.forecastDetail = forecastDetail;
        }

        public ForecastDetail getForecastDetail(){
            return forecastDetail;
        }
    }
}
