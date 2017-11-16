package nyc.insideout.weathervane.domain.interactor;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import nyc.insidout.weathervane.domain.interactor.GetForecastUseCase;
import nyc.insidout.weathervane.domain.interactor.GetForecastUseCase.RequestParam;
import nyc.insidout.weathervane.domain.interactor.GetForecastUseCase.RequestResult;
import nyc.insidout.weathervane.domain.interactor.UseCase;
import nyc.insidout.weathervane.domain.model.Forecast;
import nyc.insidout.weathervane.domain.repository.WeatherRepository;
import nyc.insidout.weathervane.domain.repository.WeatherRepository.DataRequestCallback;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/*
* Unit tests for GetForecastUseCase
*/
public class GetForecastUseCaseTest {

    @Captor
    private ArgumentCaptor<DataRequestCallback<RequestResult>> mDataRequestCallbackCaptor;

    @Mock
    private WeatherRepository mWeatherRepository;

    @Mock
    private UseCase.UseCaseCallback<RequestResult> mUseCaseCallback;

    private GetForecastUseCase mGetForecastUseCase;
    private List<Forecast> mForecastList;
    private String mLocation;
    private RequestParam mParam;

    @Before
    public void setupGetForecastUsecase(){
        MockitoAnnotations.initMocks(this);
        mGetForecastUseCase = new GetForecastUseCase(mWeatherRepository);

        mForecastList =
                Arrays.asList(new Forecast(123, 45, 32, 12, "Cloudy"),
                        new Forecast(124, 46, 34, 13, "Sunny"),
                        new Forecast(125, 47, 35, 14, "Rain"));

        mLocation = "Ytown";
        mParam = new RequestParam(mLocation);
    }

    @Test
    public void whenLocationIsValidThenReturnListOfForecast(){
        RequestResult result = new RequestResult(mLocation, mForecastList);

        //when forecast is requested
        mGetForecastUseCase.execute(mParam, mUseCaseCallback);

        //callback values captured
        verify(mWeatherRepository).getForecast(eq(mLocation), mDataRequestCallbackCaptor.capture());
        mDataRequestCallbackCaptor.getValue().onDataLoaded(result);
        ArgumentCaptor<RequestResult> resultArgumentCaptor = ArgumentCaptor.forClass(RequestResult.class);

        //then results are sent back through UseCaseCallback.
        verify(mUseCaseCallback).onComplete(resultArgumentCaptor.capture());
        assertTrue(resultArgumentCaptor.getValue().getForecastList().size() == 3);
        assertEquals(mLocation, resultArgumentCaptor.getValue().getLocation());
    }

    @Test
    public void whenErrorOccursThenReturnError(){
        String msg = "Unable to complete request";
        Throwable e = new Throwable(msg);

        //when forecast is requested
        mGetForecastUseCase.execute(mParam, mUseCaseCallback);

        //callback values captured
        verify(mWeatherRepository).getForecast(eq(mLocation), mDataRequestCallbackCaptor.capture());
        mDataRequestCallbackCaptor.getValue().onError(e);
        ArgumentCaptor<Throwable> errorArgumentCaptor = ArgumentCaptor.forClass(Throwable.class);

        //then error is sent back through UseCaseCallback.
        verify(mUseCaseCallback).onError(errorArgumentCaptor.capture());
        assertEquals(msg, errorArgumentCaptor.getValue().getMessage());
    }
}
