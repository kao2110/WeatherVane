package nyc.insideout.weathervane.ui.forecast.list;


public interface ForecastContract {

    public interface Presenter{

        void start(String location);

        void onSubmitForecastLocation(String location);

        void onForecastItemClicked(long date);
    }

    public interface View{

        void showProgress();

        void hideProgress();

        void enableLocationEntry();

        void disableLocationEntry();

        void showForecastList();

        void showForecastDetails(long data);
    }
}
