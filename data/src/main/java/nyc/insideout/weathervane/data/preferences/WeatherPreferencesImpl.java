package nyc.insideout.weathervane.data.preferences;


import android.content.Context;
import android.content.SharedPreferences;

public class WeatherPreferencesImpl implements WeatherPreferences {

    private static String PREFERENCES_NAME = "weatherPrefs";
    private static String LAST_SEARCH_TIME = "lastSearchTime";
    private static String LAST_SEARCH_LOCATION = "lastSearchLocation";
    private SharedPreferences mWeatherPreferences;

    public WeatherPreferencesImpl(Context context){
        mWeatherPreferences = context.getSharedPreferences(PREFERENCES_NAME, 0);
    }

    @Override
    public long getLastSearchTime() {
        return mWeatherPreferences.getLong(LAST_SEARCH_TIME, 0);
    }

    @Override
    public void setLastSearchTime(long searchTime) {
        SharedPreferences.Editor spe = mWeatherPreferences.edit();
        spe.putLong(LAST_SEARCH_TIME, searchTime);
        spe.apply();
    }

    @Override
    public String getLastSearchLocation() {
       return mWeatherPreferences.getString(LAST_SEARCH_LOCATION, "New York");
    }

    @Override
    public void setLastSearchLocation(String location) {
        SharedPreferences.Editor spe = mWeatherPreferences.edit();
        spe.putString(LAST_SEARCH_LOCATION, location);
        spe.apply();
    }
}
