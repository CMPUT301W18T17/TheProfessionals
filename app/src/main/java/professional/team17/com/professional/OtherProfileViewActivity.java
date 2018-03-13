package professional.team17.com.professional;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

/**
 * OtherProfileViewActivity displays information of another user
 *
 * @version 2.0 Last updated: Mar.12, 2018
 * @see ProfileViewActivity
 */
public class OtherProfileViewActivity extends ProfileViewActivity{
    private ListView listViewOfRatings;

    /**
     * Upon selecting another user's profile, info is displayed until doneButton is selected
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentMode.setText("An User's Profile");
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * Set information (relevant EditTexts are in ProfileViewActivity) acquired from ElasticSearch (by the other user's username)
     * @param aUserName
     * @param aName
     * @param anEmail
     * @param aPhoneNumber
     */
    protected void setInfo(String aUserName, String aName, String anEmail, String aPhoneNumber) {
        super.setInfo(aUserName, aName, anEmail, aPhoneNumber);
        // Make mock ratings and adapter for reviews; Initiate listViewOfRatings here.
    }
}
