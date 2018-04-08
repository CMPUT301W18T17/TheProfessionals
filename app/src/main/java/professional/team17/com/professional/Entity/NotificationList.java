package professional.team17.com.professional.Entity;

import java.util.ArrayList;

/**
 * An entity class for a list which contains notifications. Notifications can only be added
 * or cleared (they are cleared once they have been viewed).
 * @author lauren
 * @see Notification
 * @see Profile
 */

public class NotificationList {

    private ArrayList<Notification> notificationList;

    /**
     * When the object is created, it makes an ArrayList of Notification objects
     */
   public NotificationList(){
       notificationList = new ArrayList<>();
   }

    /**
     * Empties the list
     */
   public void clearList(){
       notificationList.clear();
   }

    /**
     * Returns the ArrayList
     * @return notificationList
     */
   public ArrayList<Notification> getList() { return notificationList; }

    /**
     * Adds a bid notification to the list
     * @param task The task associated with the notification
     * @param amount The amount of the bid
     * @param user The user who bidded on the task
     */
   public void newBidNotification(Task task, double amount, String user){
       Notification notification = new Notification(task, amount, user);
       notificationList.add(notification);
   }

   public Notification get(int position){
       return notificationList.get(position);
   }

    /**
     * Adds an assignment notification to the list
     * @param task The task associated with the notification
     * @param user The task creator
     */
   public void newAssignedNotification(Task task, String user){
       Notification notification = new Notification(task, user);
       notificationList.add(notification);
   }

    /**
     * Returns true if the list is empty
     */
    public boolean isEmpty(){
        return notificationList.isEmpty();
    }
}
