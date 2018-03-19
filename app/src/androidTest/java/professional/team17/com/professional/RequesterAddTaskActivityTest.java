package professional.team17.com.professional;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.EditText;

import com.robotium.solo.Solo;

import java.util.ArrayList;

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
        solo.clickOnView(solo.getView(R.id.calendarButton));
        solo.clickOnText("OK");
        // For photo and map
        // Leave For now
        solo.clickOnButton("Add");

        // Check
        solo.clickOnView(solo.getView(R.id.requestedTasksRequesterButton));
        solo.clickOnView(solo.getView(R.id.requester_requested_title));
        assertTrue(solo.waitForText("Task Name 1"));
        assertTrue(solo.waitForText("Requested"));
        assertTrue(solo.waitForText("Task Description"));
    }
}
