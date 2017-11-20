package nyc.insideout.weathervane.ui.forecast.list;


import java.util.List;

import nyc.insideout.weathervane.ui.model.ForecastViewModel;

public interface ForecastContract {

    interface Presenter{

        void start(String location);

        void onSubmitForecastLocation(String location);

        void onForecastItemClicked(long date);

        void onViewActive();

        void onViewInactive();

        void onViewDestroyed();
    }

    interface View{

        void showProgressIndicator();

        void hideProgressIndicator();

        void enableLocationEntry();

        void disableLocationEntry();

        void showForecastList(List<ForecastViewModel> forecastItems);

        void showForecastDetails(long data);
    }
}
