package nyc.insideout.weathervane.data.preferences;


import android.content.Context;
import android.content.SharedPreferences;

/**
 * This class persists application data which is easily stored in key-value pairs
 * With more time a Preference Activity and this class would be used to store user preferences
 */
public class WeatherPreferencesImpl implements WeatherPreferences {

    private static String PREFERENCES_NAME = "weatherPrefs";
    private static String LAST_SEARCH_TIME = "lastSearchTime";
    private static String LAST_SEARCH_LOCATION = "lastSearchLocation";
    private SharedPreferences mWeatherPreferences;

    public WeatherPreferencesImpl(Context context){
        mWeatherPreferences = context.getSharedPreferences(PREFERENCES_NAME, 0);
    }

    // retrieve last search time to determine if cached data is stale
    @Override
    public long getLastSearchTime() {
        return mWeatherPreferences.getLong(LAST_SEARCH_TIME, 0);
    }

    // store last search time to determine if cached data is stale
    @Override
    public void setLastSearchTime(long searchTime) {
        SharedPreferences.Editor spe = mWeatherPreferences.edit();
        spe.putLong(LAST_SEARCH_TIME, searchTime);
        spe.apply();
    }

    // retrieve last search location to display data when app starts
    // defaults to new york...with more time would obtain ask to obtain user's current location
    // instead
    @Override
    public String getLastSearchLocation() {
       return mWeatherPreferences.getString(LAST_SEARCH_LOCATION, "New York");
    }

    // store last search location to display data when app starts
    @Override
    public void setLastSearchLocation(String location) {
        SharedPreferences.Editor spe = mWeatherPreferences.edit();
        spe.putString(LAST_SEARCH_LOCATION, location);
        spe.apply();
    }
}
