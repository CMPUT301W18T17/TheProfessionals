package professional.team17.com.professional.navigation;

import android.content.Context;
import android.content.Intent;

import professional.team17.com.professional.ProviderTaskListActivity;

/**
 * Button to Move to Provider Activity to see Done tasks
 */
public  class DoneTaskProviderNav extends NavButton {
        public DoneTaskProviderNav(Context con) {
            super(con);
        }

        public Intent getIntent() {
            intent = new Intent(context, ProviderTaskListActivity.class);
            intent.putExtra("Status", "Done");
            return intent;


        }


    }

