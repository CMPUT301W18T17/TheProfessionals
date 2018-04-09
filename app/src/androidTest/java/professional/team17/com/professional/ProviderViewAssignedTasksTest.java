package professional.team17.com.professional;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import professional.team17.com.professional.Activity.ProviderTaskListActivity;
import professional.team17.com.professional.Activity.ProviderViewTask;
import professional.team17.com.professional.Controllers.ServerHelper;
import professional.team17.com.professional.Entity.Bid;
import professional.team17.com.professional.Entity.Profile;
import professional.team17.com.professional.Entity.Task;

public class ProviderViewAssignedTasksTest extends ActivityInstrumentationTestCase2 {

    private Solo solo;
    private ServerHelper serverHelper;
    private Profile profile;
    private Task task;

    public ProviderViewAssignedTasksTest() throws Exception{
        super(ProviderTaskListActivity.class);
    }

    public void setUp(){
        Context context = getInstrumentation().getTargetContext();
        serverHelper = new ServerHelper(context);

        profile = new Profile("__testName", "__testUsername",
                "testEmail@test.com", "111 111 1111");
        serverHelper.addProfile(profile);

        task = new Task("__otherTester", "__testTask", "__test description");
        serverHelper.addTasks(task);

        Bid bid = new Bid("__testUsername", 10.00);
        task.addBid(bid);
        task.setStatus("Assigned");
        serverHelper.updateTasks(task);

        SharedPreferences pref = context.getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("username", profile.getUserName()); // Storing string
        editor.commit();

        Intent i = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("Status", "Assigned");
        i.putExtras(bundle);
        setActivityIntent(i);
        solo = new Solo(getInstrumentation(),getActivity());
    }

    public void testStart(){
        Activity activity = getActivity();
    }

    public void testNavigation(){
        solo.assertCurrentActivity("Wrong Activity",ProviderTaskListActivity.class);

//        solo.waitForText("__testTask");
        solo.clickOnText("__testTask");

        solo.assertCurrentActivity("Wrong Activity", ProviderViewTask.class);
    }

    @Override
    public void tearDown(){
        serverHelper.deleteTasks(task);
        serverHelper.deleteProfile(profile);

        solo.finishOpenedActivities();
    }
}