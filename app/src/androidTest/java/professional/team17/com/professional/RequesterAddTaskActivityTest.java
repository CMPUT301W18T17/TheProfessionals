package professional.team17.com.professional;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;

import professional.team17.com.professional.Activity.RequesterAddTaskActivity;
import professional.team17.com.professional.Controllers.ServerHelper;
import professional.team17.com.professional.Entity.Profile;

/**
 * Created by Zhipeng Zhang on 2018/3/14.
 */

public class RequesterAddTaskActivityTest extends ActivityInstrumentationTestCase2<RequesterAddTaskActivity> {
    private Solo solo;
    private ServerHelper serverHelper;
    private Profile profile;


    public RequesterAddTaskActivityTest() {
        super(RequesterAddTaskActivity.class);
    }

    public void setUp() throws Exception{

        Context context = getInstrumentation().getTargetContext();
        profile = new Profile("Tester", "testUser1", "abc@abc.com", "110");
        serverHelper = new ServerHelper(context);
        serverHelper.addProfile(profile);

        solo = new Solo(getInstrumentation(), getActivity());
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("username", profile.getUserName()); // Storing string
        editor.commit();
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
        solo.clickOnView(solo.getView(R.id.taskMapProviderButton));
        solo.clickOnView(solo.getView(R.id.requester_requested_title));
        assertTrue(solo.waitForText("Task Name 1"));
        assertTrue(solo.waitForText("Requested"));
        assertTrue(solo.waitForText("Task Description"));
        solo.clickOnView(solo.getView(R.id.taskMapProviderButton));
        solo.clickOnView(solo.getView(R.id.deleteTaskButton));
        solo.clickOnText("Yes");
    }

    @Override
    public void tearDown() throws Exception {
        serverHelper.deleteProfile(profile);
        solo.finishOpenedActivities();
    }

}
