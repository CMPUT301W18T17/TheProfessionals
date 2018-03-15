package professional.team17.com.professional;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import javax.security.auth.login.LoginException;

/**
 * Created by ag on 2018-02-28.
 */

public class ProviderCustomArrayAdapter extends ArrayAdapter<Task> {

        private SharedPreferences sharedPreferences;
        private String username;

        public ProviderCustomArrayAdapter(Activity context, TaskList taskArrayList) {

            super(context, 0,  taskArrayList);
            sharedPreferences = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
            username = sharedPreferences.getString("username", "error");

        }


        // aided by https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
        public View getView(int position, View v, ViewGroup parent) {

            ProviderRequestedViewHolder providerRequestedViewHolder = null;
            ProviderBiddedViewHolder providerBiddedViewHolder = null;
            ProviderAssignedViewHolder providerAssignedViewHolder = null;

            Task task= getItem(position);

            if (v == null) {
                // For requested tasks
                if (task.isRequested()) {
                    v = LayoutInflater.from(getContext()).inflate(R.layout.provider_requested_row, parent, false);
                    TextView statusTextField = (TextView) v.findViewById(R.id.provider_requested_status);
                    TextView userNameTextField = (TextView) v.findViewById(R.id.provider_requested_userName);
                    TextView taskTitleTextField = (TextView) v.findViewById(R.id.provider_requested_title);

                    providerRequestedViewHolder = new ProviderRequestedViewHolder(statusTextField, userNameTextField, taskTitleTextField);
                    v.setTag(providerRequestedViewHolder);
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

                    providerBiddedViewHolder = new ProviderBiddedViewHolder(statusTextField, userNameTextField, taskTitleTextField, taskLowBidAmountTextField, taskMyBidAmountTextField);
                    v.setTag(providerBiddedViewHolder);
                }

                // For assigned tasks
                else if (task.isAssigned()) {
                    v = LayoutInflater.from(getContext()).inflate(R.layout.provider_assigned_row, parent, false);
                    //get view
                    TextView statusTextField = (TextView) v.findViewById(R.id.provider_assigned_status);
                    TextView userNameTextField = (TextView) v.findViewById(R.id.provider_assigned_userName);
                    TextView taskTitleTextField = (TextView) v.findViewById(R.id.provider_assigned_title);
                    TextView taskMyBidAmountTextField = (TextView) v.findViewById(R.id.provider_assigned_mybidAmount);

                    providerAssignedViewHolder = new ProviderAssignedViewHolder(statusTextField, userNameTextField, taskTitleTextField, taskMyBidAmountTextField);
                    v.setTag(providerAssignedViewHolder);
                }
            }

            // If not null
            else{
                if (task.isRequested()){
                    providerRequestedViewHolder = (ProviderRequestedViewHolder) v.getTag();
                }
                else if (task.isBidded()){
                    providerBiddedViewHolder = (ProviderBiddedViewHolder) v.getTag();
                }
                else if (task.isAssigned()) {
                    providerAssignedViewHolder = (ProviderAssignedViewHolder) v.getTag();
                }
            }

            //plug in item to row
            if (task.isRequested()){
                providerRequestedViewHolder.getStatusTextField().setText(task.getStatus());
                providerRequestedViewHolder.getUserNameTextField().setText(task.getProfileName());
                providerRequestedViewHolder.getTaskTitleTextField().setText(task.getName());;
            }
            else if (task.isBidded()){
                providerBiddedViewHolder.getStatusTextField().setText(task.getStatus());
                providerBiddedViewHolder.getUserNameTextField().setText(task.getProfileName());
                providerBiddedViewHolder.getTaskTitleTextField().setText(task.getName());
                providerBiddedViewHolder.getTaskLowBidAmount().setText(task.getBids().getLowest().getAmountAsString());
                if (task.getBids().userBidded(username)){
                    providerBiddedViewHolder.getTaskMyBidAmount().setText(task.getBids().getBid(username).getAmountAsString());
                }
            }

            else if (task.isAssigned()) {
                providerAssignedViewHolder.getStatusTextField().setText(task.getStatus());
                providerAssignedViewHolder.getUserNameTextField().setText(task.getProfileName());
                providerAssignedViewHolder.getTaskTitleTextField().setText(task.getName());
                providerAssignedViewHolder.getTaskMyBidAmount().setText(task.getBids().getBid(username).getAmountAsString());
            }

            return v;
        }
}
