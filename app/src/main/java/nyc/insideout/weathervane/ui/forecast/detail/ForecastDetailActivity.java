package nyc.insideout.weathervane.ui.forecast.detail;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import nyc.insideout.weathervane.R;
import nyc.insideout.weathervane.ui.model.ForecastDetailViewModel;

/**
 * This class is responsible for displaying Forecast data details.
 */
public class ForecastDetailActivity extends AppCompatActivity implements ForecastDetailContract.View{

    // used to pass date via intent when starting this activity.
    public static String UNIX_DATE_VAL = "unixDateVal";

    @Inject
    ForecastDetailContract.Presenter mPresenter;

    private TextView mDate;
    private TextView mDesc;
    private TextView mTempMax;
    private TextView mTempMin;
    private ImageView mIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // This must be called before the call to super in order for dagger to inject dependencies
        // before the objects they inject are used.
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast_details);

        mDate = findViewById(R.id.detail_date);
        mDesc = findViewById(R.id.detail_desc);
        mTempMax = findViewById(R.id.detail_tempMax);
        mTempMin = findViewById(R.id.detail_tempMin);
        mIcon= findViewById(R.id.detail_icon);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // With more time I would create a string resource rather than hardcode this value
        actionBar.setTitle("Forecast Details");

        // Get the unix time stamp associated with the forecast item what was selected from the
        // list of forecast items. This long value is used as the unique key to retrieve details
        // from the repository
        Intent intent = getIntent();
        long unixDate = intent.getLongExtra(UNIX_DATE_VAL, 0);
        mPresenter.start(unixDate);
    }

    @Override
    public void showDetails(ForecastDetailViewModel model) {
        mDate.setText(model.date);
        mDesc.setText(model.descDetail);
        mTempMax.setText(model.tempMax);
        mTempMin.setText(model.tempMin);
        Picasso.with(getApplicationContext())
                .load(model.forecastIconUrl)
                .placeholder(R.drawable.ic_weathervane)
              //  .resize(150,150)
                .into(mIcon);
    }

    @Override
    public void showProgressIndicator() {

    }

    @Override
    public void hideProgressIndicator() {

    }

    @Override
    public void showErrorMessage(String text) {

    }
}
