package professional.team17.com.professional.Controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import professional.team17.com.professional.Activity.RequesterViewTaskActivity;
import professional.team17.com.professional.Entity.Bid;
import professional.team17.com.professional.Entity.BidList;
import professional.team17.com.professional.Entity.NotificationList;
import professional.team17.com.professional.Entity.Profile;
import professional.team17.com.professional.Entity.Task;

/**
 * Created by Hailan
 * Referenced from Allison's ProviderTaskController
 * @since 2018-04-05
 * @see RequesterViewTaskActivity
 */

public class RequesterViewTaskController {
    private Context context;
    private ServerHelper serverHelper;
    private Task task;
    private String taskid;

    public RequesterViewTaskController(Context context) {
        this.context = context;
        serverHelper = new ServerHelper(context);
    }

    /**
     * Get old task from server by ID
     * @param taskID id of task
     * @return task
     */
    public Task getOldTaskFromServer(String taskID){
        taskid = taskID;
        task = serverHelper.getTask(taskID);
        return task;
    }

    /**
     * put location info as extra (prep for MapShowALocationActivity
     * @param intent
     */
    public void getLocation(Intent intent) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("aTaskLatLng", task.getLatLng());
        intent.putExtras(bundle);
        intent.putExtra("aTaskAddress", task.getLocation());
    }

    /**
     * sets visibility of map icon depending on the existence of location
     * @param viewLocation - location's ImageButton
     */
    public void mapIconVisibility(ImageButton viewLocation){
        /* Check Existence of Location */
        if (task.getLatLng() == null) {
            viewLocation.setVisibility(View.INVISIBLE);
        }
        else {
            viewLocation.setVisibility(View.VISIBLE);
        }
    }

    /**
     * sets visibility of photos icon depending on the existence of photos
     * @param viewPhotos - photos' ImageButton
     */
    public void photoIconVisibility(ImageButton viewPhotos){
        if (task.getPhotos().size()==0){
            viewPhotos.setVisibility(View.INVISIBLE);
        } else {
            viewPhotos.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Put task photo as extra to prepare for viewing photo activity
     * @param intent
     */
    public void photos(Intent intent){
        ArrayList<String> photos = task.getPhotos(); // store the image in your bitmap
        intent.putStringArrayListExtra("yourImage", photos);
    }

    /**
     * Set info in task views
     * @param titleView - TextView for title
     * @param dateView - TextView for date
     * @param descriptionView - TextView for description
     * @param statusView - - TextView for status
     */
    public void setInfoInTaskViews(TextView titleView, TextView dateView, TextView descriptionView, TextView statusView){
        titleView.setText(task.getName());
        dateView.setText(task.getDateAsString());
        descriptionView.setText(task.getDescription());
        statusView.setText(task.getStatus());
    }

    /**
     * get first item from bidlist
     * @return first item in bidlist
     */
    public Bid getFirstFromBidList(){
        return getCurrentTaskBidList().getBid(0);
    }


    /**
     * get bidlist of current task
     * @return - list of bids
     */
    public BidList getCurrentTaskBidList(){
        return task.getBids();
    }

    /**
     * get status of current task
     * @return task status
     */
    public String getCurrentTaskStatus(){
        return task.status;
    }

    /**
     * Hide Views
     * @param values - Views
     */
    public void hide(View... values) {
        for (View value : values) {
            value.setVisibility(View.GONE);
        }
    }

    /**
     * Show Views
     * @param values - Views
     */
    public void show(View... values) {
        for (View value : values) {
            value.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Assign a bid to carry out task
     * @param bid - the bid that the requester chose
     */
    public void choseBid(Bid bid){
        task.chooseBid(bid);
        task.setStatus("Assigned");
    }

    /**
     * Send notification to bidder that (s)he won
     * @param bidder - the bidder that has to carry out the task
     */
    public void sendNotifications(String bidder){
        Profile bidderProfile = serverHelper.getProfile(bidder);
        NotificationList notificationList = bidderProfile.getNotificationList();
        notificationList.newAssignedNotification(task, task.getProfileName());
        bidderProfile.setNotificationList(notificationList);
        serverHelper.addProfile(bidderProfile);
    }

    /**
     * Remove a bid from task
     * @param bid
     */
    public void decline(Bid bid){
        task.removeBid(bid);
    }

    /**
     * Set task status to requested
     */
    public void setTaskToRequested(){
        task.setRequested();
    }

    /**
     * Set task status to done
     */
    public void setTaskToDone(){
        task.setDone();
    }

    /**
     * Update the info of the task to server
     */
    public void addOldTaskToServer(){
        serverHelper.updateTasks(task);
    }
}
