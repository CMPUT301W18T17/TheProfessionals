package professional.team17.com.professional;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

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
    public void onClick(View view) {
        switch (view.getId()) {
            /* If user tapped the Bids button */
            case R.id.addTaskRequesterButton:
                /* Commented out as the activity does not exist yet
                Intent intent = new Intent(this, AddTaskActivity.class);
                startActivity(intent);
                */
                break;
            /* If user tapped the assigned button */
            case R.id.biddedTasksRequesterButton:
                /* Commented out as the activity does not exist yet
                Intent intent = new Intent(this, RequesterListActivity.class);
                intent.putExtra("Status", "Bidded");
                startActivity(intent);
                */
                break;
            /* If the user tapped the search button */
            case R.id.acceptedTasksRequesterButton:
                /* Commented out as the activity does not exist yet
                Intent intent = new Intent(this, RequesterListActivity.class);
                intent.putExtra("Status", "Assigned");
                startActivity(intent);
                */
                break;
            /* If the user tapped the map button */
            case R.id.requestedTasksRequesterButton:
                /* Commented out as the activity does not exist yet
                Intent intent = new Intent(this, RequesterListActivity.class);
                intent.putExtra("Status", "Requested");
                startActivity(intent);
                */
                break;
            /* If the user taps the switch button */
            case R.id.switchViewRequesterButton:
                /*Commented out as the activity does not exist yet
                Intent intent = new Intent(this, ProviderListActivity.class);
                intent.putExtra("Status", "Bidded");
                startActivity(intent);
                */
                break;
            /* If the user taps the user menu button */
            case R.id.userMenuButton:
                //TODO implement dropdown menu
                PopupMenu popupMenu = new PopupMenu(this, userMenuButton);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        Toast.makeText(RequesterLayout.this, "" + menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });

                popupMenu.show();
                break;
        }
    }


}
