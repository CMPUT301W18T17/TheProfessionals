
/*
 * ProviderTaskListActivity
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
import android.widget.AdapterView;
import android.widget.ListView;

/**
 *
 * An activity where the provider can see the tasks - with different ui depending
 * On the task status, and input
 * @author Allison
 * @see ServerHelper , ProviderCustomArrayAdapter TaskLIst, Profile
 */
public class ProviderTaskListActivity extends Navigation {
    private ProviderCustomArrayAdapter adapterHelper;
    private ListView listView;
    private TaskListController taskListController;

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
        adapterHelper = new ProviderCustomArrayAdapter(this, taskListController.tasklist);
        listView = findViewById(R.id.provider_taskList_view_list);
        listView.setAdapter(adapterHelper);
        listView.setOnItemClickListener(clickListener);
        adapterHelper.notifyDataSetChanged();

        if (taskListController.checkOffline()){
            Offline fragment = new Offline();
            getSupportFragmentManager().beginTransaction().replace(R.id.provider_task_list_frame, fragment).commit();
        }
    }

    /**
     * This is an anonymous method to create a click listener for the listview rows. If the row
     * is selected, it packages up the task selected and the position to ProviderViewTask
     */
    private AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener(){
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            Intent intention = new Intent(ProviderTaskListActivity.this, ProviderViewTask.class);
            intention = taskListController.findTask(position, intention);
            startActivity(intention);
        }

    };

}
