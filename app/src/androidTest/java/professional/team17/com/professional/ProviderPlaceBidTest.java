package professional.team17.com.professional;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;

/**
 * Created by kaixiangzhang on 2018-03-13.
 */

public class ProviderPlaceBidTest extends ActivityInstrumentationTestCase2<SearchActivity> {
    private Solo solo;
    private ElasticSearchController elasticSearchController = new ElasticSearchController();
    private MockTask mockTask;
    private Profile testProfile;

    public ProviderPlaceBidTest() {
        super(SearchActivity.class);
    }

    public void setUp() throws Exception {
        MockTask mockTask = new MockTask("kaixiang", "kaixiang's task", "Test Description", "Test Location", "01/01/2000");
        ElasticSearchController mockES = new ElasticSearchController();
        String ID = mockES.addTasks(mockTask);
        mockTask.setId(ID);
        Profile testProfile = new Profile("kaixiang","TestUser1", "tester@ualberta.ca","123-456-7890");
        elasticSearchController.addProfile(testProfile);
        /*Intent i = new Intent();
        i.putExtra("Status", "Bidded");
        setActivityIntent(i);
        */
        solo = new Solo(getInstrumentation(), getActivity());
        Context context = getInstrumentation().getTargetContext();
        SharedPreferences pref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString("username", "TestUser1"); // Storing string
        editor.commit();

    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void testAddDeleteBid() {
        solo.assertCurrentActivity("Wrong Activity", SearchActivity.class);
        //solo.enterText((EditText) solo.getView(R.id.usernameBox), "kaixiang");
        //solo.clickOnButton("Sign In");
        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong Activity", ProviderViewTask.class);
        //check add a new bid.
        solo.clickOnImageButton(0);
        solo.enterText((EditText) solo.getView(R.id.place_bid_fragment_bid_input), "66.6");
        solo.clickOnButton("Add");
        assertTrue(solo.waitForText("66.6"));
        solo.assertCurrentActivity("Wrong Activity", ProviderViewTask.class);
        //check edit my bid.
        solo.clickOnImageButton(1);
        solo.clearEditText((EditText) solo.getView(R.id.place_bid_fragment_bid_input));
        solo.enterText((EditText) solo.getView(R.id.place_bid_fragment_bid_input), "77.7");
        solo.clickOnButton("Add");
        assertTrue(solo.waitForText("77.7"));
        solo.assertCurrentActivity("Wrong Activity", ProviderViewTask.class);
        //check delete bid.
        solo.clickOnImageButton(0);
        solo.clickOnButton("Yes");
        solo.assertCurrentActivity("Wrong Activity", ProviderViewTask.class);





    }

    //public void testDeleteButton() {
        //solo.assertCurrentActivity("Wrong Activity", ProviderViewTask.class);
        //solo.clickOnButton("provider_view_task_AddBid");
        //solo.assertCurrentActivity("Wrong Activity", PlaceBidDialog.class);
        //solo.clickOnButton("Dismiss");
        //solo.assertCurrentActivity("Wrong Activity", ProviderViewTask.class);

    //}

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        elasticSearchController.deleteProfile(testProfile);
        elasticSearchController.deleteTasks(mockTask);

    }
}

