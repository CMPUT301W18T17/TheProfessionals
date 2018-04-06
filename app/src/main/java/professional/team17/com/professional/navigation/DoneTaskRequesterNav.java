package professional.team17.com.professional.navigation;

import android.content.Context;
import android.content.Intent;

import professional.team17.com.professional.ProviderTaskListActivity;
import professional.team17.com.professional.RequesterViewListActivity;

/**
 * Button to Move to Requester Activity to see Done tasks
 */
public  class DoneTaskRequesterNav extends NavButton {
        public DoneTaskRequesterNav(Context con) {
            super(con);
        }

        public Intent getIntent() {
            intent = new Intent(context, RequesterViewListActivity.class);
            intent.putExtra("Status", "Done");
            return intent;


        }


    }

