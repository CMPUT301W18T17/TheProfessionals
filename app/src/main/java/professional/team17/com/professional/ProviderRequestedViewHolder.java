package professional.team17.com.professional;

import android.widget.TextView;

/**
 * Created by Zhipeng Zhang on 2018/3/13.
 */

public class ProviderRequestedViewHolder {
    TextView statusTextField;
    TextView userNameTextField;
    TextView taskTitleTextField;

    // Tis calls is for requested tasls
    public ProviderRequestedViewHolder(TextView statusTextField, TextView userNameTextField, TextView taskTitleTextField){
        this.statusTextField = statusTextField;
        this.userNameTextField = userNameTextField;
        this.taskTitleTextField = taskTitleTextField;
    }

    // This returns userNameTextField text view
    public TextView getStatusTextField() {
        return statusTextField;
    }

    // This returns userNameTextField text view
    public TextView getUserNameTextField() {
        return userNameTextField;
    }

    // This returns taskTitleTextField text view
    public TextView getTaskTitleTextField() {
        return taskTitleTextField;
    }
}
