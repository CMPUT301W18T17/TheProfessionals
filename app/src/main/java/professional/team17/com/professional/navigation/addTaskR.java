package professional.team17.com.professional.navigation;

import android.content.Context;
import android.content.Intent;

import professional.team17.com.professional.RequesterAddTaskActivity;
import professional.team17.com.professional.navigation.navButton;


/**
 * Created by ag on 2018-03-26.
 */

public class addTaskR extends navButton {
        public addTaskR(Context con) {
            super(con);
        }

        public Intent getIntent() {
            intent = new Intent(context, RequesterAddTaskActivity.class);
            return intent;
        }

}
