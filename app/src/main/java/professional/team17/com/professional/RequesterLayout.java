/*
 * RequesterLayout
 *
 * March 7, 2018
 *
 * Copyright @ 2018 Team 17, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behaviour at the University of Alberta.
 * You can find a copy of the license in the github wiki for this project.
 */

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

/**
 * An abstract activity which implements the navigation for the Requester options.
 */
public abstract class RequesterLayout extends AppCompatActivity implements ImageView.OnClickListener {

    private String layout;
    private TextView activityTitleView;
    private ImageView requesterAddTaskButton;
    private ImageView requesterBiddedButton;
    private ImageView requesterAssignedButton;
    private ImageView requesterRequestedButton;
    private ImageView switchLayoutButton;
    private ImageView userMenuButton;
    private PopupMenu popupMenu;

    /**
     * On the activity's creation, set all variables.
     * @param savedInstanceState The activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requester_layout);

        layout = "Requester";

        /* Setting all View variables */
        activityTitleView = (TextView) findViewById(R.id.activityTitleView);
        requesterAddTaskButton = (ImageView) findViewById(R.id.addTaskRequesterButton);
        requesterBiddedButton = (ImageView) findViewById(R.id.biddedTasksRequesterButton);
        requesterAssignedButton = (ImageView) findViewById(R.id.acceptedTasksRequesterButton);
        requesterRequestedButton = (ImageView) findViewById(R.id.requestedTasksRequesterButton);
        switchLayoutButton = (ImageView) findViewById(R.id.switchViewRequesterButton);
        userMenuButton = (ImageView) findViewById(R.id.userMenuButton);
    }

    /**
     * Sets the title at the top of the layout.
     * @param title The title of the activity being displayed.
     */
    public void setActivityTitle(final String title) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activityTitleView = (TextView) findViewById(R.id.activityTitleView);
                activityTitleView.setText(title);
            }
        });
    }

    /**
     * Returns "Provider" or "Requester" depending on which layout the user is in.
     * @return The layout that the user is in.
     */
    public String getLayout(){
        return layout;
    }

    /**
     * Handles all click events for the bottom navigation and the userMenuButton
     * in the top right corner.
     * @param view The view object that has been clicked.
     */
    //TODO uncomment as activities are created
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            /* If user tapped the add button */
            case R.id.addTaskRequesterButton:
                Intent intent = new Intent(this, RequesterAddTaskActivity.class);
                startActivity(intent);
                finish();
                break;
            /* If user tapped the bidded button */
            case R.id.biddedTasksRequesterButton:
                intent = new Intent(this, RequesterViewListActivity.class);
                intent.putExtra("Status", "Bidded");
                startActivity(intent);
                finish();
                break;
            /* If the user tapped the assigned button */
            case R.id.acceptedTasksRequesterButton:
                intent = new Intent(this, RequesterViewListActivity.class);
                intent.putExtra("Status", "Assigned");
                startActivity(intent);
                finish();
                break;
            /* If the user tapped the requested button */
            case R.id.requestedTasksRequesterButton:
                intent = new Intent(this, RequesterViewListActivity.class);
                intent.putExtra("Status", "Requested");
                startActivity(intent);
                finish();
                break;
            /* If the user taps the switch button */
            case R.id.switchViewRequesterButton:
                intent = new Intent(this, ProviderTaskListActivity.class);
                intent.putExtra("Status", "Bidded");
                startActivity(intent);
                finish();
                break;
            /* If the user taps the user menu button */
            case R.id.userMenuButton:
                popupMenu.show();
                break;
        }
    }

    /**
     * Creates the popup menu that is shown when the userMenuButton is clicked.
     */
    protected void createPopup(){
        /* Create Popup Menu */
        popupMenu = new PopupMenu(this, userMenuButton);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                //Toast.makeText(ProviderLayout.this, "" + menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                if(menuItem.getTitle().equals("My Profile")){
                    Intent intent = new Intent(getApplicationContext(), MyProfileViewActivity.class);
                    startActivity(intent);
                } else if (menuItem.getTitle().equals("Edit My Profile")){
                    Intent intent = new Intent(getApplicationContext(), EditMyProfileActivity.class);
                    startActivity(intent);
                }
                return true;
            }
        });
    }

    /**
     * On start of the activity, create the popup menu.
     */
    @Override
    protected void onStart() {
        super.onStart();
        createPopup();

    }

}
