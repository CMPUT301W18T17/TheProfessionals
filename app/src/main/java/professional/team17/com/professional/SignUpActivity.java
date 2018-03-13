package professional.team17.com.professional;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Date;

/**
 * Allows the user to create a new profile
 *
 * @see LogInActivity
 * @see Profile
 * @see SearchActivity
 */
public class SignUpActivity extends AppCompatActivity {

    private EditText usernameBox;
    private EditText nameBox;
    private EditText emailBox;
    private EditText phoneNumberBox;
    private TextView errorBox;
    private ElasticSearchController elasticSearchController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        usernameBox = (EditText) findViewById(R.id.usernameBox);
        nameBox = (EditText) findViewById(R.id.fullNameBox);
        emailBox = (EditText) findViewById(R.id.emailBox);
        phoneNumberBox = (EditText) findViewById(R.id.phoneNumberBox);
        errorBox = (TextView) findViewById(R.id.errorText);
        elasticSearchController = new ElasticSearchController();
    }

    /**
     * returns the user to the Login Activity
     *
     * @see LogInActivity
     * @param view
     */
    public void back(View view) {
        changeActivity(LogInActivity.class);
    }

    /**
     * Creates the user's profile and moves them
     *
     * @see ElasticSearchController
     * @param view
     */
    public void saveProfile(View view) {
        String username = usernameBox.getText().toString();
        String name = nameBox.getText().toString();
        String email = emailBox.getText().toString();
        String phoneNumber = phoneNumberBox.getText().toString();


        //TODO exception handling for real email/phone number?
        if ((usernameBox.getText().length() == 0) || (nameBox.getText().length() == 0) ||
                (emailBox.getText().length() == 0) || (phoneNumberBox.getText().length() == 0)){
            errorBox.setText("Please make sure all fields are filled");
        } else if (usernameBox.getText().length() < 4) {
            errorBox.setText("Username must be at least 4 characters");
        } else if (elasticSearchController.profileExists(username) == true) {
            errorBox.setText("Username is already taken");
        } else {
            Profile profile = new Profile(name, username, email, phoneNumber);

            if (!(elasticSearchController.addProfile(profile))) {
                errorBox.setText("Something went wrong! We are unable to create profile");
            } else {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();

                editor.putString("username", username); // Storing string
                editor.commit(); // commit changes

                //String test = pref.getString(username, "not working");
                changeActivity(SearchActivity.class);
            }
        }
    }
        //set profile name as global variable?

    /**
     * Moves the user to a new activity
     */
    private void changeActivity(Class activity){
        //can change navigation, this is just a stand in
        Intent intent = new Intent(this, activity);

        startActivity(intent);
        finish();
    }
}

