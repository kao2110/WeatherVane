package nyc.insidout.weathervane.domain.repository;


public interface WeatherRepository {

    void getForecast(String location, DataRequestCallback callback);

    void getForecastDetails(int date, DataRequestCallback callback);

    void setRecentSearchLocation(String location);

    String getRecentSearchLocation();

    /* Interface used to communicate results for data requests made to repository */
    public interface DataRequestCallback<R>{

        void onDataLoaded(R result);

        void onError(Throwable e);
    }
}
