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

    // This class is for requester assigned tasks
    public RequesterAssignedViewHolder(TextView statusTextField, TextView userNameTextField, TextView taskTitleTextField, TextView taskAcceptBidAmountTextField){
        this.statusTextField = statusTextField;
        this.userNameTextField = userNameTextField;
        this.taskTitleTextField = taskTitleTextField;
        this.taskAcceptBidAmountTextField = taskAcceptBidAmountTextField;
    }

    // This returns the text view of statusTextField
    public TextView getStatusTextField() {
        return statusTextField;
    }

    // This returns the text view of userNameTextField
    public TextView getUserNameTextField() {
        return userNameTextField;
    }

    // This returns the text view of taskTitleTextField
    public TextView getTaskTitleTextField() { return taskTitleTextField; }

    // This returns the text view of taskAcceptBidAmountTextField
    public TextView getTaskAcceptBidAmount() { return taskAcceptBidAmountTextField;}
}
