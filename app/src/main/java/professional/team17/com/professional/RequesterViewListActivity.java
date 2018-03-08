package professional.team17.com.professional;

import android.content.Context;
import android.content.Intent;
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

public class RequesterViewListActivity extends AppCompatActivity {
    /**
     * where can i get the profile name
     */
    private ListView taskL;
    private static final String FILENAME = "tasks.sav";
    /**
     * what the real thing is here for our customArrayAdapter
     *  MyCustomAdapter adapter = new MyCustomAdapter(list, this);
     *  should be like this.
     *  in order to handle our
     *  for now we use the formal one to instead of it.
     */
    private RequesterCustomArrayAdapter adapter;
    private TaskList taskList;
    private Task task1;
    private String name;
    private String profileName = "123";
    private String location;
    private String description;
    private Date date;
    private String dateTime;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requester_view_list);
        taskL = (ListView) findViewById(R.id.tasklist);
        /**
         * this part is for edit or delete the existing task by clicking the items on the screem.
         * however since we didnt use the usual arrayAdapter
         * we need to fix this by create a new customed one.
         */
        taskL.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // your arraylist ot array to remove item

                task1 = taskList.get(position);
                name = task1.getName();
                //String VS Date
                date = task1.getDate();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                dateTime = dateFormat.format(date);
                description = task1.getDescription();
                location = task1.getLocation();
                Intent intent = new Intent(getApplicationContext(), RequesterViewTaskActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("string1", name);
                bundle.putString("string2", dateTime);
                bundle.putString("string3", description);
                bundle.putString("string4", location);
                intent.putExtras(bundle);

                startActivityForResult(intent, 0);

            }

        });

    }

    /**
     * get userinput by click the bottom-right button!
     * will be called by the button!
     * @param view
     */
    public void toTheNext(View view) {
        Intent intent = new Intent(this, RequesterAddTaskActivity.class);
        startActivityForResult(intent, 0);
    }
    /**
     * this part is for receiving information from other activities, use RESULT_OK(FROM lonelyTwitter to set up the gate)
     * and assign these value properly and combine them into the Task-obj!
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RequesterAddTaskActivity.RESULT_OK){
            super.onActivityResult(requestCode, resultCode, data);
            Bundle bundle = data.getExtras();
            String name = bundle.getString("name");
            String year = bundle.getString("date");
            String dtStart = year;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            try {
                date = format.parse(dtStart);
                System.out.println(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String description = bundle.getString("description");
            String location = bundle.getString("location");
            int id1 = bundle.getInt("id");
            String ID = Integer.toString(id1);
            // should i generate an id in the addTask?
            //change year from String to Date? or just let the date be String.
            Task task = new Task(profileName, name, description, location, date, ID);
            //System.out.println("------------------------------------------------------");
            //System.out.println(task);
            taskList.addTask(task);
            adapter.notifyDataSetChanged();
            //System.out.println(taskList);
            //System.out.println("------------------------------------------------------");
            saveInFile();
        }
        else if (resultCode == RequesterEditTaskActivity.RESULT_OK){
            super.onActivityResult(requestCode, resultCode, data);
            Bundle bundle = data.getExtras();
            String name = bundle.getString("name");
            String year = bundle.getString("date");
            String dtStart = year;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            try {
                date = format.parse(dtStart);
                System.out.println(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String description = bundle.getString("description");
            String location = bundle.getString("location");
            int id1 = bundle.getInt("id");
            String ID = Integer.toString(id1);
            // should i generate an id in the addTask?
            //change year from String to Date? or just let the date be String.
            task1.setProfileName(profileName);
            task1.setName(name);
            task1.setDescription(description);
            task1.setDate(date);
            task1.setLocation(location);
            task1.setUniqueID(ID);
            adapter.notifyDataSetChanged();
            //System.out.println(taskList);
            //System.out.println("------------------------------------------------------");
            saveInFile();
        }

    }




    @Override
    protected void onStart() {

        // TODO Auto-generated method stub
        super.onStart();
        Log.i("LifeCycle --->", "onStart is called");
        //System.out.println("------------------------------------------------------");
        //System.out.println("lol");
        //System.out.println("------------------------------------------------------");

        loadFromFile();
        adapter = new RequesterCustomArrayAdapter(this, taskList);
        taskL.setAdapter(adapter);



    }

    /**
     * the following parts should be replaced later by gason helper!!!
     */
    private void loadFromFile() {

        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            // Taken https://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            // 2018-01-23
            Type listType = new TypeToken<TaskList>(){}.getType();
            taskList = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            taskList = new TaskList();
        }

    }

    private void saveInFile() {
        try {

            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(taskList, out);
            //System.out.println("------------------------------------------------------");
            //System.out.println("dota");
            // System.out.println("------------------------------------------------------");
            out.flush();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }
}
