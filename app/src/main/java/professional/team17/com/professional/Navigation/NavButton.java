package professional.team17.com.professional.Navigation;

import android.content.Context;
import android.content.Intent;

import professional.team17.com.professional.Helpers.ConnectedState;

/**
 * Class that will have build the navigation controls
 * that will switch the views
 * @see ConnectedState
 */
public abstract class NavButton {
    protected Intent intent;
    protected Context context;
    private ConnectedState c;

    public NavButton(Context con){
        context = con;
        c = ConnectedState.getInstance();
    }

    public abstract Intent getIntent();

}

