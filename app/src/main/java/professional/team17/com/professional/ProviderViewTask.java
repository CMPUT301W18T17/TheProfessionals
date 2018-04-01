/*
 * ProviderViewTask
 *
 * March 10, 2018
 *
 * Copyright @ 2018 Team 17, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behaviour at the University of Alberta.
 * You can find a copy of the license in the github wiki for this project.
 */

package professional.team17.com.professional;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;


/**
 *
 * An activity where the provider can see the tasks - with different ui depending
 * On the task status, and input
 * @author Allison
 * @see ServerHelper , Task, Profile
 */
public class ProviderViewTask extends Navigation implements ImageView.OnClickListener, PlaceBidDialog.PlaceBidDialogListener, ConfirmDialog.ConfirmDialogListener {

    private String username;
    //TODO below item is needed for protoype, part 5 persistence will remove this

    private SharedPreferences sharedpreferences;

    //TODO both items below can be put in controller (project part 5)
    private Task task;
    private final ServerHelper serverHelper = new ServerHelper();


    //Everything below maybe able to be set into the controller. Do not reflect in uml
    private TextView statusTextField;
    private TextView userNameTextField;
    private TextView taskTitleTextField;
    private TextView taskDateTextField;
    private TextView taskDescriptionTextField;
    private TextView taskLowBidTextField;
    private TextView taskMyBidTextField;
    private TextView taskAddressTextField;

    private TextView taskLowBidDollar;
    private TextView taskMyBidDollar;
    private TextView requesterAvgTextView; //project 5 implement
    private RatingBar requesterAvgTextField; //project 5 implement
    private ImageButton viewMapButton;
    private Button button5;


    //both buttons start as invisible by default
    private ImageButton bidButton;
    private ImageButton deleteButton;
    private ImageButton appendButton;


    /**
     * On creation of the activity, set all view objects
     * @param savedInstanceState The activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.provider_view_task);

        //get textfields ids //TODO this can be moved to controller (project part 5)
        statusTextField = (TextView) findViewById(R.id.provider_view_task_statusType);
        userNameTextField = (TextView) findViewById(R.id.provider_view_task_userName);
        taskTitleTextField = (TextView) findViewById(R.id.provider_view_task_title);
        taskDateTextField = (TextView) findViewById(R.id.provider_view_task_date_input);
        taskDescriptionTextField = (TextView) findViewById(R.id.provider_view_task_paragraph);
        taskLowBidTextField = (TextView) findViewById(R.id.provider_view_task_lowBidInput);
        taskLowBidDollar = (TextView) findViewById(R.id.provider_view_task_lowBidDollar);
        taskMyBidTextField = (TextView) findViewById(R.id.provider_view_task_myBidInput);
        taskMyBidDollar = (TextView) findViewById(R.id.provider_view_task_myBidInputDollar);
        bidButton = (ImageButton) findViewById(R.id.provider_view_task_AddBid);
        deleteButton = (ImageButton) findViewById(R.id.provider_view_task_removeBid);
        appendButton = (ImageButton) findViewById(R.id.provider_view_task_manageBid);
        taskAddressTextField = (TextView) findViewById(R.id.provider_view_task_address);
        viewMapButton = (ImageButton) findViewById(R.id.provider_view_map_button);
        button5 = (Button) findViewById(R.id.button5);

        this.setActivityTitleProvider("View Task");


        sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        username = sharedpreferences.getString("username", "error");

        viewMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProviderViewTask.this, MapsShowALocationActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("aTaskLatLng", task.getLatLng());
                intent.putExtras(bundle);
                intent.putExtra("aTaskAddress", task.getLocation());
                startActivity(intent);
            }
        });
        task = getTask();
        System.out.println("------------------------------------------------------");
        System.out.println(task.getPhotos().get(0));
        System.out.println("------------------------------------------------------");
        if (task.getPhotos() != null)
            button5.setOnClickListener(this);



        checkOffline();


        //setRating();
    }


    @Override
    void checkOffline() {
        ConnectedState c = ConnectedState.getInstance();
        if(c.isOffline()) {
            Offline fragment = new Offline();
            getSupportFragmentManager().beginTransaction().replace(R.id.provider_view_task_frame, fragment).commit();
        }
        else{
            if (task.getLatLng() == null){
                viewMapButton.setVisibility(View.INVISIBLE);
            }
            //getRequester();

            checkStatus();
            fillTask();
        }

    }
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.button5:
                Intent yourIntent = new Intent(this, providerCheckImage.class);
                Bitmap bmp = task.getPhotos().get(0); // store the image in your bitmap
                ByteArrayOutputStream bao = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 50, bao);
                yourIntent.putExtra("yourImage", bao.toByteArray());
                startActivity(yourIntent);

        }

    }


    /***
     * Interface method from PlaceBidDialog.PlaceBidDialogListener
     * @param inputText boolean value representing the user response in the dialog
     * true means the user add/changed the bid
     */
    public void onFinishPlaceBidDialog(String inputText){
        double bidAmount =  Double.valueOf(inputText);
        int duration = Toast.LENGTH_SHORT;


        task.addBid(new Bid(username, bidAmount));

        serverHelper.updateTasks(task);
        statusTextField.setText(task.getStatus());
        fillBidded();

        /* Send notification */
        String requester = task.getProfileName();
        Profile requesterProfile = serverHelper.getProfile(requester);
        NotificationList notificationList = requesterProfile.getNotificationList();
        notificationList.newBidNotification(task, bidAmount, username);
        requesterProfile.setNotificationList(notificationList);
        serverHelper.addProfile(requesterProfile);
    }

    /***
     * Interface method from ConfirmDialog.ConfirmDialogListener
     * @param confirmed boolean value representing the user response in the dialog
     * true means the user confirmed.
     */
    public void onFinishConfirmDialog(Boolean confirmed){
        if (confirmed==true){
            Bid bid = task.getBids().getBid(username);
            task.removeBid(bid);
        }
        serverHelper.updateTasks(task);
        statusTextField.setText(task.getStatus());
        if (task.isBidded()) {

            fillBidded();
        }
        if (task.isRequested()) {
            fillRequested();
        }

    }

    /***
     * Interface method from ConfirmDialog.ConfirmDialogListener
     * @param confirmed boolean value representing the user response in the dialog
     * @param dialog - the type of dialog called
     * true means the user confirmed.
     */
    public void onFinishConfirmDialog(Boolean confirmed, String dialog){}

    //TODO move all UI controls into controller (project part 5)
    /**
     * This will fill the basic task info of status, requestername, taskname, date, and description
     */
    public void fillTask() {
        //plug items from task
        statusTextField.setText(task.getStatus());
        userNameTextField.setText(task.getProfileName());
        taskTitleTextField.setText(task.getName());
        taskDateTextField.setText(task.getDateAsString());
        taskDescriptionTextField.setText(task.getDescription());
        if (task.getLatLng()!= null){
            taskAddressTextField.setText(task.getLocation());
        } else {
            taskAddressTextField.setText("N/A");
        }

    }

    /**
     * This will display the correct UI for tasks in 'Requested' status.
     */
    public void fillRequested(){
        deleteButton.setVisibility(View.GONE);
        appendButton.setVisibility(View.GONE);
        bidButton.setVisibility(View.VISIBLE);
        taskLowBidTextField.setText("No bids yet!");
        taskMyBidTextField.setText("Place a bid!");
        taskLowBidDollar.setVisibility(View.INVISIBLE);
        taskMyBidDollar.setVisibility(View.INVISIBLE);
    }


    /**
     * This will display the correct UI for tasks in "assigned status'
     */
    public void fillAssigned(){
        if (task.getBids().userBidded(username)) {
            BidList bids = task.getBids();
            taskMyBidTextField.setVisibility(View.VISIBLE);
            taskMyBidDollar.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.INVISIBLE);
            bidButton.setVisibility(View.INVISIBLE);
            taskLowBidTextField.setVisibility(View.INVISIBLE);
            taskLowBidDollar.setVisibility(View.INVISIBLE);
            appendButton.setVisibility(View.INVISIBLE);
            TextView taskLowBidDesc = (TextView) findViewById(R.id.provider_view_task_lowBid);
            taskLowBidDesc.setVisibility(View.INVISIBLE);
            taskMyBidTextField.setText(bids.getBid(username).getAmountAsString());
        }
        else {
            //TODO what displays if user is not bidder?
        }
    }

    /**
     * This will dispay the correct info for tasks in bidded status
     */
    public void fillBidded() {
        //set lowest bid amount
        BidList bids = task.getBids();
        Bid bid = bids.getBid(username);

        deleteButton.setVisibility(View.INVISIBLE);
        bidButton.setVisibility(View.VISIBLE);
        appendButton.setVisibility(View.INVISIBLE);

        taskLowBidTextField.setText(bids.getLowest().getAmountAsString());
        if (bid!=null) {
            taskMyBidTextField.setText(bids.getBid(username).getAmountAsString());
            deleteButton.setVisibility(View.VISIBLE);
            bidButton.setVisibility(View.INVISIBLE);
            appendButton.setVisibility(View.VISIBLE);
        }
        taskLowBidDollar.setVisibility(View.VISIBLE);
        taskMyBidDollar.setVisibility(View.VISIBLE);
    }

/* //TODO implement in project part 5
    public void setRating(){
        if (requester.getReviewList().isEmpty()==false) {
            requesterAvgTextView = (TextView) findViewById(R.id.provider_view_rating);
            requesterAvgTextField = (RatingBar) findViewById(R.id.provider_view_rating_bar);
            requesterAvgTextView.setVisibility(View.VISIBLE);
            requesterAvgTextField.setVisibility(View.VISIBLE);
            String sAvg = requester.getReviewList().getAvgString();
            float fAvg = (float) requester.getReviewList().getAvg();
            requesterAvgTextField.setRating(fAvg / 5);
            requesterAvgTextView.setText(sAvg);
        }
    }
*/
    /**
     *
     * Controls and get teh correct UI to display
     */
    public void checkStatus() {

        if (task.isRequested()){
            fillRequested();
        }

        else if (task.isBidded()){
            fillBidded();
        }

        else if (task.isAssigned() || task.isDone()){
            fillAssigned();
        }
    }

    /**
     * Move to the profile view to see the requested info
     * @param v the view the button is located on
     */
    public void viewProfile(View v){
        Intent intention = new Intent(this, OtherProfileViewActivity.class);
        intention.putExtra("profile", task.getProfileName());
        startActivity(intention);
    }

    /**
     * Build fragment to add a bid
     * @param v the view the button is located on
     */
    public void placeBid(View v){
        FragmentManager fm = getSupportFragmentManager();
        PlaceBidDialog dialogFragment = new PlaceBidDialog ();
        Bundle args = new Bundle();
        args.putString("title", "Place Bid");
        args.putString("amount","");

        dialogFragment.setArguments(args);
        dialogFragment.show(fm, "Sample Fragment");

    }


    /**
     * Build fragment to edit bid
     * @param v the view the button is located on
     */
    public void appendBid(View v){
        FragmentManager fm = getSupportFragmentManager();
        PlaceBidDialog dialogFragment = new PlaceBidDialog ();
        Bundle args = new Bundle();
        args.putString("title", "Change Bid");
        args.putString("amount", taskMyBidTextField.getText().toString());
        dialogFragment.setArguments(args);
        dialogFragment.show(fm, "Append bid");
    }

    /**
     * Build fragment to delete a bid
     * @param v the view the button is located on
     */
    public void cancelBid(View v){
        FragmentManager fm = getSupportFragmentManager();

        ConfirmDialog confirmDialog = new ConfirmDialog();
        Bundle args = new Bundle();
        args.putString("title", "Cancel Bid");
        args.putString("cancel", "Cancel");
        args.putString("confirm", "Yes");
        args.putString("message", "Are you sure you want to delete?");

        confirmDialog.setArguments(args);
        confirmDialog.show(fm, "Sample Fragment");
    }

    /**
     *
     * @return Returns the subscription sent with the Intent
     */
    private Task getTask() {
        Bundle intent = getIntent().getExtras();
        String task = intent.getString("Task");
        return serverHelper.getTask(task);
    }
}