package professional.team17.com.professional;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;


/**
 * Created by Hailan on 2018-03-14.
 */

public class MyProfileViewActivityTest extends ActivityInstrumentationTestCase2{
    private Solo solo;

    public MyProfileViewActivityTest() {
        super(professional.team17.com.professional.MyProfileViewActivity.class);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void testEditMyProfile(){
        solo.assertCurrentActivity("Wrong Activity", MyProfileViewActivity.class);

        solo.clickOnView(solo.getView(R.id.profilePicButton));
        solo.assertCurrentActivity("Wrong Activity", EditMyProfileActivity.class);

        EditText enterName = (EditText) solo.getView(R.id.editName);
        EditText enterEmail = (EditText) solo.getView(R.id.editEmail);
        EditText enterPhone = (EditText) solo.getView(R.id.editEmail);

        solo.clearEditText(enterName);
        solo.clearEditText(enterEmail);
        solo.clearEditText(enterPhone);

        solo.enterText(enterName, "TEST name");
        solo.enterText(enterEmail, "TEST@email.ca");
        solo.enterText(enterPhone, "1111111111");

        solo.clickOnButton("SAVE");

        solo.assertCurrentActivity("WrongActivity", MyProfileViewActivity.class);
        



    }

}
