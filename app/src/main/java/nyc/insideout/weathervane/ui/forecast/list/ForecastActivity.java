package nyc.insideout.weathervane.ui.forecast.list;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import javax.inject.Inject;

import dagger.Provides;
import dagger.android.AndroidInjection;
import nyc.insideout.weathervane.R;
import nyc.insideout.weathervane.ui.model.ForecastViewModel;

public class ForecastActivity extends AppCompatActivity implements ForecastContract.View {

    @Inject ForecastContract.Presenter mPresenter;

    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);
        mPresenter.start(null);
    }

    @Override
    public void onDestroy(){
       super.onDestroy();
    }

    @Override
    public void showProgressIndicator() {

    }

    @Override
    public void hideProgressIndicator() {

    }

    @Override
    public void enableLocationEntry() {

    }

    @Override
    public void disableLocationEntry() {

    }

    @Override
    public void showForecastList(List<ForecastViewModel> forecastItems) {

    }

    @Override
    public void showForecastDetails(long data) {

    }
}
