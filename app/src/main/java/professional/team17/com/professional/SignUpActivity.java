package professional.team17.com.professional;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        addProfile();

        //TODO check username does not exist using elasticSearch

        //TODO exception handling for phone number/email

    }


        //TODO for time being keep in controller, but we can generalize later as we see the methods we need
        public void addProfile() {
            Profile profile1 = new Profile("wer", "wer", "wer", "wer");
            ElasticSearchController.AddProfile addProfile = new ElasticSearchController.AddProfile();
            addProfile.execute(profile1);

            Intent intent = new Intent(this, SearchActivity.class);

            startActivity(intent);
        }
        //TODO save profile to server
        //set profile name as global variable?

    }

