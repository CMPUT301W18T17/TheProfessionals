/*
 * ProviderCustomArrayAdapter
 *
 * March 12, 2018
 *
 * Copyright @ 2018 Team 17, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behaviour at the University of Alberta.
 * You can find a copy of the license in the github wiki for this project.
 */
package professional.team17.com.professional;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * An array adapter for SearchActivity and ProviderTaskListActivity.
 * @author zhipeng
 * @see ProviderTaskListActivity
 * @see SearchActivity
 * @see TaskList
 */

public class ProviderCustomArrayAdapter extends ArrayAdapter<Task> {

        private SharedPreferences sharedPreferences;
        private String username;

    /**
     * Constructs a ProviderCustomArrayAdapter object.
     * @param context The activity where the adapter is used
     * @param taskArrayList The list of tasks
     */
    public ProviderCustomArrayAdapter(Activity context, TaskList taskArrayList) {

            super(context, 0,  taskArrayList);
            sharedPreferences = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
            username = sharedPreferences.getString("username", "error");

        }


    /**
     * Gets the list's row view
     * @param position The position of the row view
     * @param v The view object
     * @param parent The viewgroup parent
     * @return The row view
     */
        // aided by https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
        public View getView(int position, View v, ViewGroup parent) {

            ProviderViewHolder providerViewHolder = null;

            Task task= getItem(position);

            // For requested tasks
            if (task.isRequested()) {
                v = LayoutInflater.from(getContext()).inflate(R.layout.provider_requested_row, parent, false);
                TextView statusTextField = (TextView) v.findViewById(R.id.provider_requested_status);
                TextView userNameTextField = (TextView) v.findViewById(R.id.provider_requested_userName);
                TextView taskTitleTextField = (TextView) v.findViewById(R.id.provider_requested_title);

                providerViewHolder = new ProviderViewHolder(statusTextField, userNameTextField, taskTitleTextField);
                v.setTag(providerViewHolder);

                providerViewHolder = (ProviderViewHolder) v.getTag();
            }

            // For bidded tasks
            else if (task.isBidded()) {
                v = LayoutInflater.from(getContext()).inflate(R.layout.provider_bidded_row, parent, false);
                //get view
                TextView statusTextField = (TextView) v.findViewById(R.id.provider_bidded_status);
                TextView userNameTextField = (TextView) v.findViewById(R.id.provider_bidded_userName);
                TextView taskTitleTextField = (TextView) v.findViewById(R.id.provider_bidded_title);
                TextView taskLowBidAmountTextField = (TextView) v.findViewById(R.id.provider_bidded_lowbidAmount);
                TextView taskMyBidAmountTextField = (TextView) v.findViewById(R.id.provider_bidded_mybidAmount);
                providerViewHolder = new ProviderViewHolder(statusTextField, userNameTextField, taskTitleTextField, taskLowBidAmountTextField, taskMyBidAmountTextField);
                v.setTag(providerViewHolder);

                providerViewHolder = (ProviderViewHolder) v.getTag();
            }

            // For assigned tasks
            else if (task.isAssigned()) {
                v = LayoutInflater.from(getContext()).inflate(R.layout.provider_assigned_row, parent, false);
                //get view
                TextView statusTextField = (TextView) v.findViewById(R.id.provider_assigned_status);
                TextView userNameTextField = (TextView) v.findViewById(R.id.provider_assigned_userName);
                TextView taskTitleTextField = (TextView) v.findViewById(R.id.provider_assigned_title);
                TextView taskMyBidAmountTextField = (TextView) v.findViewById(R.id.provider_assigned_mybidAmount);

                providerViewHolder = new ProviderViewHolder(statusTextField, userNameTextField, taskTitleTextField, taskMyBidAmountTextField);
                v.setTag(providerViewHolder);

                providerViewHolder = (ProviderViewHolder) v.getTag();
            }

            //plug in item to row
            if (task.isRequested()){
                providerViewHolder.getStatusTextField().setText(task.getStatus());
                providerViewHolder.getUserNameTextField().setText(task.getProfileName());
                providerViewHolder.getTaskTitleTextField().setText(task.getName());
            }
            else if (task.isBidded()){
                providerViewHolder.getStatusTextField().setText(task.getStatus());
                providerViewHolder.getUserNameTextField().setText(task.getProfileName());
                providerViewHolder.getTaskTitleTextField().setText(task.getName());
                providerViewHolder.getTaskLowBidAmount().setText(task.getBids().getLowest().getAmountAsString());
                if (task.getBids().userBidded(username)){
                    providerViewHolder.getTaskMyBidAmount().setText(task.getBids().getBid(username).getAmountAsString());
                }
            }

            else if (task.isAssigned()) {
                providerViewHolder.getStatusTextField().setText(task.getStatus());
                providerViewHolder.getUserNameTextField().setText(task.getProfileName());
                providerViewHolder.getTaskTitleTextField().setText(task.getName());
                providerViewHolder.getTaskMyBidAmount().setText(task.getBids().getBid(username).getAmountAsString());
            }

            return v;
        }
}
