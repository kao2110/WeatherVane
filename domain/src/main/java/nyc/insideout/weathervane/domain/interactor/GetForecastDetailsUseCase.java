package nyc.insideout.weathervane.domain.interactor;

import nyc.insideout.weathervane.domain.model.ForecastDetail;
import nyc.insideout.weathervane.domain.repository.WeatherRepository;
import nyc.insideout.weathervane.domain.repository.WeatherRepository.DataRequestCallback;


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


    /*
    * Class used to carry request parameters to GetForecastDetailsUseCase
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

    /*
    Class used to carry results from of request made by GetForecastUseCase
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
