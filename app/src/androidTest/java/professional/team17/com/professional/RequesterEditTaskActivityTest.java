/*
 * RequesterEditTaskActivityTest
 *
 * March 14, 2018
 *
 * Copyright @ 2018 Team 17, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behaviour at the University of Alberta.
 * You can find a copy of the license in the github wiki for this project.
 *
 */

package professional.team17.com.professional;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;

/**
 * An intent testing class to test RequesterEditTaskActivity.
 * @author Lauren
 * @see RequesterEditTaskActivity
 */
public class RequesterEditTaskActivityTest extends ActivityInstrumentationTestCase2<RequesterEditTaskActivity> {
    private Solo solo;
    private Task mockTask;
    private ServerHelper mockES;
    private Profile testProfile;

    /**
     * Constructor for the test.
     */
    public RequesterEditTaskActivityTest() {
        super(RequesterEditTaskActivity.class);
    }

    /**
     * Set up the test.
     * @throws Exception
     */
    public void setUp() throws Exception {
        /* Create mock task and push to server */
        Context context = getInstrumentation().getTargetContext();
        mockTask = new Task("TestUser", "Test Title", "Test Description");
        mockES = new ServerHelper(context);
        String ID = mockES.addTasks(mockTask);
        testProfile = new Profile("tester","TestUser",
                "tester@ualberta.ca","123-456-7890");
        mockES.addProfile(testProfile);
        mockTask.setId(ID);

        SharedPreferences pref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString("username", "TestUser"); // Storing string
        editor.commit();
        /* Put the task ID in the intent */
        /* Adapted from https://stackoverflow.com/questions/8335239/android-robotium-test-activity-that-expects-an-extra */
        Intent i = new Intent();
        i.putExtra("ID", mockTask.getUniqueID());
        setActivityIntent(i);
        /* Make solo object */
        solo = new Solo(getInstrumentation(), getActivity());

    }

    public void testStart() {
        /* Build activity */
        Activity activity = getActivity();
    }



    /**
     * Tests to see what happens if the edit fields are left blank
     */
    public void testLeaveEditFieldsBlank() {
        mockES.updateTasks(mockTask);
        /* Check that this is the right activity */
        solo.assertCurrentActivity("Wrong Activity", RequesterEditTaskActivity.class);
        /* Press the submit button without changing anything */
        solo.clickOnView(solo.getView(R.id.submitButton));
        /* Check that the submit button leads to the right place */
        solo.assertCurrentActivity("Wrong activity after pressing submit", RequesterViewListActivity.class);
        solo.clickOnView(solo.getView(R.id.requester_requested_title));
        /* Check that clicking on the task title leads to the right place */
        solo.assertCurrentActivity("Wrong activity after clicking on task title", RequesterViewTaskActivity.class);
        assertTrue(solo.waitForText("Test Title"));
        assertTrue(solo.waitForText("Test Description"));
        //don't display description
        //assertTrue(solo.waitForText("Test Location"));
        assertTrue(solo.waitForText("01/01/2000"));
    }

    public void testFillEditFields() {
        /* Check that this is the right activity */
        solo.assertCurrentActivity("Wrong Activity", RequesterEditTaskActivity.class);
        /*Change text fields */
        solo.enterText((EditText) solo.getView(R.id.TaskNameField), "Test Title Changed");
        solo.enterText((EditText) solo.getView(R.id.taskDescriptionField), "Test Description Changed");
        solo.enterText((EditText) solo.getView(R.id.textualAddressField), "Test Location Changed");
        solo.clickOnView(solo.getView(R.id.calendarButton));
        /* Locate datepicker and set date */
        solo.getCurrentActivity().getFragmentManager().findFragmentByTag("datePicker");
        //Month is 0 indexed for some reason so 02 will be 03
        solo.setDatePicker(0, 2001, 02, 02);
        solo.clickOnText("OK");
        /* Check that the submit button leads to the right place */
        solo.clickOnButton("Edit");
        solo.assertCurrentActivity("Wrong activity after pressing submit", RequesterViewListActivity.class);
        solo.clickOnView(solo.getView(R.id.requester_requested_title));
        /* Check that clicking on the task title leads to the right place */
        solo.assertCurrentActivity("Wrong activity after clicking on task title", RequesterViewTaskActivity.class);
        assertTrue(solo.waitForText("Test Title Changed"));
        assertTrue(solo.waitForText("Test Description Changed"));
        //don't have location display
        //assertTrue(solo.waitForText("Test Location Changed"));
        assertTrue(solo.waitForText("02/03/2001"));
    }
    /**
     * Tear down the test.
     */
    @Override
    public void tearDown(){
        mockES.deleteTasks(mockTask);
        mockES.deleteProfile(testProfile);
        solo.finishOpenedActivities();

    }

}
