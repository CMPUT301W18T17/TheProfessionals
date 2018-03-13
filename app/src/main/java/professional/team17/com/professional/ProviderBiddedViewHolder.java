package professional.team17.com.professional;

import android.widget.TextView;

/**
 * Created by Zhipeng Zhang on 2018/3/13.
 */

public class ProviderBiddedViewHolder {
    TextView statusTextField;
    TextView userNameTextField;
    TextView taskTitleTextField;
    TextView taskLowBidAmountTextField;
    TextView taskMyBidAmountTextField;

    public ProviderBiddedViewHolder(TextView statusTextField, TextView userNameTextField, TextView taskTitleTextField, TextView taskLowBidAmountTextField, TextView taskMyBidAmountTextField){
        this.statusTextField = statusTextField;
        this.userNameTextField = userNameTextField;
        this.taskTitleTextField = taskTitleTextField;
        this.taskLowBidAmountTextField = taskLowBidAmountTextField;
        this.taskMyBidAmountTextField = taskMyBidAmountTextField;
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

    public TextView getTaskLowBidAmount() { return taskLowBidAmountTextField;}

    public TextView getTaskMyBidAmount() { return taskMyBidAmountTextField;}
}
