package professional.team17.com.professional.navigation;

import android.content.Context;
import android.content.Intent;

import professional.team17.com.professional.RequesterViewListActivity;


/**
 * Button to Move to Requester Activity to see Bidded tasks
 */
public class BiddedTasksReqNav extends NavButton {

        public BiddedTasksReqNav(Context con) {
            super(con);
        }

        public Intent getIntent() {
            intent = new Intent(context, RequesterViewListActivity.class);
            intent.putExtra("Status", "Bidded");
            return intent;
        }



}
