package professional.team17.com.professional;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

public class Search_Activity extends AppCompatActivity {
    private ArrayAdapterSearchResults searchAdapterHelper;
    private ListView listView;
    private SearchView searchView;
    private TaskList taskList;

    //TODO DELETE
    private TaskList dummyTaskList;

    //TODO DELETE METHOD
    public void dummyDate(){
        dummyTaskList = new TaskList();
        Task task1 = new Task("ProfileName1", "Name1", "Description", "Location1","ID1" );
        Task task2 = new Task("ProfileName2", "Name2", "Description", "Location2","ID2" );
        Task task3 = new Task("ProfileName3", "Name3", "Description", "Location3","ID3" );
        dummyTaskList.addTask(task1);
        dummyTaskList.addTask(task2);
        dummyTaskList.addTask(task3);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        taskList = new TaskList();
        searchAdapterHelper = new ArrayAdapterSearchResults(this, taskList);
        listView =findViewById(R.id.Search_Activity_list);
        listView.setAdapter(searchAdapterHelper);
        listView.setOnItemClickListener(clickListener);


        searchView = (SearchView) findViewById(R.id.Search_Activity_Input);
        searchView.setQueryHint("Enter search");



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, "SEARCH ENTERED"+query, duration);
                toast.show();
                displayResults();

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
            TaskList taskList = dummyTaskList;
            Task task = taskList.get(position);
            Intent intention = new Intent(Search_Activity.this, MainActivity.class);
            intention.putExtra("Task", task);
            intention.putExtra("position", position);
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
        searchAdapterHelper.notifyDataSetChanged();
    }


}