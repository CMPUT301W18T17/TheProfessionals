package professional.team17.com.professional;

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

   public NotificationList(){
       notificationList = new ArrayList<>();
   }

   public void clearList(){
       notificationList.clear();
   }

   public void newBidNotification(Task task, int amount, String user){
       Notification notification = new Notification(task, amount, user);
       notificationList.add(notification);
   }

   public void newAssignedNotification(Task task, String user){
       Notification notification = new Notification(task, user);
       notificationList.add(notification);
   }
}
