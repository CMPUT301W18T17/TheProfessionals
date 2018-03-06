package professional.team17.com.professional;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditMyProfileActivity extends AppCompatActivity {
    private TextView showUserName;
    private EditText editName;
    private EditText editEmail;
    private EditText editPhone;

    private Button saveButton;
    private Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Todo: change layout depending on requester or provider mode
        setContentView(R.layout.activity_edit_my_profile);

//        showUserName = findViewById(R.id.showUserName);
//        editName = findViewById(R.id.editName);
//        editEmail = findViewById(R.id.editEmail);
//        editPhone = findViewById(R.id.editPhone);

        saveButton = (Button) findViewById(R.id.saveButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);

        // Get values from intent!!! Or global?
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
