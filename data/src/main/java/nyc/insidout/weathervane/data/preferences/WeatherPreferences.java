package nyc.insidout.weathervane.data.preferences;


public interface WeatherPreferences {

    long getLastSearchTime();

    void setLastSearchTime(long searchTime);

    String getLastSearchLocation();

    void setLastSearchLocation(String location);
}
