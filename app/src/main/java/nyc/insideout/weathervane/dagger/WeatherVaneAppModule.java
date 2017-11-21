package nyc.insideout.weathervane.dagger;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class WeatherVaneAppModule {

    @Singleton
    @Provides
    Context provideContext(Application application){
        return application;
    }
}
