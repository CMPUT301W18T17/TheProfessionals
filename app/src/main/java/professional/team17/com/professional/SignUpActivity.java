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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        usernameBox = (EditText) findViewById(R.id.usernameBox);
        nameBox = (EditText) findViewById(R.id.fullNameBox);
        emailBox = (EditText) findViewById(R.id.emailBox);
        phoneNumberBox = (EditText) findViewById(R.id.phoneNumberBox);

    }
    //TODO: back to login navigation
    //this is added in the manifests.xml however, we don't have a toolbar currently?

    public void saveProfile(View view){
        String username = usernameBox.getText().toString();
        String name = nameBox.getText().toString();
        String email = emailBox.getText().toString();
        String phoneNumber = phoneNumberBox.getText().toString();

        //TODO check username does not exist using elasticSearch

        //TODO exception handling for phone number/email

        Profile profile = new Profile(username, name, email, phoneNumber);

        //TODO save profile to server
        //set profile name as global variable?

        Intent intent = new Intent(this, SearchActivity.class);

        startActivity(intent);
    }

}
