package professional.team17.com.professional;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.EditText;

import com.robotium.solo.Solo;

import java.util.ArrayList;

/**
 * Created by Zhipeng Zhang on 2018/3/16.
 */

public class ProviderViewRequestedTaskTest extends ActivityInstrumentationTestCase2<SearchActivity> {
    private Solo solo;
    private String taskDescription;
    private MockTask mockTask;
    private Profile profile;
    private ElasticSearchController elasticSearchController_1, elasticSearchController_2;
    private SharedPreferences sharedPreferences;

    public ProviderViewRequestedTaskTest() {
        super(SearchActivity.class);
    }

    public void setUp() throws Exception{
        mockTask = new MockTask("TestUser", "Test Name 1", "Task Description", "Task Location", "19/03/2018");
        elasticSearchController_1 = new ElasticSearchController();
        String ID = elasticSearchController_1.addTasks(mockTask);
        mockTask.setId(ID);
        profile = new Profile("Tester", "TestUser1", "abc@abc.com", "110");
        elasticSearchController_2 = new ElasticSearchController();
        elasticSearchController_2.addProfile(profile);

        solo = new Solo(getInstrumentation(), getActivity());

        Context context = getInstrumentation().getTargetContext();
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
        solo.clickOnView(solo.getView(R.id.searchTasksButton));

        assertTrue(solo.waitForText("Test Name 1"));
        assertTrue(solo.waitForText("Requested"));
        assertTrue(solo.waitForText("TestUser"));
    }


    @Override
    public void tearDown() throws Exception {
        elasticSearchController_2.deleteTasks(mockTask);
        elasticSearchController_2.deleteProfile(profile);
        solo.finishOpenedActivities();
    }
}
