package professional.team17.com.professional;

import android.widget.TextView;

/**
 * Created by Zhipeng Zhang on 2018/3/13.
 */

public class RequesterAssignedViewHolder {
    TextView statusTextField;
    TextView userNameTextField;
    TextView taskTitleTextField;
    TextView taskAcceptBidAmountTextField;

    public RequesterAssignedViewHolder(TextView statusTextField, TextView userNameTextField, TextView taskTitleTextField, TextView taskAcceptBidAmountTextField){
        this.statusTextField = statusTextField;
        this.userNameTextField = userNameTextField;
        this.taskTitleTextField = taskTitleTextField;
        this.taskAcceptBidAmountTextField = taskAcceptBidAmountTextField;
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

    public TextView getTaskAcceptBidAmount() { return taskAcceptBidAmountTextField;}
}
