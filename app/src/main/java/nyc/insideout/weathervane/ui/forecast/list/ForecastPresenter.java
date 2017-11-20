package nyc.insideout.weathervane.ui.forecast.list;


import java.util.List;

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
    private boolean mViewIsActive = false;

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
        getForecastItemList(location);
    }

    @Override
    public void onSubmitForecastLocation(String location) {
        mView.disableLocationEntry();
        mView.showProgressIndicator();
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
                    displayForecastItems(result.getForecastList());
                    evictUiCallback();
                }
            }

            @Override
            public void onError(Throwable e) {
                evictUiCallback();
            }
        });
    }

    private void displayForecastItems(List<Forecast> items){
        List<ForecastViewModel> list = mDataMapper.domainToForecastList(items);
        mView.hideProgressIndicator();
        mView.enableLocationEntry();
        mView.showForecastList(list);
    }

    private void evictUiCallback(){
        mUseCaseExecutor.cancelUiCallback(mUiCallbackCacheKey);
        mUiCallbackCacheKey = "";
    }
}
