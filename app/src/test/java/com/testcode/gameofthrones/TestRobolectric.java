package com.testcode.gameofthrones;

import android.content.Intent;

import com.testcode.gameofthrones.fragments.GoTHousesListFragment;
import com.testcode.gameofthrones.models.GoTHouse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;

/**
 * Created by Fabian on 12/8/16.
 */


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk=21)

public class TestRobolectric {

    private HomeActivity activity;

    @Before
    public void setUp() throws Exception
    {
        activity = Robolectric.buildActivity( HomeActivity.class )
                .create()
                .resume()
                .get();
    }

    @Test
    public void shouldNotBeNull() throws Exception
    {
        assertNotNull( activity );
    }

    @Test
    public void shouldHaveFragment() throws Exception
    {
        assertNotNull( activity.getVp().getCurrentItem());
    }
/*
    @Test
    public void buttonClickShouldStartNewActivity() throws Exception
    {
        // get recyclerview click and check if sending to next
        //Button button = (Button) activity.findViewById( R.id.button2 );
        //button.performClick();
        Intent intent = Shadows.shadowOf(activity).peekNextStartedActivity();
        assertEquals(DetailActivity.class.getCanonicalName(), intent.getComponent().getClassName());
    }
*/
    @Test
    public void shouldNotBeNullFragment() throws Exception
    {
        GoTHousesListFragment fragment = new GoTHousesListFragment();
        startFragment( fragment );
        assertNotNull( fragment );
        assertNotNull(fragment.getView().findViewById(R.id.search_view));
    }



}
