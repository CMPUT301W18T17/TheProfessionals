package professional.team17.com.professional;

import android.content.Context;
import android.text.BoringLayout;
import android.util.Log;

import java.util.ArrayList;

/**
 * To be called whenever a
 */

public class SyncController {
    ServerHelper es;
    Context context;


    public SyncController(Context context){
        this.context = context;
        es = new ServerHelper(context);
    }



    public boolean getUpdated(){
        Task task;
        Task newtask;
        Boolean flag = true;
        TaskDAO db = new TaskDAO(context);
        ArrayList<String> ids = db.updatedTasks();
        db.close();
        for (String id : ids) {
            Log.i("loop", "getEDDITEDS: "+id);
            task = es.getTask(id);
            Log.i("loop", "getEDDITEDS: "+id+task.isRequested());
            if (!task.isRequested()){

                flag = false;
            }
            else {
                newtask = db.getTask(id);
                es.updateTasks(newtask);
            }
        }
        return flag;
    }




    public boolean getDeleted() {
        Task task;
        Task newtask;
        Boolean flag = true;
        TaskDAO db = new TaskDAO(context);
        ArrayList<String> ids = db.deletedTasks();
        db.close();
        for (String id : ids) {
            task = es.getTask(id);
            if (!task.isRequested()) {
                flag = false;
            } else {
                newtask = db.getTask(id);
                es.deleteTasks(newtask);
            }
        }
        return flag;
    }


    public void getAdded(){
        Task newtask;
        TaskDAO db = new TaskDAO(context);
        ArrayList<String> ids = db.newTasks();
        db.close();
        for (String id : ids) {
            newtask = db.getTask(id);
            es.addTasks(newtask);
        }

    }


    public boolean sync() {
        Boolean flag;
        getAdded();
        flag = getUpdated();
        //getDeleted();
        return flag;
    }

    public void resetRequested(String username) {
        TaskList tasklist = es.getTasksRequester(username, "Requested");
        TaskDAO db = new TaskDAO(context);
        db.insertAll(tasklist);
    }
}
