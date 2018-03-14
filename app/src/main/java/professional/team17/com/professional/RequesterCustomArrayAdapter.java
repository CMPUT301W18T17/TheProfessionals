package professional.team17.com.professional;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import static android.os.Build.ID;


/**
 * Created by Zhipeng Zhang on 2018-03-05.
 */

public class RequesterCustomArrayAdapter extends ArrayAdapter<Task> {

        public RequesterCustomArrayAdapter(Activity context, TaskList taskArrayList) {

                super(context, 0,  taskArrayList);

        }


        // aided by https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
        public View getView(int position, View v, ViewGroup parent) {

                RequesterRequestedViewHolder requesterRequestedViewHolder = null;
                RequesterBiddedViewHolder requesterBiddedViewHolder = null;
                RequesterAssignedViewHolder requesterAssignedViewHolder = null;

                Task task= getItem(position);

                if (v == null) {
                        if (task.isRequested()) {
                                v = LayoutInflater.from(getContext()).inflate(R.layout.requester_requested_row, parent, false);
                                TextView statusTextField = (TextView) v.findViewById(R.id.requester_requested_status);
                                TextView taskTitleTextField = (TextView) v.findViewById(R.id.requester_requested_title);

                                requesterRequestedViewHolder = new RequesterRequestedViewHolder(statusTextField, taskTitleTextField);
                                v.setTag(requesterRequestedViewHolder);
                        }

                        else if (task.isBidded()) {
                                v = LayoutInflater.from(getContext()).inflate(R.layout.requester_bidded_row, parent, false);
                                //get view
                                TextView statusTextField = (TextView) v.findViewById(R.id.requester_bidded_status);
                                TextView taskTitleTextField = (TextView) v.findViewById(R.id.requester_bidded_title);

                                requesterBiddedViewHolder = new RequesterBiddedViewHolder(statusTextField, taskTitleTextField);
                                v.setTag(requesterBiddedViewHolder);
                        }

                        else if (task.isAssigned()) {
                                v = LayoutInflater.from(getContext()).inflate(R.layout.requester_assigned_row, parent, false);
                                //get view
                                TextView statusTextField = (TextView) v.findViewById(R.id.requester_assigned_status);
                                TextView userNameTextField = (TextView) v.findViewById(R.id.requester_assigned_userName);
                                TextView taskTitleTextField = (TextView) v.findViewById(R.id.requester_assigned_title);
                                TextView taskAcceptBidAmountTextField = (TextView) v.findViewById(R.id.requester_assigned_acceptbidAmount);

                                requesterAssignedViewHolder = new RequesterAssignedViewHolder(statusTextField, userNameTextField, taskTitleTextField, taskAcceptBidAmountTextField);
                                v.setTag(requesterAssignedViewHolder);
                        }
                }

                else{
                        if (task.isRequested()){
                                requesterRequestedViewHolder = (RequesterRequestedViewHolder) v.getTag();
                        }
                        else if (task.isBidded()){
                                requesterBiddedViewHolder = (RequesterBiddedViewHolder) v.getTag();
                        }
                        else if (task.isAssigned()) {
                                requesterAssignedViewHolder = (RequesterAssignedViewHolder) v.getTag();
                        }
                }

                //plug in item to row
                if (task.isRequested()){
                        requesterRequestedViewHolder.getStatusTextField().setText(task.getStatus());
                        requesterRequestedViewHolder.getTaskTitleTextField().setText(task.getName());
                }
                else if (task.isBidded()){
                        requesterBiddedViewHolder.getStatusTextField().setText(task.getStatus());
                        requesterBiddedViewHolder.getTaskTitleTextField().setText(task.getName());
                }

                else if (task.isAssigned()) {
                        requesterAssignedViewHolder.getStatusTextField().setText(task.getStatus());
                        // Once assigned, all other bids are removed, only one left
                        requesterAssignedViewHolder.getUserNameTextField().setText(task.getBids().getBid(0).getName());
                        requesterAssignedViewHolder.getTaskTitleTextField().setText(task.getName());
                        requesterAssignedViewHolder.getTaskAcceptBidAmount().setText(task.getBids().getBid(0).getAmountAsString());
                }

                return v;
        }
}