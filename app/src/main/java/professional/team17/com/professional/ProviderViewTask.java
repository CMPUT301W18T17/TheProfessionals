package professional.team17.com.professional;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class ProviderViewTask extends AppCompatActivity {
    private Profile user;
    private Profile requester;
    private Task task;
    private TextView statusTextField;
    private TextView userNameTextField;
    private TextView taskTitleTextField;
    private TextView taskDateTextField;
    private TextView taskDescriptionTextField;
    private TextView taskLowBidTextField;
    private TextView taskMyBidTextField;
    private RatingBar requesterAvgTextField;
    private TextView myBidTextView;
    private TextView lowBidTextView;
    private TextView requesterAvgTextView;


    //both buttons start as invisible by default
    private Button bidButton;
    private Button deleteButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.provider_view_task);

        //get textfields ids
        statusTextField = (TextView) findViewById(R.id.provider_view_task_statusType);
        userNameTextField = (TextView) findViewById(R.id.provider_view_task_userName);
        taskTitleTextField = (TextView) findViewById(R.id.provider_view_task_title);
        taskDateTextField = (TextView) findViewById(R.id.provider_view_task_date_input);
        taskDescriptionTextField = (TextView) findViewById(R.id.provider_view_task_description);
        taskLowBidTextField = (TextView) findViewById(R.id.provider_view_task_lowBidInput);
        taskMyBidTextField = (TextView) findViewById(R.id.provider_view_task_myBidInput);

        user = getUser();
        task = getTask();
        getRequester();


        checkStatus();
        fillTask();
        setRating();
    }


    /**
     *
     * @return Returns the subscription sent with the Intent
     */
    public void fillTask() {
        //plug items from task
        statusTextField.setText(task.getStatus());
        userNameTextField.setText(task.getProfileName());
        taskTitleTextField.setText(task.getName());
        taskDateTextField.setText(task.getDateAsString());
        taskDescriptionTextField.setText(task.getDescription());

    }

    //If status is requested - place bid button will show, and lowest bid text fields hides
    public void fillRequested(){
        bidButton.setVisibility(View.VISIBLE);
        taskLowBidTextField.setVisibility(View.INVISIBLE);
        lowBidTextView.setVisibility(View.INVISIBLE);
    }

    //If status is assigned, hide lowbid fields and show user bid.
    public void fillAssigned(){
        if (task.getBids().userBidded(user.getUserName())) {
            BidList bids = task.getBids();
            taskLowBidTextField.setVisibility(View.INVISIBLE);
            lowBidTextView.setVisibility(View.INVISIBLE);
            taskMyBidTextField.setVisibility(View.VISIBLE);
            myBidTextView.setVisibility(View.VISIBLE);
            taskMyBidTextField.setText(bids.getBid(user.getUserName()).getAmountAsString());
        }
        else {
            //TODO what displays if user is not bidder?
        }
    }

    //If bidded, check if user has bid, if they have show cancel bid button and show their bid info
    // if user has not bid, then show place bid button
    public void fillBidded() {
        //set lowest bid amount
        BidList bids = task.getBids();
        taskLowBidTextField.setText(bids.getLowest().getAmountAsString());
        if (task.getBids().userBidded(user.getUserName())) {
            //find user bid amount and show cancel bid button
            taskMyBidTextField.setVisibility(View.VISIBLE);
            myBidTextView.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.VISIBLE);
            taskMyBidTextField.setText(bids.getBid(user.getUserName()).getAmountAsString());
        }
        else {
            bidButton.setVisibility(View.VISIBLE);
        }
    }


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

    /**
     *
     * Sets the proper fields depending on status of task
     * //TODO Delete explanatory
     * Default state of this view is to hide the delete/place bid buttons and myBid textfields
     * //If status is requested - place bid button will show, and lowest bid text fields hides
     * //If status is bidded and user has placed bid, then cancel bid button shows, and my bid fields show
     * //If status is bidded and user has not placed bid, than place bid buttons shows
     * //If status is assigned or done and user has bid, then they will see their bid amount
     */
    public void checkStatus() {


        bidButton = (Button) findViewById(R.id.provider_view_task_bidButton);
        deleteButton = (Button) findViewById(R.id.provider_view_task_deleteButton);
        myBidTextView = (TextView) findViewById(R.id.provider_view_task_myBid);
        lowBidTextView = (TextView) findViewById(R.id.provider_view_task_lowBid);

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


    //TODO
    public void viewProfile(View v){
        //TODO - would have to search ES currently.
        //Profile profile = null;
        Intent intention = new Intent(this, LogInActivity.class);
        //intention.putExtra("profile", user);
        startActivity(intention);

    }



    //TODO
    public void placeBid(View v){
        FragmentManager fm = getSupportFragmentManager();
        PlaceBidDialog placeBidDialog = PlaceBidDialog.newInstance("Some Title");
        placeBidDialog.show(fm, "Test");

    }
    /**
     *
     * @return Returns the subscription sent with the Intent
     */
    private Profile getUser() {
        Intent intent = getIntent();
        Profile profile = (Profile) intent.getSerializableExtra("profile");
        return profile;
    }

    /**
     *
     * @return Returns the subscription sent with the Intent
     */
    private Task getTask() {
        Intent intent = getIntent();
        Task task = (Task) intent.getSerializableExtra("Task");
        return task;
    }

    /**
     *
     * @return Returns the subscription sent with the Intent
     */
    private void getRequester() {
        if (task.isBidded()){
            DummyData();
        }
        else {
            DummyData1();
        }
    }

    public void DummyData(){
        requester = new Profile("John Smith", task.getProfileName(), "email", "123123123");
        ReviewList reviewList  = requester.getReviewList();
        Review review1 = new Review(5.0, "reviewer","comment", "title");
        Review review2 = new Review(2.1, "reviewer1","comment", "title");
        reviewList.addReview(review1);
        reviewList.addReview(review2);
    }

    public void DummyData1(){
        requester = new Profile("John Smith", task.getProfileName(), "email", "123123123");
    }
}
