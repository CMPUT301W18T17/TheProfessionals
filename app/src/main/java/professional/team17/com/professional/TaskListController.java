package professional.team17.com.professional;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

/**
 * This class is used to help control any TaskList view for the
 * Requester or Provider. It acts as a middle man between the
 * Server and the activity.
 * @see ServerHelper
 * @see TaskList
 * @see Task
 */
public class TaskListController {
    private Context context;
    private ServerHelper serverHelper;
    public TaskList tasklist;
    private String username;
    private Task task;


    TaskListController(Context context) {
        this.context = context;
        serverHelper = new ServerHelper(context);
        tasklist = new TaskList();
        setUsername();
    }

    /**
     * Ths username of the session
     */
    private void setUsername() {
        SharedPreferences sharedpreferences = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        username = sharedpreferences.getString("username", "error");
    }

    /**
     * This is used to get the type of status the task row should be populated with
     * @param type - The string type of status
     */
    public void setType(Bundle type) {
        String status = type.getString("Status");
        TaskStatus.getInstance().setStatus(status);
    }

    /**
     * This is used to create the list for the provider view.
     */
    public void createList() {
        if (TaskStatus.getInstance().isBidded()) {
            tasklist = serverHelper.getTasksBidded(username, "Bidded");
        }
        if (TaskStatus.getInstance().isAssigned()) {
            tasklist = serverHelper.getTasksBidded(username, "Assigned");
        }
        if (TaskStatus.getInstance().isDone()){
            tasklist = serverHelper.getTasksBidded(username, "Done");
        }
    }

    /**
     *
     * @return True means the app is offline, false is online
     */
    public Boolean checkOffline() {
        ConnectedState c = ConnectedState.getInstance();
        if (c.isOffline()) {
            return true;
        } else return false;
    }

    /**
     *
     * @param position - The index of the tasklist
     * @param intention - The intent to be passed to the next activity
     * @return
     */
    public Intent findTask(int position, Intent intention) {
        Task task = tasklist.get(position);
        intention.putExtra("ID", task.getUniqueID());
        return intention;
    }

    /**
     *
     * @param position - the index of the tasklist being looked at
     */
    public void setTask(int position) {
        task = tasklist.get(position);
    }

    /**
     * This is used to delete a task for the requester view
     */
    public void delete() {
        tasklist.deleteTask(task);
        serverHelper.deleteTasks(task);
        task = null;
    }

    /**
     * This is used to create the list depending on the state of the tasklist
     */
    public void createListRequester() {
            if (TaskStatus.getInstance().isAssigned()) {
                tasklist = serverHelper.getTasksRequester(username, "Assigned");
            }
            if (TaskStatus.getInstance().isRequested()) {
                tasklist = serverHelper.getTasksRequester(username, "Requested");
            }
            if (TaskStatus.getInstance().isBidded()) {
                tasklist = serverHelper.getTasksRequester(username, "Bidded");
            }
            if (TaskStatus.getInstance().isDone()) {
                tasklist = serverHelper.getTasksRequester(username, "Done");
        }
    }

    /**
     * This will add all open tasks
     */
    public void addAllOpen() {
        tasklist = serverHelper.getTasksSearch(username);
        }

    /**
     *
     * @param query  - the String search value being looked for
     */
    public void search(String query) {
        TaskList temp = new TaskList();
        temp = serverHelper.getSearch(query);
        tasklist.clear();
        tasklist.addAll(temp);
    }
}
