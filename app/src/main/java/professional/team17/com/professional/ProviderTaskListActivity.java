package professional.team17.com.professional;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class ProviderTaskListActivity extends ProviderLayout {
    private ArrayAdapterSearchResults adapterHelper;
    private ListView listView;
    private TaskList taskList;
    private Profile user;
    private final ElasticSearchController elasticSearchController = new ElasticSearchController();
    //TODO DELETE
    private TaskList dummyTaskList;

    //TODO DELETE METHOD
    public void dummyDate(){
        dummyTaskList = new TaskList();

        Task task1 = new Task("ProfileName1", "Name1", "Description1", "Location1","ID1" );
        Task task2 = new Task("ProfileName2", "Name2", "Description2", "Location2","ID2" );
        Task task3 = new Task("ProfileName3", "Name3", "Description3", "Location3","ID3" );
        dummyTaskList.addTask(task1);
        dummyTaskList.addTask(task2);
        dummyTaskList.addTask(task3);
        task1.addBid(new Bid("john123", 234.3));
        task1.addBid(new Bid("ProfileName4", 23.40));
        task2.addBid(new Bid("ProfileName4", 277.40));

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = new Profile("John Smith", "john123", "johnSmith@email.ca", "123-4567");
        setContentView(R.layout.provider_tasklist_view);
        taskList = new TaskList();
        adapterHelper = new ArrayAdapterSearchResults(this, taskList);
        listView = findViewById(R.id.provider_taskList_view_list);
        listView.setAdapter(adapterHelper);
        listView.setOnItemClickListener(clickListener);
        String type = setProviderViewType();
        createList(type);
        //taskList = elasticSearchController.getTasksBidded("john123", "Bidded");
        taskList.addAll(createList(type));
        //adapterHelper.notifyDataSetChanged();

    }



    /**
     * This is an anonymous method to create a click listener for the listview rows. If the row
     * is selected, it packages up the task selected and the position to ViewTaskBidded
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



    //TODO this will fill results from search (may need to take parameters in bundle)
    public TaskList getTasks() {
        dummyDate();
        return dummyTaskList;
    }

    //This updates the adapter with the results
    public void displayResults(){
        taskList.clear();
        taskList.addAll(getTasks());
        adapterHelper.notifyDataSetChanged();
    }

    private TaskList createList(String type) {
        TaskList taskList =null;
        Log.i("DOUR", "createList: "+type);

        if (type.equals("Bidded")) {
            taskList = elasticSearchController.getTasksBidded("john123", "Bidded");
            Log.i("DOUR", "createList: "+taskList);

        }

        if (type.equals("Assigned")) {
            //get assigned list from es
        }
       // dummyDate();
       // taskList = dummyTaskList;


        return taskList;
    }

    //activity will pass flag into this 1 = my bids, 0 = assigned
    private String setProviderViewType() {

        Bundle intent = getIntent().getExtras();
        String type = intent.getString("Status");
        return type;
    }

}
