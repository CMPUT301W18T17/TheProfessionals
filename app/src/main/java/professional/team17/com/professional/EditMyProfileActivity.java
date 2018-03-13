package professional.team17.com.professional;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * EditMyProfileActivity starts after user selects the UserMenuButton --> "Edit My Profile"
 *
 * @version 3.0 Last updated: Mar 13, 2018
 * @see ElasticSearchController
 * @see ProfileViewActivity
 */
public class EditMyProfileActivity extends AppCompatActivity {
    private TextView showUserName;
    private EditText editName;
    private EditText editEmail;
    private EditText editPhone;

    private final ElasticSearchController elasticSearchController = new ElasticSearchController();
    private SharedPreferences pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
    private String theUserName = pref.getString("username", "error");
    private Profile userProfile = elasticSearchController.getProfile(theUserName);

    /**
     * Upon creation, EditText will be set with relevant user info grabbed from ES
     *
     * Select 'save' to update info for user and go back to last activity
     * Select 'cancel' to go back to last activity
     *
     * @param savedInstanceState default parameter
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* initialization of objects on layout and user's info*/
        setContentView(R.layout.activity_edit_my_profile);
        Button saveButton = (Button) findViewById(R.id.saveButton);
        Button cancelButton = (Button) findViewById(R.id.cancelButton);

        showUserName = findViewById(R.id.showUserName);
        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editPhone = findViewById(R.id.editPhone);

        showUserName.setText(theUserName);
        editName.setText(userProfile.getName());
        editEmail.setText(userProfile.getEmail());
        editPhone.setText(userProfile.getPhoneNumber());

        /* OnClickListeners for save and cancel */
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userProfile.setName(editName.getText().toString());
                userProfile.setEmail(editEmail.getText().toString());
                userProfile.setPhoneNumber(editEmail.getText().toString());
                elasticSearchController.addProfile(userProfile);
                finish();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
