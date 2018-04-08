package professional.team17.com.professional.Navigation;

import android.content.Context;
import android.content.Intent;

import professional.team17.com.professional.Activity.ProviderTaskListActivity;

/**
 * Created by ag on 2018-03-26.
 */

public class SwitchViewReqNav extends NavButton {
    public SwitchViewReqNav(Context con) {
        super(con);
    }

    public Intent getIntent() {
        intent = new Intent(context, ProviderTaskListActivity.class);
        intent.putExtra("Status", "Bidded");

        return intent;

    }
}
