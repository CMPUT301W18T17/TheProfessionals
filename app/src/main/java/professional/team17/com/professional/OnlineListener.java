package professional.team17.com.professional;

import android.content.Context;
import android.util.Log;

/**
 * Created by ag on 2018-03-29.
 */

public class OnlineListener {


    public void notifyOnlineChange() {
        if (listener != null)
            Log.i("KKKKKKKKKKK", "notifyOnlineChange: ");
            listener.changetoOnline();
    }

    public void notifyOnline() {
        if (listener != null)
            listener.stayOnline();

    }

    // Step 1 - This interface defines the type of messages I want to communicate to my owner
    public interface MyCustomObjectListener {
        // These methods are the different events and
        // need to pass relevant arguments related to the event triggered
        public void changetoOnline();
        // or when data has been loaded

        public void stayOnline();
    }

    private MyCustomObjectListener listener;

    public OnlineListener() {
        // set null or default listener or accept as argument to constructor
    }


    // Assign the listener implementing events interface that will receive the events
    public void setCustomObjectListener(MyCustomObjectListener listener) {
        this.listener = listener;
    }
}

