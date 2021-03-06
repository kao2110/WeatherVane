package nyc.insideout.weathervane.ui.mapper;


import java.util.ArrayList;
import java.util.List;

import nyc.insideout.weathervane.domain.model.Forecast;
import nyc.insideout.weathervane.domain.model.ForecastDetail;
import nyc.insideout.weathervane.ui.model.ForecastDetailViewModel;
import nyc.insideout.weathervane.ui.model.ForecastViewModel;

public class ForecastDataMapperImpl implements ForecastDataMapper{

    // used to format values for display before mapping them to View Models
    private DataFormatter mDataFormatter;

    public ForecastDataMapperImpl(DataFormatter formatter){
        mDataFormatter = formatter;
    }

    // takes a list of domain models (Forecast) and maps the data to a list of view models (ForecastViewModel)
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

    // takes a domain model (ForecastDetail) and maps the data to a view model (ForecastDetailViewModel)
    @Override
    public ForecastDetailViewModel domainToDetailViewModel(ForecastDetail forecastDetail) {
        // ForecastDetailModel extends ForecastModel, create formatted ForecastModel then use those
        // values to create a detail model
        ForecastViewModel model = domainToForecastViewModel(forecastDetail);
        return new ForecastDetailViewModel(model.date, model.tempMax,
                model.tempMin, forecastDetail.forecastId, forecastDetail.desc,
                forecastDetail.humidity, forecastDetail.descDetail, model.forecastIconUrl, forecastDetail.date);
    }

    // helper method
    private ForecastViewModel domainToForecastViewModel(Forecast forecast){
        // format values for display
        String dateText = mDataFormatter.formatDate(forecast.date);
        String tempMax = mDataFormatter.formatTemperature(forecast.tempMax);
        String tempMin = mDataFormatter.formatTemperature(forecast.tempMin);
        String iconUrl = mDataFormatter.formatImgUrl(forecast.forecastIconId);

        return new ForecastViewModel(dateText, tempMax, tempMin, forecast.forecastId, forecast.desc, iconUrl, forecast.date);
    }
}
