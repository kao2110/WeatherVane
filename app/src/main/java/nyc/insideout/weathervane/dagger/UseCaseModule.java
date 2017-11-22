package nyc.insideout.weathervane.dagger;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import nyc.insideout.weathervane.domain.interactor.GetForecastDetailsUseCase;
import nyc.insideout.weathervane.domain.interactor.GetForecastUseCase;
import nyc.insideout.weathervane.domain.repository.WeatherRepository;

@Module
public class UseCaseModule {

    @Singleton
    @Provides
    GetForecastUseCase provideGetForecastUseCase(WeatherRepository weatherRepository){
        return new GetForecastUseCase(weatherRepository);
    }

    @Singleton
    @Provides
    GetForecastDetailsUseCase provideGetForecastDetailsUseCase(WeatherRepository weatherRepository){
        return new GetForecastDetailsUseCase(weatherRepository);
    }
}