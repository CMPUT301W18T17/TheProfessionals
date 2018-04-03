package professional.team17.com.professional;

import android.view.animation.BounceInterpolator;

/**
 * Created by ag on 2018-04-02.
 */

public class TaskStatus {
    private static status state  = null;
    private static TaskStatus taskStatus = null;

    public static TaskStatus getInstance() {
        if (taskStatus==null) {
            taskStatus = new TaskStatus();
        }
        return taskStatus;
    }


    /**
     * Will change the state to either
     * Online - if Online state already
     * NewOnline - if previously offline
     */
    public void setStatus(String status) {
        switch (status) {
            case "Assigned":
                this.state = TaskStatus.status.ASSIGNED;
                break;
            case "Requested":
                this.state = TaskStatus.status.REQUESTED;
                break;
            case "Bidded":
                this.state = TaskStatus.status.BIDDED;
                break;
            case "Done":
                this.state = TaskStatus.status.DONE;
                break;
        }
    }

    public Boolean isAssigned(){
        return (this.state == status.ASSIGNED);
    }
    public Boolean isBidded(){
        return (this.state == status.BIDDED);
    }
    public Boolean isRequested(){
        return (this.state == status.REQUESTED);
    }



    /**
     * enum values for 3 states
     */
    public enum status {
        REQUESTED, BIDDED, ASSIGNED, DONE
    }

}
