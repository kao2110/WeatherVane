package nyc.insidout.weathervane.data.service;


public interface WeatherService {

    void fetchForecast(String location, WeatherServiceCallback callback);

    /* Interface used to communicate results for data requests made to WeatherService */
    public interface WeatherServiceCallback<T>{

        void onDataLoaded(T result);

        void onError(Throwable e);
    }
}
