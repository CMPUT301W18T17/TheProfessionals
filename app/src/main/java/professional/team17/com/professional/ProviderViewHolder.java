package professional.team17.com.professional;

import android.widget.TextView;

/**
 * Created by Zhipeng Zhang on 2018/3/13.
 */

public class ProviderViewHolder {
    TextView statusTextField;
    TextView userNameTextField;
    TextView taskTitleTextField;
    TextView taskLowBidAmountTextField;
    TextView taskMyBidAmountTextField;

    // This is a class for provider bidded tasks
    public ProviderViewHolder(TextView statusTextField, TextView userNameTextField, TextView taskTitleTextField, TextView taskLowBidAmountTextField, TextView taskMyBidAmountTextField){
        this.statusTextField = statusTextField;
        this.userNameTextField = userNameTextField;
        this.taskTitleTextField = taskTitleTextField;
        this.taskLowBidAmountTextField = taskLowBidAmountTextField;
        this.taskMyBidAmountTextField = taskMyBidAmountTextField;
    }
    public ProviderViewHolder(TextView statusTextField, TextView userNameTextField, TextView taskTitleTextField){
        this.statusTextField = statusTextField;
        this.userNameTextField = userNameTextField;
        this.taskTitleTextField = taskTitleTextField;
    }
    public ProviderViewHolder(TextView statusTextField, TextView userNameTextField, TextView taskTitleTextField, TextView taskMyBidAmountTextField){
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

    // Return the text view of taskTitleTextField
    public TextView getTaskTitleTextField() {
        return taskTitleTextField;
    }

    // Return the text view of taskLowBidAmountTextField
    public TextView getTaskLowBidAmount() { return taskLowBidAmountTextField;}

    // Return the text view of taskMyBidAmountTextField
    public TextView getTaskMyBidAmount() { return taskMyBidAmountTextField;}
}
