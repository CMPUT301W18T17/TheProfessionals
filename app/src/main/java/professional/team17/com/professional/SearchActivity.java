package professional.team17.com.professional;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;


/**
 *
 * An activity where the provider can search for tasks in either requested or bidded states ('open to bidding')
 * On default it will display the most recently requested tasks/
 *
 *
 * @author Allison
 * @see ProviderCustomArrayAdapter , TaskList, ElasticSearchController
 */
public class SearchActivity extends ProviderLayout {
    private ProviderCustomArrayAdapter searchAdapterHelper;
    private ListView listView;
    private SearchView searchView;
    private TaskList taskList;
    private final ElasticSearchController elasticSearchController = new ElasticSearchController();
    private String username;
    private SharedPreferences sharedpreferences;


    /**
     * On creation of the activity, set all view objects and onClickListeners.
     * @param savedInstanceState The activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        /* Change activity title */
        this.setActivityTitle("Task Search");

        /*testing sharedPreferences
        SharedPreferences sharedpreferences = getSharedPreferences("MyPref",
                Context.MODE_PRIVATE);
        String test = sharedpreferences.getString("username", "not working");
        this.setActivityTitle(test);
        */

        taskList = new TaskList();
        searchAdapterHelper = new ProviderCustomArrayAdapter(this, taskList);
        listView =findViewById(R.id.provider_taskList_view_list);
        listView.setAdapter(searchAdapterHelper);
        listView.setOnItemClickListener(clickListener);

        //initial list with open results (requested or bidded)
        taskList.addAll(getOpenTasks());

        //initialize search input
        searchView = (SearchView) findViewById(R.id.Search_Activity_Input);
        searchView.setQueryHint("Enter search");

        sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        username = sharedpreferences.getString("username", "error");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, "SEARCH ENTERED"+query, duration);
                toast.show();
                search(query);
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
     * This will search the server for a match against the search input,
     * and update the tasklist with the results. If there are no results, it
     * will display an empty message.
     * @param query - the string representing the task being searched for
     */
    private void search(String query) {
        /*
        //TODO implement query builder for search in elasticserch
        TaskList temp = new TaskList();
        temp = elasticSearchController.getTasksStatus(query);
        taskList.clear();
        taskList.addAll(temp);
        searchAdapterHelper.notifyDataSetChanged();
        if (temp == null || temp.isEmpty()){
            notifyEmptyResults();
        }
        */
    }

    /**
     * This will display a message that no results were found.
     */
    private void notifyEmptyResults(){
        /*
        display no results message
         */
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
            startActivity(intention);
        }

    };


    /**
     *
     * @return tasklist - all the tasks in either bidded or requested state
     */
    private TaskList getOpenTasks() {
        TaskList tasklist = new TaskList();
        tasklist = elasticSearchController.getTasksStatus("Bidded");
        return tasklist;
    }
}