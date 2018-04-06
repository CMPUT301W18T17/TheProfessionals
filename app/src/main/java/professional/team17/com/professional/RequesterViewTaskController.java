package professional.team17.com.professional;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by Hailan on 2018-04-05.
 */

public class RequesterViewTaskController {
    private Context context;
    private ServerHelper serverHelper;
    private Task task;
    private String taskid;

    RequesterViewTaskController(Context context) {
        this.context = context;
        serverHelper = new ServerHelper(context);
    }

    public Task getOldTaskFromServer(String taskID){
        taskid = taskID;
        task = serverHelper.getTask(taskID);
        return task;
    }

    public void getLocation(Intent intent) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("aTaskLatLng", task.getLatLng());
        intent.putExtras(bundle);
        intent.putExtra("aTaskAddress", task.getLocation());
    }

    public LatLng checkTaskLatLng(){
        return task.getLatLng();
    }

    public void mapIconVisibility(ImageButton viewLocation){
        /* Check Existence of Location */
        if (task.getLatLng() == null) {
            viewLocation.setVisibility(View.INVISIBLE);
        }
        else {
            viewLocation.setVisibility(View.VISIBLE);
        }
    }

    public void photos(Intent intent){
        ArrayList<String> photos = task.getPhotos(); // store the image in your bitmap
        intent.putStringArrayListExtra("yourImage", photos);
    }

    public void setInfoInTaskViews(TextView titleView, TextView dateView, TextView descriptionView, TextView statusView){
        titleView.setText(task.getName());
        dateView.setText(task.getDateAsString());
        descriptionView.setText(task.getDescription());
        statusView.setText(task.getStatus());
    }

    public Bid getFirstFromBidList(){
        return getCurrentTaskBidList().getBid(0);
    }

    public BidList getCurrentTaskBidList(){
        return task.getBids();
    }

    public String getCurrentTaskStatus(){
        return task.status;
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

    public void choseBid(Bid bid){
        task.chooseBid(bid);
        task.setStatus("Assigned");
    }

    public void sendNotifications(String bidder){
        Profile bidderProfile = serverHelper.getProfile(bidder);
        NotificationList notificationList = bidderProfile.getNotificationList();
        notificationList.newAssignedNotification(task, task.getProfileName());
        bidderProfile.setNotificationList(notificationList);
        serverHelper.addProfile(bidderProfile);
    }

    public void decline(Bid bid){
        task.removeBid(bid);
    }

    public void setTaskToRequested(){
        task.setRequested();
    }

    public void setTaskToDone(){
        task.setDone();
    }

    public void addOldTaskToServer(){
        serverHelper.updateTasks(task);
    }
}
