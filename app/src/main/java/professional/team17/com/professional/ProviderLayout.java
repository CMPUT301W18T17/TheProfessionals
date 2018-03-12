/*
 * ProviderLayout
 *
 * March 7, 2018
 *
 * Copyright info goes here
 */

package professional.team17.com.professional;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

/**
 * An abstract activity which implements the navigation for the Provider options.
 *
 * @author: Lauren, Hailan, Allison
 * @see ProviderViewTask
 * @see ProviderTaskListActivity
 */
public abstract class ProviderLayout extends AppCompatActivity implements ImageView.OnClickListener {

    private String layout;
    private TextView activityTitleView;
    private ImageView providerBiddedButton;
    private ImageView providerAssignedButton;
    private ImageView providerSearchButton;
    private ImageView providerMapButton;
    private ImageView switchLayoutButton;
    private ImageView userMenuButton;
    private PopupMenu popupMenu;

    /**
     * On creation of the activity, assign values to all variables.
     *
     * @param savedInstanceState The activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.provider_layout);

        /* This is the ProviderLayout, so setting layout to Provider */
        layout = "Provider";

        /* Setting all View variables */
        providerBiddedButton = (ImageView) findViewById(R.id.biddedTasksProviderButton);
        providerAssignedButton = (ImageView) findViewById(R.id.acceptedTasksProviderButton);
        providerSearchButton = (ImageView) findViewById(R.id.searchTasksButton);
        providerMapButton = (ImageView) findViewById(R.id.taskMapButton);
        switchLayoutButton = (ImageView) findViewById(R.id.switchViewProviderButton);
        userMenuButton = (ImageView) findViewById(R.id.userMenuButton);

    }

    /**
     * Changes the title at the top of the layout.
     *
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
     * Handles all click events for the bottom navigation and the user button in the
     * top right corner of the layout.
     *
     * @param view The view object that has been clicked.
     */
    //TODO uncomment as activities are created
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            /* If user tapped the Bids button */
            case R.id.biddedTasksProviderButton:
                intent = new Intent(this, ProviderTaskListActivity.class);
                intent.putExtra("Status", "Bidded");
                startActivity(intent);
                finish();
                break;
        /* If user tapped the assigned button */
            case R.id.acceptedTasksProviderButton:
                intent = new Intent(this, ProviderTaskListActivity.class);
                intent.putExtra("Status", "Assigned");
                startActivity(intent);
                finish();
                break;
        /* If the user tapped the search button */
            case R.id.searchTasksButton:
                intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                finish();
                break;
        /* If the user tapped the map button */
            case R.id.taskMapButton:
                /* Commented out as the activity does not exist yet
                Intent intent = new Intent(this, MapActivity.class);
                startActivity(intent);
                      finish();
                */
                break;
        /* If the user taps the switch button */
            case R.id.switchViewProviderButton:
                intent = new Intent(this, RequesterViewListActivity.class);
                intent.putExtra("Status", "Requested");
                startActivity(intent);
                finish();
                break;
        /* If the user taps the user menu button */
            case R.id.userMenuButton:
                //TODO implement dropdown menu
                popupMenu.show();
                break;
        }
    }

    /**
     * Creates the popup menu displayed when the user clicks on the userMenuButton.
     */
    protected void createPopup(){
        /* Create Popup Menu */
        int[] location = new int[2];
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
     * When the activity starts, create the popup menu.
     */
    @Override
    protected void onStart() {
        super.onStart();
        createPopup();

    }


}