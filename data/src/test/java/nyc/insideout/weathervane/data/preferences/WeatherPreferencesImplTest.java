package nyc.insideout.weathervane.data.preferences;

import android.content.Context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;


@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class WeatherPreferencesImplTest {

    private WeatherPreferencesImpl weatherPreferences;

    @Before
    public void setup(){
        Context context = RuntimeEnvironment.application.getApplicationContext();
        weatherPreferences = new WeatherPreferencesImpl(context);
    }

    @Test
    public void whenLastSearchTimeSaved_ThenReturnValue(){
        weatherPreferences.setLastSearchTime(123456);

        final long time = weatherPreferences.getLastSearchTime();

        Assert.assertEquals(123456, time);
    }

    @Test
    public void whenLastSearchLocationSave_ThenReturnValue(){
        weatherPreferences.setLastSearchLocation("Ytown");
        String location = weatherPreferences.getLastSearchLocation();
        Assert.assertEquals("Ytown", location);
    }
}
