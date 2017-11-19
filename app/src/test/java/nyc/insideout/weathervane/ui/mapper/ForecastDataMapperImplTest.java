package nyc.insideout.weathervane.ui.mapper;


import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

import nyc.insideout.weathervane.domain.model.Forecast;
import nyc.insideout.weathervane.domain.model.ForecastDetail;
import nyc.insideout.weathervane.ui.model.ForecastDetailViewModel;
import nyc.insideout.weathervane.ui.model.ForecastViewModel;

public class ForecastDataMapperImplTest {

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
    private static final String DESC = "cloudy";
    private static final String DESC_2 = "sunny";
    private static final String DETAIL_DESC = "scattered clouds";

    @Before
    public void setup(){
        forecastDataMapper = new ForecastDataMapperImpl();
    }

    @Test
    public void givenListOfForecastItems_ThenReturnListOfForecastViewModel(){
        Forecast forecast = new Forecast(DATE, TEMP_MAX, TEMP_MIN, FORECAST_ID, DESC);
        Forecast forecast2 = new Forecast(DATE_2, TEMP_MAX_2, TEMP_MIN_2, FORECAST_ID_2, DESC_2);
        List<Forecast> domainList = new ArrayList<>();
        domainList.add(forecast);
        domainList.add(forecast2);

        List<ForecastViewModel> viewList = forecastDataMapper.domainToForecastList(domainList);

        Assert.assertNotNull(viewList);
        assertEquals(domainList.size(), viewList.size());
        ForecastViewModel forecastViewModel = viewList.get(0);

        assertEquals(forecast.date, forecastViewModel.date);
        assertEquals(forecast.tempMax, forecastViewModel.tempMax, TEST_EPSILON);
        assertEquals(forecast.tempMin, forecastViewModel.tempMin, TEST_EPSILON);
        assertEquals(forecast.forecastId, forecastViewModel.forecastId);
        assertEquals(forecast.desc, forecastViewModel.desc);
    }

    @Test
    public void givenForecastDetailItem_ThenReturnForecastDetailViewModel(){
        ForecastDetail forecastDetail = new ForecastDetail(DATE, TEMP_MAX, TEMP_MIN, FORECAST_ID, DESC, HUMIDITY, DETAIL_DESC);

        ForecastDetailViewModel viewModel = forecastDataMapper.domainToDetailViewModel(forecastDetail);

        Assert.assertNotNull(viewModel);
        assertEquals(forecastDetail.date, viewModel.date);
        assertEquals(forecastDetail.tempMax, viewModel.tempMax, TEST_EPSILON);
        assertEquals(forecastDetail.tempMin, viewModel.tempMin, TEST_EPSILON);
        assertEquals(forecastDetail.forecastId, viewModel.forecastId);
        assertEquals(forecastDetail.desc, viewModel.desc);
        assertEquals(forecastDetail.humidity, viewModel.humidity);
        assertEquals(forecastDetail.descDetail, viewModel.descDetail);
    }

}
