package professional.team17.com.professional;

import android.util.Log;

/**
 * Created by ag on 2018-03-25.
 */

public class ConnectedState {
    public static status state = null;
    private static ConnectedState connectedState = null;
    private static OnlineListener OnlineListener;

    /**
     *
     * @return the instance of the singleton
     */
    public static ConnectedState getInstance() {
        if (connectedState==null) {
            connectedState = new ConnectedState();

        }
        return connectedState;
    }

    /**
     * Will change to online
     */
    public void setOnline(){
        if (this.state==status.OFFLINE) {
            Log.i("werwer", "setOnline: ");
            this.state = status.NEWONLINE;
        }
         else {
            this.state = status.ONLINE;
        }
    }

    /**
     * will change state to offline
     */
    public void setOffline(){
        if (this.state ==null || this.state==status.ONLINE) {
            this.state = status.OFFLINE;
        }
    }

    /**
     *
     * @return false if state is online, true if offline
     */
    public boolean isNewONline(){
        return (this.state == status.NEWONLINE);
    }

    /**
     *
     * @return false if state is online, true if offline
     */
    public boolean isOffline(){
        return (this.state == status.OFFLINE);
    }

    public void bind(OnlineListener object) {
        OnlineListener = object;
    }

    public boolean isOnline() {
        return (this.state == status.ONLINE);
    }

    public enum status {
        ONLINE, OFFLINE, NEWONLINE

    }


}
