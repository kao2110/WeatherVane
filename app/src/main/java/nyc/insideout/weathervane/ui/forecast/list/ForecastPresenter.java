package nyc.insideout.weathervane.ui.forecast.list;


import java.util.List;

import javax.inject.Inject;

import nyc.insideout.weathervane.domain.interactor.GetForecastUseCase;
import nyc.insideout.weathervane.domain.model.Forecast;
import nyc.insideout.weathervane.ui.UseCaseExecutor;
import nyc.insideout.weathervane.ui.mapper.ForecastDataMapper;
import nyc.insideout.weathervane.ui.model.ForecastViewModel;

public class ForecastPresenter implements ForecastContract.Presenter {

    // used to execute UseCases off the main thread
    private UseCaseExecutor mUseCaseExecutor;

    private ForecastContract.View mView;

    private GetForecastUseCase mGetForecastUseCase;

    // responsible for mapping data from the domain model to the view model
    private ForecastDataMapper mDataMapper;

    // this value is used to evict the callback submitted to the UseCaseExecutor on the UseCase
    // has completed or if the view is destroyed before the UseCase has completed execution.
    private String mUiCallbackCacheKey = "";

    // this value is used to let the presenter know if it should update the view with data
    private boolean mViewIsActive = true;

    @Inject
    public ForecastPresenter(GetForecastUseCase useCase, UseCaseExecutor useCaseExecutor, ForecastDataMapper dataMapper,
                             ForecastContract.View view){
        mGetForecastUseCase = useCase;
        mUseCaseExecutor = useCaseExecutor;
        mDataMapper = dataMapper;
        mView = view;
    }

    @Override
    public void start(String location) {
        mView.disableLocationEntry();
        mView.showProgressIndicator();
        mView.hideErrorMessage();
        mView.hideRecyclerView();
        getForecastItemList(location);
    }

    @Override
    public void onSubmitForecastLocation(String location) {
        mView.disableLocationEntry();
        mView.showProgressIndicator();
        mView.hideErrorMessage();
        mView.hideRecyclerView();
        getForecastItemList(location);
    }

    @Override
    public void onViewActive() {
        mViewIsActive = true;
    }

    @Override
    public void onViewInactive() {
        mViewIsActive = false;
    }

    @Override
    public void onViewDestroyed() {
        // view is being destroyed so remove UiCallback created from UseCaseExecutor to prevent
        // possible memory leak.
        evictUiCallback();
    }

    @Override
    public void onForecastItemClicked(long date) {
        mView.showForecastDetails(date);
    }

    // execute the UseCase and react accordingly
    private void getForecastItemList(String location){
        mUiCallbackCacheKey = location;

        // create the object used to pass request parameters to the use case
        GetForecastUseCase.RequestParam param = new GetForecastUseCase.RequestParam(location);

        // the UiCallback is parametrized with the expected Request Result object
        mUseCaseExecutor.executeUseCase(mGetForecastUseCase, param, location, new UseCaseExecutor.UiCallback<GetForecastUseCase.RequestResult>() {
            @Override
            public void onComplete(GetForecastUseCase.RequestResult result) {
                if(mViewIsActive){
                    displayForecastItems(result.getLocation(), result.getForecastList());
                }
                // remove old callback
                evictUiCallback();
            }

            @Override
            public void onError(Throwable e) {
                if(mViewIsActive){
                    mView.hideProgressIndicator();
                    mView.showErrorMessage(e.getMessage());
                }
                // remove old callback
                evictUiCallback();
            }
        });
    }

    // map the data retrieved from the UseCase to the View Model class used to display the data
    private void displayForecastItems(String location, List<Forecast> items){
        List<ForecastViewModel> list = mDataMapper.domainToForecastList(items);
        mView.hideProgressIndicator();
        mView.enableLocationEntry();
        mView.hideErrorMessage();
        mView.showRecyclerView();
        mView.showForecastList(location, list);
    }

    // this call is necessary to remove UiCallback created in the call to the UseCaseExecutor.
    // because the callback is an anonymous class and the UseCaseExecutor is a singleton a memory
    // leak will occur if the callback is not evicted from the cache used by the UseCaseExecutor.
    private void evictUiCallback(){
        mUseCaseExecutor.cancelUiCallback(mUiCallbackCacheKey);
        mUiCallbackCacheKey = "";
    }
}
