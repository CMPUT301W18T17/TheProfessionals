package professional.team17.com.professional.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.concurrent.ExecutionException;

import professional.team17.com.professional.Dialogs.ConfirmDialog;
import professional.team17.com.professional.Helpers.ConnectedState;
import professional.team17.com.professional.Helpers.ConnectivityCheck;
import professional.team17.com.professional.R;
import professional.team17.com.professional.Controllers.ServerHelper;
import professional.team17.com.professional.Controllers.SyncController;
import professional.team17.com.professional.Navigation.NavFactory;
import professional.team17.com.professional.Navigation.NavButton;

/**
 * Created by ag on 2018-03-26.
 */

public  class Navigation extends AppCompatActivity implements ConfirmDialog.ConfirmDialogListener {

    protected ServerHelper serverHelper;
    protected SharedPreferences sharedpreferences;
    protected String username;


    /**
     * On creation of the activity, assign values to all variables.
     *
     * @param savedInstanceState The activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        serverHelper = new ServerHelper(this);
        // Create the custom object
        sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        username = sharedpreferences.getString("username", "error");
        connectivityCheck();
        syncCheck();
    }

    /**
     * Used to check the if the app was recently pushed online
     * If moving to online and tasks were added, it will sync the data
     */
    private void syncCheck() {
        SyncController controller = new SyncController(getApplicationContext());
        ConnectedState c = ConnectedState.getInstance();
        if (c.isOnline()){
            controller.resetRequested(username);
        }
        if (c.isNewOnline()){
            managesync();
        }
    }

    /**
     * This will sync the activity
     */
    private void managesync() {
        boolean success;
        SyncController controller = new SyncController(this);
        success = controller.sync();
        if (!success) {
            createSync();
        }
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
                TextView requesteractivityTitleView = (TextView) findViewById(R.id.requesteractivityTitleView);
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
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView provideractivityTitleView = (TextView) findViewById(R.id.provideractivityTitleView);
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
        if (view.getId() == R.id.userMenuButton){
            showPopup();
        }
        else if (view.getId()==R.id.taskMapProviderButton){
            if(checkServices()){
                Intent intent = new Intent(this, MapsSearchTasksActivity.class);
                startActivity(intent);
                finish();
            }
        }
        else {
            NavButton NavButton = NavFactory.makeFor(view.getId(), this);
            startActivity(NavButton.getIntent());
            finish();
        }
    }

    public void connectivityCheck(){
        ConnectivityCheck.isOnline c = new ConnectivityCheck.isOnline();
        try {
            Object result=c.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
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
                } else if (menuItem.getTitle().equals("Log Out")){
                    Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.clear();
                    editor.apply();
                    SharedPreferences sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                    String username = sharedpreferences.getString("username", "error");
                    startActivity(intent);
                }
                return true;
            }
        });
        popup.show();
    }

    /**
     * Check if google services is in place (for preparation of map activity)
     * @return boolean
     */
    private boolean checkServices(){
        String TAG = "ProviderLayout";
        Log.d(TAG, "checkServices - google services version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        if (available == ConnectionResult.SUCCESS){
            Toast.makeText(this, "Loading Map", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "checkServices - google services is ok");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Toast.makeText(this, "Google Services is missing", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "checkServices - okay error");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(this, available, 9001);
            dialog.show();
        } else {
            Toast.makeText(this, "Cannot make map request.", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    /**
     * Sync
     */
    protected void createSync() {
        FragmentManager fm = getSupportFragmentManager();
        ConfirmDialog confirmDialog = new ConfirmDialog();
        confirmDialog.show(fm, "Sync");
    }

    /**
     *  Override Methods
     */
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onFinishConfirmDialog(Boolean confirmed) {
        if (confirmed==true){
            Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onFinishConfirmDialog(Boolean confirmed, String dialog) {
    }

}
