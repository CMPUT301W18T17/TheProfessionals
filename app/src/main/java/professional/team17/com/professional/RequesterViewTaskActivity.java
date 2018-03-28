/*
 * RequesterViewTaskActivity
 *
 * March 11, 2018
 *
 * Copyright @ 2018 Team 17, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behaviour at the University of Alberta.
 * You can find a copy of the license in the github wiki for this project.
 */
package professional.team17.com.professional;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
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
 * @author Lauren, Allison
 * @see RequesterViewListActivity
 * @see RequesterEditTaskActivity
 * @see RequesterAddTaskActivity
 * @see BidListAdapter
 * @see Task
 * @see Bid
 * @see BidList
 */
public class RequesterViewTaskActivity extends Navigation implements ConfirmDialog.ConfirmDialogListener {
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
    private Task task;
    ElasticSearchController elasticSearchController = new ElasticSearchController();
    BidListAdapter bidAdapter;
    BidList bidList;
    Bid chosenBid;
    String dialogFlag;

    /**
     * On creation of the activity, set the layout elements, onClickListeners, and retrieve
     * the task from the server.
     * @param savedInstanceState The activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requester_view_task2);

        /* Set activity title */
        setActivityTitleRequester("View Task");

        /*Set layout elements */
        backButton = (ImageButton) findViewById(R.id.requester_view_taskbackButton);
        viewPhotos = (ImageButton) findViewById(R.id.requester_view_taskViewPhotosButton);
        viewLocation = (ImageButton) findViewById(R.id.requester_view_taskviewLocationButton);
        listView = (ListView) findViewById(R.id.requester_task_view_listview);
        bidderName = (TextView) findViewById(R.id.requester_view_taskbidder);
        bidderNameView = (TextView) findViewById(R.id.requester_view_taskbidderUsernameView);
        acceptedBid = (TextView) findViewById(R.id.requester_view_taskacceptedBid);
        acceptedBidView = (TextView) findViewById(R.id.requester_view_taskbidAmountView);
        setStatusTo = (TextView) findViewById(R.id.requester_view_tasksetStatus);
        requestedButton = (Button) findViewById(R.id.requester_view_taskrequestedButton);
        doneButton = (Button) findViewById(R.id.requester_view_taskdoneButton);

        /* Set adapters */
        bidList = new BidList();
        bidAdapter = new BidListAdapter(this, bidList);
        listView.setAdapter(bidAdapter);


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
        checkOffline();

        populateBidList();

        /* Check Existence of Location */
        if (task.getLatLng()== null){
            viewLocation.setVisibility(View.INVISIBLE);
        } else {
            viewLocation.setVisibility(View.VISIBLE);
        }

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
                Intent intent = new Intent(RequesterViewTaskActivity.this, MapsShowALocationActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("aTaskLatLng", task.getLatLng());
                intent.putExtras(bundle);
                intent.putExtra("aTaskAddress", task.getLocation());
                startActivity(intent);
            }
        });

        requestedButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                setToRequested();
            }
        });

        doneButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                setToDone();
            }
        });






    }

    @Override
    void checkOffline() {
        ConnectedState c = ConnectedState.getInstance();
        Log.i("WEWE", "checkOffline: ");
        if (c.isOffline()) {
            Offline fragment = new Offline();
            getSupportFragmentManager().beginTransaction().replace(R.id.requester_view_task_frame, fragment).commit();
        } else {
            populateBidList();

        /* Check Existence of Location */
            if (task.getLatLng() == null) {
                viewLocation.setVisibility(View.INVISIBLE);
            }
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
            ID = extrasBundle.getString("task");
        }
    }

    /**
     * Gets the task information from the server using the ID.
     * @throws Exception If the server fails to return a task.
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
                titleView = (TextView) findViewById(R.id.requester_view_tasktitleView);
                titleView.setText(task.getName());
                dateView = (TextView) findViewById(R.id.requester_view_taskdateView);
                dateView.setText(task.getDateAsString());
                descriptionView = (TextView) findViewById(R.id.requester_view_taskdescriptionView);
                descriptionView.setText(task.getDescription());
                statusView = (TextView) findViewById(R.id.requester_view_taskstatusView);
                statusView.setText(task.getStatus());
            }
        });
    }

    /*
     * Sets the bottom half of the layout and displays different info depending on the task's
     * status.
     */
    private void populateBidList(){
        BidList temp = new BidList();
        String status = task.getStatus();
        setTaskViews();
        if (status.equals("Requested")){
            /* does not display additional information */
            setRequestedView();
        }
        else if (status.equals("Bidded")){
            /* displays a list of bids */
            setBiddedView();
            temp = task.getBids();
            bidList.addAll(temp);
            bidAdapter.notifyDataSetChanged();
        }
        else if (status.equals("Assigned")){
            /* displays provider info and requested/done buttons */
            setAssignedView();
            bidList = task.getBids();
            chosenBid = bidList.getBid(0);
            setBidViews(chosenBid.getName(), chosenBid.getAmountAsString());
        }
        else if (status.equals("Done")){
            /* displays provider info and allows a review to be left */
            setDoneView();
            bidList = task.getBids();
            chosenBid = bidList.getBid(0);
            setBidViews(chosenBid.getName(), chosenBid.getAmountAsString());
            //TODO allow requester to leave a review for the provider
        }
    }


    /**
     * Shows certain layout elements when the task status is Requested
     */
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

    /**
     * Shows certain layout elements when the task status is Bidded
     */
    private void setBiddedView(){
        listView.setVisibility(View.VISIBLE);
        bidderName.setVisibility(View.GONE);
        bidderNameView.setVisibility(GONE);
        acceptedBid.setVisibility(GONE);
        acceptedBidView.setVisibility(GONE);
        setStatusTo.setVisibility(GONE);
        requestedButton.setVisibility(GONE);
        doneButton.setVisibility(GONE);
    }

    /**
     * Shows certain layout elements when the task status is Assigned
     */
    private void setAssignedView(){
        listView.setVisibility(View.GONE);
        bidderName.setVisibility(VISIBLE);
        bidderNameView.setVisibility(VISIBLE);
        acceptedBid.setVisibility(VISIBLE);
        acceptedBidView.setVisibility(VISIBLE);
        setStatusTo.setVisibility(VISIBLE);
        requestedButton.setVisibility(VISIBLE);
        doneButton.setVisibility(VISIBLE);
    }

    /**
     * Shows certain layout elements when the task status is Done
     */
    private void setDoneView(){
        listView.setVisibility(View.GONE);
        bidderName.setVisibility(VISIBLE);
        bidderNameView.setVisibility(VISIBLE);
        acceptedBid.setVisibility(VISIBLE);
        acceptedBidView.setVisibility(VISIBLE);
        setStatusTo.setVisibility(GONE);
        requestedButton.setVisibility(GONE);
        doneButton.setVisibility(GONE);
        //TODO leaving review button
    }

    /**
     * Updates the TextViews that are conditionally displayed, depending on status.
     * @param bidder The bidder's username
     * @param bidAmount The winning bid's amount
     */
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
     * Handles the "Set task to requested" dialog fragment (populates it with text).
     */
    public void setToRequested(){
        FragmentManager fm = getSupportFragmentManager();


        ConfirmDialog confirmDialog = new ConfirmDialog();
        Bundle args = new Bundle();
        args.putString("dialogFlag", "Requested");
        args.putString("title", "Set Status To Requested");
        args.putString("cancel", "Cancel");
        args.putString("confirm", "Yes");
        args.putString("message", "Are you sure you want to set the status to Requested? This will" +
                "reopen bidding.");

        confirmDialog.setArguments(args);
        confirmDialog.show(fm, "To Requested");
    }


    /**
     * Handles the "Set task to done" dialog fragment (populates it with text).
     */
    public void setToDone(){
        FragmentManager fm = getSupportFragmentManager();

        ConfirmDialog confirmDialog = new ConfirmDialog();
        Bundle args = new Bundle();
        args.putString("dialogFlag", "Done");
        args.putString("title", "Set Status to Done");
        args.putString("cancel", "Cancel");
        args.putString("confirm", "Yes");
        args.putString("message", "Are you sure you want to set status to Done?");

        confirmDialog.setArguments(args);
        confirmDialog.show(fm, "To Done");
    }


    /**
     * Handles the "accept bid" dialog fragment (populates it with text).
     */
    private void acceptBidDialog(){
        FragmentManager fm = getSupportFragmentManager();
        ConfirmDialog confirmDialog = new ConfirmDialog();
        Bundle args = new Bundle();
        args.putString("dialogFlag", "Accept");
        args.putString("title", "Accept Bid");
        args.putString("cancel", "Cancel");
        args.putString("confirm", "Yes");
        args.putString("message", "Are you sure you want to accept this bid? This will delete all other bids.");

        confirmDialog.setArguments(args);
        confirmDialog.show(fm, "To Done");
    }


    /**
     * Handles the "decline bid" dialog fragment (populates it with text).
     */
    private void declineBidDialog(){
        FragmentManager fm = getSupportFragmentManager();
        ConfirmDialog confirmDialog = new ConfirmDialog();
        Bundle args = new Bundle();
        args.putString("dialogFlag", "Decline");
        args.putString("title", "Decline Bid");
        args.putString("cancel", "Cancel");
        args.putString("confirm", "Yes");
        args.putString("message", "Are you sure you want to decline this bid? It will be deleted.");

        confirmDialog.setArguments(args);
        confirmDialog.show(fm, "To Done");
    }

    /***
     * Interface method from ConfirmDialog.ConfirmDialogListener
     * @param confirmed boolean value representing the user response in the dialog
     * @param dialog - the type of dialog called
     * true means the user confirmed.
     */
    public void onFinishConfirmDialog(Boolean confirmed, String dialog){
        BidList temp;
        if (confirmed ==true) {
            if (dialog.equals("Accept")) {
                /* Bid is accepted by the user. Delete all other bids, set the chosen bid as the
                 * sole bid, update the task's status to Assigned, update the page's layout to show
                 * the Assigned layout and fill the new layout with the chosen bid's info.
                 */
                task.chooseBid(chosenBid);
                task.setStatus("Assigned");

                bidList.clear();
                bidList.add(chosenBid);


                setAssignedView();
                setBidViews(chosenBid.getName(), chosenBid.getAmountAsString());

                /* send notification to bidder */
                String bidder = chosenBid.getName();
                Profile bidderProfile = elasticSearchController.getProfile(bidder);
                NotificationList notificationList = bidderProfile.getNotificationList();
                notificationList.newAssignedNotification(task, task.getProfileName());
                bidderProfile.setNotificationList(notificationList);
                elasticSearchController.addProfile(bidderProfile);

            } else if (dialog.equals("Decline")) {
                /* Bid is declined by the user. Remove the bid from bidList and refresh the
                 *listview.
                 */
                task.removeBid(chosenBid);
                bidList.remove(chosenBid);
                bidAdapter.notifyDataSetChanged();
            } else if (dialog.equals("Requested")) {
                /* User changes the task's status back to requested. Change the task status, update
                 * the layout to show the Requested Layout and remove the chosen bid.
                 */
                bidList.clear();
                task.setRequested();
                setTaskViews();
                setRequestedView();
            } else if (dialog.equals("Done")) {
                /* User changes the task's status to done. Change the task status and update the
                 * layout to show the Done layout.
                 */
                task.setDone();
                setTaskViews();
                setDoneView();
            }
            Log.i("TAD", "CHANGE TASK "+task);
            elasticSearchController.updateTasks(task);
        }
    }


    /**
     * The onClick method for the profile username of the bidder
     * Move to the profile view to see the requested info
     * @param v the view the button is located on
     */
    public void viewProfileRow(View v){
        final int position = listView.getPositionForView((View) v.getParent());
        String bidder = bidList.getBid(position).getName();
        viewProfileActivity(bidder);
    }

    /**
     * The onClick method for the profile username of the bidder
     * Move to the profile view to see the requested info
     * @param v the view the button is located on
     */
    public void viewProfileName(View v){
        String bidder = bidderNameView.getText().toString();
        viewProfileActivity(bidder);
    }

    /**
     *
     * @param name - user name to be added to intent
     */
    public void viewProfileActivity(String name){
        Intent intention = new Intent(this, OtherProfileViewActivity.class);
        intention.putExtra("profile", name);
        startActivity(intention);
    }


    /**
     * The onClick method for the delete bid button.
     * @param v The delete bid view object.
     */
    public void deleteBid(View v){

        final int position = listView.getPositionForView((View) v.getParent());
        chosenBid = bidList.getBid(position);
        declineBidDialog();

    }

    /**
     * The onClick method for thboolean value representing the user response in the dialoge accept bid button.
     * @param v The accept bid view object.
     */
    public void acceptBid(View v){

        final int position = listView.getPositionForView((View) v.getParent());
        chosenBid = bidList.getBid(position);
        acceptBidDialog();

    }

    /**
     * Interface method from ConfirmDialog.
     * @param confirmed boolean value representing the user response in the dialog
     */
    public void onFinishConfirmDialog(Boolean confirmed){}


}
