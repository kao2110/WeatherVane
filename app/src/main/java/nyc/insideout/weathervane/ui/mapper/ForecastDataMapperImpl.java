package nyc.insideout.weathervane.ui.mapper;


import java.util.ArrayList;
import java.util.List;

import nyc.insideout.weathervane.domain.model.Forecast;
import nyc.insideout.weathervane.domain.model.ForecastDetail;
import nyc.insideout.weathervane.ui.model.ForecastDetailViewModel;
import nyc.insideout.weathervane.ui.model.ForecastViewModel;

public class ForecastDataMapperImpl implements ForecastDataMapper{

    @Override
    public List<ForecastViewModel> domainToForecastList(List<Forecast> forecastList) {
        List<ForecastViewModel> list = new ArrayList<>();

        // as per best android practices avoid Collection based ForLoop to minimize object creation
        // by avoiding creation of iterator
        for(int i = 0; i < forecastList.size(); i++){
            list.add(domainToForecastViewModel(forecastList.get(i)));
        }

        return list;
    }

    @Override
    public ForecastDetailViewModel domainToDetailViewModel(ForecastDetail forecastDetail) {
        return new ForecastDetailViewModel(forecastDetail.date, forecastDetail.tempMax,
                forecastDetail.tempMin, forecastDetail.forecastId, forecastDetail.desc,
                forecastDetail.humidity, forecastDetail.descDetail);
    }

    private ForecastViewModel domainToForecastViewModel(Forecast forecast){
        return new ForecastViewModel(forecast.date, forecast.tempMax, forecast.tempMin,
                forecast.forecastId, forecast.desc);
    }
}
