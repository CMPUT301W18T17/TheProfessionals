package professional.team17.com.professional.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import professional.team17.com.professional.R;

/**
 * Created by ag on 2018-04-03.
 */

public class BiddedListProviderView extends RecyclerView.ViewHolder {
    private TextView statusTextField, userNameTextField, taskTitleTextField;
    private TextView taskLowBidAmountTextField, taskMyBidAmountTextField;

    public BiddedListProviderView(View v) {
        super(v);
        statusTextField = (TextView) v.findViewById(R.id.provider_bidded_status);
        userNameTextField = (TextView) v.findViewById(R.id.provider_bidded_userName);
        taskTitleTextField = (TextView) v.findViewById(R.id.provider_bidded_title);
        taskLowBidAmountTextField = (TextView) v.findViewById(R.id.provider_bidded_lowbidAmount);
        taskMyBidAmountTextField = (TextView) v.findViewById(R.id.provider_bidded_mybidAmount);
    }


    public TextView getStatusTextField() {
        return statusTextField;
    }

    public TextView getUserNameTextField() {
        return userNameTextField;
    }

    public TextView getTaskTitleTextField() {
        return taskTitleTextField;
    }

    public TextView getTaskLowBidAmount() {
        return taskLowBidAmountTextField;
    }

    public TextView getTaskMyBidAmount() {
        return taskMyBidAmountTextField;
    }
}

