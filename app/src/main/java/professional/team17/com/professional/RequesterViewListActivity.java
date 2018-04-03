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
    private TaskListController taskListController;

    /**
     * On creation of the activity, set the layout and populate the task list.
     * @param savedInstanceState The activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requester_view_list);

        taskListController = new TaskListController(this);
        taskListController.setType(getIntent().getExtras());
        taskListController.createListRequester();

        if (taskListController.checkOffline() && !TaskStatus.getInstance().isRequested()){
            Offline fragment = new Offline();
            getSupportFragmentManager().beginTransaction().replace(R.id.requester_task_list_frame, fragment).commit();
        }

        adapterHelper = new RequesterCustomArrayAdapter(this, taskListController.tasklist);
        listView = findViewById(R.id.tasklistRequester);
        listView.setAdapter(adapterHelper);

        adapterHelper.notifyDataSetChanged();

    }

    /**
     * The onclick method for viewing a task's details. Triggered by clicking on a task's title.
     * @param v The view object (the task's title)
     */
    public void titleClick(View v){
        Intent intention = new Intent(RequesterViewListActivity.this, RequesterViewTaskActivity.class);
        final int position = listView.getPositionForView((View) v.getParent());
        intention = taskListController.findTask(position, intention);
        startActivity(intention);
    }

    /**
     * Onclick method for the delete button; shows the deleteTaskDialog.
     * @param v The view object (the delete button)
     */
    public void deleteTask(View v){
        final int position = listView.getPositionForView((View) v.getParent());
        taskListController.setTask(position);
        deleteTaskDialog();
    }

    /**
     * The onclick method for the edit task button; starts RequesterEditTaskActivity,
     * @param v The view object (the edit button).
     */
    public void editTask(View v){
        final int position = listView.getPositionForView((View) v.getParent());
        Intent intention = new Intent(RequesterViewListActivity.this, RequesterEditTaskActivity.class);
        intention = taskListController.findTask(position, intention);
        startActivity(intention);
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
            taskListController.delete();
            adapterHelper.notifyDataSetChanged();
        }
    }
}
