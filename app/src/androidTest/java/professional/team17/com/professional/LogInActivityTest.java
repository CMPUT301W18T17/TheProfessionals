package professional.team17.com.professional;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;

import professional.team17.com.professional.Activity.LogInActivity;
import professional.team17.com.professional.Activity.SearchActivity;
import professional.team17.com.professional.Activity.SignUpActivity;
import professional.team17.com.professional.Controllers.ServerHelper;
import professional.team17.com.professional.Entity.Profile;

/**
 *
 * @see LogInActivity
 */

public class LogInActivityTest extends ActivityInstrumentationTestCase2<LogInActivity> {

    private Solo solo;

    /**
     * test constructor
     */
    public LogInActivityTest() {
        super(LogInActivity.class);
    }

    /**
     * Standard test setUp
     */
    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    /**
     * tests activity start
     */
    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    /**
     * tests logIn functionality and error handling
     */
    public void testLogIn(){
        Context context = getInstrumentation().getTargetContext();
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.apply();
        solo.assertCurrentActivity("Wrong Activity", LogInActivity.class);
        solo.clickOnButton("Sign In");
        assertTrue(solo.waitForText("Please Enter a Username"));

        solo.enterText((EditText) solo.getView(R.id.usernameBox), "Non-Existent User");
        solo.clickOnButton("Sign In");
        assertTrue(solo.waitForText("Username does not exist"));

        solo.clearEditText((EditText) solo.getView(R.id.usernameBox));

        Profile testProfile = new Profile("tester","TestUser",
                "tester@ualberta.ca","123-456-7890");

        ServerHelper serverHelper = new ServerHelper(context);
        serverHelper.addProfile(testProfile);
        solo.enterText((EditText) solo.getView(R.id.usernameBox), "TestUser");
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong Activity", SearchActivity.class);
        serverHelper.deleteProfile(testProfile);
        pref = context.getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        editor = pref.edit();
        editor.clear();
        editor.apply();
    }

    /**
     * tests sign up button functionality
     */
    public void testSignUp(){
        LogInActivity activity = (LogInActivity) solo.getCurrentActivity();

        solo.assertCurrentActivity("Wrong Activity", LogInActivity.class);
        solo.clickOnButton("Sign Up");
        solo.assertCurrentActivity("Wrong Activity", SignUpActivity.class);
    }

    /**
     * standard tearDown to end tests
     */
    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();

    }
}
