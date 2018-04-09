package professional.team17.com.professional;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;

import com.robotium.solo.Solo;

import professional.team17.com.professional.Activity.ProviderViewTask;
import professional.team17.com.professional.Activity.SearchActivity;
import professional.team17.com.professional.Controllers.ServerHelper;
import professional.team17.com.professional.Entity.Profile;
import professional.team17.com.professional.Entity.Task;

public class SearchActivityTest extends ActivityInstrumentationTestCase2<SearchActivity> {

    private Solo solo;
    private ServerHelper serverHelper;
    private Profile testProfile;
    private Task testTask;
    private Context context;


    public SearchActivityTest() {
        super(SearchActivity.class);
    }

    public void setUp()throws Exception {

        context = getInstrumentation().getTargetContext();
        serverHelper = new ServerHelper(context);


        testTask = new Task("tester", "Test Title", "__Test Description");
        serverHelper.addTasks(testTask);

        testProfile = new Profile("tester","testUser",
                "tester@ualberta.ca","123-456-7890");
        serverHelper.addProfile(testProfile);

        SharedPreferences pref = context.getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("username", testProfile.getUserName()); // Storing string
        editor.commit();

        solo = new Solo(getInstrumentation(),getActivity());
    }

    public void testStart(){
        Activity activity = getActivity();
    }

    public void testNavigation(){
        solo.assertCurrentActivity("Wrong Activity", SearchActivity.class);

        solo.clickOnText("Test Title");
        solo.assertCurrentActivity("Wrong Activity", ProviderViewTask.class);

        solo.waitForText("tester");
        solo.waitForText("Test Title");
        solo.waitForText("__Test Description");
    }

    //At this point in time, I do not believe that there is a way to interact with searchViews using
    //Solo and as such this test is not possible
/*
public void testSearchBar(){
solo.assertCurrentActivity("Wrong Activity", SearchActivity.class);

solo.clickOnView(solo.getView(R.id.Search_Activity_Input));
}
*/

    @Override
    public void tearDown() throws Exception{
        serverHelper.deleteProfile(testProfile);
        serverHelper.deleteTasks(testTask);
        solo.finishOpenedActivities();
    }

}
