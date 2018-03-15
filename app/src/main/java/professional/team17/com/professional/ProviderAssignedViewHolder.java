package professional.team17.com.professional;

import android.widget.TextView;

/**
 * Created by Zhipeng Zhang on 2018/3/13.
 */

// This is a ViewHolder class for Provider Assigned Tasks
public class ProviderAssignedViewHolder {
    TextView statusTextField;
    TextView userNameTextField;
    TextView taskTitleTextField;
    TextView taskMyBidAmountTextField;

    public ProviderAssignedViewHolder(TextView statusTextField, TextView userNameTextField, TextView taskTitleTextField, TextView taskMyBidAmountTextField){
        this.statusTextField = statusTextField;
        this.userNameTextField = userNameTextField;
        this.taskTitleTextField = taskTitleTextField;
        this.taskMyBidAmountTextField = taskMyBidAmountTextField;
    }
    // Return the text view of statusTextField
    public TextView getStatusTextField() {
        return statusTextField;
    }

    // Return the text view of userNameTextField
    public TextView getUserNameTextField() {
        return userNameTextField;
    }

    // Return the text view of taskMyBidAmountTextField
    public TextView getTaskTitleTextField() {
        return taskTitleTextField;
    }

    // Return the text view of taskMyBidAmountTextField
    public TextView getTaskMyBidAmount() { return taskMyBidAmountTextField;}
}
