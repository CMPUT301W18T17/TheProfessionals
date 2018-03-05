package professional.team17.com.professional;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    //TODO: Login in navigation
    public void logIn(View view){

        //can change from ProviderViewTask, this is just a stand in
        Intent intent = new Intent(this, ProviderViewTask.class);
        EditText usernameBox = (EditText) findViewById(R.id.usernameBox);
        String username = usernameBox.getText().toString();

        //TODO Elastic search for username

        //set usernameas global variable?

        startActivity(intent);
    }

    public void signUp(View view) {

        Intent intent = new Intent(this, SignUpActivity.class);

        //not sure if I should be starting with result or not
        startActivity(intent);
    }
}
