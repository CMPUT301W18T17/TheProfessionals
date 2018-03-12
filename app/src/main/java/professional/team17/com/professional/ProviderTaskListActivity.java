package professional.team17.com.professional;

import android.content.Intent;
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
 * @see ElasticSearchController, ArrayAdapterSearchResults TaskLIst, Profile
 */
public class ProviderTaskListActivity extends ProviderLayout {
    private ArrayAdapterSearchResults adapterHelper;
    private ListView listView;

    //TODO both items below can be put in controller (project part 5)
    private TaskList taskList;
    private final ElasticSearchController elasticSearchController = new ElasticSearchController();

    //TODO below item is needed for protoype, part 5 persistence will remove this
    private Profile user;


    /**
     * On creation of the activity, set all view objects
     * @param savedInstanceState The activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO Delete after project part 5 with persistenct
        user = new Profile("John Smith", "john123", "johnSmith@email.ca", "123-4567");
        setContentView(R.layout.provider_tasklist_view);
        taskList = new TaskList();
        adapterHelper = new ArrayAdapterSearchResults(this, taskList);
        listView = findViewById(R.id.provider_taskList_view_list);
        listView.setAdapter(adapterHelper);
        listView.setOnItemClickListener(clickListener);
        String type = setProviderViewType();
        createList(type);
        taskList.addAll(createList(type));
        adapterHelper.notifyDataSetChanged();

    }

    /**
     * This is an anonymous method to create a click listener for the listview rows. If the row
     * is selected, it packages up the task selected and the position to ProviderViewTask
     */
    private AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener(){
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            Task task = taskList.get(position);
            Intent intention = new Intent(ProviderTaskListActivity.this, ProviderViewTask.class);
            intention.putExtra("Task", task.getUniqueID());
            intention.putExtra("profile", user);
            startActivity(intention);

        }

    };


    /**
     *
     * @param type - a string representing the status of the task being displayed
     * @return tasklist - a list of tasks of either the tasks assigned to the user,
     * of, tasks the user has bidded on.
     */
    private TaskList createList(String type) {
        TaskList taskList =null;
        if (type.equals("Bidded")) {
            taskList = elasticSearchController.getTasksBidded("john123", "Bidded");
        }
        if (type.equals("Assigned")) {
            taskList = elasticSearchController.getTasksBidded("john123", "Assigned");
        }
        return taskList;
    }

    //activity will pass flag into this 1 = my bids, 0 = assigned

    /**
     *
     * @return type - a string representing the type of lists the user wishes to see
     * the types of lists are either: 1) Assigned -  the tasks assigned to the user,
     * or 2) Bidded - tasks the user has bidded on.
     */
    private String setProviderViewType() {
        Bundle intent = getIntent().getExtras();
        String type = intent.getString("Status");
        return type;
    }

}
