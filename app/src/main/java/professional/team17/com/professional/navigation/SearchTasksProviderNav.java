package professional.team17.com.professional.navigation;

import android.content.Context;
import android.content.Intent;

import professional.team17.com.professional.SearchActivity;

/**
 * Created by ag on 2018-03-26.
 */

public class SearchTasksProviderNav extends NavButton {
        public SearchTasksProviderNav(Context con) {
            super(con);
        }

        public Intent getIntent() {

            intent = new Intent(context, SearchActivity.class);
            intent.putExtra("Status", "Bidded");
            return intent;

        }
}
