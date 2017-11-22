package nyc.insideout.weathervane.ui.mapper;


import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

import nyc.insideout.weathervane.domain.model.Forecast;
import nyc.insideout.weathervane.domain.model.ForecastDetail;
import nyc.insideout.weathervane.ui.model.ForecastDetailViewModel;
import nyc.insideout.weathervane.ui.model.ForecastViewModel;

public class ForecastDataMapperImplTest {

    @Mock
    private DataFormatter dataFormatter;
    private ForecastDataMapperImpl forecastDataMapper;

    private static final double TEST_EPSILON = 0.001;
    private static final double TEMP_MIN = 45.0;
    private static final double TEMP_MIN_2 = 25.0;
    private static final double TEMP_MAX = 45.0;
    private static final double TEMP_MAX_2 = 45.0;
    private static final long DATE = 1510881000;
    private static final long DATE_2 = 1510881587;
    private static final int HUMIDITY = 26;
    private static final int FORECAST_ID = 234;
    private static final int FORECAST_ID_2 = 234783;
    private static final String FORECAST_ICON = "04d";
    private static final String FORECAST_ICON2 = "14d";
    private static final String DESC = "cloudy";
    private static final String DESC_2 = "sunny";
    private static final String DETAIL_DESC = "scattered clouds";

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        forecastDataMapper = new ForecastDataMapperImpl(dataFormatter);
    }

    @Test
    public void givenListOfForecastItems_ThenReturnListOfForecastViewModel(){
        Forecast forecast = new Forecast(DATE, TEMP_MAX, TEMP_MIN, FORECAST_ID, DESC, FORECAST_ICON);
        Forecast forecast2 = new Forecast(DATE_2, TEMP_MAX_2, TEMP_MIN_2, FORECAST_ID_2, DESC_2, FORECAST_ICON2);
        List<Forecast> domainList = new ArrayList<>();
        domainList.add(forecast);
        domainList.add(forecast2);

        List<ForecastViewModel> viewList = forecastDataMapper.domainToForecastList(domainList);

        Assert.assertNotNull(viewList);
        assertEquals(domainList.size(), viewList.size());
    }

    @Test
    public void givenForecastDetailItem_ThenReturnForecastDetailViewModel(){
        ForecastDetail forecastDetail = new ForecastDetail(DATE, TEMP_MAX, TEMP_MIN, FORECAST_ID, DESC, HUMIDITY, DETAIL_DESC, FORECAST_ICON);

        ForecastDetailViewModel viewModel = forecastDataMapper.domainToDetailViewModel(forecastDetail);

        Assert.assertNotNull(viewModel);

    }

}
