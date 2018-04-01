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
        Log.i("VIEWLISR", "setOnline: ");
        if (this.state==status.OFFLINE) {
            this.state = status.ONLINE;
            if (OnlineListener != null){
                OnlineListener.notifyOnlineChange();
            }

        }
        if (this.state ==null) {
            this.state = status.ONLINE;

        }
        else {
            //OnlineListener.notifyOnline();
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
    public boolean isOffline(){
        return (this.state == status.OFFLINE);
    }

    public void bind(OnlineListener object) {
        OnlineListener = object;
    }

    public enum status {
        ONLINE, OFFLINE

    }


}
