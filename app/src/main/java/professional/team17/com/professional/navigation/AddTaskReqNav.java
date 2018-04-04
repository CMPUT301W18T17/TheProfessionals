package professional.team17.com.professional.navigation;

import android.content.Context;
import android.content.Intent;

import professional.team17.com.professional.RequesterAddTaskActivity;


/**
 * Created by ag on 2018-03-26.
 */

public class AddTaskReqNav extends NavButton {
        public AddTaskReqNav(Context con) {
            super(con);
        }

        public Intent getIntent() {
            intent = new Intent(context, RequesterAddTaskActivity.class);
            return intent;
        }

}
