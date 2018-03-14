package professional.team17.com.professional;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RequesterViewListActivity extends RequesterLayout {
    private RequesterCustomArrayAdapter adapterHelper;
    private ListView listView;
    private String username;
    private SharedPreferences sharedpreferences;
    //TODO both items below can be put in controller (project part 5)
    private TaskList taskList;
    private final ElasticSearchController elasticSearchController = new ElasticSearchController();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO Delete after project part 5 with persistenct
        setContentView(R.layout.activity_requester_view_list);
        taskList = new TaskList();
        adapterHelper = new RequesterCustomArrayAdapter(this, taskList);
        listView = findViewById(R.id.tasklistRequester);
        listView.setAdapter(adapterHelper);
        listView.setOnItemClickListener(clickListener);


        //get username
        sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        username = sharedpreferences.getString("username", "error");


        String type = setRequesterViewType();
        createList(type);

        taskList.addAll(createList(type));
        adapterHelper.notifyDataSetChanged();


    }

    private String setRequesterViewType() {
        Bundle intent = getIntent().getExtras();
        String type = intent.getString("Status");
        setActivityTitle("My"+type+"Tasks");
        return type;
    }




    /**
     * This is an anonymous method to create a click listener for the listview rows. If the row
     * is selected, it packages up the task selected and the position to RequesterViewTask
     */
    private AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener(){
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            Task task = taskList.get(position);
            Intent intention = new Intent(RequesterViewListActivity.this, RequesterViewTaskActivity.class);
            intention.putExtra("Task", task.getUniqueID());
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
        TaskList taskList = null;
        if (type.equals("Assigned")) {
            taskList = elasticSearchController.getTasksRequester(username, "Assigned");
            Log.i("boukll", "createList: "+taskList+username);
        }
        if (type.equals("Requested")) {
            taskList = elasticSearchController.getTasksRequester(username, "Requested");
        }
        if (type.equals("Bidded")) {
            taskList = elasticSearchController.getTasksRequester(username, "Bidded");
        }
        Log.i("WTWT", "createList: "+taskList);
        return taskList;
    }



}
