package professional.team17.com.professional;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Hailan
 * Referenced from Allison's ProviderTaskController
 * @since 2018-04-04.
 * @see RequesterAddTaskActivity
 * @see RequesterEditTaskActivity
 */
public class RequesterTaskController {
    private Context context;
    private ServerHelper serverHelper;
    private Task task;
    private String username;
    private String taskid;

    RequesterTaskController(Context context) {
        this.context = context;
        serverHelper = new ServerHelper(context);
        setUsername();
    }

    /**
     * set the task requester username for current task
     */
    private void setUsername() {
        SharedPreferences sharedpreferences = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        username = sharedpreferences.getString("username", "error");
    }

    /**
     * Get an old task from server by using task id
     * @param taskID - id of oldtask
     * @return - old task
     */
    public Task getOldTaskFromServer(String taskID){
        taskid = taskID;
        task = serverHelper.getTask(taskID);
        return task;
    }

    /**
     * Add a new task to server
     * @param title - title of task
     * @param description - description of task
     * @param locationString - location of task
     * @param dateString - due date for task
     * @param latLng - latlng for the location
     * @param photos - photos of task
     */
    public void addTaskToServer(String title, String description, String locationString, String dateString, LatLng latLng, ArrayList<String> photos){
        task = new Task(username, title, description, locationString, dateString, latLng, photos);
        taskid = serverHelper.addTasks(task);
    }

    /**
     * Update info for an old task in the server
     * @param title - title of task
     * @param description - description of task
     * @param locationString - location of task
     * @param dateString - due date for task
     * @param latLng - latlng for the location
     * @param photos - photos of task
     */
    public void addOldTaskToServer(String title, String description, String locationString, String dateString, LatLng latLng, ArrayList<String> photos){
        task.setName(title);
        task.setDate(parseDate(dateString));
        task.setDescription(description);
        task.setLocation(locationString);
        task.setLatLng(latLng);

        serverHelper.updateTasks(task);
    }

    /**
     * set task to requested state
     */
    public void setTaskToRequested(){
        task.setRequested();
    }

    /**
     * Converts a String date into a Date date.
     * @param sdate String date
     * @return Date date
     */
    public java.util.Date parseDate(String sdate) {
        String myFormat = "dd/MM/yyyy";
        java.util.Date input = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        if (sdate != null) {
            try {
                input = dateFormat.parse(sdate);
            } catch (ParseException e) {
                //do nothing
            }
        }
        return input;
    }

    /**
     *
     * @return - current task id
     */
    public String getCurrentTaskId(){
        return taskid;
    }

}
