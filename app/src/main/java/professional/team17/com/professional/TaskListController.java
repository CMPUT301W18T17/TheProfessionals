package professional.team17.com.professional;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by ag on 2018-04-02.
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

    private void setUsername() {
        SharedPreferences sharedpreferences = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        username = sharedpreferences.getString("username", "error");
    }

    public void setType(Bundle type) {
        String status = type.getString("Status");
        TaskStatus.getInstance().setStatus(status);
    }

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

    public Boolean checkOffline() {
        ConnectedState c = ConnectedState.getInstance();
        if (c.isOffline()) {
            return true;


        } else return false;
    }

    public Intent findTask(int position, Intent intention) {
        Task task = tasklist.get(position);
        intention.putExtra("ID", task.getUniqueID());
        return intention;
    }

    public void setTask(int position) {
        task = tasklist.get(position);
    }

    public void delete() {
        tasklist.deleteTask(task);
        serverHelper.deleteTasks(task);
        task = null;
    }

    /**
     *
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
