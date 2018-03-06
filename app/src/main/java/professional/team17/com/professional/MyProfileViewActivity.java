package professional.team17.com.professional;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MyProfileViewActivity extends ProfileViewActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentMode.setText("My Profile");

    }

    protected void setInfo(String aUserName, String aName, String anEmail, String aPhoneNumber) {
        super.setInfo(aUserName, aName, anEmail, aPhoneNumber);
    }
}

