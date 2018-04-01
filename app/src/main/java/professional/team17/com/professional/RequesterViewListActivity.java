/*
 * RequesterViewListActivity
 *
 * March 14, 2018
 *
 * Copyright @ 2018 Team 17, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behaviour at the University of Alberta.
 * You can find a copy of the license in the github wiki for this project.
 */

package professional.team17.com.professional;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

/**
 * An activity where the requester can view all of their tasks with status requested, bidded or
 * assigned.
 * @author Allison, Lauren
 * @see RequesterCustomArrayAdapter
 * @see TaskList
 * @see Navigation
 */
public class RequesterViewListActivity extends Navigation {
    private RequesterCustomArrayAdapter adapterHelper;
    private ListView listView;
    private Task task;
    String type;
    //TODO both items below can be put in controller (project part 5)
    private TaskList taskList;


    /**
     * On creation of the activity, set the layout and populate the task list.
     * @param savedInstanceState The activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO Delete after project part 5 with persistence
        setContentView(R.layout.activity_requester_view_list);
        taskList = new TaskList();
        adapterHelper = new RequesterCustomArrayAdapter(this, taskList);
        listView = findViewById(R.id.tasklistRequester);
        listView.setAdapter(adapterHelper);
        //listView.setOnItemClickListener(clickListener);
        SyncController controller = new SyncController(getApplicationContext());
        ConnectedState c = ConnectedState.getInstance();
        Log.i("VIEWLISR", "onCreate: "+c.isOffline());
      //  if (!c.isOffline()){
    //        controller.resetRequested(username);
   //     }

        //get username

        //controller.resetRequested(username);
        type = setRequesterViewType();
        taskList.addAll(createList(type));
        checkOffline();
        adapterHelper.notifyDataSetChanged();


    }

    void checkOffline() {
        ConnectedState c = ConnectedState.getInstance();
        if(c.isOffline() && !(type.equals("Requested"))) {
            Offline fragment = new Offline();
            getSupportFragmentManager().beginTransaction().replace(R.id.requester_task_list_frame, fragment).commit();
        }
    }

    /**
     * Returns the task status which should be shown.
     * @return The task status
     */
    private String setRequesterViewType() {
        Bundle intent = getIntent().getExtras();
        String type = intent.getString("Status");
        setActivityTitleRequester("My " + type + " Tasks");
        return type;
    }


/*

    /**
     * This is an anonymous method to create a click listener for the listview rows. If the row
     * is selected, it packages up the task selected and the position to RequesterViewTask

    private AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener(){
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            Log.i("WEWE", "onItemClick: "+position);
            Task task = taskList.get(position);
            Intent intention = new Intent(RequesterViewListActivity.this, RequesterViewTaskActivity.class);
            Log.i("WEWE", "onItemClick: "+task.getUniqueID());
            intention.putExtra("task", task.getUniqueID());
            startActivity(intention);

        }

    }; */

    /**
     * The onclick method for viewing a task's details. Triggered by clicking on a task's title.
     * @param v The view object (the task's title)
     */
    public void titleClick(View v){
          /*
        final int position = listView.getPositionForView((View) v.getParent());
        Task task = taskList.get(position);
        Intent intention = new Intent(RequesterViewListActivity.this, RequesterViewTaskActivity.class);
        intention.putExtra("task", task.getUniqueID());
        startActivity(intention);
*/

        boolean success;
        ConnectedState c2 = ConnectedState.getInstance();
        c2.setOnline();
        SyncController controller = new SyncController(this);

        success = controller.sync();
        if (!success) {
            createSync();
        }

    }



    /**
     * Populates the taskList depending on the desired status.
     * @param type - a string representing the status of the task being displayed
     * @return taskList - a list of tasks that the requester has created.
     */
    private TaskList createList(String type) {
        TaskList taskList = null;
        if (type.equals("Assigned")) {
            taskList = serverHelper.getTasksRequester(username, "Assigned");
        }
        if (type.equals("Requested")) {
            taskList = serverHelper.getTasksRequester(username, "Requested");
        }
        if (type.equals("Bidded")) {
            taskList = serverHelper.getTasksRequester(username, "Bidded");
        }
        return taskList;
    }


    /**
     * Onclick method for the delete button; shows the deleteTaskDialog.
     * @param v The view object (the delete button)
     */
    public void deleteTask(View v){
        final int position = listView.getPositionForView((View) v.getParent());
        task = taskList.get(position);
        deleteTaskDialog();

    }

    /**
     * The onclick method for the edit task button; starts RequesterEditTaskActivity,
     * @param v The view object (the edit button).
     */
    public void editTask(View v){

        final int position = listView.getPositionForView((View) v.getParent());
        Task editTask = taskList.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("ID", editTask.getUniqueID());
        Intent intent = new Intent(RequesterViewListActivity.this, RequesterEditTaskActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    /**
     * A ConfirmDialogFragment that is shown when the user clicks the delete task button.
     */
    public void deleteTaskDialog(){
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        ConfirmDialog confirmDialog = new ConfirmDialog();
        Bundle args = new Bundle();
        args.putString("title", "Delete Task");
        args.putString("cancel", "Cancel");
        args.putString("confirm", "Yes");
        args.putString("dialogFlag", "Delete");
        args.putString("message", "Are you sure you want to delete this task?");

        confirmDialog.setArguments(args);
        confirmDialog.show(fm, "To Done");
    }



    /**
     * If the user confirms the task deletion, this deletes the task from the taskList and also from
     * the server.
     * @param confirmed Whether the user confirmed the deletion or cancelled.
     */
    @Override
    public void onFinishConfirmDialog(Boolean confirmed, String string) {
        if (confirmed){
            taskList.deleteTask(task);
            adapterHelper.notifyDataSetChanged();
            serverHelper.deleteTasks(task);
        }
    }
}
