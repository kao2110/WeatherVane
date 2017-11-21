package nyc.insideout.weathervane;


import android.os.StrictMode;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import nyc.insideout.weathervane.dagger.component.DaggerWeatherVaneAppComponent;
import nyc.insideout.weathervane.dagger.component.WeatherVaneAppComponent;


public class WeatherVaneApplication extends DaggerApplication{

    @Override public void onCreate() {

        // add StrictMode if using debug build
        if(BuildConfig.DEBUG){ setupStrictMode();}
        super.onCreate();
        setupLeakCanary();
    }


    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector(){
        WeatherVaneAppComponent component =  DaggerWeatherVaneAppComponent.builder().application(this).build();
        component.inject(this);
        return component;
    }


    // helper method used to setup LeakCanary. Check LeakCanary documentation for details.
    protected RefWatcher setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return RefWatcher.DISABLED;
        }
        return LeakCanary.install(this);
    }

    private void setupStrictMode(){
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .penaltyDeath()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectActivityLeaks()
                .detectLeakedClosableObjects()
                .detectLeakedRegistrationObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());
    }
}
