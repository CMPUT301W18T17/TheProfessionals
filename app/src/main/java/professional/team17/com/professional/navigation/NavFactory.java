package professional.team17.com.professional.navigation;

import android.content.Context;

import professional.team17.com.professional.R;

/**
 * Created by ag on 2018-03-26.
 */

public class NavFactory {
    public static navButton makeFor(int id, Context cont) {
        switch(id) {
            case R.id.switchViewRequesterButton:
                return new switchViewR(cont);
            case R.id.requestedTasksRequesterButton:
                return new requestedTaskR(cont);
            case R.id.biddedTasksProviderButton:
                return new biddedTaskP(cont);
            case R.id.acceptedTasksProviderButton:
                return new acceptedTasksP(cont);
            case R.id.searchTasksButton:
                return new searchTasksP(cont);
            case R.id.switchViewProviderButton:
                return new switchViewP(cont);
            case R.id.addTaskRequesterButton:
                return new addTaskR(cont);
            case R.id.biddedTasksRequesterButton:
                return new biddedTasksR(cont);
            case R.id.acceptedTasksRequesterButton:
                return new acceptedTasksR(cont);


        }
        return null;
    }
}
