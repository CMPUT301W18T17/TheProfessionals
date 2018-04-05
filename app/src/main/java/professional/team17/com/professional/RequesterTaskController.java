package professional.team17.com.professional;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by Hailan on 2018-04-04.
 */

public class RequesterTaskController {
    private Context context;
    private ServerHelper serverHelper;
    private Task task;
    private String username;

    RequesterTaskController(Context context) {
        this.context = context;
        serverHelper = new ServerHelper(context);
        setUsername();
    }

    private void setUsername() {
        SharedPreferences sharedpreferences = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        username = sharedpreferences.getString("username", "error");
    }

    public void addTaskToServer(String title, String description, String locationString, String dateString, LatLng latLng, ArrayList<String> photos){
        task = new Task(username, title, description, locationString, dateString, latLng, photos);
        serverHelper.addTasks(task);
    }

    public void setTaskToRequested(){
        task.setRequested();
    }

    public Task getOldTaskFromServer(String taskID){
        task = serverHelper.getTask(taskID);
        return task;
    }



}
