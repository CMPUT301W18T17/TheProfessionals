package professional.team17.com.professional;

import android.widget.TextView;

/**
 * Created by Zhipeng Zhang on 2018/3/13.
 */

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

    public TextView getStatusTextField() {
        return statusTextField;
    }

    public TextView getUserNameTextField() {
        return userNameTextField;
    }

    public TextView getTaskTitleTextField() {
        return taskTitleTextField;
    }

    public TextView getTaskMyBidAmount() { return taskMyBidAmountTextField;}
}
