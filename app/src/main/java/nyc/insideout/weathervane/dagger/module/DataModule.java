package nyc.insideout.weathervane.dagger.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import nyc.insideout.weathervane.data.WeatherRepositoryImpl;
import nyc.insideout.weathervane.data.mapper.WeatherDataMapper;
import nyc.insideout.weathervane.data.mapper.WeatherDataMapperImpl;
import nyc.insideout.weathervane.data.preferences.WeatherPreferences;
import nyc.insideout.weathervane.data.preferences.WeatherPreferencesImpl;
import nyc.insideout.weathervane.data.service.WeatherService;
import nyc.insideout.weathervane.data.service.WeatherServiceImpl;
import nyc.insideout.weathervane.domain.repository.WeatherRepository;
import retrofit2.Retrofit;

/**
 *  This module provides the dependencies used by the WeatherRepositoryImpl
 */
@Module
public class DataModule {

    @Singleton
    @Provides
    WeatherDataMapper provideWeatherDataMapper(){
        return new WeatherDataMapperImpl();
    }

    @Singleton
    @Provides
    WeatherPreferences provideWeatherPreferences(Context context){
        return new WeatherPreferencesImpl(context);
    }

    @Singleton
    @Provides
    WeatherService provideWeatherService(Retrofit retrofit){
        return new WeatherServiceImpl(retrofit);
    }

    @Singleton
    @Provides
    WeatherRepository provideWeatherRepository(WeatherService weatherService,
                                               WeatherPreferences weatherPreferences, WeatherDataMapper weatherDataMapper){
        return new WeatherRepositoryImpl(weatherService, weatherPreferences, weatherDataMapper);
    }
}
