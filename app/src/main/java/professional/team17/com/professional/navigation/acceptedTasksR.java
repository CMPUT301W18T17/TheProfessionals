package professional.team17.com.professional.navigation;

import android.content.Context;
import android.content.Intent;

import professional.team17.com.professional.RequesterViewListActivity;
import professional.team17.com.professional.navigation.navButton;


/**
 * Created by ag on 2018-03-26.
 */

class acceptedTasksR extends navButton {
        public acceptedTasksR(Context con) {
            super(con);
        }

        public Intent getIntent() {
            intent = new Intent(context, RequesterViewListActivity.class);
            intent.putExtra("Status", "Assigned");
            return intent;


        }
}
