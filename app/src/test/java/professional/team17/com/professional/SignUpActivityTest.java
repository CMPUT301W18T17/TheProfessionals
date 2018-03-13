package professional.team17.com.professional;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

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
        
    }
}
