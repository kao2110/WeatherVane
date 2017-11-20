package nyc.insideout.weathervane.ui;


import android.os.Handler;

import java.util.Map;
import java.util.concurrent.Executor;

import nyc.insideout.weathervane.domain.interactor.UseCase;
import nyc.insideout.weathervane.domain.interactor.UseCase.RequestParam;
import nyc.insideout.weathervane.domain.interactor.UseCase.RequestResult;

/**
 * The UseCaseExecutorImpl is used to execute UseCases on a background thread. Results are then returned
 * back to the main thread for display via a UiCallback. If the view or presenter are destroyed
 * before the request has completed the UiCallback is cleared to avoid a memory leak.
 */
public class UseCaseExecutorImpl implements UseCaseExecutor{

    private final Handler mHandler;
    private final Executor mExecutor;
    private Map<String, UiCallback> mUiCallbackMap;


    public UseCaseExecutorImpl(Executor executor, Handler mainThreadHandler, Map<String, UiCallback> cache){
        mExecutor = executor;
        mHandler = mainThreadHandler;
        mUiCallbackMap = cache;
    }


    @Override
    public <T extends UseCase, V extends RequestParam, R extends RequestResult>
      void executeUseCase(final T useCase, final V params, final String key, UiCallback<R> uiCallback){

        mUiCallbackMap.put(key, uiCallback);

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



}
