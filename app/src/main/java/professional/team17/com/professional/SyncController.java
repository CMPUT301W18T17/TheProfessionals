package professional.team17.com.professional;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

/**
 *
 * This controller is used to sync offline changes with online
 * The business logic driving this sync is that only a task in
 * "Requested" status can be edited/deleted. So if offline, as user
 * edited a requested task, while at the same time someone placed the bid,
 * this sync controller would not allow that edit to override the task once
 * the user goes online. The user will be notified of any of these situations.
 * @author Allison
 * @see ServerHelper
 * @see Context
 * @see Task (associated)
 * @see TaskList (associated)
 * @see TaskDAO (associated)
 */
public class SyncController {
    private final ServerHelper serverHelper;
    private final Context context;

    /**
     *
     * @param context - the activity calling the sync
     *                Any activity can be used to sync
     */
    public SyncController(Context context){
        this.context = context;
        serverHelper = new ServerHelper(context);
    }


    /**
     *
     * @return Boolean -
     * True signifies all offline updates could be pushed to server
     * False signifies not all offline updated could be pushed to server
     * This also updates the server with those that can be updated
     */
    private boolean getUpdated(){
        Task task;
        Task newtask;
        Boolean flag = true;
        TaskDAO db = new TaskDAO(context);
        ArrayList<String> ids = db.updatedTasks();
        db.close();
        for (String id : ids) {
            task = serverHelper.getTask(id);
            //if the task on the server is no longer requested
            if (!task.isRequested()){
                flag = false;
            }
            else {
                newtask = db.getTask(id);
                serverHelper.updateTasks(newtask);
            }
        }
        return flag;
    }



    /**
     *
     * @return Boolean -
     * True signifies all offline deleted could be pushed to server
     * False signifies not all offline deletes could be pushed to server
     * This also updates the server with those that can be deleted
     */
    public boolean getDeleted() {
        Task task;
        Task newtask;
        Boolean flag = true;
        TaskDAO db = new TaskDAO(context);
        ArrayList<String> ids = db.deletedTasks();
        db.close();
        for (String id : ids) {
            task = serverHelper.getTask(id);
            if (!task.isRequested()) {
                flag = false;
            } else {
                newtask = db.getTask(id);
                serverHelper.deleteTasks(newtask);
            }
        }
        return flag;
    }

    /**
     * This updates the server with all offline added tasks
     */
    private void getAdded(){
        Task newtask;
        TaskDAO db = new TaskDAO(context);
        ArrayList<String> ids = db.newTasks();
        db.close();
        for (String id : ids) {
            newtask = db.getTask(id);
            serverHelper.onlineAddTask(newtask);
        }

    }

    /**
     * @return Boolean
     * True signifies all offline actions could be synced to server
     * False signifies not all offline actions could be pushed to server
     */
    public boolean sync() {
        Boolean flag;
        getAdded();
        flag = getUpdated();
        flag = getDeleted();
        return flag;
    }

    /**
     *
     * @param username - the username of the task requester
     *                 This will push to the server all
     *                 those tasks from online for that requester
     */
    public void resetRequested(String username) {
        TaskList tasklist = serverHelper.getTasksRequester(username, "Requested");
        TaskDAO db = new TaskDAO(context);
        db.insertAll(tasklist);
    }
}
