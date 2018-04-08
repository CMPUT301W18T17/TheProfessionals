package professional.team17.com.professional.Navigation;

import android.content.Context;
import android.content.Intent;

import professional.team17.com.professional.Activity.SearchActivity;

/**
 * Button to Move to Provider Activity to see searched for tasks
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
