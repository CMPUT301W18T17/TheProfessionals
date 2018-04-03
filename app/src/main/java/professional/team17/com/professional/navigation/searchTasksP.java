package professional.team17.com.professional.navigation;

import android.content.Context;
import android.content.Intent;

import professional.team17.com.professional.ConnectedState;
import professional.team17.com.professional.SearchActivity;
import professional.team17.com.professional.navigation.navButton;

/**
 * Created by ag on 2018-03-26.
 */

public class searchTasksP extends navButton {
        public searchTasksP(Context con) {
            super(con);
        }

        public Intent getIntent() {

            intent = new Intent(context, SearchActivity.class);
            intent.putExtra("Status", "Bidded");
            return intent;

        }
}
