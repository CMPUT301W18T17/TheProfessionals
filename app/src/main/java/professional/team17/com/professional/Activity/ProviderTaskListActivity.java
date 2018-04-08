
/*
 * ProviderTaskListActivity
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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import professional.team17.com.professional.Adapters.ItemClickSupport;
import professional.team17.com.professional.Adapters.ProviderListViewAdapter;
import professional.team17.com.professional.Dialogs.Offline;
import professional.team17.com.professional.R;
import professional.team17.com.professional.Controllers.TaskListController;
import professional.team17.com.professional.Helpers.TaskStatus;

/**
 *
 * An activity where the provider can see the tasks - with different ui depending
 * On the task status, and input
 * @author Allison
 * @see TaskListController
 */
public class ProviderTaskListActivity extends Navigation {
    private TaskListController taskListController;
    private RecyclerView recyclerView;

    /**
     * On creation of the activity, set all view objects
     * @param savedInstanceState The activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.provider_tasklist_view);
        taskListController = new TaskListController(this);
        taskListController.setType(getIntent().getExtras());
        taskListController.createList();

        if (taskListController.checkOffline()) {
            Offline fragment = new Offline();
            getSupportFragmentManager().beginTransaction().replace(R.id.provider_task_list_frame, fragment).commit();
        }

        recyclerView = (RecyclerView) findViewById(R.id.provider_taskList_view_list);
        recyclerView.setAdapter(new ProviderListViewAdapter(taskListController.tasklist));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Intent intention = new Intent(ProviderTaskListActivity.this, ProviderViewTask.class);
                intention = taskListController.findTask(position, intention);
                startActivity(intention);
            }
        });

        setActivityTitle();
    }

    /**
     * Sets the activity's title depending on the task status.
     */
    private void setActivityTitle() {
        if (TaskStatus.getInstance().isBidded()){
            setActivityTitleProvider("Bidded Tasks");
        } else if (TaskStatus.getInstance().isAssigned()){
            setActivityTitleProvider("Assigned Tasks");
        } else if (TaskStatus.getInstance().isDone()){
            setActivityTitleProvider("Completed Tasks");
        }
    }
}
