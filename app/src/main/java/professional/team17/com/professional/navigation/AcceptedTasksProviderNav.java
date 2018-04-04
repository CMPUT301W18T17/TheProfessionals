package professional.team17.com.professional.navigation;

import android.content.Context;
import android.content.Intent;

import professional.team17.com.professional.ProviderTaskListActivity;

/**
 * Created by ag on 2018-03-26.
 */

public class AcceptedTasksProviderNav extends NavButton {
    public AcceptedTasksProviderNav(Context con) {
        super(con);
    }

    public Intent getIntent() {
        intent = new Intent(context, ProviderTaskListActivity.class);
        intent.putExtra("Status", "Assigned");
        return intent;
    }
}


