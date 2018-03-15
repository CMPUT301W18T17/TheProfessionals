package professional.team17.com.professional;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


/**
 * Created by Zhipeng Zhang on 2018-03-05.
 */

public class RequesterCustomArrayAdapter extends ArrayAdapter<Task> {

        public RequesterCustomArrayAdapter(Activity context, TaskList taskArrayList) {

                super(context, 0,  taskArrayList);

        }


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