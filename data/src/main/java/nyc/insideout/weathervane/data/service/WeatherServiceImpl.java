package nyc.insideout.weathervane.data.service;

import nyc.insideout.weathervane.data.service.model.ApiWeatherData;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Calls used to make calls to api.
 * With more time would add unit tests.
 */
public class WeatherServiceImpl implements WeatherService<ApiWeatherData> {

    Retrofit mRetroFit;

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

        queryForecastData(apiCall, callback);

    }

    private Call<ApiWeatherData> buildForecastListCall(String location){
        WeatherApiEndpoints endpoint = mRetroFit.create(WeatherApiEndpoints.class);

        return endpoint.getForecastList(location, UNITS, NUM_DAYS, API_KEY);
    }

    private void queryForecastData(Call<ApiWeatherData> forecastQuery, final WeatherServiceCallback<ApiWeatherData> callback){

    }
}
