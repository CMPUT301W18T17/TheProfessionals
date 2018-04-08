package professional.team17.com.professional.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import professional.team17.com.professional.Activity.NotificationActivity;
import professional.team17.com.professional.Entity.Notification;
import professional.team17.com.professional.Entity.NotificationList;
import professional.team17.com.professional.R;

/**
 * An adapter to show the list of notifications.
 * @author lauren (used code from allison's BidListAdapter)
 * @see NotificationList
 * @see NotificationActivity
 * @see Notification
 */

public class NotificationListAdapter extends ArrayAdapter<Notification> {


    /**
     * Makes a NotificationListAdapter object
     * @param context The activity in which it's used
     * @param notificationList The list of notifications
     */
    public NotificationListAdapter(Activity context, ArrayList<Notification> notificationList) {
        super(context, 0,  notificationList);
    }

    /**
     * Sets the row view items
     * @param position The position of the notification in a list
     * @param v The view object
     * @param parent The parent viewgroup
     * @return updated view object
     */
    public View getView(int position, View v, ViewGroup parent) {
        final Notification notification= getItem(position);
        if (v == null) {
            v = LayoutInflater.from(getContext()).inflate(R.layout.notification_row, parent, false);
        }

        //get view
        TextView notificationTypeField = (TextView) v.findViewById(R.id.notificationTypeField);
        TextView notificationMessageField = (TextView) v.findViewById(R.id.notificationMessageField);


        //plug in item to row
        notificationTypeField.setText(notification.getType());
        notificationMessageField.setText(notification.getMessage());


        return v;
    }
}
