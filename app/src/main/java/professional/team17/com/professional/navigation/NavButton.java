package professional.team17.com.professional.navigation;

import android.content.Context;
import android.content.Intent;

import professional.team17.com.professional.ConnectedState;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Class that will have build the navigation
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

