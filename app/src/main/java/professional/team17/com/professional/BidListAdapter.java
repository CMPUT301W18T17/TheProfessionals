package professional.team17.com.professional;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 *
 * To inflate the row selection for bids
 *
 * @author Allison, Lauren
 * @see Bid
 * @see BidList
 */
public class BidListAdapter extends ArrayAdapter<Bid> {

    /**
     * Contructs a BidListAdapter.
     * @param context The activity that the adapter is used in.
     * @param bids A list of Bid objects.
     */
    public BidListAdapter(Activity context, BidList bids) {

        super(context, 0,  bids);
    }


    /**
     * Gets and inflates the ListView associated with this adapter.
     * @param position The position of the row
     * @param v The view object
     * @param parent The parent viewgroup
     * @return the row's view.
     */
    // aided by https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
    public View getView(int position, View v, ViewGroup parent) {
        final Bid bid= getItem(position);
        if (v == null) {
            v = LayoutInflater.from(getContext()).inflate(R.layout.bid_row, parent, false);
        }
        //get view
        TextView userNameTextField = (TextView) v.findViewById(R.id.bid_row_userName);
        TextView taskBidTextField = (TextView) v.findViewById(R.id.bid_row_amount);
        ImageButton deleteButton = (ImageButton) v.findViewById(R.id.bid_row_delete);
        ImageButton acceptButton = (ImageButton) v.findViewById(R.id.bid_row_accept);

        //plug in item to row
        userNameTextField.setText(bid.getName());
        taskBidTextField.setText(bid.getAmountAsString());


        return v;
    }

}