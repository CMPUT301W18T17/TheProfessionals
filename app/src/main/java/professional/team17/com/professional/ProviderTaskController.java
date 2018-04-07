/*
 * ProviderTaskController
 *
 * March 17, 2018
 *
 * Copyright @ 2018 Team 17, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behaviour at the University of Alberta.
 * You can find a copy of the license in the github wiki for this project.
 */
package professional.team17.com.professional;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * This class is used to help control any Task view for the
 * Provider. It acts as a middle man between the
 * Server and the activity.
 * @see ServerHelper
 * @see Task
 */
public class ProviderTaskController {
    private Context context;
    private ServerHelper serverHelper;
    private Task task;
    private String username;

    ProviderTaskController(Context context) {
        this.context = context;
        serverHelper = new ServerHelper(context);
        setUsername();
    }

    /**
     * Username for session
     */
    private void setUsername() {
        SharedPreferences sharedpreferences = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        username = sharedpreferences.getString("username", "error");
    }

    /**
     * This sets the task being looked at
     * @param intent - The intent passed to the activity, of which
     *               we pull out the id
     */
    public void setTask(Bundle intent) {
        String id = intent.getString("ID");
        task = serverHelper.getTask(id);
        check();
    }

    /**
     *
     * @return False means offline, true means online
     */
    public Boolean checkOffline() {
        ConnectedState c = ConnectedState.getInstance();
        if (c.isOffline() && !task.isRequested()) {
            return true;
        } else return false;
    }
    public ArrayList<String> getPhotos(){
        return this.task.getPhotos();
    }

    /**
     *
     * @return - An intent with the profile being looked at (from the task object)
     */
    public Intent viewProfile() {
        Intent intention = new Intent(context, OtherProfileViewActivity.class);
        intention.putExtra("profile", task.getProfileName());
        return intention;
    }

    /**
     *
     * @param bidAmount - The double amount being added to the task
     */
    public void placeBid(double bidAmount) {
        task.addBid(new Bid(username, bidAmount));
        serverHelper.updateTasks(task);
        setNotification(bidAmount);
    }

    /**
     *
     * @param bidAmount ) - The double amount being added to the notification
     */
    private void setNotification(Double bidAmount) {
        String requester = task.getProfileName();
        Profile requesterProfile = serverHelper.getProfile(requester);
        NotificationList notificationList = requesterProfile.getNotificationList();
        notificationList.newBidNotification(task, bidAmount, username);
        requesterProfile.setNotificationList(notificationList);
        serverHelper.addProfile(requesterProfile);
    }

    /**
     *
     * @param statusTextField - The textview that will change it the bid is deleted
     */
    public void onDelete(TextView statusTextField) {
        Bid bid = task.getBids().getBid(username);
        task.removeBid(bid);
        serverHelper.updateTasks(task);
        statusTextField.setText(task.getStatus());
        TaskStatus.getInstance().setStatus(task.getStatus());
    }

    /**
     *
     * @param values  - View objects that should be hidden
     */
    public void hide(View... values) {
        for (View value : values) {
            value.setVisibility(View.GONE);
        }
    }

    /**
     *
     * @param values - View objects that should be visible
     */
    public void show(View... values) {
        for (View value : values) {
            value.setVisibility(View.VISIBLE);
        }
    }

    /**
     *
     * @param bid - The user bid amount that should be set into the activity
     */
    public void setUserBid(TextView bid) {
        BidList bids = task.getBids();
        if (bids.getBid(username) != null) {
            bid.setText(bids.getBid(username).getAmountAsString());
        }
        else{
            bid.setText("Task is closed for bidding");
        }
    }

    /**
     *
     * @param bid - The lowest bid that should be set into the task
     */
    public void setLowestBid(TextView bid) {
        BidList bids = task.getBids();
        bid.setText(bids.getLowest().getAmountAsString());
    }

    /**
     *
     * @param viewMapButton - The button to see the map
     */
    public void location(ImageButton viewMapButton) {
        if (task.getLatLng() == null) {
            viewMapButton.setVisibility(View.INVISIBLE);
        }
    }

    /**
     *
     * @param viewPhotoButton - The button to see the photos
     */
    public void photos(ImageButton viewPhotoButton) {
        if (task.getPhotos().isEmpty()) {
            viewPhotoButton.setVisibility(View.INVISIBLE);
        }
    }

    public Bundle getLocation(Intent intent) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("aTaskLatLng", task.getLatLng());
        intent.putExtras(bundle);
        intent.putExtra("aTaskAddress", task.getLocation());
        return bundle;
    }

    /**
     * To check what state the task is in
     */
    public void check() {
        TaskStatus taskstatus = TaskStatus.getInstance();
        taskstatus.setStatus(task.getStatus());
    }

    /**
     *
     * @param status textview object to be set with the status
     * @param name textview object to be set with the name
     * @param title textview object to be set with the title
     * @param date textview object to be set with the date
     * @param descri textview object to be set with the desc
     * @param address textview object to be set with the address
     */
    public void fillTask(TextView status, TextView name, TextView title, TextView date, TextView descri, TextView address) {
        status.setText(task.getStatus());
        name.setText(task.getProfileName());
        title.setText(task.getName());
        date.setText(task.getDateAsString());
        descri.setText(task.getDescription());
        if (task.getLatLng()!= null){
            address.setText(task.getLocation());
        } else {
            address.setText("N/A");
        }
    }

    /**
     *
     * @return - True if session use has bid, false otherwise
     */
    public boolean hasBid() {
        BidList bids = task.getBids();
        Bid bid = bids.getBid(username);
        return (bid!=null);
    }
}
