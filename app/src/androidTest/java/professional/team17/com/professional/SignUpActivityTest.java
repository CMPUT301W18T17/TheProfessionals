package professional.team17.com.professional;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;

/**
 * Created by Logan Yue on 2018-03-12.
 */

public class SignUpActivityTest extends ActivityInstrumentationTestCase2<SignUpActivity> {

    private Solo solo;

    public SignUpActivityTest(){
        super(SignUpActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() {
        Activity activity = getActivity();
    }

    public void testSignUp(){
        solo.assertCurrentActivity("Wrong Activity", SignUpActivity.class);
        //can only test error handling, as I cannot create a new profile every time we run the test
        //also, it seems redundant to test every combination of missing fields for the missing fields exception

        solo.clickOnButton("Sign Up");

        assertTrue(solo.waitForText("Please make sure all fields are filled"));

        solo.enterText((EditText) solo.getView(R.id.usernameBox), "TestUser");

        solo.enterText((EditText) solo.getView(R.id.fullNameBox), "tester");

        solo.enterText((EditText) solo.getView(R.id.emailBox), "tester@ualberta.ca");

        solo.enterText((EditText) solo.getView(R.id.phoneNumberBox), "123-456-7890");

        solo.clickOnButton("Sign Up");

        assertTrue(solo.waitForText("Username is already taken"));
    }

    public void testBack(){
        solo.assertCurrentActivity("Wrong Activity", SignUpActivity.class);

        solo.clickOnButton("Back");

        solo.assertCurrentActivity("Wrong Activity", LogInActivity.class);
    }
}
