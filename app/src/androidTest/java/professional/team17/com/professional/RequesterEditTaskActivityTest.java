/*
 * RequesterEditTaskActivityTest
 *
 * March 14, 2018
 *
 * Copyright
 */

package professional.team17.com.professional;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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
        Solo solo = new Solo(getInstrumentation(), getActivity());
        MockTask mockTask = new MockTask("test", "Test Title", "Test Description", "Test Location", "01/01/2000");
        ElasticSearchController mockES = new ElasticSearchController();
        String ID = mockES.addTasks(mockTask);
        mockTask.setId(ID);
    }

    public void testStart() {
        Activity activity = getActivity();
    }

    /**
     * Tear down the test.
     */
    public void tearDown(){
        solo.finishOpenedActivities();
    }

    /**
     * Tests to see what happens if the edit fields are left blank
     */
    public void testLeaveEditFieldsBlank() {
        /* Check that this is the right activity */
        solo.assertCurrentActivity("Wrong Activity", RequesterEditTaskActivity.class);
        /* Press the submit button without changing anything */
        solo.clickOnButton(R.id.submitButton);
        /* Check that the submit button leads to the right place */
        solo.assertCurrentActivity("Wrong activity after pressing submit", RequesterViewListActivity.class);
        solo.clickOnView(solo.getView(R.id.requester_requested_title));
        /* Check that clicking on the task title leads to the right place */
        solo.assertCurrentActivity("Wrong activity after clicking on task title", RequesterViewTaskActivity.class);
        assertTrue(solo.waitForText("Test Title"));
        assertTrue(solo.waitForText("Test Description"));
        assertTrue(solo.waitForText("Test Location"));
        assertTrue(solo.waitForText("01/01/2000"));
    }

    public void testFillEditFields() {
        /* Check that this is the right activity */
    }

}
