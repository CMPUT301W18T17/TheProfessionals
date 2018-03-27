package professional.team17.com.professional;

/**
 * An entity class which stores a notification.
 * @author lauren
 * @see NotificationList
 * @see NotificationListAdapter
 * @see NotificationActivity
 */

public class Notification {

    private String message;
    private Task task;
    private String user;
    private String type;

    /**
     * This constructor takes an amount, and thus adds a bid notification
     * @param task The task bidded on
     * @param amount The amount of the bid
     * @param user The bidder
     */
    public Notification(Task task, double amount, String user){
        this.task = task;
        this.user = user;
        type = "New Bid";
        buildBidMessage(amount);
    }

    /**
     * This constructor does not take an amount, and thus creates an assignment notification
     * @param task The task assigned
     * @param user The task creator
     */
    public Notification(Task task, String user){
        this.task = task;
        this.user = user;
        type = "Task Assigned";
        buildAssignedMessage();
    }

    /**
     * Builds the notification message for a bid notification
     * @param amount The bid amount
     */
    private void buildBidMessage(double amount){
        message = user + " has placed a bid on " + task.getName() + " for $" + Double.toString(amount) + ".";
    }

    /**
     * Builds the notification message for an assignment notification
     */
    private void buildAssignedMessage(){
        message = user + " has assigned you their task " + task.getName() + ".";
    }

    /**
     * Returns the notification's message
     * @return message
     */
    public String getMessage(){
        return message;
    }

    /**
     * Returns the type of notification
     * @return type
     */
    public String getType() { return type; }

    /**
     * Returns the task ID associated with the notification
     * @return task's unique ID
     */
    public String getNotificationTaskID(){
        return task.getUniqueID();
    }
}
