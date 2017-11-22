package nyc.insideout.weathervane.ui.forecast.list;


import java.util.List;

import javax.inject.Inject;

import nyc.insideout.weathervane.domain.interactor.GetForecastUseCase;
import nyc.insideout.weathervane.domain.model.Forecast;
import nyc.insideout.weathervane.ui.UseCaseExecutor;
import nyc.insideout.weathervane.ui.mapper.ForecastDataMapper;
import nyc.insideout.weathervane.ui.model.ForecastViewModel;

public class ForecastPresenter implements ForecastContract.Presenter {

    private UseCaseExecutor mUseCaseExecutor;
    private ForecastContract.View mView;
    private GetForecastUseCase mGetForecastUseCase;
    private ForecastDataMapper mDataMapper;
    private String mUiCallbackCacheKey = "";
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
        // view is being destroyed so remove UiCallback created from UseCaseExecuter to prevent
        // possible memory leak.
        evictUiCallback();
    }

    @Override
    public void onForecastItemClicked(long date) {
        mView.showForecastDetails(date);
    }

    private void getForecastItemList(String location){
        mUiCallbackCacheKey = location;
        GetForecastUseCase.RequestParam param = new GetForecastUseCase.RequestParam(location);
        mUseCaseExecutor.executeUseCase(mGetForecastUseCase, param, location, new UseCaseExecutor.UiCallback<GetForecastUseCase.RequestResult>() {
            @Override
            public void onComplete(GetForecastUseCase.RequestResult result) {
                if(mViewIsActive){
                    displayForecastItems(result.getLocation(), result.getForecastList());
                    // remove old callback
                    evictUiCallback();
                }
            }

            @Override
            public void onError(Throwable e) {
                evictUiCallback();
                mView.hideProgressIndicator();
                mView.showErrorMessage(e.getMessage());
            }
        });
    }

    private void displayForecastItems(String location, List<Forecast> items){
        List<ForecastViewModel> list = mDataMapper.domainToForecastList(items);
        mView.hideProgressIndicator();
        mView.enableLocationEntry();
        mView.hideErrorMessage();
        mView.showRecyclerView();
        mView.showForecastList(location, list);
    }

    private void evictUiCallback(){
        mUseCaseExecutor.cancelUiCallback(mUiCallbackCacheKey);
        mUiCallbackCacheKey = "";
    }
}
