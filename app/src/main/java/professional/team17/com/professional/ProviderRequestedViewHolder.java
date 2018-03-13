package professional.team17.com.professional;

import android.widget.TextView;

/**
 * Created by Zhipeng Zhang on 2018/3/13.
 */

public class ProviderRequestedViewHolder {
    TextView statusTextField;
    TextView userNameTextField;
    TextView taskTitleTextField;

    public ProviderRequestedViewHolder(TextView statusTextField, TextView userNameTextField, TextView taskTitleTextField){
        this.statusTextField = statusTextField;
        this.userNameTextField = userNameTextField;
        this.taskTitleTextField = taskTitleTextField;
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
}
