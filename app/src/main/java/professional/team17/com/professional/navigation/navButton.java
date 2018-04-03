package professional.team17.com.professional.navigation;

import android.content.Context;
import android.content.Intent;

import professional.team17.com.professional.ConnectedState;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by ag on 2018-03-26.
 */

public abstract class navButton {
    Intent intent;
    Context context;
    ConnectedState c;

    public navButton(Context con){
        context = con;
        c = ConnectedState.getInstance();
    }

    public abstract Intent getIntent();





}

