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

public abstract class ProviderLayout extends AppCompatActivity implements ImageView.OnClickListener {

    private String activityTitle;
    private TextView activityTitleView;
    private ImageView providerBiddedButton;
    private ImageView providerAssignedButton;
    private ImageView providerSearchButton;
    private ImageView providerMapButton;
    private ImageView switchLayoutButton;
    private ImageView userMenuButton;
    private PopupMenu popupMenu;

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
        activityTitleView.setText(activityTitle);
    }


    //TODO uncomment as activities are created
    public void onClick(View view) {
        switch (view.getId()){
            /* If user tapped the Bids button */
            case R.id.biddedTasksProviderButton:
                /* Commented out as the activity does not exist yet
                Intent intent = new Intent(this, ProviderListActivity.class);
                intent.putExtra("Status", "Bidded");
                startActivity(intent);
                */
                break;
        /* If user tapped the assigned button */
            case R.id.acceptedTasksProviderButton:
                /* Commented out as the activity does not exist yet
                Intent intent = new Intent(this, ProviderListActivity.class);
                intent.putExtra("Status", "Assigned");
                startActivity(intent);
                */
                break;
        /* If the user tapped the search button */
            case R.id.searchTasksButton:
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                break;
        /* If the user tapped the map button */
            case R.id.taskMapButton:
                /* Commented out as the activity does not exist yet
                Intent intent = new Intent(this, MapActivity.class);
                startActivity(intent);
                */
                break;
        /* If the user taps the switch button */
            case R.id.switchViewProviderButton:
                /*Commented out as the activity does not exist yet
                Intent intent = new Intent(this, RequesterListActivity.class);
                intent.putExtra("Status", "Requested");
                startActivity(intent);
                */
                break;
        /* If the user taps the user menu button */
            case R.id.userMenuButton:
                //TODO implement dropdown menu
                popupMenu.show();
                break;
        }
    }

    protected void createPopup(){
        /* Create Popup Menu */
        popupMenu = new PopupMenu(this, userMenuButton);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Toast.makeText(ProviderLayout.this, "" + menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        createPopup();

    }


}