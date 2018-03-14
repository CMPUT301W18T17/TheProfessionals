package professional.team17.com.professional;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

/**
 * OtherProfileViewActivity displays information of another user
 * @author Hailan
 * @version 2.0 Last updated: Mar.12, 2018
 * @see ProfileViewActivity
 */
public class OtherProfileViewActivity extends ProfileViewActivity{
    private ListView listViewOfRatings;
    private final ElasticSearchController elasticSearchController = new ElasticSearchController();

    /**
     * Upon selecting another user's profile, info is displayed until doneButton is selected
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentMode.setText("An User's Profile");
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Bundle bundle = getIntent().getExtras();
        String aUserName = bundle.getString("profile");

        //Toast.makeText(getApplicationContext(), "" + aUserName,  Toast.LENGTH_SHORT).show();

        setInfo(aUserName);
    }
    
}
