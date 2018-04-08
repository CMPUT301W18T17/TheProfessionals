package professional.team17.com.professional.Navigation;

import android.content.Context;
import android.content.Intent;

import professional.team17.com.professional.Activity.RequesterAddTaskActivity;


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
