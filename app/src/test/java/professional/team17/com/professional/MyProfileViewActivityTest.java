package professional.team17.com.professional;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

/**
 * Created by Hailan on 2018-03-13.
 */

public class MyProfileViewActivityTest extends ActivityInstrumentationTestCase2<MyProfileViewActivity> {
    private Solo solo;

    public MyProfileViewActivityTest() {
        super(MyProfileViewActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() {
        Activity activity = getActivity();
    }

    public void testGetMyProfile(){
        solo.assertCurrentActivity("Wrong Activity", MyProfileViewActivity.class);

    }
}