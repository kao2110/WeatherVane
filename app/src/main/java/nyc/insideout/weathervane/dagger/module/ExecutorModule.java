package nyc.insideout.weathervane.dagger.module;

import android.os.Handler;
import android.support.v4.util.ArrayMap;

import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import nyc.insideout.weathervane.ui.UseCaseExecutor;
import nyc.insideout.weathervane.ui.UseCaseExecutorImpl;

@Module
public class ExecutorModule {

    @Provides
    Executor provideExecutor(){
        return Executors.newSingleThreadExecutor();
    }

    @Provides
    Handler provideHandler(){
        return new Handler();
    }

    @Provides
    Map<String, UseCaseExecutor.UiCallback> provideExecuterCallback(){
        return new ArrayMap<>();
    }

    @Singleton
    @Provides
    UseCaseExecutor provideUseCaseExecutor(Executor executor, Handler mainThreadHandler,
                                           Map<String, UseCaseExecutor.UiCallback> callbackMap){
        return new UseCaseExecutorImpl(executor, mainThreadHandler, callbackMap);
    }
}
