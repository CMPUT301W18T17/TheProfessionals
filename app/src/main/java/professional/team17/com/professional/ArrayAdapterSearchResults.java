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

public class ArrayAdapterSearchResults extends ArrayAdapter<Task> {



        public ArrayAdapterSearchResults(Activity context, TaskList taskArrayList) {

            super(context, 0,  taskArrayList);

        }


        // aided by https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
        public View getView(int position, View v, ViewGroup parent) {
            Task task= getItem(position);
            Log.i("LOOOKINAT", "getView: "+position);
            if (v == null) {
                v = LayoutInflater.from(getContext()).inflate(R.layout.search_task_row, parent, false);
            }
            //get view
            TextView statusTextField = (TextView) v.findViewById(R.id.search_task_row_statusText);
            TextView userNameTextField = (TextView) v.findViewById(R.id.search_task_row_userName);
            TextView taskTitleTextField = (TextView) v.findViewById(R.id.search_task_row_title);
            TextView taskLowBidTextField = (TextView) v.findViewById(R.id.search_task_row_lowbid);
            TextView taskMyBidTextField = (TextView) v.findViewById(R.id.search_task_row_mybid);
            TextView taskLowBidAmountTextField = (TextView) v.findViewById(R.id.search_task_row_lowbidAmount);
            TextView taskMyBidAmountTextField = (TextView) v.findViewById(R.id.search_task_row_mybidAmount);


            //plug in item to row
            statusTextField.setText(task.getStatus());
            userNameTextField.setText(task.getProfileName());
            taskTitleTextField.setText(task.getName());
            Log.i("Task", "getView: "+task.isRequested()+task.getStatus()+task.getName());
            if (task.isRequested()){
                taskLowBidTextField.setVisibility(View.INVISIBLE);
                taskMyBidTextField.setVisibility(View.INVISIBLE);
                taskLowBidAmountTextField.setVisibility(View.INVISIBLE);
                taskMyBidAmountTextField.setVisibility(View.INVISIBLE);
            }
            else {
                taskLowBidAmountTextField.setText(task.getBids().getLowest().getAmountAsString());
                taskLowBidTextField.setVisibility(View.VISIBLE);
                taskMyBidTextField.setVisibility(View.VISIBLE);
                taskLowBidAmountTextField.setVisibility(View.VISIBLE);
                taskMyBidAmountTextField.setVisibility(View.VISIBLE);
            }


            return v;
        }



}
