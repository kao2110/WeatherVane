package nyc.insideout.weathervane.ui;

import android.os.Handler;
import android.support.test.espresso.idling.concurrent.IdlingThreadPoolExecutor;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;
import java.util.concurrent.Executor;

import nyc.insideout.weathervane.domain.interactor.GetForecastUseCase;
import nyc.insideout.weathervane.domain.interactor.UseCase;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;


public class UseCaseExecutorTest {

    @Mock
    private IdlingThreadPoolExecutor executor;

    @Mock
    private UseCase usecase;

    @Mock
    private UseCaseExecutor.UiCallback<GetForecastUseCase.RequestResult> uiCallback;

    @Mock
    private Map<String, UseCaseExecutor.UiCallback> cache;

    @Mock
    private Handler handler;

    @Mock
    private UseCase.RequestResult requestResult;

    @Mock
    private UseCase.RequestParam requestParam;

    @Captor
    private ArgumentCaptor<UseCase.UseCaseCallback<UseCase.RequestResult>> useCaseCallbackArgumentCaptor;

    @Captor
    private ArgumentCaptor<Runnable> runnableArgumentCaptor;
    private String location = "Ytown";
    private UseCaseExecutorImpl useCaseExecutor;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        useCaseExecutor = new UseCaseExecutorImpl(executor, handler, cache);
    }

    @Test
    public void whenExecuteUseCase_ThenCacheUiCallbackExecuteRunnable(){
        // when executeUseCase called
        useCaseExecutor.executeUseCase(usecase, requestParam, location, uiCallback);

        // then uiCallback cached
        verify(cache).put(location, uiCallback);
        // then UseCase executed
        verify(executor).execute(any(Runnable.class));
    }

    @Test
    public void whenSuccessful_ThenResultPostedToMainThread(){
        useCaseExecutor.executeUseCase(usecase, requestParam, location, uiCallback);

        // capture arguments
        verify(executor).execute(runnableArgumentCaptor.capture());
        runnableArgumentCaptor.getValue().run();
        verify(usecase).execute(any(UseCase.RequestParam.class), useCaseCallbackArgumentCaptor.capture());

        // when UseCase executed successfully
        useCaseCallbackArgumentCaptor.getValue().onComplete(requestResult);

        // then result is posted back to main thread.
        verify(handler).post(any(Runnable.class));
    }

    @Test
    public void whenUnsuccessful_ThenResultPostedToMainThread(){
        useCaseExecutor.executeUseCase(usecase, requestParam, location, uiCallback);

        // capture arguments
        verify(executor).execute(runnableArgumentCaptor.capture());
        runnableArgumentCaptor.getValue().run();
        verify(usecase).execute(any(UseCase.RequestParam.class), useCaseCallbackArgumentCaptor.capture());

        // when UseCase executed in error
        useCaseCallbackArgumentCaptor.getValue().onError(any(Throwable.class));

        // then result is posted back to main thread.
        verify(handler).post(any(Runnable.class));
    }

    @Test
    public void whenCallbackCancelled_ThenRemoveFromCache(){
        doReturn(true).when(cache).containsKey(location);
        useCaseExecutor.cancelUiCallback(location);
        verify(cache).remove(location);
    }

}
