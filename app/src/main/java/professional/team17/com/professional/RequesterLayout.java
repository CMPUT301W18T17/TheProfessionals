package professional.team17.com.professional;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public abstract class RequesterLayout extends AppCompatActivity implements ImageView.OnClickListener {

    private String activityTitle;
    private TextView activityTitleView;
    private ImageView requesterAddTaskButton;
    private ImageView requesterBiddedButton;
    private ImageView requesterAssignedButton;
    private ImageView requesterRequestedButton;
    private ImageView switchLayoutButton;
    private ImageView userMenuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requester_layout);

        /* Setting all View variables */
        activityTitleView = (TextView) findViewById(R.id.activityTitleView);
        requesterAddTaskButton = (ImageView) findViewById(R.id.addTaskRequesterButton);
        requesterBiddedButton = (ImageView) findViewById(R.id.biddedTasksRequesterButton);
        requesterAssignedButton = (ImageView) findViewById(R.id.acceptedTasksRequesterButton);
        requesterRequestedButton = (ImageView) findViewById(R.id.requestedTasksRequesterButton);
        switchLayoutButton = (ImageView) findViewById(R.id.switchViewRequesterButton);
        userMenuButton = (ImageView) findViewById(R.id.userMenuButton);
    }

    /*
    Changes the title at the top of the page.
     */
    public void setActivityTitle(String title) {
        this.activityTitle = title;
        activityTitleView.setText(activityTitle);
    }


    //TODO uncomment as activities are created
    @Override
    public void onClick(View v) {
        /* If user tapped the Bids button */
        if (v == requesterAddTaskButton) {
            /* Commented out as the activity does not exist yet
            Intent intent = new Intent(this, AddTaskActivity.class);
            startActivity(intent);
            */
        }
        /* If user tapped the assigned button */
        if (v == requesterBiddedButton) {
            /* Commented out as the activity does not exist yet
            Intent intent = new Intent(this, RequesterListActivity.class);
            intent.putExtra("Status", "Bidded");
            startActivity(intent);
            */
        }
        /* If the user tapped the search button */
        if (v == requesterAssignedButton) {
            /* Commented out as the activity does not exist yet
            Intent intent = new Intent(this, RequesterListActivity.class);
            intent.putExtra("Status", "Assigned");
            startActivity(intent);
            */
        }
        /* If the user tapped the map button */
        if (v == requesterRequestedButton) {
            /* Commented out as the activity does not exist yet
            Intent intent = new Intent(this, RequesterListActivity.class);
            intent.putExtra("Status", "Requested");
            startActivity(intent);
            */
        }
        /* If the user taps the switch button */
        if (v == switchLayoutButton) {
            /*Commented out as the activity does not exist yet
            Intent intent = new Intent(this, ProviderListActivity.class);
            intent.putExtra("Status", "Bidded");
            startActivity(intent);
            */
        }
        /* If the user taps the user menu button */
        if (v == userMenuButton) {
            //TODO implement dropdown menu
        }

    }


}
