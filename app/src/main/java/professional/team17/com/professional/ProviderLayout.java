package professional.team17.com.professional;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public abstract class ProviderLayout extends AppCompatActivity implements ImageView.OnClickListener {

    private String activityTitle;
    private TextView activityTitleView;
    private ImageView providerBiddedButton;
    private ImageView providerAssignedButton;
    private ImageView providerSearchButton;
    private ImageView providerMapButton;
    private ImageView switchLayoutButton;
    private ImageView userMenuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.provider_layout);

        /* Setting all View variables */
        activityTitleView = (TextView) findViewById(R.id.activityTitleView);
        providerBiddedButton = (ImageView) findViewById(R.id.biddedTasksProviderButton);
        providerAssignedButton = (ImageView) findViewById(R.id.acceptedTasksProviderButton);
        providerSearchButton = (ImageView) findViewById(R.id.searchTasksButton);
        providerMapButton = (ImageView) findViewById(R.id.taskMapButton);
        switchLayoutButton = (ImageView) findViewById(R.id.switchViewProviderButton);
        userMenuButton = (ImageView) findViewById(R.id.userMenuButton);
    }

    /*
    Changes the title at the top of the page.
     */
    public void setActivityTitle(String title) {
        this.activityTitle = title;
    }


    //TODO uncomment as activities are created
    @Override
    public void onClick(View v) {
        /* If user tapped the Bids button */
        if (v == providerBiddedButton) {
            /* Commented out as the activity does not exist yet
            Intent intent = new Intent(this, ProviderListActivity.class);
            intent.putExtra("Status", "Bidded");
            startActivity(intent);
            */
        }
        /* If user tapped the assigned button */
        if (v == providerAssignedButton) {
            /* Commented out as the activity does not exist yet
            Intent intent = new Intent(this, ProviderListActivity.class);
            intent.putExtra("Status", "Assigned");
            startActivity(intent);
            */
        }
        /* If the user tapped the search button */
        if (v == providerSearchButton) {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
        }
        /* If the user tapped the map button */
        if (v == providerMapButton) {
            /* Commented out as the activity does not exist yet
            Intent intent = new Intent(this, MapActivity.class);
            startActivity(intent);
            */
        }
        /* If the user taps the switch button */
        if (v == switchLayoutButton) {
            /*Commented out as the activity does not exist yet
            Intent intent = new Intent(this, RequesterListActivity.class);
            intent.putExtra("Status", "Requested");
            startActivity(intent);
            */
        }
        /* If the user taps the user menu button */
        if (v == userMenuButton) {
            //TODO implement dropdown menu
        }

    }


}