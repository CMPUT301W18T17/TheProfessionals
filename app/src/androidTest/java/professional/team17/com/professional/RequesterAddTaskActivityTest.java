package professional.team17.com.professional;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;

/**
 * Created by Zhipeng Zhang on 2018/3/14.
 */

public class RequesterAddTaskActivityTest extends ActivityInstrumentationTestCase2<RequesterAddTaskActivity> {
    private Solo solo;

    public RequesterAddTaskActivityTest() {
        super(RequesterAddTaskActivity.class);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void testRequesterAddTaskActivity(){

        // Start
        solo.assertCurrentActivity("Wrong Activity", RequesterAddTaskActivity.class);
        solo.enterText((EditText) solo.getView(R.id.TaskNameField), "Task Name 1");
        solo.enterText((EditText) solo.getView(R.id.taskDescriptionField), "Task Description");
        //solo.clickOnButton("calendarButton");
        //solo.clickInList(1,4);
        // For photo and map
        // Leave For now
        solo.enterText((EditText) solo.getView(R.id.textualAddressField), "My Location");
        solo.clickOnButton("Add");

        // Check
        solo.clickOnView(solo.getView(R.id.requester_requested_title));
        solo.assertCurrentActivity("Wrong Activity", RequesterViewTaskActivity.class);
        assertTrue(solo.waitForText("Task Name 1"));
        assertTrue(solo.waitForText("Requested"));
        assertTrue(solo.waitForText("Task Description"));
        assertTrue(solo.waitForText("My Location"));
    }
}
