package nyc.insideout.weathervane.ui.forecast.list;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.Toolbar;
import android.widget.AutoCompleteTextView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import nyc.insideout.weathervane.R;

import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.action.ViewActions.click;
import static org.hamcrest.core.Is.is;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
public class ForecastActivityTest {

    private IdlingRegistry mIdlingRegistry = IdlingRegistry.getInstance();

    private IdlingResource mIdlingResource;

    // custom matcher used to find toolbar subtitle
    private static Matcher<Object> withToolbarSubTitle(final Matcher<CharSequence> textMatcher){

        return new BoundedMatcher<Object, Toolbar>(Toolbar.class) {

            @Override
            public boolean matchesSafely(Toolbar toolbar){
                return textMatcher.matches(toolbar.getSubtitle());
            }

            @Override
            public void describeTo(Description description){
                description.appendText("with toolbar title: ");
                textMatcher.describeTo(description);
            }
        };
    }

    @Rule
    public ActivityTestRule<ForecastActivity> mForecastActivityTestRule =
            new ActivityTestRule<ForecastActivity>(ForecastActivity.class);

    @Before
    public void setup(){
        ForecastPresenter presenter = (ForecastPresenter) mForecastActivityTestRule.getActivity().mPresenter;
        mIdlingResource = presenter.getCountingIdlingResource();
        mIdlingRegistry.register(mIdlingResource);
    }

    @Test
    public void whenActivityStarts_ThenDisplayLastSearchedLocation(){
        // default location for mock data
        CharSequence subTitle = "Ytown";

        // check that toolbar displays mock data location as subtitle
        onView(isAssignableFrom(Toolbar.class)).check(matches(withToolbarSubTitle(is(subTitle))));

        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()));
        onView(withId(R.id.error_text)).check(matches(not(isDisplayed())));
    }

    @Test
    public void whenValidLocation_ThenDisplayResults(){
        // valid mock data location
        CharSequence location = "New York";

        // click search icon and type search location
        onView(withId(R.id.app_bar_search)).perform(click());
        onView(isAssignableFrom(AutoCompleteTextView.class)).perform(typeText(location.toString()), pressImeActionButton());

        // check correct info is displayed
        onView(isAssignableFrom(Toolbar.class)).check(matches(withToolbarSubTitle(is(location))));
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()));
        onView(withId(R.id.error_text)).check(matches(not(isDisplayed())));
    }

    @Test
    public void whenInvalidLocation_ThisDisplayError(){
        // invalid mock data location
        CharSequence location = "nowhereville";

        // click search icon and type search location
        onView(withId(R.id.app_bar_search)).perform(click());
        onView(isAssignableFrom(AutoCompleteTextView.class)).perform(typeText(location.toString()), pressImeActionButton());

        // check correct info is displayed
        onView(isAssignableFrom(Toolbar.class)).check(matches(withToolbarSubTitle(not(location))));
        onView(withId(R.id.recycler_view)).check(matches(not(isDisplayed())));
        onView(withId(R.id.error_text)).check(matches(isDisplayed()));
    }

    @After
    public void tearDown(){
        mIdlingRegistry.unregister(mIdlingResource);
    }
}
