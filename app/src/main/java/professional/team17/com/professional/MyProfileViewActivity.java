package professional.team17.com.professional;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MyProfileViewActivity extends ProfileViewActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_my_profile_view;
    }
}

