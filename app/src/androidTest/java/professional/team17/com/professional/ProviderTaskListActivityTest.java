package professional.team17.com.professional;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

// Example Robtium tests: http://www.vogella.com/tutorials/Robotium/article.html
// All Robotium Methods: http://recorder.robotium.com/javadoc/com/robotium/solo/Solo.html

public class ProviderTaskListActivityTest extends ActivityInstrumentationTestCase2<ProviderTaskListActivity> {
    private Solo solo;

    public ProviderTaskListActivityTest() {
        super(ProviderTaskListActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

    public void testViewOtherProfile() throws Exception {
        solo.assertCurrentActivity("WrongActivity", ProviderTaskListActivity.class);
        solo.clickInList(1);


    }

}
