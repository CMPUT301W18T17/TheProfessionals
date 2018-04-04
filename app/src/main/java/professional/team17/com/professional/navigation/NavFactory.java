package professional.team17.com.professional.navigation;

import android.content.Context;

import professional.team17.com.professional.R;

/**
 * Created by ag on 2018-03-26.
 */

public class NavFactory {
    public static NavButton makeFor(int id, Context cont) {
        switch(id) {
            case R.id.switchViewRequesterButton:
                return new SwitchViewReqNav(cont);
            case R.id.requestedTasksRequesterButton:
                return new RequestedTaskReqNav(cont);
            case R.id.biddedTasksProviderButton:
                return new BiddedTaskProviderNav(cont);
            case R.id.acceptedTasksProviderButton:
                return new AcceptedTasksProviderNav(cont);
            case R.id.searchTasksButton:
                return new SearchTasksProviderNav(cont);
            case R.id.switchViewProviderButton:
                return new SwitchViewProviderNav(cont);
            case R.id.addTaskRequesterButton:
                return new AddTaskReqNav(cont);
            case R.id.biddedTasksRequesterButton:
                return new BiddedTasksReqNav(cont);
            case R.id.acceptedTasksRequesterButton:
                return new AcceptedTasksReqNav(cont);
        }
        return null;
    }
}
