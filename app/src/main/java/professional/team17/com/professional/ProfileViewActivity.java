package professional.team17.com.professional;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public abstract class ProfileViewActivity extends ProviderLayout {
    protected TextView username;
    protected TextView name;
    protected TextView email;
    protected TextView phoneNumber;
    protected TextView currentMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);
        currentMode = findViewById(R.id.currentModeTV);

    }

    // don't call setInfo from here. Call it from MyProfileViewActivity or OtherProfileViewActivity
    protected void setInfo(String aUserName, String aName, String anEmail, String aPhoneNumber) {
        username =findViewById(R.id.userNameTV);
        name =findViewById(R.id.nameTV);
        email = findViewById(R.id.emailTV);
        phoneNumber = findViewById(R.id.phoneTV);

        username.setText(aUserName);
        name.setText(aName);
        email.setText(anEmail);
        phoneNumber.setText(aPhoneNumber);
    }
}
