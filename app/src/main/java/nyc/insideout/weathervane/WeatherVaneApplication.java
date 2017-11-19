package nyc.insideout.weathervane;


import android.app.Application;
import android.os.StrictMode;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;


public class WeatherVaneApplication extends Application {

    @Override public void onCreate() {

        // add StrictMode if using debug build
        if(BuildConfig.DEBUG){ setupStrictMode();}
        super.onCreate();
        setupLeakCanary();
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
                .detectAll()
                .penaltyLog()
                .penaltyDeath()
                .build());
    }
}
