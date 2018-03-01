package professional.team17.com.professional;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Date;

public class Search_Activity extends AppCompatActivity {
    private ArrayAdapterSearchResults searchAdapterHelper;
    private ListView listView;

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

    //TODO this will fill results from search (may need to take parameters in bundle)
    public void getTasks() {
        dummyDate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_);
        getTasks();
        searchAdapterHelper = new ArrayAdapterSearchResults(this, dummyTaskList);
        listView =findViewById(R.id.Search_Activity_list);
        listView.setAdapter(searchAdapterHelper);
        listView.setOnItemClickListener(clickListener);
    }


    /**
     * This is an anonymous method to create a click listener for the listview rows. If the row
     * is selected, it packages up the subscription selected and the position to EditSubscriptActivity
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
}
