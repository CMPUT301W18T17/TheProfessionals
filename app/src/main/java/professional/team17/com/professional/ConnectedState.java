package professional.team17.com.professional;

import android.util.Log;

/**
 * Created by ag on 2018-03-25.
 */

public class ConnectedState {
    public static status customVar = null;
    private static ConnectedState connectedState = null;

        public static ConnectedState getInstance() {
            if (connectedState==null) {
                connectedState = new ConnectedState();
            }
            return connectedState;
        }

        public void setOnline(){
            if (this.customVar ==null || this.customVar==status.OFFLINE) {
                this.customVar = status.ONLINE;
            }

    }
    public void setOffline(){
        if (this.customVar ==null || this.customVar==status.ONLINE) {
            this.customVar = status.OFFLINE;
        }
    }

    public enum status {
        ONLINE, OFFLINE

    }


}
