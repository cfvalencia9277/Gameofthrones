package com.testcode.gameofthrones;

/**
 * Created by Fabian on 12/9/16.
 */

import com.testcode.gameofthrones.models.GoTCharacter;
import com.testcode.gameofthrones.models.GoTHouse;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static org.mockito.Mockito.*;

public class MockitoTest {

    @Mock
    HomeActivity test;
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

}
