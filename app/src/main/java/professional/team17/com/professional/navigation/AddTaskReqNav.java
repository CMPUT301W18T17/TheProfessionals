package professional.team17.com.professional.navigation;

import android.content.Context;
import android.content.Intent;

import professional.team17.com.professional.RequesterAddTaskActivity;


/**
 * Button to Move to Requester Activity to add task
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
