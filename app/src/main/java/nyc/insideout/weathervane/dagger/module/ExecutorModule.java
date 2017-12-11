package nyc.insideout.weathervane.dagger.module;

import android.os.Handler;
import android.support.test.espresso.idling.concurrent.IdlingThreadPoolExecutor;
import android.support.v4.util.ArrayMap;

import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import nyc.insideout.weathervane.ui.UseCaseExecutor;
import nyc.insideout.weathervane.ui.UseCaseExecutorImpl;

/**
 * This module provides an instance of the UseCaseExecutor used Presenter classes.
 */
@Module
public class ExecutorModule {

    @Provides
    IdlingThreadPoolExecutor provideExecutor(){
        IdlingThreadPoolExecutor executor = new IdlingThreadPoolExecutor("UseCaseExecutor",
                1,
                1,
                300,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<Runnable>(5),
                Executors.defaultThreadFactory());

        executor.allowCoreThreadTimeOut(true);
        return executor;
    }

    @Provides
    Handler provideHandler(){
        return new Handler();
    }

    @Provides
    Map<String, UseCaseExecutor.UiCallback> provideExecutorCallback(){
        return new ArrayMap<>();
    }

    @Singleton
    @Provides
    UseCaseExecutor provideUseCaseExecutor(IdlingThreadPoolExecutor executor, Handler mainThreadHandler,
                                           Map<String, UseCaseExecutor.UiCallback> callbackMap){
        return new UseCaseExecutorImpl(executor, mainThreadHandler, callbackMap);
    }
}
