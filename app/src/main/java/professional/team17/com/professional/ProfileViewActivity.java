package professional.team17.com.professional;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public abstract class ProfileViewActivity extends AppCompatActivity {
    protected TextView username;
    protected TextView name;
    protected TextView email;
    protected TextView phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());
    }

    protected abstract int getLayoutResourceId();

    // don't call setInfo from here. Call it from MyProfileViewActivity or OtherProfileViewActivity
    protected void setInfo(String aUserName, String aName, String anEmail, String aPhoneNumber) {
        username.setText(aUserName);
        name.setText(aName);
        email.setText(anEmail);
        phoneNumber.setText(aPhoneNumber);
    }
}
