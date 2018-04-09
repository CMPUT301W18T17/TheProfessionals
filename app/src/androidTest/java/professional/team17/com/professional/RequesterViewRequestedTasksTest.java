package professional.team17.com.professional;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import professional.team17.com.professional.Activity.ProviderTaskListActivity;
import professional.team17.com.professional.Activity.RequesterEditTaskActivity;
import professional.team17.com.professional.Activity.RequesterViewListActivity;
import professional.team17.com.professional.Activity.RequesterViewTaskActivity;
import professional.team17.com.professional.Controllers.ServerHelper;
import professional.team17.com.professional.Entity.Bid;
import professional.team17.com.professional.Entity.Profile;
import professional.team17.com.professional.Entity.Task;

public class RequesterViewRequestedTasksTest extends ActivityInstrumentationTestCase2<RequesterViewListActivity> {
    private Solo solo;
    private ServerHelper serverHelper;
    private Profile profile;
    private Task task;

    /**
     * This test primarily checks the navigation in all of the requester view list navigations
     * @throws Exception
     */
    public RequesterViewRequestedTasksTest() throws Exception{
        super(RequesterViewListActivity.class);
    }

    public void setUp(){
        Context context = getInstrumentation().getTargetContext();
        serverHelper = new ServerHelper(context);

        profile = new Profile("__testName", "__testUsername",
                "testEmail@test.com", "111 111 1111");
        serverHelper.addProfile(profile);

        task = new Task("__testUsername", "__testTask", "__test description");
        serverHelper.addTasks(task);

//        Bid bid = new Bid("__testUsername", 10.00);
//        task.addBid(bid);
//        task.setStatus("Bidded");
//        serverHelper.updateTasks(task);

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

    public void testRequestedNavigation(){

        solo.assertCurrentActivity("Wrong Activity", RequesterViewListActivity.class);

        //in case of slow servers
        try {
            solo.waitForText("__testTask");
        } catch (Exception e){
            solo.clickOnView(solo.getView(R.id.requestedTaskRequesterButton));
        }

        solo.clickOnText("__testTask");

        solo.assertCurrentActivity("Wrong Activity", RequesterViewTaskActivity.class);

        solo.clickOnView(solo.getView(R.id.requestedTaskRequesterButton));
        solo.assertCurrentActivity("Wrong Activity", RequesterViewListActivity.class);

        solo.clickOnView(solo.getView(R.id.editTaskButton));
        solo.assertCurrentActivity("Wrong Activity", RequesterEditTaskActivity.class);

        solo.clickOnView(solo.getView(R.id.requestedTaskRequesterButton));
        solo.clickOnView(solo.getView(R.id.deleteTaskButton));

        solo.waitForText("Confirm");
        solo.waitForText("Cancel");
        solo.clickOnText("Cancel");
        solo.assertCurrentActivity("WrongActivity", RequesterViewListActivity.class);

        solo.clickOnView(solo.getView(R.id.deleteTaskButton));
        solo.clickOnText("Confirm");
        solo.assertCurrentActivity("Wrong Activity", RequesterViewListActivity.class);

    }

//    public void testBiddedNavigation(){
//        Bid bid = new Bid("__otherTester", 10.01);
//        task.addBid(bid);
//        task.setStatus("Bidded");
//        serverHelper.updateTasks(task);
//
//        solo.clickOnView(solo.getView(R.id.biddedTasksRequesterButton));
//        solo.assertCurrentActivity("Wrong Activity", RequesterViewListActivity.class);
//    }

    @Override
    public void tearDown(){
        serverHelper.deleteProfile(profile);

        solo.finishOpenedActivities();
    }
}
