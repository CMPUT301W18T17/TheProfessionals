package professional.team17.com.professional;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;

/**
 * To be called whenever a
 */

public class SyncController {
    ElasticSearchController es;
    Context context;

    public SyncController(Context context){
        es = new ElasticSearchController();
        this.context = context;
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


}
