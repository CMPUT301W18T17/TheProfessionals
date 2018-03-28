package professional.team17.com.professional;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;

/**
 * Created by Logan Yue on 2018-03-12.
 *
 * @see SignUpActivity
 */

public class SignUpActivityTest extends ActivityInstrumentationTestCase2<SignUpActivity> {

    private Solo solo;
    private ServerHelper serverHelper = new ServerHelper();

    /**
     * Test Constructor
     */
    public SignUpActivityTest(){
        super(SignUpActivity.class);
    }

    /**
     * Test set up, starting test on activity
     * @throws Exception
     */
    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    /**
     * tests to see if activity starts
     */
    public void testStart() {
        Activity activity = getActivity();
    }

    /**
     * tests sign up functionality and sign up error handling
     */
    public void testSignUp(){
        solo.assertCurrentActivity("Wrong Activity", SignUpActivity.class);
        //can only test error handling, as I cannot create a new profile every time we run the test
        //also, it seems redundant to test every combination of missing fields for the missing fields exception

        solo.clickOnButton("Sign Up");

        assertTrue(solo.waitForText("Please make sure all fields are filled"));

        Profile testProfile = new Profile("tester","TestUser",
                "tester@ualberta.ca","123-456-7890");
        serverHelper.addProfile(testProfile);


        solo.enterText((EditText) solo.getView(R.id.usernameBox), "TestUser");

        solo.enterText((EditText) solo.getView(R.id.fullNameBox), "tester");

        solo.enterText((EditText) solo.getView(R.id.emailBox), "tester@ualberta.ca");

        solo.enterText((EditText) solo.getView(R.id.phoneNumberBox), "123-456-7890");

        solo.clickOnButton("Sign Up");

        assertTrue(solo.waitForText("Username is already taken"));

        Profile testUser = serverHelper.getProfile("TestUser");
        serverHelper.deleteProfile(testUser);

        solo.clickOnButton("Sign Up");

        solo.assertCurrentActivity("Wrong Activity", SearchActivity.class);

        testUser = serverHelper.getProfile("TestUser");
        serverHelper.deleteProfile(testUser);

    }

    /**
     * tests the back functionality
     */
    public void testBack(){
        solo.assertCurrentActivity("Wrong Activity", SignUpActivity.class);

        solo.clickOnButton("Back");

        solo.assertCurrentActivity("Wrong Activity", LogInActivity.class);
    }

    /**
     * finishes tests
     * @throws Exception
     */
    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
