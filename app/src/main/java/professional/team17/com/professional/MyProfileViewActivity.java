package professional.team17.com.professional;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * MyProfileViewActivity displays information of current user
 *
 * @version 2.0 Last updated: Mar.13, 2018
 * @see ProfileViewActivity
 * @see EditMyProfileActivity
 */
public class MyProfileViewActivity extends ProfileViewActivity {
    private ImageButton profilePic;
    /**
     * Upon selecting UserMenuButton --> "My Profile", info is displayed until doneButton is selected
     * User can edit their info by clicking on their profile picture (goes to EditMyProfileActivity)
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUp();
    }

    /**
     * setUp uses initiates everything identified in the layout
     * Is used in onCreate and onResume
     */
    private void setUp(){
        currentMode.setText("My Profile");

        /* profilePic and doneButton selection treatments */
        profilePic = (ImageButton) findViewById(R.id.profilePicButton);
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyProfileViewActivity.this, EditMyProfileActivity.class);
                startActivity(intent);
            }
        });
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        SharedPreferences pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String theUserName = pref.getString("username", "error");

        setInfo(theUserName);
    }

    /**
     * uses the setUp method (to possibly refresh and show edited profile information)
     */
    protected void onResume(){
        super.onResume();
        setUp();
    }


}

