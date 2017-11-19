package nyc.insideout.weathervane;


import com.squareup.leakcanary.RefWatcher;

/**
 * This test application instance is necessary to make sure LeakCanary doesn't operate during unit
 * tests.
 */
public class TestWeatherVaneApplication extends WeatherVaneApplication {

    @Override
    protected RefWatcher setupLeakCanary() {
        // No LeakCanary in unit tests.
        return RefWatcher.DISABLED;
    }
}
