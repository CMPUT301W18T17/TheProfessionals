/*
 * RequesterCustomArrayAdapter
 *
 * March 14, 2018
 *
 * Copyright @ 2018 Team 17, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behaviour at the University of Alberta.
 * You can find a copy of the license in the github wiki for this project.
 */
package professional.team17.com.professional;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


/**
 * An array adapter for RequesterViewListActivity.
 */

public class RequesterCustomArrayAdapter extends ArrayAdapter<Task> {

        /**
         * Constructs a RequesterCustomArrayAdapter object.
         * @param context The activity in which the adapter is used.
         * @param taskArrayList A list of tasks.
         */
        public RequesterCustomArrayAdapter(Activity context, TaskList taskArrayList) {

                super(context, 0,  taskArrayList);

        }


        /**
         * Gets the list row view.
         * @param position Row's position
         * @param v The view object itself
         * @param parent The viewgroup parent
         * @return The row view.
         */
        // aided by https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
        public View getView(int position, View v, ViewGroup parent) {
                
                RequesterViewHolder requesterViewHolder = null;

                Task task= getItem(position);

                if (v == null) {
                        if (task.isRequested()) {
                                v = LayoutInflater.from(getContext()).inflate(R.layout.requester_requested_row, parent, false);
                                TextView statusTextField = (TextView) v.findViewById(R.id.requester_requested_status);
                                TextView taskTitleTextField = (TextView) v.findViewById(R.id.requester_requested_title);

                                requesterViewHolder = new RequesterViewHolder(statusTextField, taskTitleTextField);
                                v.setTag(requesterViewHolder);
                        }

                        else if (task.isBidded()) {
                                v = LayoutInflater.from(getContext()).inflate(R.layout.requester_bidded_row, parent, false);
                                //get view
                                TextView statusTextField = (TextView) v.findViewById(R.id.requester_bidded_status);
                                TextView taskTitleTextField = (TextView) v.findViewById(R.id.requester_bidded_title);

                                requesterViewHolder = new RequesterViewHolder(statusTextField, taskTitleTextField);
                                v.setTag(requesterViewHolder);
                        }

                        else if (task.isAssigned()) {
                                v = LayoutInflater.from(getContext()).inflate(R.layout.requester_assigned_row, parent, false);
                                //get view
                                TextView statusTextField = (TextView) v.findViewById(R.id.requester_assigned_status);
                                TextView userNameTextField = (TextView) v.findViewById(R.id.requester_assigned_userName);
                                TextView taskTitleTextField = (TextView) v.findViewById(R.id.requester_assigned_title);
                                TextView taskAcceptBidAmountTextField = (TextView) v.findViewById(R.id.requester_assigned_acceptbidAmount);

                                requesterViewHolder = new RequesterViewHolder(statusTextField, userNameTextField, taskTitleTextField, taskAcceptBidAmountTextField);
                                v.setTag(requesterViewHolder);
                        }
                }

                else{
                        if (task.isRequested()){
                                requesterViewHolder = (RequesterViewHolder) v.getTag();
                        }
                        else if (task.isBidded()){
                                requesterViewHolder = (RequesterViewHolder) v.getTag();
                        }
                        else if (task.isAssigned()) {
                                requesterViewHolder = (RequesterViewHolder) v.getTag();
                        }
                }

                //plug in item to row
                if (task.isRequested()){
                        requesterViewHolder.getStatusTextField().setText(task.getStatus());
                        requesterViewHolder.getTaskTitleTextField().setText(task.getName());
                }
                else if (task.isBidded()){
                        requesterViewHolder.getStatusTextField().setText(task.getStatus());
                        requesterViewHolder.getTaskTitleTextField().setText(task.getName());
                }

                else if (task.isAssigned()) {
                        requesterViewHolder.getStatusTextField().setText(task.getStatus());
                        // Once assigned, all other bids are removed, only one left
                        requesterViewHolder.getUserNameTextField().setText(task.getBids().getBid(0).getName());
                        requesterViewHolder.getTaskTitleTextField().setText(task.getName());
                        requesterViewHolder.getTaskAcceptBidAmount().setText(task.getBids().getBid(0).getAmountAsString());
                }

                return v;
        }
}