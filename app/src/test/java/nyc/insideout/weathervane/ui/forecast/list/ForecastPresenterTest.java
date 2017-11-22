package nyc.insideout.weathervane.ui.forecast.list;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import nyc.insideout.weathervane.domain.interactor.GetForecastUseCase;
import nyc.insideout.weathervane.domain.interactor.UseCase;
import nyc.insideout.weathervane.domain.model.Forecast;
import nyc.insideout.weathervane.ui.UseCaseExecutor;
import nyc.insideout.weathervane.ui.mapper.DataFormatter;
import nyc.insideout.weathervane.ui.mapper.ForecastDataMapper;
import nyc.insideout.weathervane.ui.mapper.ForecastDataMapperImpl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

public class ForecastPresenterTest {

    @Mock
    private GetForecastUseCase getForecastUseCase;

    @Mock
    private UseCaseExecutor useCaseExecutor;

    @Mock
    private ForecastContract.View forecastView;

    @Mock
    private DataFormatter dataFormatter;

    @Captor
    private ArgumentCaptor<UseCaseExecutor.UiCallback<UseCase.RequestResult>> uiCallbackCaptor;

    private ForecastPresenter forecastPresenter;

    private String location = "Ytown";
    private static final double TEMP_MIN = 45.0;
    private static final double TEMP_MIN_2 = 25.0;
    private static final double TEMP_MAX = 45.0;
    private static final double TEMP_MAX_2 = 45.0;
    private static final long DATE = 1510881000;
    private static final long DATE_2 = 1510881587;
    private static final int FORECAST_ID = 234;
    private static final int FORECAST_ID_2 = 234783;
    public static final String FORECAST_ICON = "04d";
    public static final String FORECAST_ICON2 = "14d";
    private static final String DESC = "cloudy";
    private static final String DESC_2 = "sunny";

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        ForecastDataMapper dataMapper = new ForecastDataMapperImpl(dataFormatter);
        forecastPresenter = new ForecastPresenter(getForecastUseCase, useCaseExecutor, dataMapper, forecastView);
    }

    @Test
    public void whenPresenterStarts_ThenExecuteUseCase(){
        // when start called
        forecastPresenter.start(location);

        // then
        verify(forecastView).disableLocationEntry();
        verify(forecastView).showProgressIndicator();
        verify(useCaseExecutor).executeUseCase(eq(getForecastUseCase),
                any(GetForecastUseCase.RequestParam.class), eq(location), any(UseCaseExecutor.UiCallback.class));
    }

    @Test
    public void whenForecastLocationSubmitted_ThenExecuteUseCase(){

        forecastPresenter.onSubmitForecastLocation(location);
        verify(forecastView).disableLocationEntry();
        verify(forecastView).showProgressIndicator();
        verify(useCaseExecutor).executeUseCase(eq(getForecastUseCase),
                any(GetForecastUseCase.RequestParam.class), eq(location), any(UseCaseExecutor.UiCallback.class));
    }

    @Test
    public void whenForecastItemSelected_ThenShowDetails(){
        long date = 1293938;
        forecastPresenter.onForecastItemClicked(date);
        verify(forecastView).showForecastDetails(date);
    }

    @Test
    public void whenViewDestroyed_ThenEvictUiCallbackFromCache(){
        forecastPresenter.onViewDestroyed();
        verify(useCaseExecutor).cancelUiCallback(any(String.class));
    }

    @Test
    public void whenUseCaseExecutedSuccessfully_ThenShowForecastList(){
        // setup
        forecastPresenter.onSubmitForecastLocation(location);
        forecastPresenter.onViewActive();
        Forecast item1 = new Forecast(DATE, TEMP_MAX, TEMP_MIN, FORECAST_ID, DESC, FORECAST_ICON);
        Forecast item2 = new Forecast(DATE_2, TEMP_MAX_2, TEMP_MIN_2, FORECAST_ID_2, DESC_2, FORECAST_ICON2);
        List<Forecast> list = new ArrayList<>();
        list.add(item1);
        list.add(item2);
        GetForecastUseCase.RequestResult requestResult = new GetForecastUseCase.RequestResult(location, list);

        //capture values
        verify(useCaseExecutor).executeUseCase(eq(getForecastUseCase),
                any(GetForecastUseCase.RequestParam.class), eq(location), uiCallbackCaptor.capture());
        uiCallbackCaptor.getValue().onComplete(requestResult);
        ArgumentCaptor<List> forecastDisplayCaptor = ArgumentCaptor.forClass(List.class);

        verify(forecastView).enableLocationEntry();
        verify(forecastView).hideProgressIndicator();
        verify(forecastView).showForecastList(eq(location), forecastDisplayCaptor.capture());
        Assert.assertEquals(list.size(), forecastDisplayCaptor.getValue().size());
        verify(useCaseExecutor).cancelUiCallback(location);
    }
}
