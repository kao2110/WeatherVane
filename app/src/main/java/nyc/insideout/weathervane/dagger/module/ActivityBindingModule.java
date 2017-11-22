package nyc.insideout.weathervane.dagger.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import nyc.insideout.weathervane.dagger.ActivityScope;
import nyc.insideout.weathervane.ui.forecast.detail.ForecastDetailActivity;
import nyc.insideout.weathervane.ui.forecast.list.ForecastActivity;

/**
 * @ContributesAndroidInjector annotation can be used to provide instance of
 * ForecastActivity because the AndroidInjector<> SubComponent and
 * AndroidInjector.Factory<> Module have default implementations
 */

@Module
public abstract class ActivityBindingModule {


    @ActivityScope
    @ContributesAndroidInjector(modules = ForecastActivityModule.class)
    abstract ForecastActivity forecastActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = ForecastDetailActivityModule.class)
    abstract ForecastDetailActivity forecastDetailActivity();

}
