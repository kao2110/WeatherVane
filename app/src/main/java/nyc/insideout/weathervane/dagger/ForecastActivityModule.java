package nyc.insideout.weathervane.dagger;

import dagger.Module;
import dagger.Provides;
import nyc.insideout.weathervane.ui.forecast.list.ForecastActivity;
import nyc.insideout.weathervane.ui.forecast.list.ForecastContract;
import nyc.insideout.weathervane.ui.forecast.list.ForecastPresenter;
import nyc.insideout.weathervane.ui.mapper.ForecastDataMapper;
import nyc.insideout.weathervane.ui.mapper.ForecastDataMapperImpl;

@Module
public class ForecastActivityModule {

    @ActivityScope
    @Provides
    ForecastDataMapper provideForecastDataMapper(){
        return new ForecastDataMapperImpl();
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
