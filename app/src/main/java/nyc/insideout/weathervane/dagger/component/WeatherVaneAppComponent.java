package nyc.insideout.weathervane.dagger.component;


import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import nyc.insideout.weathervane.WeatherVaneApplication;

/**
 * This Dagger Component is responsible for allowing Android Components(i.e. Activities, Services, etc.)
 * to be injected with their declared dependencies.
 */
@Singleton
@Component(modules = {AndroidSupportInjectionModule.class})
public interface WeatherVaneAppComponent extends AndroidInjector<WeatherVaneApplication>{

    void inject(WeatherVaneApplication application);

    @Component.Builder
    interface Builder {
        @BindsInstance
        WeatherVaneAppComponent.Builder application(Application application);

        WeatherVaneAppComponent build();
    }
}
