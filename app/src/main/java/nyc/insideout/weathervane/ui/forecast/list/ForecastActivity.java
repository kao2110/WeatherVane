package nyc.insideout.weathervane.ui.forecast.list;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import nyc.insideout.weathervane.R;
import nyc.insideout.weathervane.ui.forecast.detail.ForecastDetailActivity;
import nyc.insideout.weathervane.ui.model.ForecastViewModel;
import nyc.insideout.weathervane.ui.util.RecyclerViewTouchListener;

import static nyc.insideout.weathervane.ui.forecast.detail.ForecastDetailActivity.UNIX_DATE_VAL;

public class ForecastActivity extends AppCompatActivity implements ForecastContract.View, SearchView.OnQueryTextListener {

    @Inject
    ForecastContract.Presenter mPresenter;
    private SearchView mSearchView;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ForecastAdapter mAdapter;
    private ProgressBar mProgressBar;
    private TextView mProgressLabel;
    private TextView mErrorText;

    private Menu mMenu;
    private ActionBar mToolbar;

    protected void onCreate(Bundle savedInstanceState) {
        // used by dagger to inject dependencies for this activity
        // must be called before call to super.
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        // setup recycler view
        mRecyclerView = findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ForecastAdapter(getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);
        initRecyclerViewTouchListener();

        mProgressBar = findViewById(R.id.progress_bar);
        mProgressLabel = findViewById(R.id.progress_label);
        mToolbar = getSupportActionBar();
        mErrorText = findViewById(R.id.error_text);

        // Pass null value to presenter when starting. By default the last successful search location
        // will be used.
        mPresenter.start(null);

    }

    @Override
    public void onStart(){
        super.onStart();

        // Set the view as active to receive ui updates
        mPresenter.onViewActive();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Setup search view in activity toolbar
        getMenuInflater().inflate(R.menu.search_menu, menu);
        mMenu = menu;
        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        mSearchView = (SearchView)searchItem.getActionView();
        mSearchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onStop(){
        super.onStop();

        // Prevent the view from receiving ui updates
        mPresenter.onViewInactive();
    }

    @Override
    public void onDestroy(){
       super.onDestroy();

       // Let the presenter know the view is being destroyed to prevent memory leaks
       mPresenter.onViewDestroyed();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        //capture submitted search query and pass it to the presenter
        mPresenter.onSubmitForecastLocation(query);

        // return the search view to its original state
        mSearchView.setIconified(true);
        mSearchView.clearFocus();
        if(mMenu != null)
            mMenu.findItem(R.id.app_bar_search).collapseActionView();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void showProgressIndicator() {
        mToolbar.setSubtitle("");
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressLabel.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressIndicator() {
        mProgressBar.setVisibility(View.INVISIBLE);
        mProgressLabel.setVisibility(View.INVISIBLE);
    }

    @Override
    public void enableLocationEntry() {

        // Enable search view after submitted query is complete.
        if(mSearchView != null)
        mSearchView.setSubmitButtonEnabled(false);
    }

    @Override
    public void disableLocationEntry() {

        // Disable search view to prevent multiple search submissions
        if(mSearchView != null)
        mSearchView.setSubmitButtonEnabled(false);
    }

    @Override
    public void showForecastList(String location, List<ForecastViewModel> forecastItems) {
        mAdapter.setForecastItems(forecastItems);
        mToolbar.setSubtitle(location);
    }

    @Override
    public void showForecastDetails(long data) {
        Intent intent = new Intent(this, ForecastDetailActivity.class);
        intent.putExtra(UNIX_DATE_VAL, data);
        startActivity(intent);
    }

    @Override
    public void showRecyclerView(){
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRecyclerView(){
        mRecyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showErrorMessage(String text){
        mErrorText.setVisibility(View.VISIBLE);
        mErrorText.setText(text);
    }

    @Override
    public void hideErrorMessage(){
        mErrorText.setVisibility(View.INVISIBLE);
    }

    private void initRecyclerViewTouchListener(){
        RecyclerViewTouchListener listener = new RecyclerViewTouchListener(new GestureDetector(this,
                new GestureDetector.SimpleOnGestureListener(){
                    @Override
                    public boolean onSingleTapUp(MotionEvent e){
                        View child = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
                        if(child != null){
                            int position = mRecyclerView.getChildAdapterPosition(child);
                            long unixDate = mAdapter.getItemUnixDate(position);
                            mPresenter.onForecastItemClicked(unixDate);
                        }
                        return true;
                    }
                }));
        mRecyclerView.addOnItemTouchListener(listener);
    }
}
