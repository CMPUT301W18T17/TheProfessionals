/*
 * ProviderViewTask
 *
 * March 10, 2018
 *
 * Copyright @ 2018 Team 17, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behaviour at the University of Alberta.
 * You can find a copy of the license in the github wiki for this project.
 */

package professional.team17.com.professional.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import professional.team17.com.professional.Dialogs.ConfirmDialog;
import professional.team17.com.professional.Dialogs.Offline;
import professional.team17.com.professional.Dialogs.PlaceBidDialog;
import professional.team17.com.professional.Controllers.ProviderTaskController;
import professional.team17.com.professional.R;
import professional.team17.com.professional.Controllers.ServerHelper;
import professional.team17.com.professional.Helpers.TaskStatus;


/**
 *
 * An activity where the provider can see the tasks - with different ui depending
 * On the task status, and input
 * @author Allison
 * @see ServerHelper , Task, Profile
 */
public class ProviderViewTask extends Navigation implements PlaceBidDialog.PlaceBidDialogListener {

    private ProviderTaskController providerTaskController;

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
    private TextView taskLowBidDesc;


    //both buttons start as invisible by default
    private ImageButton bidButton;
    private ImageButton deleteButton;
    private ImageButton appendButton;
    private ImageButton viewMapButton;
    private ImageButton  button5;
    private ImageButton back;


    /**
     * On creation of the activity, set all view objects
     * @param savedInstanceState The activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.provider_view_task);
        taskLowBidDesc = (TextView) findViewById(R.id.provider_view_task_lowBid);
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
        button5 = (ImageButton) findViewById(R.id.provider_view_task_photo);
        back = (ImageButton) findViewById(R.id.backProvider);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        this.setActivityTitleProvider("View Task");

        providerTaskController = new ProviderTaskController(this);
        providerTaskController.setTask(getIntent().getExtras());
        if (providerTaskController.checkOffline()){
           Offline fragment = new Offline();
           getSupportFragmentManager().beginTransaction().replace(R.id.provider_view_task_frame, fragment).commit();
        }


        providerTaskController.fillTask(statusTextField,userNameTextField,taskTitleTextField,taskDateTextField,taskDescriptionTextField,taskAddressTextField);
        providerTaskController.location(viewMapButton);
        providerTaskController.photos(button5);
        checkStatus();

    }
   public void photoClick(View view){
                Intent yourIntent = new Intent(ProviderViewTask.this, providerCheckImage.class);
                yourIntent.putStringArrayListExtra("yourImage", providerTaskController.getPhotos());
                startActivity(yourIntent);

        }


    public void mapClick(View view){
        Intent intent = new Intent(ProviderViewTask.this, MapsShowALocationActivity.class);
        Bundle bundle = providerTaskController.getLocation(intent);
        startActivity(intent);
    }

    /***
     *
     * Interface method from PlaceBidDialog.PlaceBidDialogListener
     * @param inputText boolean value representing the user response in the dialog
     * true means the user add/changed the bid
     */
    public void onFinishPlaceBidDialog(String inputText){
        double bidAmount =  Double.valueOf(inputText);
        providerTaskController.placeBid(bidAmount);
        fillBidded();
    }

    /***
     * Interface method from ConfirmDialog.ConfirmDialogListener
     * @param confirmed boolean value representing the user response in the dialog
     * true means the user confirmed.
     */
    public void onFinishConfirmDialog(Boolean confirmed, String text){
        if (confirmed==true){
            providerTaskController.onDelete(statusTextField);
        }
        checkStatus();
    }


    /**
     * This will display the correct UI for tasks in 'Requested' status.
     */
    public void fillRequested(){
        taskLowBidTextField.setText("No bids yet!");
        taskMyBidTextField.setText("Place a bid!");
        providerTaskController.hide(deleteButton, appendButton, taskLowBidDollar, taskMyBidDollar);
        providerTaskController.show(bidButton);
    }

    /**
     * This will display the correct UI for tasks in "assigned status'
     */
    public void fillAssigned(){
        providerTaskController.hide(deleteButton, bidButton, taskLowBidTextField,  taskLowBidDollar, appendButton, taskLowBidDesc);
        providerTaskController.show(taskMyBidTextField,taskMyBidDollar);
        providerTaskController.setUserBid(taskMyBidTextField);
    }

    /**
     * This will dispay the correct info for tasks in bidded status
     */
    public void fillBidded() {
        providerTaskController.hide(deleteButton, appendButton);
        providerTaskController.show(taskLowBidDollar, taskMyBidDollar,  bidButton);
        providerTaskController.setLowestBid(taskLowBidTextField);
        userBid();

    }

    public void userBid(){
        if (providerTaskController.hasBid()) {
            providerTaskController.show(deleteButton, appendButton);
            providerTaskController.hide(bidButton);
            providerTaskController.setUserBid(taskMyBidTextField);
        }
    }

    /**
     *
     * Controls and get teh correct UI to display
     */
    public void checkStatus() {
        providerTaskController.check();
        TaskStatus task = TaskStatus.getInstance();
        if (task.isRequested()){
            fillRequested();
        }
        else if (task.isBidded()){
            fillBidded();
        }
        else {
            fillAssigned();
        }
    }

    /**
     * Move to the profile view to see the requested info
     * @param v the view the button is located on
     */
    public void viewProfile(View v){
        Intent intent= providerTaskController.viewProfile();
        startActivity(intent);
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
        confirmDialog.show(fm, "Cancel Bid");
    }

}