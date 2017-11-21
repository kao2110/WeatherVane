package nyc.insideout.weathervane.data;

import android.support.v4.util.ArrayMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import nyc.insideout.weathervane.data.database.model.WeatherData;
import nyc.insideout.weathervane.data.mapper.WeatherDataMapper;
import nyc.insideout.weathervane.data.preferences.WeatherPreferences;
import nyc.insideout.weathervane.data.service.WeatherService;
import nyc.insideout.weathervane.data.service.model.ApiWeatherData;
import nyc.insideout.weathervane.domain.interactor.GetForecastDetailsUseCase;
import nyc.insideout.weathervane.domain.interactor.GetForecastUseCase;
import nyc.insideout.weathervane.domain.repository.WeatherRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class WeatherRepositoryImplTest {

    @Captor
    private ArgumentCaptor<WeatherService.WeatherServiceCallback<ApiWeatherData>> weatherServiceCallbackCaptor;

    @Mock
    private WeatherRepository.DataRequestCallback<GetForecastUseCase.RequestResult> forecastListDataRequestCallback;

    @Mock
    private WeatherRepository.DataRequestCallback<GetForecastDetailsUseCase.RequestResult> forecastDetailsDataRequestCallback;

    @Mock
    private WeatherService weatherService;

    @Mock
    private ArrayMap<Long, WeatherData> cache;

    @Mock
    private ApiWeatherData apiWeatherData;

    @Mock
    private WeatherPreferences weatherPreferences;

    @Mock
    private WeatherDataMapper weatherDataMapper;

    private WeatherRepository weatherRepository;
    private String location = "Ytown";


    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        apiWeatherData = TestData.getApiWeatherData();
        doReturn(cache).when(weatherDataMapper).apiToCache(apiWeatherData);
        weatherRepository = new WeatherRepositoryImpl(weatherService, weatherPreferences, weatherDataMapper);
    }

    @Test
    public void whenCacheIsEmpty_ThenRequestForecastListFromService(){
        // When forecast data requested from repository first time
        weatherRepository.getForecast(location, forecastListDataRequestCallback);

        // Then forecast data requested from repository
        verify(weatherService).fetchForecast(eq(location), any(WeatherService.WeatherServiceCallback.class));
    }

    @Test
    public void whenCacheHasData_ThenRequestForecastListFromCache(){
        // When forecast data requested from repository first time
        weatherRepository.getForecast(location, forecastListDataRequestCallback);
        verify(weatherService).fetchForecast(eq(location), weatherServiceCallbackCaptor.capture());
        weatherServiceCallbackCaptor.getValue().onDataLoaded(apiWeatherData);

        // Then on second request data requested from cache
        doReturn(location).when(weatherPreferences).getLastSearchLocation();
        weatherRepository.getForecast(location, forecastListDataRequestCallback);
        verify(weatherService).fetchForecast(eq(location), any(WeatherService.WeatherServiceCallback.class));
        verify(cache, times(2)).values();
    }

    @Test
    public void whenCacheHasDataAndStale_ThenRequestForecastListFromService(){
        // When forecast data requested from repository first time
        weatherRepository.getForecast(location, forecastListDataRequestCallback);
        verify(weatherService).fetchForecast(eq(location), weatherServiceCallbackCaptor.capture());
        weatherServiceCallbackCaptor.getValue().onDataLoaded(apiWeatherData);

        // Then on second request with new location data requested from service
        doReturn("New Location").when(weatherPreferences).getLastSearchLocation();
        weatherRepository.getForecast(location, forecastListDataRequestCallback);
        verify(weatherService, times(2)).fetchForecast(eq(location), any(WeatherService.WeatherServiceCallback.class));
        verify(cache).values();
    }

    @Test
    public void whenCacheHasData_ThenRequestForecastDetailsFromCache(){
        // Given forecast data has already been requested
        weatherRepository.getForecast(location, forecastListDataRequestCallback);
        verify(weatherService).fetchForecast(eq(location), weatherServiceCallbackCaptor.capture());
        weatherServiceCallbackCaptor.getValue().onDataLoaded(apiWeatherData);

        // When forecast details are requested
        weatherRepository.getForecastDetails(100L, forecastDetailsDataRequestCallback);

        // Then data obtained from cache
        verify(cache).get(100L);
    }
}
