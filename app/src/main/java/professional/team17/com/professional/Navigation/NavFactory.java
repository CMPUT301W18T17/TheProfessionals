package professional.team17.com.professional.Navigation;

import android.content.Context;

import professional.team17.com.professional.R;

/**
 * Factory to build the Navigation button controls and activities
 */
public class NavFactory {
    public static NavButton makeFor(int id, Context cont) {
        switch(id) {
            case R.id.switchViewRequesterButton:
                return new SwitchViewReqNav(cont);
            case R.id.requestedTaskRequesterButton:
                return new RequestedTaskReqNav(cont);
            case R.id.biddedTasksProviderButton:
                return new BiddedTaskProviderNav(cont);
            case R.id.acceptedTasksProviderButton:
                return new AcceptedTasksProviderNav(cont);
            case R.id.taskSearchProviderButton:
                return new SearchTasksProviderNav(cont);
            case R.id.switchViewProviderButton:
                return new SwitchViewProviderNav(cont);
            case R.id.addTaskRequesterButton:
                return new AddTaskReqNav(cont);
            case R.id.biddedTasksRequesterButton:
                return new BiddedTasksReqNav(cont);
            case R.id.acceptedTasksRequesterButton:
                return new AcceptedTasksReqNav(cont);
            case R.id.doneTasksProviderButton:
                return new DoneTaskProviderNav(cont);
            case R.id.doneTasksRequesterButton:
                return new DoneTaskRequesterNav(cont);
        }
        return null;
    }
}
