package professional.team17.com.professional;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import professional.team17.com.professional.Activity.SearchActivity;
import professional.team17.com.professional.Controllers.ServerHelper;
import professional.team17.com.professional.Entity.Profile;
import professional.team17.com.professional.Entity.Task;

/**
 * Created by Zhipeng Zhang on 2018/3/16.
 */

public class ProviderViewRequestedTaskTest extends ActivityInstrumentationTestCase2<SearchActivity> {
    private Solo solo;
    private String taskDescription;
    private Task mockTask;
    private Profile profile;
    private ServerHelper serverHelper_1, serverHelper_2;
    private SharedPreferences sharedPreferences;

    public ProviderViewRequestedTaskTest() {
        super(SearchActivity.class);
    }

    public void setUp() throws Exception{
        Context context = getInstrumentation().getTargetContext();
        mockTask = new Task("testUser", "Test Name 1", "Task Description");
        serverHelper_1 = new ServerHelper(context);
        String ID = serverHelper_1.addTasks(mockTask);
        mockTask.setId(ID);
        profile = new Profile("Tester", "tester", "abc@abc.com", "110");
        serverHelper_2 = new ServerHelper(context);
        serverHelper_2.addProfile(profile);

        solo = new Solo(getInstrumentation(), getActivity());


        sharedPreferences = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("username", "TestUser1"); // Storing string
        editor.commit();

    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void testProviderViewRequestedTaskActivity(){
        // Start
        solo.assertCurrentActivity("Wrong Activity", SearchActivity.class);
        //solo.clickOnView(solo.getView(R.id.searchTasksButton));

        assertTrue(solo.waitForText("Test Name 1"));
        assertTrue(solo.waitForText("Requested"));
        assertTrue(solo.waitForText("TestUser"));
    }


    @Override
    public void tearDown() throws Exception {
        serverHelper_2.deleteTasks(mockTask);
        serverHelper_2.deleteProfile(profile);
        solo.finishOpenedActivities();
    }
}
