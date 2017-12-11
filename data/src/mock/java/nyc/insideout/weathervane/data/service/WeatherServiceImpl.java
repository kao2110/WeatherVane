package nyc.insideout.weathervane.data.service;

import java.util.logging.Logger;

import nyc.insideout.weathervane.data.service.model.ApiWeatherData;
import retrofit2.Retrofit;

/**
 * Used to make calls to mock api.
 * Returns mock data.
 */
public class WeatherServiceImpl implements WeatherService<ApiWeatherData> {

    Retrofit mRetroFit;
    private static final Logger LOGGER = Logger.getLogger(WeatherServiceImpl.class.getName());

    public WeatherServiceImpl(Retrofit retrofit){
        mRetroFit = retrofit;
    }

    public void fetchForecast(String location, WeatherServiceCallback<ApiWeatherData> callback) {

        // simulate network latency by putting thread to sleep for 3 seconds
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {}

        // location equals "Ytown" this is to simulate last known good location
        if (location.equalsIgnoreCase("Ytown")) {
            callback.onDataLoaded(MockData.getApiWeatherData(location));
        }
        // location equals "New York" this is to simulate a searched location
        else if (location.equalsIgnoreCase("New York")){
            callback.onDataLoaded(MockData.getApiWeatherData(location));

        }
        // location parameter not equal to "Ytown" or "New York" so simulate error response
        else{
            Throwable throwable = new Throwable("Unable to find Mock Location");
            callback.onError(throwable);
        }
    }


}
