package nyc.insideout.weathervane.dagger.module;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @Singleton
    @Provides
    @Named("api_base_url")
    String provideWeatherDataBaseUrl(){
        return "http://api.openweathermap.org/data/2.5/";
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(@Named("api_base_url") String base_url){
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(base_url)
                .build();
    }
}
