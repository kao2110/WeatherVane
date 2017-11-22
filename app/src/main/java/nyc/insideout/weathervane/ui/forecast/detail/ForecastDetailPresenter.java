package nyc.insideout.weathervane.ui.forecast.detail;


import javax.inject.Inject;

import nyc.insideout.weathervane.domain.interactor.GetForecastDetailsUseCase;
import nyc.insideout.weathervane.domain.model.ForecastDetail;
import nyc.insideout.weathervane.ui.UseCaseExecutor;
import nyc.insideout.weathervane.ui.mapper.ForecastDataMapper;
import nyc.insideout.weathervane.ui.model.ForecastDetailViewModel;

public class ForecastDetailPresenter implements ForecastDetailContract.Presenter {

    // used to execute UseCases off the main thread
    private UseCaseExecutor mUseCaseExecutor;

    private ForecastDetailContract.View mView;

    private GetForecastDetailsUseCase mGetForecastDetailsUseCase;

    // responsible for mapping data from the domain model to the view model
    private ForecastDataMapper mDataMapper;

    // this value is used to evict the callback submitted to the UseCaseExecutor on the UseCase
    // has completed or if the view is destroyed before the UseCase has completed execution.
    private String mUiCallbackCacheKey = "";

    // this value is used to let the presenter know if it should update the view with data
    private boolean mViewIsActive = true;

    @Inject
    public ForecastDetailPresenter(GetForecastDetailsUseCase useCase,  UseCaseExecutor executor,
                                   ForecastDataMapper mapper, ForecastDetailContract.View view){
        mGetForecastDetailsUseCase = useCase;
        mUseCaseExecutor = executor;
        mDataMapper = mapper;
        mView = view;
    }

    @Override
    public void start(long date) {
        mView.showProgressIndicator();
        getForeCastDetails(date);
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
        evictUiCallback();
    }

    // this call is necessary to remove UiCallback created in the call to the UseCaseExecutor.
    // because the callback is an anonymous class and the UseCaseExecutor is a singleton a memory
    // leak will occur if the callback is not evicted from the cache used by the UseCaseExecutor.
    private void evictUiCallback(){
        mUseCaseExecutor.cancelUiCallback(mUiCallbackCacheKey);
    }

    // execute the UseCase and react accordingly
    private void getForeCastDetails(long date){
        // set the value used that will be used to evict the callback from the UseCaseExecutor cache
        // once the UseCase is executed
        mUiCallbackCacheKey = Long.toString(date);

        // create the object used to pass request parameters to the use case
        GetForecastDetailsUseCase.RequestParam param = new GetForecastDetailsUseCase.RequestParam(date);

        // the UiCallback is parametrized with the expected Request Result object
        mUseCaseExecutor.executeUseCase(mGetForecastDetailsUseCase, param,
                mUiCallbackCacheKey, new UseCaseExecutor.UiCallback<GetForecastDetailsUseCase.RequestResult>() {
            @Override
            public void onComplete(GetForecastDetailsUseCase.RequestResult result) {
                if(mViewIsActive){
                    displayForecastDetails(result.getForecastDetail());
                    // remove old callback
                    evictUiCallback();
                }
            }

            @Override
            public void onError(Throwable e) {
                evictUiCallback();
                if(mViewIsActive) {
                    mView.hideProgressIndicator();
                    mView.showErrorMessage(e.getMessage());
                }
            }
        });
    }

    // map the data retrieved from the UseCase to the View Model class used to display the data
    private void displayForecastDetails(ForecastDetail domainModel){
        ForecastDetailViewModel model = mDataMapper.domainToDetailViewModel(domainModel);
        mView.hideProgressIndicator();
        mView.showDetails(model);

    }
}
