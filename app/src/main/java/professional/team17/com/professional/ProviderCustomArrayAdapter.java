package professional.team17.com.professional;

import android.app.Activity;
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



        public ProviderCustomArrayAdapter(Activity context, TaskList taskArrayList) {

            super(context, 0,  taskArrayList);

        }


        // aided by https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
        public View getView(int position, View v, ViewGroup parent) {
            ProviderRequestedViewHolder providerRequestedViewHolder = null;
            ProviderBiddedViewHolder providerBiddedViewHolder = null;

            Task task= getItem(position);

            if (v == null) {
                if (task.isRequested()) {
                    v = LayoutInflater.from(getContext()).inflate(R.layout.provider_requested_row, parent, false);
                    TextView statusTextField = (TextView) v.findViewById(R.id.provider_requested_status);
                    TextView userNameTextField = (TextView) v.findViewById(R.id.provider_requested_userName);
                    TextView taskTitleTextField = (TextView) v.findViewById(R.id.provider_requested_title);

                    providerRequestedViewHolder = new ProviderRequestedViewHolder(statusTextField, userNameTextField, taskTitleTextField);
                    v.setTag(providerRequestedViewHolder);
                }
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
                    /**
                     taskLowBidAmountTextField.setText(task.getBids().getLowest().getAmountAsString());
                     taskLowBidTextField.setVisibility(View.VISIBLE);
                     taskMyBidTextField.setVisibility(View.VISIBLE);
                     taskLowBidAmountTextField.setVisibility(View.VISIBLE);
                     taskMyBidAmountTextField.setVisibility(View.VISIBLE);
                     */
                    //todo, add in if condition for bidded, pull from global user value
                    /**
                     if task.getBids().userBidded(user.getUserName()){

                     taskMyBidTextField.setVisibility(View.VISIBLE);
                     taskMyBidAmountTextField.setVisibility(View.VISIBLE);
                     taskMyBidAmountTextField.setText(bids.getBid(user.getUserName()).getAmountAsString());
                     }
                     */
                }
                /**
                else if (task.isAssigned()) {
                    taskLowBidTextField.setVisibility(View.INVISIBLE);
                    taskLowBidAmountTextField.setVisibility(View.INVISIBLE);
                    taskMyBidTextField.setVisibility(View.VISIBLE);
                    taskMyBidAmountTextField.setVisibility(View.VISIBLE);
                }
                 */
            }

            else{
                if (task.isRequested()){
                    providerRequestedViewHolder = (ProviderRequestedViewHolder) v.getTag();
                }
                else if (task.isBidded()){
                    providerBiddedViewHolder = (ProviderBiddedViewHolder) v.getTag();
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
            }

            return v;
        }



}
