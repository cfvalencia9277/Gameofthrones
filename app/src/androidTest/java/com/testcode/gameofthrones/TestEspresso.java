package com.testcode.gameofthrones;

import android.app.Activity;
import android.os.SystemClock;
import android.support.design.widget.TabLayout;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.view.View;


import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayingAtLeast;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.runner.lifecycle.Stage.RESUMED;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
/**
 * Created by Fabian on 09/12/2016.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TestEspresso {

    @Rule
    public ActivityTestRule<HomeActivity> mActivityRule = new ActivityTestRule<>(HomeActivity.class);

    @Test
    public void checktoolbarAndContainer() {
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
        onView(withId(R.id.tabs)).check(matches(isDisplayed()));
        onView(withId(R.id.container)).check(matches(isDisplayed()));
    }

    @Test
    public void TestViewPagerGetTitles() {
        CharSequence first = "Characters";
        CharSequence second = "Houses";
        assertThat(mActivityRule.getActivity().vp.getAdapter().getPageTitle(0),is(first));
        assertThat(mActivityRule.getActivity().vp.getAdapter().getPageTitle(1),is(second));
    }

    @Test
    public void TestCharacterRecyclerviews() {
        onView(allOf(withId(R.id.rv_characters))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.rv_characters))).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        Activity activity = getActivityInstance();
        boolean b = (activity instanceof  DetailActivity);
        assertTrue(b);
    }

    @Test
    public void TestHousesRecyclerviews() {
        onView(withId(R.id.container)).perform(withCustomConstraints(swipeLeft(), isDisplayingAtLeast(85)));
        SystemClock.sleep(800); // Wait a little until the content is loaded
        onView(allOf(withId(R.id.rv_houses))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.rv_houses))).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        Activity activity = getActivityInstance();
        boolean b = (activity instanceof  FamilyListActivity);
        assertTrue(b);
    }

    public static ViewAction withCustomConstraints(final ViewAction action, final Matcher<View> constraints) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return constraints;
            }

            @Override
            public String getDescription() {
                return action.getDescription();
            }

            @Override
            public void perform(UiController uiController, View view) {
                action.perform(uiController, view);
            }
        };
    }

    public Activity getActivityInstance() {
        final Activity[] activity = new Activity[1];
        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable( ) {
            public void run() {
                Activity currentActivity = null;
                Collection resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(RESUMED);
                if (resumedActivities.iterator().hasNext()){
                    currentActivity = (Activity) resumedActivities.iterator().next();
                    activity[0] = currentActivity;
                }
            }
        });
        return activity[0];
    }

}
