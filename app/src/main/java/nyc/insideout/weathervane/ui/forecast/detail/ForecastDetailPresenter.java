package nyc.insideout.weathervane.ui.forecast.detail;


import javax.inject.Inject;

import nyc.insideout.weathervane.domain.interactor.GetForecastDetailsUseCase;
import nyc.insideout.weathervane.domain.model.ForecastDetail;
import nyc.insideout.weathervane.ui.UseCaseExecutor;
import nyc.insideout.weathervane.ui.mapper.ForecastDataMapper;
import nyc.insideout.weathervane.ui.model.ForecastDetailViewModel;

public class ForecastDetailPresenter implements ForecastDetailContract.Presenter {

    private UseCaseExecutor mUseCaseExecutor;
    private ForecastDetailContract.View mView;
    private GetForecastDetailsUseCase mGetForecastDetailsUseCase;
    private ForecastDataMapper mDataMapper;
    private String mUiCallbackCacheKey = "";
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

    private void evictUiCallback(){
        mUseCaseExecutor.cancelUiCallback(mUiCallbackCacheKey);
    }

    private void getForeCastDetails(long date){
        mUiCallbackCacheKey = Long.toString(date);
        GetForecastDetailsUseCase.RequestParam param = new GetForecastDetailsUseCase.RequestParam(date);

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

    private void displayForecastDetails(ForecastDetail domainModel){
        ForecastDetailViewModel model = mDataMapper.domainToDetailViewModel(domainModel);
        mView.hideProgressIndicator();
        mView.showDetails(model);

    }
}
