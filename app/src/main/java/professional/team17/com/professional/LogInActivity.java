package professional.team17.com.professional;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.searchly.jestdroid.DroidClientConfig;

import javax.security.auth.login.LoginException;

/**
 * Allows the user to log in or move to a signup activity
 *
 * @see ElasticSearchController
 * @see SignUpActivity
 */
public class LogInActivity extends AppCompatActivity {
    private Profile profile;
    private ElasticSearchController elasticSearchController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        elasticSearchController = new ElasticSearchController();

    }

    /**
     * On selecting Sign In, checks username and signs the user in
     *
     * @param view
     * @see SearchActivity
     */
    public void logIn(View view){

        EditText usernameBox = (EditText) findViewById(R.id.usernameBox);
        String username = usernameBox.getText().toString();
        TextView error = (TextView) findViewById(R.id.ErrorText);

        if (usernameBox.getText().length() == 0){
            error.setText("Please Enter a Username");
        } else {
            profile = elasticSearchController.getProfile(username);
            if (!(profile == null)) {
                changeActivity(SearchActivity.class);
                //good to go
                //set username global variable?
            } else {
                error.setText("Invalid Username");
            }
        }
    }



    /**
     * On clicking the Sign Up button, user is moved to the Sign Up page
     *
     * @param view
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

        startActivity(intent);
        finish();
    }
}
