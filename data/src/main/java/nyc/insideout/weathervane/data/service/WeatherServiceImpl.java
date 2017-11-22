package nyc.insideout.weathervane.data.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import nyc.insideout.weathervane.data.service.model.ApiWeatherData;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Calls used to make calls to api.
 * With more time would add unit tests.
 * Also would do more extensive error handling.
 */
public class WeatherServiceImpl implements WeatherService<ApiWeatherData> {

    Retrofit mRetroFit;
    private static final Logger LOGGER = Logger.getLogger(WeatherServiceImpl.class.getName());

    // With more time the user would be able to set these variables via a Preferences Activity.
    // their values would be stored/retrieved from the WeatherPreferencesImpl class
    private static final String UNITS = "imperial";
    private static final String NUM_DAYS = "10";

    // The API_KEY is stored here only for demo purposes
    private static final String API_KEY = "0b5e4f9622c47c2e633e8c3ed8dc4e28";

    public WeatherServiceImpl(Retrofit retrofit){
        mRetroFit = retrofit;
    }

    public void fetchForecast(String location, WeatherServiceCallback<ApiWeatherData> callback){
        Call<ApiWeatherData> apiCall = buildForecastListCall(location);

        queryForecastData(location, apiCall, callback);

    }

    private Call<ApiWeatherData> buildForecastListCall(String location){
        WeatherApiEndpoints endpoint = mRetroFit.create(WeatherApiEndpoints.class);

        return endpoint.getForecastList(location, UNITS, NUM_DAYS, API_KEY);
    }

    private void queryForecastData(String location, Call<ApiWeatherData> forecastQuery,
                                   final WeatherServiceCallback<ApiWeatherData> callback){

        try{
            Response<ApiWeatherData> response = forecastQuery.execute();
            if(response.isSuccessful()){
                LOGGER.log(Level.INFO, "API Response successful");
                ApiWeatherData apiWeatherData = response.body();
                if(apiWeatherData == null) {
                    LOGGER.log(Level.WARNING, "API Response is Null");
                    Throwable throwable = new Throwable("Error Retrieving Forecast Data");
                    callback.onError(throwable);
                    return;
                }
                LOGGER.log(Level.INFO, "Data for " + apiWeatherData.getApiForecastList().size() + " downloaded");
                callback.onDataLoaded(apiWeatherData);
                return;
            }
            LOGGER.log(Level.SEVERE, "API Response Fail");
            Throwable throwable = new Throwable("Unable to find Forecast data for: " + location);
            callback.onError(throwable);

        }catch (Exception e){
            LOGGER.log(Level.SEVERE, e.getMessage());
            Throwable throwable = new Throwable("Please check your internet connection :)");
            callback.onError(throwable);
        }
    }
}
