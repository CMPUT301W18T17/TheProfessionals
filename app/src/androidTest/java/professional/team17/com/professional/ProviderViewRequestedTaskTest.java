package professional.team17.com.professional;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.EditText;

import com.robotium.solo.Solo;

import java.util.ArrayList;

/**
 * Created by Zhipeng Zhang on 2018/3/16.
 */

public class ProviderViewRequestedTaskTest extends ActivityInstrumentationTestCase2<LogInActivity> {
    private Solo solo;
    private String taskDescription;

    public ProviderViewRequestedTaskTest() {
        super(LogInActivity.class);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void testRequesterAddTaskActivity(){

        taskDescription = "asdzxc";

        // Start
        solo.assertCurrentActivity("Wrong Activity", LogInActivity.class);
        solo.enterText((EditText) solo.getView(R.id.usernameBox), "zhipeng");
        solo.clickOnButton("Sign In");
        solo.clickOnView(solo.getView(R.id.switchViewProviderButton));
        solo.clickOnView(solo.getView(R.id.addTaskRequesterButton));
        solo.enterText((EditText) solo.getView(R.id.TaskNameField), "Task Name 1");
        solo.enterText((EditText) solo.getView(R.id.taskDescriptionField), taskDescription);
        solo.clickOnView(solo.getView(R.id.calendarButton));
        solo.clickOnText("OK");
        // For photo and map
        // Leave For now
        solo.enterText((EditText) solo.getView(R.id.textualAddressField), "My Location");
        solo.clickOnButton("Add");


        // Check
        solo.finishOpenedActivities();
        this.launchActivity("professional.team17.com.professional", LogInActivity.class, null);
        solo = new Solo(getInstrumentation(), getActivity());
        solo.enterText((EditText) solo.getView(R.id.usernameBox), "hailan333");
        solo.clickOnButton("Sign In");

        assertTrue(solo.waitForText("Task Name 1"));
        assertTrue(solo.waitForText("Requested"));
        assertTrue(solo.waitForText("zhipeng"));
    }
}
