package professional.team17.com.professional;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.searchly.jestdroid.DroidClientConfig;

import javax.security.auth.login.LoginException;

public class LogInActivity extends AppCompatActivity {
    private Profile profile;
    private ElasticSearchController elasticSearchController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        elasticSearchController = new ElasticSearchController();

    }

    //TODO: Login in navigation
    public void logIn(View view){

        EditText usernameBox = (EditText) findViewById(R.id.usernameBox);
        String username = usernameBox.getText().toString();
        Profile profile = elasticSearchController.getProfile(username);
        if (!(profile==null)){
            changeActivity();
            //good to go
            //set usernameas global variable?
        }
        else {
            //error with users name
        }

    }


    private void changeActivity(){
        //can change navigation, this is just a stand in
        Intent intent = new Intent(this, SearchActivity.class);

        startActivity(intent);

    }



    public void signUp(View view) {

        Intent intent = new Intent(this, SignUpActivity.class);

        //not sure if I should be starting with result or not
        startActivity(intent);
    }
}
