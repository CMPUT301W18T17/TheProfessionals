package professional.team17.com.professional;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;

/**
 * Created by kaixiangzhang on 2018-03-13.
 */

public class ProviderPlaceBidTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public ProviderPlaceBidTest() {
        super(PlaceBidDialog.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();

    }

    public void testAddBidButton() {
        solo.assertCurrentActivity("Wrong Activity", PlaceBidDialog.class);
        solo.enterText((EditText) solo.getView(R.id.usernameBox), "66.6");
        solo.clickOnButton("Add");
        assertTrue(solo.waitForText("66.6"));
    }

    public void testDismissButton() {
        solo.assertCurrentActivity("Wrong Activity", PlaceBidDialog.class);
        solo.clickOnButton("Dismiss");
        solo.assertCurrentActivity("Wrong Activity", ProviderViewTask.class);

    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}

