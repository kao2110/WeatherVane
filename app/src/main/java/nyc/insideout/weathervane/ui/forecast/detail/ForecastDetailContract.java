package nyc.insideout.weathervane.ui.forecast.detail;


import nyc.insideout.weathervane.ui.model.ForecastDetailViewModel;

public interface ForecastDetailContract {

    interface Presenter{

        void start(long date);

        void onViewActive();

        void onViewInactive();

        void onViewDestroyed();
    }

    interface View{

        void showDetails(ForecastDetailViewModel model);

        void showProgressIndicator();

        void hideProgressIndicator();

        void showErrorMessage(String text);
    }
}
