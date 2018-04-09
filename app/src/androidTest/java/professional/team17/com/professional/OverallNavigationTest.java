package professional.team17.com.professional;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import professional.team17.com.professional.Activity.RequesterViewListActivity;
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
    }

    @Override
    public void tearDown(){
        serverHelper.deleteProfile(profile);

        solo.finishOpenedActivities();
    }
}
