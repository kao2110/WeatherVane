package nyc.insidout.weathervane.data.mapper;

import android.support.v4.util.ArrayMap;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

import nyc.insidout.weathervane.data.database.model.WeatherData;
import nyc.insidout.weathervane.data.service.model.ApiCity;
import nyc.insidout.weathervane.data.service.model.ApiForecastDescription;
import nyc.insidout.weathervane.data.service.model.ApiForecastItem;
import nyc.insidout.weathervane.data.service.model.ApiLatLong;
import nyc.insidout.weathervane.data.service.model.ApiTemperature;
import nyc.insidout.weathervane.data.service.model.ApiWeatherData;
import nyc.insidout.weathervane.domain.model.Forecast;
import nyc.insidout.weathervane.domain.model.ForecastDetail;

public class WeatherDataMapperImplTest {

    private WeatherDataMapperImpl mWeatherDataMapper;
    private ApiForecastItem mApiForecastItem;
    private ApiWeatherData mApiWeatherData;


    private static final double TEST_EPSILON = 0.001;
    private static final int CITY_ID = 789;
    private static final String CITY_NAME = "Ytown";
    private static final String CITY_COUNTRY = "USA";
    private static final int CITY_POP = 456784;
    private static final double LONGITUDE = 78.6598;
    private static final double LATITUDE = 35.6987;

    private static final double TEMP_DAY = 45.0;
    private static final double TEMP_MIN = 25.0;
    private static final double TEMP_MAX = 45.0;
    private static final double TEMP_NIGHT = 30.0;
    private static final double TEMP_EVE = 42.0;
    private static final double TEMP_MORN = 32.0;
    private static final long DATE = 1510881000;
    private static final double PRESSURE = 18.5;
    private static final int HUMIDITY = 26;
    private static final double SPEED = 34.6;
    private static final int WIND_DIRECTION = 4;
    private static final int CLOUDS = 78;
    private static final double RAIN = 63.2;
    private static final int FORECAST_ID = 234;
    private static final String MAIN_DESC = "cloudy";
    private static final String DETAIL_DESC = "scattered clouds";


    @Before
    public void setupWeatherDataMapperImplTest(){
        mWeatherDataMapper = new WeatherDataMapperImpl();

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
        List<ApiForecastDescription> forecastDescriptionList = new ArrayList<>();
        forecastDescriptionList.add(forecastDescription);

        mApiForecastItem = new ApiForecastItem();
        mApiForecastItem.setDate(DATE);
        mApiForecastItem.setApiTemperature(temperature);
        mApiForecastItem.setPressure(PRESSURE);
        mApiForecastItem.setHumidity(HUMIDITY);
        mApiForecastItem.setDescription(forecastDescriptionList);
        mApiForecastItem.setSpeed(SPEED);
        mApiForecastItem.setDeg(WIND_DIRECTION);
        mApiForecastItem.setClouds(CLOUDS);
        mApiForecastItem.setRain(RAIN);
        List<ApiForecastItem> forecastItemList = new ArrayList<>();
        forecastItemList.add(mApiForecastItem);

        ApiLatLong latLong = new ApiLatLong();
        latLong.setLat(LATITUDE);
        latLong.setLon(LONGITUDE);

        ApiCity apiCity = new ApiCity();
        apiCity.setId(CITY_ID);
        apiCity.setName(CITY_NAME);
        apiCity.setApiLatLong(latLong);
        apiCity.setCountry(CITY_COUNTRY);
        apiCity.setPopulation(CITY_POP);

        mApiWeatherData = new ApiWeatherData();
        mApiWeatherData.setApiCity(apiCity);
        mApiWeatherData.setApiForecastList(forecastItemList);
    }


    @Test
    public void givenApiWeatherDataReturnThenWeatherDataMap(){
        ArrayMap<Long, WeatherData> weatherDataCache = mWeatherDataMapper.apiToCache(mApiWeatherData);
        assertEquals(mApiWeatherData.getApiForecastList().size(), weatherDataCache.size());

        WeatherData weatherData = weatherDataCache.get(mApiWeatherData.getApiForecastList().get(0).getDate());

        assertEquals(CITY_ID, weatherData.getCityId());
        assertEquals(CITY_NAME, weatherData.getCityName());
        assertEquals(DATE, weatherData.getDate());
        assertEquals(HUMIDITY, weatherData.getHumidity(), TEST_EPSILON);
        assertEquals(TEMP_MAX, weatherData.getTempMax(), TEST_EPSILON);
        assertEquals(TEMP_MIN, weatherData.getTempMin(), TEST_EPSILON);
        assertEquals(PRESSURE, weatherData.getPressure(), TEST_EPSILON);
        assertEquals(WIND_DIRECTION, weatherData.getWindDirection());
        assertEquals(SPEED, weatherData.getWindSpeed(), TEST_EPSILON);
        assertEquals(MAIN_DESC, weatherData.getForecastDesc());
        assertEquals(DETAIL_DESC, weatherData.getForecastDescDetail());
        assertEquals(FORECAST_ID, weatherData.getForecastId());
    }

    @Test
    public void givenCollectionOfWeatherDataItemsThenReturnListOfForecastItems(){
        Collection<WeatherData> values = generateWeatherDataCollection();
        List<Forecast> forecastList = mWeatherDataMapper.cacheToDomain(values);

        Forecast forecast = forecastList.get(0);

        assertEquals(DATE, forecast.date);
        assertEquals(TEMP_MAX, forecast.tempMax, TEST_EPSILON);
        assertEquals(TEMP_MIN, forecast.tempMin, TEST_EPSILON);
        assertEquals(FORECAST_ID, forecast.forecastId);
        assertEquals(MAIN_DESC, forecast.desc);
    }

    @Test
    public void givenWeatherDataItemThenReturnForecastDetailItem(){
        WeatherData weatherData = generateWeatherDataItem(mApiWeatherData.getApiCity(), mApiWeatherData.getApiForecastList().get(0));

        ForecastDetail forecastDetail = mWeatherDataMapper.cacheToDomainDetail(weatherData);

        assertEquals(DATE, forecastDetail.date);
        assertEquals(TEMP_MAX, forecastDetail.tempMax, TEST_EPSILON);
        assertEquals(TEMP_MIN, forecastDetail.tempMin, TEST_EPSILON);
        assertEquals(FORECAST_ID, forecastDetail.forecastId);
        assertEquals(MAIN_DESC, forecastDetail.desc);
        assertEquals(DETAIL_DESC, forecastDetail.descDetail);
        assertEquals(HUMIDITY, forecastDetail.humidity);
    }


    private Collection<WeatherData> generateWeatherDataCollection(){
        ArrayMap<Long, WeatherData> arrayMap = new ArrayMap<>();
        for(int i = 0; i < mApiWeatherData.getApiForecastList().size(); i++){
            ApiForecastItem forecastItem = mApiWeatherData.getApiForecastList().get(i);
            arrayMap.put(forecastItem.getDate(), generateWeatherDataItem(mApiWeatherData.getApiCity(), forecastItem));
        }
        return arrayMap.values();
    }

    private WeatherData generateWeatherDataItem(ApiCity apiCity, ApiForecastItem apiForecastItem){
        return new WeatherData(apiCity.getId(), apiCity.getName(), apiForecastItem.getDate(),
                apiForecastItem.getHumidity(), apiForecastItem.getApiTemperature().getMax(),
                apiForecastItem.getApiTemperature().getMin(), apiForecastItem.getPressure(),
                apiForecastItem.getDeg(), apiForecastItem.getSpeed(), apiForecastItem.getDescription().get(0).getMain(),
                apiForecastItem.getDescription().get(0).getDescription(), apiForecastItem.getDescription().get(0).getId());
    }
}
