package nyc.insideout.weathervane.data.preferences;


import android.content.Context;
import android.content.SharedPreferences;

/**
 * Mock implementation of class which is used to stored key-value pairs
 */
public class WeatherPreferencesImpl implements WeatherPreferences {

    public WeatherPreferencesImpl(Context context){}

    // retrieve last search time to determine if cached data is stale
    @Override
    public long getLastSearchTime() {
        return 123456L;
    }

    // store last search time to determine if cached data is stale
    @Override
    public void setLastSearchTime(long searchTime) {}

    // retrieve last search location to display data when app starts
    // defaults to new york...with more time would obtain ask to obtain user's current location
    // instead
    @Override
    public String getLastSearchLocation() {
       return "Ytown";
    }

    // store last search location to display data when app starts
    @Override
    public void setLastSearchLocation(String location) {}
}
