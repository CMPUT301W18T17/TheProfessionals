package professional.team17.com.professional;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

/**
 * Created by ag on 2018-03-26.
 */

public abstract  class Navigation extends AppCompatActivity implements ImageView.OnClickListener {
    private TextView requesteractivityTitleView;
    private TextView provideractivityTitleView;
    private ImageView providerBiddedButton;
    private ImageView providerAssignedButton;
    private ImageView providerSearchButton;
    private ImageView providerMapButton;
    private ImageView switchLayoutButtonRequester;
    private ImageView switchLayoutButtonProvider;
    private ImageView userMenuButton;
    private PopupMenu popupMenu;
    private ImageView requesterAddTaskButton;
    private ImageView requesterBiddedButton;
    private ImageView requesterAssignedButton;
    private ImageView requesterRequestedButton;


    /**
     * On creation of the activity, assign values to all variables.
     *
     * @param savedInstanceState The activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        /* Setting all View variables */
        providerBiddedButton = (ImageView) findViewById(R.id.biddedTasksProviderButton);
        providerAssignedButton = (ImageView) findViewById(R.id.acceptedTasksProviderButton);
        providerSearchButton = (ImageView) findViewById(R.id.searchTasksButton);
        providerMapButton = (ImageView) findViewById(R.id.taskMapButton);
        switchLayoutButtonProvider = (ImageView) findViewById(R.id.switchViewProviderButton);
        userMenuButton = (ImageView) findViewById(R.id.userMenuButton);


        requesterAddTaskButton = (ImageView) findViewById(R.id.addTaskRequesterButton);
        requesterBiddedButton = (ImageView) findViewById(R.id.biddedTasksRequesterButton);
        requesterAssignedButton = (ImageView) findViewById(R.id.acceptedTasksRequesterButton);
        requesterRequestedButton = (ImageView) findViewById(R.id.requestedTasksRequesterButton);
        switchLayoutButtonRequester = (ImageView) findViewById(R.id.switchViewRequesterButton);
    }

    /**
     * Changes the title at the top of the layout.
     *
     * @param title The title of the activity being displayed.
     */
    public void setActivityTitleRequester(final String title) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                requesteractivityTitleView = (TextView) findViewById(R.id.requesteractivityTitleView);
                requesteractivityTitleView.setText(title);
            }
        });
    }

    /**
     * Changes the title at the top of the layout.
     *
     * @param title The title of the activity being displayed.
     */
    public void setActivityTitleProvider(final String title) {
        Log.i("WEWE", "setActivityTitleProvider: ");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                provideractivityTitleView = (TextView) findViewById(R.id.provideractivityTitleView);
                provideractivityTitleView.setText(title);
            }
        });
    }

    /**
     * Handles all click events for the bottom navigation and the user button in the
     * top right corner of the layout.
     *
     * @param view The view object that has been clicked.
     */
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
                if(checkServices()){
                    intent = new Intent(this, MapsSearchTasksActivity.class);
                    startActivity(intent);
                    finish();

                }
                break;
        /* If the user taps the switch button */
            case R.id.switchViewProviderButton:
                intent = new Intent(this, RequesterViewListActivity.class);
                intent.putExtra("Status", "Requested");
                startActivity(intent);
                finish();
                break;
            case R.id.addTaskRequesterButton:
                intent = new Intent(this, RequesterAddTaskActivity.class);
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
                //TODO implement dropdown menu
                showPopup();
                //popupMenu.show();
                break;
        }
    }



    /**
     * Creates the popup menu displayed when the user clicks on the userMenuButton.
     */
    protected void showPopup(){
        /* Create Popup Menu */
        PopupMenu popup = new PopupMenu(this, findViewById(R.id.userMenuButton));
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.popup_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                //Toast.makeText(ProviderLayout.this, "" + menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                if(menuItem.getTitle().equals("My Profile")){
                    Intent intent = new Intent(getApplicationContext(), MyProfileViewActivity.class);
                    startActivity(intent);
                } else if (menuItem.getTitle().equals("Edit My Profile")){
                    Intent intent = new Intent(getApplicationContext(), EditMyProfileActivity.class);
                    startActivity(intent);
                } else if (menuItem.getTitle().equals("My Notifications")){
                    Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);
                    startActivity(intent);
                }
                return true;
            }
        });
        popup.show();
    }

    private boolean checkServices(){
        String TAG = "ProviderLayout";
        Log.d(TAG, "checkServices - google services version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        if (available == ConnectionResult.SUCCESS){
            Toast.makeText(this, "Google Services is OK", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "checkServices - google services is ok");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Toast.makeText(this, "Google Services is NOT Ok", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "checkServices - okay error");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(this, available, 9001);
            dialog.show();
        } else {
            Toast.makeText(this, "Cannot make map request.", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    /**
     * When the activity starts, create the popup menu.
     */
    @Override
    protected void onStart() {
        super.onStart();


    }




}
