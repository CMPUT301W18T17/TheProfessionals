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
    }

    /**
     * Tear down the test.
     */
    public void tearDown(){
        solo.finishOpenedActivities();
    }

}
