package nyc.insideout.weathervane.data.service;


public interface WeatherService<T> {

    void fetchForecast(String location, WeatherServiceCallback<T> callback);

    /* Interface used to communicate results for data requests made to WeatherService */
    public interface WeatherServiceCallback<T>{

        void onDataLoaded(T result);

        void onError(Throwable e);
    }
}
