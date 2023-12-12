package com.fac.firebasecrud;

import static org.junit.Assert.*;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.robolectric.RobolectricTestRunner;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RuntimeEnvironment;
@RunWith(MockitoJUnitRunner.class)
public class MainActivityTest {

    @Mock
    private DatabaseReference mockedDatabaseReference;
    private MainActivity mainActivityUnderTest;

    @Before
    public void setUp() {
        mainActivityUnderTest = new MainActivity();
        AutoCloseable closeable = MockitoAnnotations.openMocks(this);
        FirebaseApp.initializeApp(RuntimeEnvironment.getApplication());
        mainActivityUnderTest.myRef = mockedDatabaseReference;
    }

    @Test
    public void testCreateData()
    {
        User usertest = new User("1", "test", 20, 1000, false);

        Mockito.when(mockedDatabaseReference.push()).thenReturn(mockedDatabaseReference);
        Mockito.when(mockedDatabaseReference.getKey()).thenReturn("1");

        mainActivityUnderTest.createData();

        Mockito.verify(mockedDatabaseReference).setValue(usertest);
    }
}