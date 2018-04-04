package professional.team17.com.professional.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import professional.team17.com.professional.R;

/**
 * Created by ag on 2018-04-03.
 */

public class RequesterListProviderView extends RecyclerView.ViewHolder {
    private static TextView statusTextField, userNameTextField, taskTitleTextField;
    private static TextView taskLowBidAmountTextField, taskMyBidAmountTextField;

    public RequesterListProviderView(View v) {
        super(v);
        statusTextField = (TextView) v.findViewById(R.id.provider_requested_status);
        userNameTextField = (TextView) v.findViewById(R.id.provider_requested_userName);
        taskTitleTextField = (TextView) v.findViewById(R.id.provider_requested_title);

    }

    public static TextView getStatus() {
        return statusTextField;
    }

    public static TextView getUserName() {
        return userNameTextField;
    }

    public static TextView getName() {
        return taskTitleTextField;
    }

}
