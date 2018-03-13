package professional.team17.com.professional;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import io.searchbox.core.Get;

/**
 * EditMyProfileActivity starts after user selects the UserMenuButton --> "Edit My Profile"
 *
 * @version 2.0 Last updated: Mar 12, 2018
 * @see ElasticSearchController
 * @see ProfileViewActivity
 */
public class EditMyProfileActivity extends AppCompatActivity {
    private TextView showUserName;
    private EditText editName;
    private EditText editEmail;
    private EditText editPhone;
    private final ElasticSearchController elasticSearchController = new ElasticSearchController();

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
        setContentView(R.layout.activity_edit_my_profile);

        // Initialize variables relating to the layout
        showUserName = findViewById(R.id.showUserName);
        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editPhone = findViewById(R.id.editPhone);
        Button saveButton = (Button) findViewById(R.id.saveButton);
        Button cancelButton = (Button) findViewById(R.id.cancelButton);

        //showUserName.setText();
        //editName.setText();
        //editEmail.setText();
        //editPhone.setText();

        /* OnClickListeners for save and cancel */
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: SAVE INFO!!!!!
                // ...
                // TODO: CHECK INFO!!!!!
                //...
                // Go back to previous
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
