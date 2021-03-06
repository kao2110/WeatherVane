package nyc.insideout.weathervane.ui;


import android.os.Handler;
import android.support.annotation.RestrictTo;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.idling.concurrent.IdlingThreadPoolExecutor;

import java.util.Map;

import nyc.insideout.weathervane.domain.interactor.UseCase;
import nyc.insideout.weathervane.domain.interactor.UseCase.RequestParam;
import nyc.insideout.weathervane.domain.interactor.UseCase.RequestResult;

/**
 * The UseCaseExecutorImpl is used to execute UseCases on a background thread. Results are then returned
 * back to the main thread for display via a UiCallback. If the view or presenter are destroyed
 * before the request has completed the UiCallback is cleared to avoid a memory leak.
 */
public class UseCaseExecutorImpl implements UseCaseExecutor{

    // used to execute UseCase results on the main thread
    private final Handler mHandler;

    // used to execute UseCases on a background thread. Uses IdlingThreadPoolExecutor from the
    // Android Test Support Library because it can be registered as an IdlingResource with
    // Espresso. This is necessary to coordinate the background thread activity with UI tests.
    private final IdlingThreadPoolExecutor mExecutor;

    // store the UiCallback in a map so it can be retrieved later to both return the results
    // back to the main thread and to remove the reference held by his executor to prevent memory leaks
    private Map<String, UiCallback> mUiCallbackMap;


    public UseCaseExecutorImpl(IdlingThreadPoolExecutor executor, Handler mainThreadHandler, Map<String, UiCallback> cache){
        mExecutor = executor;
        mHandler = mainThreadHandler;
        mUiCallbackMap = cache;
    }


    @Override
    public <T extends UseCase, V extends RequestParam, R extends RequestResult>
      void executeUseCase(final T useCase, final V params, final String key, UiCallback<R> uiCallback){

        mUiCallbackMap.put(key, uiCallback);

        // execute the UseCase on a background thread
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                useCase.execute(params, new UseCase.UseCaseCallback<R>() {
                    @Override
                    public void onComplete(R result) {
                        onExecuteSuccess(result, key);
                    }

                    @Override
                    public void onError(Throwable e) {
                        onExecuteError(e, key);
                    }
                });
            }
        });
    }

    /*
    * If the view and presenter are destroyed before the request for data has completed this method
    * will remove the callback that was created on the UI thread to prevent a memory leak
    */
    @Override
    public void cancelUiCallback(String key){
        if(mUiCallbackMap.containsKey(key)){
            mUiCallbackMap.remove(key);
        }
    }

    /*
    * This method is called once the asynchronous call to retrieve data from the repository has
    * completed successfully. The UiCallback created when making the request returns results back
    * to the main thread via a Handler.
    */
    private <R extends RequestResult> void onExecuteSuccess(final R result, final String key){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if(mUiCallbackMap.containsKey(key)){
                    UiCallback callback = mUiCallbackMap.get(key);
                    callback.onComplete(result);
                }
            }
        });
    }

    /*
    * This method is called once the asynchronous call to retrieve data from the repository has
    * completed with an error. The UiCallback created when making the request returns results back
    * to the main thread via a Handler.
    */
    private void onExecuteError(final Throwable e, final String key){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if(mUiCallbackMap.containsKey(key)){
                    UiCallback callback = mUiCallbackMap.get(key);
                    callback.onError(e);
                }
            }
        });
    }

    /*
    * This method is necessary because the IdlingResources needs to be registered with
    * Espresso in order for UI tests to stay in sync with background threads.
    */
    @RestrictTo(RestrictTo.Scope.TESTS)
    public IdlingResource getCountingIdlingResource(){
        return mExecutor;
    }
}
