package professional.team17.com.professional;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.Collection;

public class SearchActivity extends ProviderLayout {
    private ArrayAdapterSearchResults searchAdapterHelper;
    private ListView listView;
    private SearchView searchView;
    private TaskList taskList;

    //TODO DELETE
    private Profile user = new Profile("John Smith", "john123", "johnSmith@email.ca", "123-4567");
    private final ElasticSearchController elasticSearchController = new ElasticSearchController();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        taskList = new TaskList();
        searchAdapterHelper = new ArrayAdapterSearchResults(this, taskList);
        listView =findViewById(R.id.provider_taskList_view_list);
        listView.setAdapter(searchAdapterHelper);
        listView.setOnItemClickListener(clickListener);

        //taskList = elasticSearchController.getTasksBidded("john123", "Bidded");
        taskList.addAll(getOpenTasks());


        //adapterHelper.notifyDataSetChanged();


        /* Change activity title */
        this.setActivityTitle("Task Search");


        searchView = (SearchView) findViewById(R.id.Search_Activity_Input);
        searchView.setQueryHint("Enter search");



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, "SEARCH ENTERED"+query, duration);
                toast.show();
                searchView.clearFocus(); //remove focus on submit
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }


    /**
     * This is an anonymous method to create a click listener for the listview rows. If the row
     * is selected, it packages up the task selected and the position to ViewTaskBidded
     */
    private AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener(){
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            Task task = taskList.get(position);
            Intent intention = new Intent(SearchActivity.this, ProviderViewTask.class);
            intention.putExtra("Task", task.getUniqueID());
            intention.putExtra("profile", user);
            startActivity(intention);

        }

    };






    public TaskList getOpenTasks() {
        TaskList tasklist = new TaskList();
        tasklist = elasticSearchController.getTasksStatus("Requested");
        return tasklist;
    }
}
