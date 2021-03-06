package nyc.insideout.weathervane.domain.interactor;


public interface UseCase<T extends UseCase.RequestParam, R extends UseCase.RequestResult> {

    void execute(T params, UseCaseCallback<R> callback);


    //Callback that is used to communicate between the UI/app layer and domain layer
    public interface UseCaseCallback<V>{

        void onComplete(V result);

        void onError(Throwable e);
    }

    //Marker Interface used by classes intended to carry request parameters used by UseCases
    public interface RequestParam{

    }

    // Marker Interface used by classes to carry results generated by executing UseCases
    public interface RequestResult{

    }
}
