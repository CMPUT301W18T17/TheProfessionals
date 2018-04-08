package professional.team17.com.professional.Navigation;

import android.content.Context;
import android.content.Intent;

import professional.team17.com.professional.Activity.ProviderTaskListActivity;

/**
 * Button to Move to Provider Activity to see Assigned tasks
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


