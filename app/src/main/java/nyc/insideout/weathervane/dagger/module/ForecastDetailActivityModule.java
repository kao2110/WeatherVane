package nyc.insideout.weathervane.dagger.module;


import dagger.Module;
import dagger.Provides;
import nyc.insideout.weathervane.dagger.ActivityScope;
import nyc.insideout.weathervane.ui.forecast.detail.ForecastDetailActivity;
import nyc.insideout.weathervane.ui.forecast.detail.ForecastDetailContract;
import nyc.insideout.weathervane.ui.forecast.detail.ForecastDetailPresenter;
import nyc.insideout.weathervane.ui.mapper.DataFormatter;
import nyc.insideout.weathervane.ui.mapper.DataFormatterImpl;
import nyc.insideout.weathervane.ui.mapper.ForecastDataMapper;
import nyc.insideout.weathervane.ui.mapper.ForecastDataMapperImpl;

/**
 * This module provieds the dependencies used by the ForecastDetailActivity.
 * Each dependency is marked with @ActivityScope meaning they will live only as long as the
 * component which they are associated with (i.e. they will die when the activity dies).
 */
@Module
public class ForecastDetailActivityModule {

    @ActivityScope
    @Provides
    DataFormatter provideDataFormatter(){
        return new DataFormatterImpl();
    }

    @ActivityScope
    @Provides
    ForecastDataMapper provideForecastDataMapper(DataFormatter dataFormatter){
        return new ForecastDataMapperImpl(dataFormatter);
    }

    @ActivityScope
    @Provides
    ForecastDetailContract.Presenter provideForecastPresenter(ForecastDetailPresenter presenter){
        return presenter;
    }

    @ActivityScope
    @Provides
    ForecastDetailContract.View provideForecastView(ForecastDetailActivity view){
        return view;
    }
}
