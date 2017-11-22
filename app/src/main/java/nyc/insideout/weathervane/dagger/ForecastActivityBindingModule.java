package nyc.insideout.weathervane.dagger;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import nyc.insideout.weathervane.ui.forecast.detail.ForecastDetailActivity;
import nyc.insideout.weathervane.ui.forecast.list.ForecastActivity;

/**
 * @ContributesAndroidInjector annotation can be used to provide instance of
 * ForecastActivity because the AndroidInjector<> SubComponent and
 * AndroidInjector.Factory<> Module have default implementations
 */

@Module
public abstract class ForecastActivityBindingModule {


    @ActivityScope
    @ContributesAndroidInjector(modules = ForecastActivityModule.class)
    abstract ForecastActivity forecastActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = ForecastDetailActivityModule.class)
    abstract ForecastDetailActivity forecastDetailActivity();

}
