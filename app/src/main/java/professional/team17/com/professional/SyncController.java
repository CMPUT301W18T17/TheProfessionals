package professional.team17.com.professional;

import android.content.Context;
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



    public void getUpdated(){
        Task task;
        Task newtask;
        TaskDAO db = new TaskDAO(context);
        ArrayList<String> ids = db.updatedTasks();
        for (String id : ids) {
            task = es.getTask(id);
            if (!task.isRequested()){
                //flag error
            }
            else {
                newtask = db.getTask(id);
                es.updateTasks(newtask);
            }
        }
        db.close();
    }




    public void getDeleted(){
        Task task;
        Task newtask;
        TaskDAO db = new TaskDAO(context);
        ArrayList<String> ids = db.deletedTasks();
        for (String id : ids) {
            task = es.getTask(id);
            if (!task.isRequested()){
                //flag error
            }
            else {
                newtask = db.getTask(id);
                es.deleteTasks(newtask);
            }
        }
        db.close();
    }

    public void getAdded(){
        Task newtask;
        TaskDAO db = new TaskDAO(context);
        ArrayList<String> ids = db.newTasks();
        for (String id : ids) {
            newtask = db.getTask(id);
            es.addTasks(newtask);
        }
        db.close();
    }


    public void sync() {
        TaskDAO db = new TaskDAO(context);

        Log.i("TAG", "sync: ");
        getAdded();
        getUpdated();
        getDeleted();
        db.close();
    }

    public void resetRequested() {


    }
}
