package professional.team17.com.professional;

/**
 * An entity class which stores a notification.
 * @author lauren
 * @see NotificationList
 */

public class Notification {

    private String message;
    private Task task;
    private String user;

    public Notification(Task task, int amount, String user){
        this.task = task;
        this.user = user;
        buildBidMessage(amount);
    }

    public Notification(Task task, String user){
        this.task = task;
        this.user = user;
        buildAssignedMessage();
    }

    private void buildBidMessage(int amount){
        message = user + " has placed a bid on " + task.getName() + " for $" + Integer.toString(amount) + ".";
    }

    private void buildAssignedMessage(){
        message = user + " has assigned you their task " + task.getName() + ".";
    }

    public String getMessage(){
        return message;
    }

    public String getNotificationTaskID(){
        return task.getUniqueID();
    }
}
