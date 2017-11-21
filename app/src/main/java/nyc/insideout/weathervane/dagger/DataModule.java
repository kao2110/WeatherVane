package nyc.insideout.weathervane.dagger;

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
    WeatherService provideWeatherService(){
        return new WeatherServiceImpl();
    }

    @Singleton
    @Provides
    WeatherRepository provideWeatherRepository(WeatherService weatherService,
                                               WeatherPreferences weatherPreferences, WeatherDataMapper weatherDataMapper){
        return new WeatherRepositoryImpl(weatherService, weatherPreferences, weatherDataMapper);
    }
}
