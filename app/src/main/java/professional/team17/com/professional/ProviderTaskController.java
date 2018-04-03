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

import java.util.HashMap;
import java.util.Map;
//TODO implement this for project part 5
/**
 * Created by kaixiangzhang on 2018-03-11.
 *
 * Here since I am not very familiar with the provider part
 * However i gonna follow the Design Patterns - MVC Pattern:
 * Which is model--controller--view :
 * 1. controller use model to achieve goal
 * 2. the activity class only do the view(get info/ show info)
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

    private void setUsername() {
        SharedPreferences sharedpreferences = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        username = sharedpreferences.getString("username", "error");
    }

    public void setTask(Bundle intent) {
        String id = intent.getString("ID");
        task = serverHelper.getTask(id);
        check();
    }

    public Boolean checkOffline() {
        ConnectedState c = ConnectedState.getInstance();
        if (c.isOffline() && !task.isRequested()) {
            return true;
        } else return false;
    }

    /**
     * Move to the profile view to see the requested info
     *
     * @param v the view the button is located on
     */
    public Intent viewProfile() {
        Intent intention = new Intent(context, OtherProfileViewActivity.class);
        intention.putExtra("profile", task.getProfileName());
        return intention;
    }

    /***
     * Interface method from PlaceBidDialog.PlaceBidDialogListener
     * @param inputText boolean value representing the user response in the dialog
     * true means the user add/changed the bid
     */
    public void placeBid(double bidAmount) {
        task.addBid(new Bid(username, bidAmount));
        serverHelper.updateTasks(task);
        setNotification(bidAmount);
    }

    private void setNotification(Double bidAmount) {
        String requester = task.getProfileName();
        Profile requesterProfile = serverHelper.getProfile(requester);
        NotificationList notificationList = requesterProfile.getNotificationList();
        notificationList.newBidNotification(task, bidAmount, username);
        requesterProfile.setNotificationList(notificationList);
        serverHelper.addProfile(requesterProfile);
    }


    public void onDelete(TextView statusTextField) {
        Bid bid = task.getBids().getBid(username);
        task.removeBid(bid);
        serverHelper.updateTasks(task);
        statusTextField.setText(task.getStatus());
        TaskStatus.getInstance().setStatus(task.getStatus());
    }

    public void hide(View... values) {
        for (View value : values) {
            value.setVisibility(View.GONE);
        }
    }

    public void show(View... values) {
        for (View value : values) {
            value.setVisibility(View.VISIBLE);
        }
    }

    public void setUserBid(TextView bid) {
        BidList bids = task.getBids();
        bid.setText(bids.getBid(username).getAmountAsString());
    }
    
    public void setLowestBid(TextView bid) {
        BidList bids = task.getBids();
        bid.setText(bids.getLowest().getAmountAsString());
    }

    public void location(ImageButton viewMapButton) {
        if (task.getLatLng() == null) {
            viewMapButton.setVisibility(View.INVISIBLE);
        }
    }

    public Bundle getLocation(Intent intent) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("aTaskLatLng", task.getLatLng());
        intent.putExtras(bundle);
        intent.putExtra("aTaskAddress", task.getLocation());
        return bundle;
    }

    public void check() {
        TaskStatus taskstatus = TaskStatus.getInstance();
        taskstatus.setStatus(task.getStatus());
    }

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

    public boolean hasBid() {
        BidList bids = task.getBids();
        Bid bid = bids.getBid(username);
        return (bid!=null);
    }
}
