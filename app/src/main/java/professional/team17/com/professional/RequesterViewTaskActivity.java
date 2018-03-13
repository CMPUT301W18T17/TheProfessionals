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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

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
    TextView bidderName;
    TextView bidderNameView;
    TextView acceptedBid;
    TextView acceptedBidView;
    TextView setStatusTo;
    Button requestedButton;
    Button doneButton;
    /* Other variables */
    String ID;
    Task task;
    ElasticSearchController elasticSearchController = new ElasticSearchController();
    BidListAdapter bidAdapter;
    BidList bidList;
    Bid winningBid;

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
        bidderName = (TextView) findViewById(R.id.bidder);
        bidderNameView = (TextView) findViewById(R.id.bidderUsernameView);
        acceptedBid = (TextView) findViewById(R.id.acceptedBid);
        acceptedBidView = (TextView) findViewById(R.id.acceptbidAmount);
        setStatusTo = (TextView) findViewById(R.id.setStatus);
        requestedButton = (Button) findViewById(R.id.requestedButton);
        doneButton = (Button) findViewById(R.id.doneButton);

        /* Set adapters */
        bidList = new BidList();
        bidAdapter = new BidListAdapter(this, bidList);
        listView.setAdapter(bidAdapter);




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

        requestedButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                //TODO display dialog
            }
        });

        doneButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
               //TODO display dialog
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

        populateBidList();

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
            setTaskViews();
        }
    }

    /**
     * Updates the TextViews with the task information.
     */
    private void setTaskViews() {
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
        String status = task.getStatus();
        if (status.equals("Requested")){
            /* does not display additional information */
            setRequestedView();
        }
        else if (status.equals("Bidded")){
            setBiddedView();
            bidList = task.getBids();
            bidAdapter.notifyDataSetChanged();
        }
        else if (status.equals("Requested")){
            setAssignedView();
            bidList = task.getBids();
            winningBid = bidList.getBid(0);
            setBidViews(winningBid.getName(), winningBid.getAmountAsString());
        }
        else if (status.equals("Done")){
            setDoneView();
            bidList = task.getBids();
            winningBid = bidList.getBid(0);
            setBidViews(winningBid.getName(), winningBid.getAmountAsString());
        }
    }

    private void setRequestedView(){
        listView.setVisibility(View.GONE);
        bidderName.setVisibility(View.GONE);
        bidderNameView.setVisibility(GONE);
        acceptedBid.setVisibility(GONE);
        acceptedBidView.setVisibility(GONE);
        setStatusTo.setVisibility(GONE);
        requestedButton.setVisibility(GONE);
        doneButton.setVisibility(GONE);
    }

    private void setBiddedView(){
        listView.setVisibility(GONE);
        bidderName.setVisibility(VISIBLE);
        bidderNameView.setVisibility(VISIBLE);
        acceptedBid.setVisibility(VISIBLE);
        acceptedBidView.setVisibility(VISIBLE);
        setStatusTo.setVisibility(VISIBLE);
        requestedButton.setVisibility(VISIBLE);
        doneButton.setVisibility(VISIBLE);
    }

    private void setAssignedView(){
        bidderName.setVisibility(VISIBLE);
        bidderNameView.setVisibility(VISIBLE);
        acceptedBid.setVisibility(VISIBLE);
        acceptedBidView.setVisibility(VISIBLE);
        //TODO leaving review button
    }

    private void setDoneView(){
        bidderName.setVisibility(VISIBLE);
        bidderNameView.setVisibility(VISIBLE);
        acceptedBid.setVisibility(VISIBLE);
        acceptedBidView.setVisibility(VISIBLE);
    }

    private void setBidViews(final String bidder, final String bidAmount){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                bidderNameView.setText(bidder);
                acceptedBidView.setText(bidAmount);
            }
        });
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


