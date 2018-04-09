package professional.team17.com.professional;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.SearchView;

import com.robotium.solo.Solo;


import professional.team17.com.professional.Activity.ProviderViewTask;
import professional.team17.com.professional.Activity.SearchActivity;
import professional.team17.com.professional.Controllers.ElasticSearchController;
import professional.team17.com.professional.Controllers.ServerHelper;
import professional.team17.com.professional.Entity.Profile;
import professional.team17.com.professional.Entity.Task;

/**
 * Created by kaixiangzhang on 2018-03-13.
 */


public class ProviderViewTaskTest extends ActivityInstrumentationTestCase2<ProviderViewTask> {
    private Solo solo;
    private Task mockTask;
    private Profile testProfile ;
    private Profile testTaskProfile;
    private ServerHelper serverHelper;

    public ProviderViewTaskTest() {
        super(ProviderViewTask.class);
    }

    public void setUp() throws Exception {

        Context context = getInstrumentation().getTargetContext();
        serverHelper = new ServerHelper(context);

        mockTask = new Task("testUser", "test task", "Test Description");
        serverHelper.addTasks(mockTask);

        testTaskProfile = new Profile("Tester","testUser", "testUser@ualberta.ca","123-456-7890");
        serverHelper.addProfile(testTaskProfile);

        testProfile = new Profile("user","tester", "tester@ualberta.ca","123-456-7890");
        serverHelper.addProfile(testProfile);

        SharedPreferences pref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("username", "Tester"); // Storing string
        editor.commit();

        Intent i = new Intent();
        i.putExtra("ID", mockTask.getUniqueID());
        setActivityIntent(i);

        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void testAddDeleteBid() {
        solo.assertCurrentActivity("Wrong Activity", ProviderViewTask.class);

//        //check add a new bid.
        solo.clickOnView(solo.getView(R.id.provider_view_task_AddBid));
        solo.enterText((EditText) solo.getView(R.id.place_bid_fragment_bid_input), "66.67");
        solo.clickOnButton("Add");
        assertTrue(solo.waitForText("66.67"));
//        solo.assertCurrentActivity("Wrong Activity", ProviderViewTask.class);
//        //check edit my bid.
        solo.clickOnView(solo.getView(R.id.provider_view_task_manageBid));
        solo.clearEditText((EditText) solo.getView(R.id.place_bid_fragment_bid_input));
        solo.enterText((EditText) solo.getView(R.id.place_bid_fragment_bid_input), "77.70");
        solo.clickOnButton("Add");
        assertTrue(solo.waitForText("77.70"));
//        solo.assertCurrentActivity("Wrong Activity", ProviderViewTask.class);
//        //check delete bid.
        solo.clickOnView(solo.getView(R.id.provider_view_task_removeBid));
        solo.clickOnButton("Confirm");
        solo.waitForText("Requested");
        solo.waitForText("No bids yet");
//        solo.assertCurrentActivity("Wrong Activity", ProviderViewTask.class);
    }


    @Override
    public void tearDown() throws Exception {
        serverHelper.deleteProfile(testProfile);
        serverHelper.deleteProfile(testTaskProfile);
        serverHelper.deleteTasks(mockTask);

        solo.finishOpenedActivities();
    }
}

