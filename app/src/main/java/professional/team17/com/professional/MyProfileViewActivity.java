package professional.team17.com.professional;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * MyProfileViewActivity displays information of current user
 *
 * @version 2.0 Last updated: Mar.12, 2018
 * @see ProfileViewActivity
 */
public class MyProfileViewActivity extends ProfileViewActivity{

    /**
     * Upon selecting UserMenuButton --> "My Profile", info is displayed until doneButton is selected
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentMode.setText("My Profile");
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

}

