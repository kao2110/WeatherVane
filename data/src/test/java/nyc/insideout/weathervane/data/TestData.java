package nyc.insideout.weathervane.data;


import android.util.ArrayMap;

import java.util.ArrayList;
import java.util.List;

import nyc.insideout.weathervane.data.database.model.WeatherData;
import nyc.insideout.weathervane.data.service.model.ApiCity;
import nyc.insideout.weathervane.data.service.model.ApiForecastDescription;
import nyc.insideout.weathervane.data.service.model.ApiForecastItem;
import nyc.insideout.weathervane.data.service.model.ApiLatLong;
import nyc.insideout.weathervane.data.service.model.ApiTemperature;
import nyc.insideout.weathervane.data.service.model.ApiWeatherData;

public class TestData {

    public static final int CITY_ID = 789;
    public static final String CITY_NAME = "Ytown";
    public static final String CITY_COUNTRY = "USA";
    public static final int CITY_POP = 456784;
    public static final double LONGITUDE = 78.6598;
    public static final double LATITUDE = 35.6987;

    public static final double TEMP_DAY = 45.0;
    public static final double TEMP_MIN = 25.0;
    public static final double TEMP_MAX = 45.0;
    public static final double TEMP_NIGHT = 30.0;
    public static final double TEMP_EVE = 42.0;
    public static final double TEMP_MORN = 32.0;
    public static final long DATE = 1510881000;
    public static final double PRESSURE = 18.5;
    public static final int HUMIDITY = 26;
    public static final double SPEED = 34.6;
    public static final int WIND_DIRECTION = 4;
    public static final int CLOUDS = 78;
    public static final double RAIN = 63.2;
    public static final int FORECAST_ID = 234;
    public static final String FORECAST_ICON = "04d";

    public static final String MAIN_DESC = "cloudy";
    public static final String DETAIL_DESC = "scattered clouds";

    public static ApiWeatherData getApiWeatherData(){
        ApiTemperature temperature = new ApiTemperature();
        temperature.setDay(TEMP_DAY);
        temperature.setMin(TEMP_MIN);
        temperature.setMax(TEMP_MAX);
        temperature.setNight(TEMP_NIGHT);
        temperature.setEve(TEMP_EVE);
        temperature.setMorn(TEMP_MORN);

        ApiForecastDescription forecastDescription = new ApiForecastDescription();
        forecastDescription.setId(FORECAST_ID);
        forecastDescription.setMain(MAIN_DESC);
        forecastDescription.setDescription(DETAIL_DESC);
        forecastDescription.setIcon(FORECAST_ICON);
        List<ApiForecastDescription> forecastDescriptionList = new ArrayList<>();
        forecastDescriptionList.add(forecastDescription);

        ApiForecastItem apiForecastItem = new ApiForecastItem();
        apiForecastItem.setDate(DATE);
        apiForecastItem.setApiTemperature(temperature);
        apiForecastItem.setPressure(PRESSURE);
        apiForecastItem.setHumidity(HUMIDITY);
        apiForecastItem.setDescription(forecastDescriptionList);
        apiForecastItem.setSpeed(SPEED);
        apiForecastItem.setDeg(WIND_DIRECTION);
        apiForecastItem.setClouds(CLOUDS);
        apiForecastItem.setRain(RAIN);
        List<ApiForecastItem> forecastItemList = new ArrayList<>();
        forecastItemList.add(apiForecastItem);

        ApiLatLong latLong = new ApiLatLong();
        latLong.setLat(LATITUDE);
        latLong.setLon(LONGITUDE);

        ApiCity apiCity = new ApiCity();
        apiCity.setId(CITY_ID);
        apiCity.setName(CITY_NAME);
        apiCity.setApiLatLong(latLong);
        apiCity.setCountry(CITY_COUNTRY);
        apiCity.setPopulation(CITY_POP);

        ApiWeatherData apiWeatherData = new ApiWeatherData();
        apiWeatherData.setApiCity(apiCity);
        apiWeatherData.setApiForecastList(forecastItemList);
        return apiWeatherData;
    }

    public static ArrayMap<Long, WeatherData> getArrayMap(){
        WeatherData data = new WeatherData(CITY_ID, CITY_NAME, DATE, HUMIDITY, TEMP_MAX, TEMP_MIN, PRESSURE, WIND_DIRECTION, SPEED, MAIN_DESC, DETAIL_DESC, FORECAST_ID, FORECAST_ICON);

        ArrayMap<Long, WeatherData> map = new ArrayMap<>();
        map.put(1L, data);
        return map;
    }
}
