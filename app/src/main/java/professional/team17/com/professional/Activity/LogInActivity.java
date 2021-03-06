/*
 * LogInActivity
 *
 * March 3, 2018
 *
 * Copyright @ 2018 Team 17, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behaviour at the University of Alberta.
 * You can find a copy of the license in the github wiki for this project.
 */

package professional.team17.com.professional.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import java.util.concurrent.ExecutionException;

import professional.team17.com.professional.Helpers.ConnectivityCheck;
import professional.team17.com.professional.Entity.Profile;
import professional.team17.com.professional.R;
import professional.team17.com.professional.Controllers.ServerHelper;

/**
 * Allows the user to log in or move to a signup activity
 * @author Logan
 * @see ServerHelper
 * @see SignUpActivity
 */
public class LogInActivity extends AppCompatActivity {
    private ServerHelper serverHelper;

    //To access sharedPreferences, copy and paste the following and adjust accordingly:
    /*
    SharedPreferences sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
    String username = sharedpreferences.getString("username", "error");
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        connectivityCheck();
        serverHelper = new ServerHelper(this);
        SharedPreferences sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        if (sharedpreferences.contains("username")){
            changeActivity(RequesterViewListActivity.class);
        }

    }

    /**
     * On selecting Sign In, checks username and signs the user in
     *
     * @see SearchActivity
     */
    public void logIn(View view){

        EditText usernameBox = findViewById(R.id.usernameBox);
        String username = usernameBox.getText().toString();
        TextView error = findViewById(R.id.ErrorText);

        if (usernameBox.getText().length() == 0){
            error.setText(R.string.enterUserPrompt);
        } else {
            Profile profile = serverHelper.getProfile(username);
            if (!(profile == null)) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("username", username); // Storing string
                editor.apply(); // commit changes
                changeActivity(RequesterViewListActivity.class);

            } else {
                error.setText(R.string.nonExistentUser);
            }
        }
    }


    public void connectivityCheck(){
        ConnectivityCheck.isOnline c = new ConnectivityCheck.isOnline();
        try {
            Object result=c.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * On clicking the Sign Up button, user is moved to the Sign Up page
     *
     * @see SignUpActivity
     */
    public void signUp(View view) {
        changeActivity(SignUpActivity.class);
    }

    /**
     * Moves the user to a new activity
     */
    private void changeActivity(Class activity){
        //can change navigation, this is just a stand in
        Intent intent = new Intent(this, activity);
        Bundle bundle = new Bundle();
        bundle.putString("Status", "Requested");
        intent.putExtras(bundle);

        startActivity(intent);
        finish();
    }

    /**
     * Overides the back button (in case they log out and don't want to accidentally log in again)
     */

    @Override
    public void onBackPressed() {
        //Nothing should happen!!!
    }
}
