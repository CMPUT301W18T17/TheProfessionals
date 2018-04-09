package professional.team17.com.professional;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import professional.team17.com.professional.Activity.EditMyProfileActivity;
import professional.team17.com.professional.Activity.LogInActivity;
import professional.team17.com.professional.Activity.MapsActivity;
import professional.team17.com.professional.Activity.MapsSearchTasksActivity;
import professional.team17.com.professional.Activity.MyProfileViewActivity;
import professional.team17.com.professional.Activity.NotificationActivity;
import professional.team17.com.professional.Activity.ProfileViewActivity;
import professional.team17.com.professional.Activity.ProviderTaskListActivity;
import professional.team17.com.professional.Activity.RequesterAddTaskActivity;
import professional.team17.com.professional.Activity.RequesterViewListActivity;
import professional.team17.com.professional.Activity.RequesterViewTaskActivity;
import professional.team17.com.professional.Activity.SearchActivity;
import professional.team17.com.professional.Controllers.ServerHelper;
import professional.team17.com.professional.Entity.Profile;
import professional.team17.com.professional.Entity.Task;

/**
 * This test will test our bottom navigation as well as profile/notification/logout navigation
 */
public class OverallNavigationTest extends ActivityInstrumentationTestCase2<RequesterViewListActivity> {

    private Solo solo;
    private ServerHelper serverHelper;
    private Profile profile;

    public OverallNavigationTest(){
        super(RequesterViewListActivity.class);
    }

    public void setUp(){

        Context context = getInstrumentation().getTargetContext();
        serverHelper = new ServerHelper(context);

        profile = new Profile("__testName", "__testUsername",
                "testEmail@test.com", "111 111 1111");
        serverHelper.addProfile(profile);

        SharedPreferences pref = context.getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("username", profile.getUserName()); // Storing string
        editor.commit();

        Intent i = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("Status", "Requested");
        i.putExtras(bundle);
        setActivityIntent(i);
        solo = new Solo(getInstrumentation(),getActivity());
    }

    public void testStart(){
        Activity activity = getActivity();
    }

    public void testNavigation(){
        solo.assertCurrentActivity("Wrong Activity", RequesterViewListActivity.class);

        solo.clickOnView(solo.getView(R.id.addTaskRequesterButton));
        solo.assertCurrentActivity("Wrong Activity", RequesterAddTaskActivity.class);
        solo.waitForText("Add a Task");

        solo.clickOnView(solo.getView(R.id.requestedTaskRequesterButton));
        solo.assertCurrentActivity("Wrong Activity", RequesterViewListActivity.class);
        solo.waitForText("My Requested Tasks");

        solo.clickOnView(solo.getView(R.id.biddedTasksRequesterButton));
        solo.assertCurrentActivity("Wrong Activity", RequesterViewListActivity.class);
        solo.waitForText("My Bidded Tasks");

        solo.clickOnView(solo.getView(R.id.acceptedTasksRequesterButton));
        solo.assertCurrentActivity("Wrong Activity", RequesterViewListActivity.class);
        solo.waitForText("My Assigned Tasks");

        solo.clickOnView(solo.getView(R.id.doneTasksRequesterButton));
        solo.assertCurrentActivity("Wrong Activity", RequesterViewListActivity.class);
        solo.waitForText("My Completed Tasks");

        solo.clickOnView(solo.getView(R.id.switchViewRequesterButton));
        solo.assertCurrentActivity("Wrong Activity", ProviderTaskListActivity.class);

//        solo.clickOnView(solo.getView(R.id.taskSearchProviderButton));
//        solo.assertCurrentActivity("Wrong Activity", SearchActivity.class);

        solo.clickOnView(solo.getView(R.id.biddedTasksProviderButton));
        solo.assertCurrentActivity("Wrong Activity", ProviderTaskListActivity.class);
        solo.waitForText("Bidded Tasks");

        solo.clickOnView(solo.getView(R.id.acceptedTasksProviderButton));
        solo.assertCurrentActivity("Wrong Activity", ProviderTaskListActivity.class);
        solo.waitForText("Assigned Tasks");

        solo.clickOnView(solo.getView(R.id.doneTasksProviderButton));
        solo.assertCurrentActivity("Wrong Activity", ProviderTaskListActivity.class);
        solo.waitForText("Completed Tasks");

//        solo.clickOnView(solo.getView(R.id.taskMapProviderButton));
//        solo.waitForActivity(MapsSearchTasksActivity.class);
//        solo.assertCurrentActivity("Wrong Activity", MapsSearchTasksActivity.class);
//
//        solo.clickOnView(solo.getView(R.id.ic_close));
//        solo.assertCurrentActivity("Wrong Activity", ProviderTaskListActivity.class);

//        solo.clickOnView(solo.getView(R.id.switchViewProviderButton));
//        solo.waitForActivity(RequesterViewListActivity.class);
//        solo.assertCurrentActivity("Wrong Activity", RequesterViewListActivity.class);

        solo.clickOnView(solo.getView(R.id.userMenuButton));
//        solo.waitForText("Edit My Profile");
        solo.clickOnText("Edit My Profile");
        solo.assertCurrentActivity("Wrong Activity", EditMyProfileActivity.class);
        solo.clickOnView(solo.getView(R.id.cancelButton));

        //cannot test this feature due to the limitations of clickOnText and clickOnView
        solo.clickOnView(solo.getView(R.id.userMenuButton));
        solo.clickOnText("My Profile", 2);
        solo.assertCurrentActivity("Wrong Activity", MyProfileViewActivity.class);
        solo.clickOnView(solo.getView(R.id.doneButton));

        solo.clickOnView(solo.getView(R.id.userMenuButton));
        solo.clickOnText("My Notifications");
        solo.assertCurrentActivity("Wrong Activity", NotificationActivity.class);
        solo.clickOnView(solo.getView(R.id.notificationBackButton));

        solo.clickOnView(solo.getView(R.id.userMenuButton));
        solo.clickOnText("Log Out");
        solo.assertCurrentActivity("Wrong Activity", LogInActivity.class);
    }

    @Override
    public void tearDown(){
        serverHelper.deleteProfile(profile);

        solo.finishOpenedActivities();
    }
}
