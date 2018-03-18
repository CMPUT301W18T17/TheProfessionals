package professional.team17.com.professional;

import android.app.Activity;
import android.graphics.LightingColorFilter;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;

/**
 * Created by Logan Yue on 2018-03-12.
 */

public class LogInActivityTest extends ActivityInstrumentationTestCase2<LogInActivity> {

    private Solo solo;

    public LogInActivityTest() {
        super(LogInActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void testLogIn(){
        solo.assertCurrentActivity("Wrong Activity", LogInActivity.class);
        solo.clickOnButton("Sign In");
        assertTrue(solo.waitForText("Please Enter a Username"));

        solo.enterText((EditText) solo.getView(R.id.usernameBox), "Non-Existent User");
        solo.clickOnButton("Sign In");
        assertTrue(solo.waitForText("Username does not exist"));

        solo.clearEditText((EditText) solo.getView(R.id.usernameBox));
        //I have created the TestUser Profile for the purposes of testing
        solo.enterText((EditText) solo.getView(R.id.usernameBox), "TestUser");
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong Activity", SearchActivity.class);
    }

    public void testSignUp(){
        LogInActivity activity = (LogInActivity) solo.getCurrentActivity();

        solo.assertCurrentActivity("Wrong Activity", LogInActivity.class);
        solo.clickOnButton("Sign Up");
        solo.assertCurrentActivity("Wrong Activity", SignUpActivity.class);
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
