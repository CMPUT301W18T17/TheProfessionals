package professional.team17.com.professional;

import android.app.Activity;
import android.util.Log;
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
                Task task= getItem(position);
                Log.i("LOOOKINAT", "getView: "+position);
                if (v == null) {
                        v = LayoutInflater.from(getContext()).inflate(R.layout.requester_custom_array_row, parent, false);
                }
                //get view
                TextView statusTextField = (TextView) v.findViewById(R.id.status);
                TextView userNameTextField = (TextView) v.findViewById(R.id.userName);
                TextView taskTitleTextField = (TextView) v.findViewById(R.id.title);
                TextView taskLowBidTextField = (TextView) v.findViewById(R.id.acceptbid);
                TextView taskLowBidAmountTextField = (TextView) v.findViewById(R.id.acceptbidAmount);


                //plug in item to row
                statusTextField.setText(task.getStatus());
                taskTitleTextField.setText(task.getName());
                Log.i("Task", "getView: "+task.isAssigned()+task.getStatus()+task.getName());

                if (task.isAssigned()) {
                        userNameTextField.setText(task.getProfileName());
                        taskLowBidAmountTextField.setText(task.getBids().getLowest().getAmountAsString());
                        taskLowBidTextField.setVisibility(View.VISIBLE);
                        taskLowBidAmountTextField.setVisibility(View.VISIBLE);
                }
                else if (task.isBidded() || task.isRequested()){
                        taskLowBidTextField.setVisibility(View.INVISIBLE);
                        taskLowBidAmountTextField.setVisibility(View.INVISIBLE);
                        userNameTextField.setVisibility(View.INVISIBLE);
                }


                return v;
        }



}