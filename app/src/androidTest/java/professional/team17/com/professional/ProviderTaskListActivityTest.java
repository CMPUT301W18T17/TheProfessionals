package professional.team17.com.professional;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.robotium.solo.Solo;

// Example Robtium tests: http://www.vogella.com/tutorials/Robotium/article.html
// All Robotium Methods: http://recorder.robotium.com/javadoc/com/robotium/solo/Solo.html

public class ProviderTaskListActivityTest extends ActivityInstrumentationTestCase2<ProviderTaskListActivity> {
    private Solo solo;
    private MockTask mockTask;
    private ElasticSearchController mockES;
    private Profile mockProfile;
    private TaskList tasklist;

    private String username = "testuser";
    private String id;


    public ProviderTaskListActivityTest() {

        super(ProviderTaskListActivity.class);
        Log.i("START2", "testStart: ");

    }


    public void setUp() throws Exception {
        Log.i("START1", "testStart: ");
        Intent i = new Intent();
        i.putExtra("Status", "Bidded");
        setActivityIntent(i);
        Context context = getInstrumentation().getTargetContext();
        SharedPreferences pref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("username", "testuser"); // Storing string
        editor.commit();
        mockES = new ElasticSearchController();
        mockProfile = new Profile("kaixiang","TestUser1", "tester@ualberta.ca","123-456-7890");
        mockES.addProfile(mockProfile);
        mockTask = new MockTask("TestUser1", "kaixiang's task tasklist", "Test Description", "Test Location", "01/01/2000");
        mockTask.addBid(new Bid(username, 45.67));
        id = mockES.addTasks(mockTask);
        Log.i("WEWE", "setUpger: "+id);
        mockTask.setId(id);

        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception {

        Log.i("START3", "testStart: ");
        Activity activity = getActivity();

    }

    @Override
    public void tearDown() throws Exception {
        Log.i("START4", "testStart: ");
        solo.finishOpenedActivities();
        Log.i("START5", "testStart: ");
       //mockES.deleteTasks(mockTask);
        //mockES.deleteProfile(mockProfile);

    }

    public void testViewOtherProfile() throws Exception {
        Log.i("START6", "testStart: ");
        tasklist = mockES.getTasksBidded(username, "Bidded");
        solo.assertCurrentActivity("Wrong Activity", ProviderTaskListActivity.class);
        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong Activity", ProviderViewTask.class);


        solo.clickOnView(solo.getView(R.id.provider_view_task_userName));
        solo.assertCurrentActivity("Wrong Activity", OtherProfileViewActivity.class);
        solo.clickOnButton("DONE");
        solo.assertCurrentActivity("Wrong  Activity", ProviderViewTask.class);

    }



}
