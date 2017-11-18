package nyc.insideout.weathervane.domain.interactor;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import nyc.insidout.weathervane.domain.interactor.GetForecastDetailsUseCase;
import nyc.insidout.weathervane.domain.interactor.GetForecastDetailsUseCase.RequestParam;
import nyc.insidout.weathervane.domain.interactor.GetForecastDetailsUseCase.RequestResult;
import nyc.insidout.weathervane.domain.interactor.UseCase;
import nyc.insidout.weathervane.domain.model.ForecastDetail;
import nyc.insidout.weathervane.domain.repository.WeatherRepository;
import nyc.insidout.weathervane.domain.repository.WeatherRepository.DataRequestCallback;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.junit.Assert.assertEquals;

public class GetForecastDetailsUseCaseTest {

    @Mock
    WeatherRepository mWeatherRepository;

    @Mock
    UseCase.UseCaseCallback<RequestResult> mUseCaseCallback;

    @Captor
    ArgumentCaptor<DataRequestCallback<RequestResult>> mDataRequestCallbackArgumentCaptor;

    private GetForecastDetailsUseCase mGetForecastDetailsUseCase;
    private long mDate;
    private RequestParam mParam;

    @Before
    public void setupGetForecastDetailsUseCase(){
        MockitoAnnotations.initMocks(this);
        mGetForecastDetailsUseCase = new GetForecastDetailsUseCase(mWeatherRepository);
        mDate = 123;
        mParam = new RequestParam(mDate);
    }

    @Test
    public void whenDateIsValidThenReturnForecastDetail(){
        ForecastDetail detail = new ForecastDetail(mDate, 45,32,
                102,"Cloudy", 54,"Partly Cloudy");

        RequestResult result = new RequestResult(detail);

        //when Forecast details are requested
        mGetForecastDetailsUseCase.execute(mParam, mUseCaseCallback);

        //capture callback values
        verify(mWeatherRepository).getForecastDetails(eq(mDate), mDataRequestCallbackArgumentCaptor.capture());
        mDataRequestCallbackArgumentCaptor.getValue().onDataLoaded(result);
        ArgumentCaptor<RequestResult> requestResultArgumentCaptor = ArgumentCaptor.forClass(RequestResult.class);

        //then results are sent back through UseCaseCallback;
        verify(mUseCaseCallback).onComplete(requestResultArgumentCaptor.capture());
        assertEquals(mDate, requestResultArgumentCaptor.getValue().getForecastDetail().date);
    }

    @Test
    public void whenForecastDetailsUnavailableReturnError(){
        String msg = "Forecast Details unavailable";
        Throwable e = new Throwable(msg);

        //when Forecast details requested are unavailable
        mGetForecastDetailsUseCase.execute(mParam, mUseCaseCallback);

        //capture callback values
        verify(mWeatherRepository).getForecastDetails(eq(mDate), mDataRequestCallbackArgumentCaptor.capture());
        mDataRequestCallbackArgumentCaptor.getValue().onError(e);
        ArgumentCaptor<Throwable> throwableArgumentCaptor = ArgumentCaptor.forClass(Throwable.class);

        //then error is sent back through UseCaseCallback;
        verify(mUseCaseCallback).onError(throwableArgumentCaptor.capture());
        assertEquals(msg, throwableArgumentCaptor.getValue().getMessage());
    }
}
