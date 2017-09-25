package br.com.marinho.thebestmoviesdb.home;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.marinho.thebestmoviesdb.R;
import br.com.marinho.thebestmoviesdb.repository.API.APISettings;
import br.com.marinho.thebestmoviesdb.ui.home.HomeActivity;
import okhttp3.mockwebserver.MockWebServer;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Marinho on 25/09/17.
 */

@RunWith(AndroidJUnit4.class)
public class HomeTest extends InstrumentationTestCase {

    private MockWebServer server;

    private static final String titleMovie = "It: A Coisa";

    @Rule
    public ActivityTestRule<HomeActivity> rule =
            new ActivityTestRule<>(HomeActivity.class, true, false);


    @Before
    public void setUp() throws Exception {
        super.setUp();
        server = new MockWebServer();
        server.start();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        APISettings.API_BASE_URL = server.url("/").toString();

    }

    private void startActivity(){
        Intent intent = new Intent();
        rule.launchActivity(intent);
    }

    @Test
    public void shouldShowMoviesList() throws Exception{
        new HomeMockApi(server, getInstrumentation().getContext()).responseNowPlayingMovie();

        startActivity();

        doWait();

        viewItemMovie().check(matches(isDisplayed()));

    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }

    private ViewInteraction viewItemMovie(){
        return onView(withText(titleMovie));
    }

    public void doWait(){
        try{
            Thread.sleep(5000);
        }catch(InterruptedException ie){
            throw new RuntimeException("Could not sleep", ie);
        }
    }

}
