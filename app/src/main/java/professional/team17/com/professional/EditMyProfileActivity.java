package professional.team17.com.professional;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class EditMyProfileActivity extends AppCompatActivity {
    private TextView showUserName;
    private EditText editName;
    private EditText editEmail;
    private EditText editPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Todo: change layout depending on requester or provider mode
        setContentView(R.layout.activity_edit_my_profile);

        showUserName = findViewById(R.id.showUserName);
        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editPhone = findViewById(R.id.editPhone);

        // Get values from intent!!! Or global?
        //showUserName.setText();
        //editName.setText();
        //editEmail.setText();
        //editPhone.setText();

        //onClick for save method!!!

    }
}
