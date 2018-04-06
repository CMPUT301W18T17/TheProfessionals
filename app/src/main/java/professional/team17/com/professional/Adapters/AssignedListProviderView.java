package professional.team17.com.professional.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import professional.team17.com.professional.R;

/**
 * ViewHolder for the recyler view for providertasks. This holds the view
 * for assigned tasks
 */
public class AssignedListProviderView extends RecyclerView.ViewHolder {
    private TextView statusTextField, userNameTextField, taskTitleTextField, myBidAmount;

    public AssignedListProviderView(View v) {
        super(v);
        statusTextField = (TextView) v.findViewById(R.id.provider_assigned_status);
        userNameTextField = (TextView) v.findViewById(R.id.provider_assigned_userName);
        taskTitleTextField = (TextView) v.findViewById(R.id.provider_assigned_title);
        myBidAmount = (TextView) v.findViewById(R.id.provider_assigned_mybidAmount);
    }

    public TextView getStatus() {
        return statusTextField;
    }

    public TextView getUserName() {
        return userNameTextField;
    }

    public TextView getTaskTitle() {
        return taskTitleTextField;
    }

    public TextView MyBidAmount() {
        return myBidAmount;
    }
}