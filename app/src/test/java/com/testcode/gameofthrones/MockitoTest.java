package com.testcode.gameofthrones;

/**
 * Created by Fabian on 12/9/16.
 */

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Loader;
import android.database.Cursor;

import com.testcode.gameofthrones.fragments.GoTListFragment;
import com.testcode.gameofthrones.models.GoTCharacter;
import com.testcode.gameofthrones.models.GoTHouse;
import com.testcode.gameofthrones.rest.ApiClient;
import com.testcode.gameofthrones.rest.ApiInterface;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class MockitoTest {

    @Mock
    HomeActivity test;
    @Mock
    GoTListFragment testListfrag;
    @Spy
    HomeActivity testspy;
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    GoTHouse houseMock = new GoTHouse(null,null,null);
    GoTHouse houseMock2 = new GoTHouse("other","other","other");
    GoTCharacter charmock = new GoTCharacter(null,null,null,null,null,null);
    GoTCharacter charmock2 = new GoTCharacter("other","other","other","other","other","other");

    @Test
    public void VerifyMethods()  {

        test.getVp();
        test.getVp();
        test.getSpa();
        test.fetchdata();
        test.onBackPressed();

        verify(test, times(2)).getVp();
        verify(test, atLeastOnce()).getSpa();
        verify(test, atLeastOnce()).fetchdata();
        verify(test, atLeastOnce()).onBackPressed();
    }

    @Test
    public void TestHousetoDb()  {

        test.addHousetodb(houseMock2);
        testspy.addHousetodb(houseMock);

        doReturn(true).when(test).addHousetodb(houseMock2);
        assertEquals(true,test.addHousetodb(houseMock2));
        assertFalse(testspy.addHousetodb(houseMock));
    }

    @Test
    public void TestChartoDb()  {

        test.addCharactertodb(charmock2);
        testspy.addCharactertodb(charmock);

        doReturn(true).when(test).addCharactertodb(charmock2);
        assertEquals(true,test.addCharactertodb(charmock2));
        assertFalse(testspy.addCharactertodb(charmock));
    }

    @Test
    public void TestRetrofit() throws IOException {
        Call<List<GoTCharacter>> call2 = ApiClient.getClient().create(ApiInterface.class).getdata();
        Response<List<GoTCharacter>> response = call2.execute();

        assertEquals(response.code(),200);
        assertEquals(true,response.isSuccessful());

    }

}
