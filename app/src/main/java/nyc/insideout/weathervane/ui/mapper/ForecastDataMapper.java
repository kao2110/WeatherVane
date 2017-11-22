package nyc.insideout.weathervane.ui.mapper;


import java.util.List;

import nyc.insideout.weathervane.domain.model.Forecast;
import nyc.insideout.weathervane.domain.model.ForecastDetail;
import nyc.insideout.weathervane.ui.model.ForecastDetailViewModel;
import nyc.insideout.weathervane.ui.model.ForecastViewModel;

/**
 * Interface to be implemented by class used to map data from domain to models used by
 * the user interface.
 *
 * This allows for loose coupling of classes used for data objects between modules.
 */
public interface ForecastDataMapper {

    List<ForecastViewModel> domainToForecastList(List<Forecast> forecastList);

    ForecastDetailViewModel domainToDetailViewModel(ForecastDetail forecastDetail);
}
