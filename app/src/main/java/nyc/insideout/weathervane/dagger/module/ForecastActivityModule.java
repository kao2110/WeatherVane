package nyc.insideout.weathervane.dagger.module;

import dagger.Module;
import dagger.Provides;
import nyc.insideout.weathervane.dagger.ActivityScope;
import nyc.insideout.weathervane.ui.forecast.list.ForecastActivity;
import nyc.insideout.weathervane.ui.forecast.list.ForecastContract;
import nyc.insideout.weathervane.ui.forecast.list.ForecastPresenter;
import nyc.insideout.weathervane.ui.mapper.DataFormatter;
import nyc.insideout.weathervane.ui.mapper.DataFormatterImpl;
import nyc.insideout.weathervane.ui.mapper.ForecastDataMapper;
import nyc.insideout.weathervane.ui.mapper.ForecastDataMapperImpl;

/**
 * This module provides the dependencies used by the ForecastActivity. Each dependency has
 * an @ActivityScope which means it will only live as long as the compo
 */
@Module
public class ForecastActivityModule {

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
    ForecastContract.Presenter provideForecastPresenter(ForecastPresenter presenter){
        return presenter;
    }

    @ActivityScope
    @Provides
    ForecastContract.View provideForecastView(ForecastActivity view){
        return view;
    }
}
