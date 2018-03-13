package professional.team17.com.professional;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
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
 * @see Bid BidList
 */
public class BidListAdapter extends ArrayAdapter<Bid> /*implements ConfirmDialog.ConfirmDialogListener*/{

        //String dialogFlag;
        private BidList bidList;
        private Task parentTask;
        private Context context;

        public BidListAdapter(Activity context, BidList bids, Task task) {

            super(context, 0,  bids);
            bidList = bids;
            parentTask = task;
        }


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
            taskBidTextField.setText(bid.getName());

            deleteButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    /* I would like to implement a dialog here, but for now it just removes the bid. */
                    bidList.delete(bid);
                    parentTask.removeBid(bid);
                    notifyDataSetChanged();
                }
            });
            acceptButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    /* I would like to implement a dialog here, but for now it just removes all other
                     * bids besides this one.
                     */
                    bidList.acceptBid(bid);
                    parentTask.chooseBid(bid);
                    notifyDataSetChanged();
                    Intent intent = new Intent(context, RequesterViewTaskActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("ID", parentTask.getUniqueID());
                    context.startActivity(intent);

                }
            });

            return v;
        }


        /*
        I want to implement dialogs in the onClickListeners and I am not sure how to do that yet,
        since this isn't an activity.

        @Override
        public void onFinishConfirmDialog(Boolean confirmed){
            if (confirmed){

            }
        }


        private void acceptBidDialog(){
            dialogFlag = "Accept";

            ConfirmDialog confirmDialog = new ConfirmDialog();
            Bundle args = new Bundle();
            args.putString("title", "Accept Bid");
            args.putString("cancel", "Cancel");
            args.putString("confirm", "Yes");
            args.putString("message", "Are you sure you want to decline this bid? This will delete all other bids.");

            confirmDialog.setArguments(args);
            confirmDialog.show(fm, "To Done");
        }

}

        private void declineBidDialog(){
            dialogFlag = "Decline";

            ConfirmDialog confirmDialog = new ConfirmDialog();
            Bundle args = new Bundle();
            args.putString("title", "Decline Bid");
            args.putString("cancel", "Cancel");
            args.putString("confirm", "Yes");
            args.putString("message", "Are you sure you want to decline this bid? It will be deleted.");

            confirmDialog.setArguments(args);
            confirmDialog.show(fm, "To Done");
    }
*/

}
