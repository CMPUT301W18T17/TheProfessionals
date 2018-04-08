/*
 * RequesterViewListActivity
 *
 * March 14, 2018
 *
 * Copyright @ 2018 Team 17, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behaviour at the University of Alberta.
 * You can find a copy of the license in the github wiki for this project.
 */

package professional.team17.com.professional.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;

import professional.team17.com.professional.Dialogs.ConfirmDialog;
import professional.team17.com.professional.Dialogs.Offline;
import professional.team17.com.professional.R;
import professional.team17.com.professional.Adapters.RequesterCustomArrayAdapter;
import professional.team17.com.professional.Entity.TaskList;
import professional.team17.com.professional.Controllers.TaskListController;
import professional.team17.com.professional.Helpers.TaskStatus;

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
    private RecyclerView recyclerView;

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

        setActivityTitle();

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
        confirmDialog.show(fm, "Delete Task");
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

    /**
     * Sets the activity's title depending on the task status.
     */
    private void setActivityTitle() {
        if (TaskStatus.getInstance().isRequested()) {
            setActivityTitleRequester("My Requested Tasks");
        } else if (TaskStatus.getInstance().isBidded()){
            setActivityTitleRequester("My Bidded Tasks");
        } else if (TaskStatus.getInstance().isAssigned()){
            setActivityTitleRequester("My Assigned Tasks");
        } else if (TaskStatus.getInstance().isDone()){
            setActivityTitleRequester("My Completed Tasks");
        }
    }
}
