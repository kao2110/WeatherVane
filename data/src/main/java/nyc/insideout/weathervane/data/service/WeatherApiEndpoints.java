package nyc.insideout.weathervane.data.service;


import nyc.insideout.weathervane.data.service.model.ApiWeatherData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Interface used by Retrofit to define the api endpoint where data is retrieved.
 */
public interface WeatherApiEndpoints {

    @GET("forecast/daily?")
    Call<ApiWeatherData> getForecastList(@Query("q") String city,
                                         @Query("units") String units,
                                         @Query("cnt") String numDays,
                                         @Query("appid") String apiKey);
}
