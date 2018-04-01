/*
 * OtherProfileViewActivity
 *
 * March 12, 2018
 *
 * Copyright @ 2018 Team 17, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behaviour at the University of Alberta.
 * You can find a copy of the license in the github wiki for this project.
 */
package professional.team17.com.professional;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

/**
 * OtherProfileViewActivity displays information of another user
 * @author Hailan
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

        Bundle bundle = getIntent().getExtras();
        String aUserName = bundle.getString("profile");

        //Toast.makeText(getApplicationContext(), "" + aUserName,  Toast.LENGTH_SHORT).show();

        setInfo(aUserName);
    }
    
}
