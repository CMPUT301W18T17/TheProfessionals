package professional.team17.com.professional;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class SignUpActivity extends AppCompatActivity {

    private EditText usernameBox;
    private EditText nameBox;
    private EditText emailBox;
    private EditText phoneNumberBox;
    private ElasticSearchController elasticSearchController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        usernameBox = (EditText) findViewById(R.id.usernameBox);
        nameBox = (EditText) findViewById(R.id.fullNameBox);
        emailBox = (EditText) findViewById(R.id.emailBox);
        phoneNumberBox = (EditText) findViewById(R.id.phoneNumberBox);
        elasticSearchController = new ElasticSearchController();

    }
    //TODO: back to login navigation
    //this is added in the manifests.xml however, we don't have a toolbar currently?

    public void saveProfile(View view) {
        String username = usernameBox.getText().toString();
        String name = nameBox.getText().toString();
        String email = emailBox.getText().toString();
        String phoneNumber = phoneNumberBox.getText().toString();
        Profile profile = new Profile(name, username, email, phoneNumber);


//if name odes not exist, then allow for profile to be created
        if (allowUsername(username)==true) {

            ElasticSearchController.AddProfile addProfile = new ElasticSearchController.AddProfile();
            addProfile.execute(profile);

        }
        else {
            //TO DO EXCEPTION HANDLING
        }


        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
        //TODO check username does not exist using elasticSearch

        //TODO exception handling for phone number/email

    }

    //return tru if username allowed, false otherwise (ie the name exists)
    private boolean allowUsername(String username) {
        ElasticSearchController.GetProfile getUserName = new ElasticSearchController.GetProfile();
        getUserName.execute(username);
        try {
            Profile profile1 = getUserName.get();
            if (profile1 != null) {
                return false;
            }
        } catch (Exception e) {
            return true;
        }
        return true;
    }


    /**
     *
     * @param name
     * @return boolean value where true means username does not exist, false if it does.
     */
    /*
    public boolean allowUsername(String name){
        if (elasticSearchController.checkUsername(name)) {
            return false;
        }
        return true;
    }

*/
        //TODO for time being keep in controller, but we can generalize later as we see the methods we need
        //TODO save profile to server
        //set profile name as global variable?

    }

