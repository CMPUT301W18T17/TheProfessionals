package professional.team17.com.professional.Helpers;

/**
 *This is a singleton class that reflects the current state of connectivity
 * There are three states -
 * Online - if online, and has been online before
 * NewOnline - If previously offline, but now online
 * Offline - if offline
 *
 * @author Allison
 */
public class ConnectedState {
    public static status state = null;
    private static ConnectedState connectedState = null;

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
     * Will change the state to either
     * Online - if Online state already
     * NewOnline - if previously offline
     */
    public void setOnline(){
        if (this.state==status.OFFLINE) {
            this.state = status.NEWONLINE;
        }
         else {
            this.state = status.ONLINE;
        }
    }

    /**
     * Will change state to offline
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
    public boolean isNewOnline(){
        return (this.state == status.NEWONLINE);
    }

    /**
     *
     * @return false if state is online, true if offline
     */
    public boolean isOffline(){
        return (this.state == status.OFFLINE);
    }

    /**
     *
     * @return false if state is offline or newonline, true if online
     */
    public boolean isOnline() {
        return (this.state == status.ONLINE);
    }

    /**
     * enum values for 3 states
     */
    public enum status {
        ONLINE, OFFLINE, NEWONLINE
    }


}
