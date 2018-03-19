/*
 * RequesterEditTaskActivityTest
 *
 * March 14, 2018
 *
 * Copyright
 */

package professional.team17.com.professional;

import android.app.Activity;
import android.content.Intent;
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
        MockTask mockTask = new MockTask("test", "Test Title", "Test Description", "Test Location", "01/01/2000");
        ElasticSearchController mockES = new ElasticSearchController();
        String ID = mockES.addTasks(mockTask);
        mockTask.setId(ID);
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
        /* Check that this is the right activity */
        solo.assertCurrentActivity("Wrong Activity", RequesterEditTaskActivity.class);
        /* Press the submit button without changing anything */
        solo.clickOnView(solo.getView(R.id.submitButton));
        /* Check that the submit button leads to the right place */
        solo.assertCurrentActivity("Wrong activity after pressing submit", RequesterViewListActivity.class);
        solo.clickOnText("Edit");
        /* Check that clicking on the task title leads to the right place */
        solo.assertCurrentActivity("Wrong activity after clicking on task title", RequesterViewTaskActivity.class);
        assertTrue(solo.waitForText("Test Title"));
        assertTrue(solo.waitForText("Test Description"));
        assertTrue(solo.waitForText("Test Location"));
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
        solo.setDatePicker(0, 2001, 02, 02);
        solo.clickOnText("OK");
        /* Check that the submit button leads to the right place */
        solo.clickOnText("Edit");
        solo.assertCurrentActivity("Wrong activity after pressing submit", RequesterViewListActivity.class);
        solo.clickOnView(solo.getView(R.id.requester_requested_title));
        /* Check that clicking on the task title leads to the right place */
        solo.assertCurrentActivity("Wrong activity after clicking on task title", RequesterViewTaskActivity.class);
        assertTrue(solo.waitForText("Test Title Changed"));
        assertTrue(solo.waitForText("Test Description Changed"));
        assertTrue(solo.waitForText("Test Location Changed"));
        assertTrue(solo.waitForText("02/02/2001"));
    }
    /**
     * Tear down the test.
     */
    public void tearDown(){
        solo.finishOpenedActivities();
    }

}
