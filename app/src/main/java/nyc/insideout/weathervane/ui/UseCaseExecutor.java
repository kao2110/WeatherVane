package nyc.insideout.weathervane.ui;

import nyc.insideout.weathervane.domain.interactor.UseCase;

/**
 * Interface to be implemented by class used to execute UseCases on a background thread.
 */
public interface UseCaseExecutor {

    <T extends UseCase, V extends UseCase.RequestParam, R extends UseCase.RequestResult>
      void executeUseCase(T useCase, V params, String key, UiCallback<R> uiCallback);

    void cancelUiCallback(String key);


    /*
    * This callback is used to return results obtained from the background thread used by the
    * Executor to the main thread.
    */
    public interface UiCallback<R extends UseCase.RequestResult>{

        void onComplete(R result);

        void onError(Throwable e);
    }
}
