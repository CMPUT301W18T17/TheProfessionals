/*
 * RequesterViewTaskActivity
 *
 * March 11, 2018
 *
 * Copyright
 */
package professional.team17.com.professional;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

/**
 * The activity that is displayed when a user in the Requester role views one of their own tasks.
 */
public class RequesterViewTaskActivity extends RequesterLayout {
    /* Layout items */
    ImageButton backButton;
    ImageButton viewPhotos;
    ImageButton viewLocation;
    TextView titleView;
    TextView dateView;
    TextView statusView;
    TextView descriptionView;
    ListView listView;
    /* Other variables */
    String ID;
    Task task;
    ElasticSearchController elasticSearchController = new ElasticSearchController();

    /**
     * On creation of the activity, set the layout elements, onClickListeners, and retrive
     * the task from the server.
     * @param savedInstanceState The activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requester_view_task2);

        /* Set activity title */
        setActivityTitle("View Task");

        /*Set layout elements */
        backButton = (ImageButton) findViewById(R.id.backButton);
        viewPhotos = (ImageButton) findViewById(R.id.ViewPhotosButton);
        viewLocation = (ImageButton) findViewById(R.id.viewLocationButton);
        listView = (ListView) findViewById(R.id.bidListView);


        /* Set OnClickListeners */
        backButton.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        viewPhotos.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO implement viewing photos
            }
        });

        viewLocation.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO implement viewing location
            }
        });

        /* Get the task ID from the previous activity, then get the task from the server. */
        try {
            getBundle();
        } catch (Exception e) {
            Log.i("Bundle", "Bundle was empty (no task ID was passed to EditTask)");
        }
        try {
            getFromServer();
        } catch (Exception e) {
            Log.i("Server", "Server failed to return a task for that ID");
        }

    }

    /**
     * Gets the task ID from the previous activity. Throws an exception if the ID is not found.
     * @throws Exception If the bundle is empty (the task is not found).
     */
    private void getBundle() throws Exception{
        Intent startedIntent = getIntent();
        Bundle extrasBundle = startedIntent.getExtras();
        if (extrasBundle.isEmpty()){
            throw new Exception();
        }
        else {
            ID = extrasBundle.getString("ID");
        }
    }

    /**
     * Gets the task information from the server using the ID.
     * @throws Exception
     */
    private void getFromServer() throws Exception{
        task = elasticSearchController.getTask(ID);
        if (task == null){
            throw new Exception();
        }
        else {
            /* Update TextViews */
            setViews();
        }
    }

    /**
     * Updates the TextViews with the task information.
     */
    private void setViews() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                titleView = (TextView) findViewById(R.id.titleView);
                titleView.setText(task.getName());
                dateView = (TextView) findViewById(R.id.dateView);
                dateView.setText(task.getDateAsString());
                descriptionView = (TextView) findViewById(R.id.descriptionView);
                descriptionView.setText(task.getDescription());
                statusView = (TextView) findViewById(R.id.statusView);
                statusView.setText(task.getStatus());
            }
        });
    }

    private void populateBidList(){

    }

    /**
     * When the user taps the back button, go back to the RequesterViewListActivity.
     */
    @Override
    public void finish(){
        Intent intent = new Intent(RequesterViewTaskActivity.this, RequesterViewListActivity.class);
        startActivity(intent);
    }
}


