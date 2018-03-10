package professional.team17.com.professional;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;


import org.apache.commons.lang3.ObjectUtils;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.searchbox.client.JestResult;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * Created by ag on 2018-02-22.
 */

/**
* TODO Below is build with assumption of two indexes (one for profile, one for tasks)
 * We could also potentially combine profile and tasks into one completely denormalized index
 * http://cmput301.softwareprocess.es:8080/cmput301w18t17
 */


public class ElasticSearchController {
    private static JestDroidClient client;
    private static String server = "http://cmput301.softwareprocess.es:8080";
    private static String task = "http://cmput301.softwareprocess.es:8080/CMPUT301W18T17/task"; //TODO COMPLETE name


//add profile and update via the username
        public static class AddProfile extends AsyncTask<Profile, Void, Void> {

            @Override
            protected Void doInBackground(Profile... profiles) {
                verifySettings();

                for (Profile profile : profiles) {
                    Index index = new Index.Builder(profile).index("cmput301w18t17").type("profile").id(profile.getUserName()).build();

                    try {
                        DocumentResult result = client.execute(index);
                    }
                    catch (Exception e) {
                        Log.i("Error", "The application failed to build and send the profile");

                    }
                }
                return null;
            }
        }


    public static class GetProfile extends AsyncTask<String, Void, Profile> {
        //TODO Complete
        @Override
        protected Profile doInBackground(String... id) {
            verifySettings();
            Profile profile = null;
            Get get = new Get.Builder("cmput301w18t17", id[0]).type("profile").build();

            try {
                JestResult result = client.execute(get);
                if (result.isSucceeded()) {
                    profile = result.getSourceAsObject(Profile.class);

                }
                else{
                Log.i("error", "Search query failed to find any thing =/");
            }
        }

            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            return profile;
        }

    }

    //  Used to add a task. Will update the task id with the id set by ES. After this, please call update Task, to
    //ensure the object holds the id.
//      ElasticSearchController.AddTask addTask = new ElasticSearchController.AddTask();
//      addTask.execute(task); (or taskid)
    public static class AddTask extends AsyncTask<Task, Void, String> {

        @Override
        protected String doInBackground(Task... tasks) {
            verifySettings();
            String id = null;

            for (Task task : tasks) {
                Index index = new Index.Builder(task).index("cmput301w18t17").type("task").build();
                try {
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()){
                        id = result.getId();
                        task.setId(result.getId());
                    }
                    else {
                        Log.i ("Error", "some error = (");
                    }
                }
                catch (Exception e) {
                    Log.i("Error", "The application failed to build and add the task");
                }
            }
            return id;
        }
    }

    //  Used to update a task (Must have ID). Must be called after adding a task, as we need to set the id in the es
//      ElasticSearchController.UpdateTask updateTask = new ElasticSearchController.UpdateTask();
//      updateTask.execute(task); (or taskid)
    public static class UpdateTask extends AsyncTask<Task, Void, Void> {

        @Override
        protected Void doInBackground(Task... tasks) {
            verifySettings();

            for (Task task : tasks) {
                Index index = new Index.Builder(task).index("cmput301w18t17").type("task").id(task.getUniqueID()).build();

                try {
                    DocumentResult result = client.execute(index);
                }
                catch (Exception e) {
                    Log.i("Error", "The application failed to build and update the task");

                }
            }
            return null;
        }
    }
//      Used to get task based on a taskid.
//      ElasticSearchController.GetTask getTask = new ElasticSearchController.GetTask();
//      getTask.execute(task.getUniqueID()); (or taskid)
//      task = getTask.get();
    public static class GetTask extends AsyncTask<String, Void, Task> {
        //TODO Complete
        @Override
        protected Task doInBackground(String... id) {
            verifySettings();
            Task task = null;

            Get get = new Get.Builder("cmput301w18t17", id[0]).type("task").build();

            try {
                JestResult result = client.execute(get);
                if (result.isSucceeded()) {
                    task = result.getSourceAsObject(Task.class);
                    task.setId(result.getValue("_id").toString());
                }
                else{
                    Log.i("error", "Search query failed to find any thing =/");
                }
            }

            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            return task;
        }

    }

    //      Used to get task based on a taskid.
//      ElasticSearchController.GetTask getTask = new ElasticSearchController.GetTask();
//      getTask.execute(task.getUniqueID()); (or taskid)
//      task = getTask.get();
    public static class GetTasks extends AsyncTask<String, Void, TaskList> {
        //TODO Complete
        @Override
        protected TaskList doInBackground(String... search_para) {
            verifySettings();
            TaskList taskList = new TaskList();

            //parse into template
            String search1 = "{ \"query\": {\"match\" : { \"status\": \"term1\"  }} }";
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = (JsonObject)jsonParser.parse(search1);
            jsonObject.getAsJsonObject("query").getAsJsonObject("match").addProperty("status", search_para[0]);



            Search search = new Search.Builder(jsonObject.toString())
                    // multiple index or types can be added.
                    .addIndex("cmput301w18t17")
                    .addType("task")
                    .build();

            try {
                SearchResult result = client.execute(search);
                if (result.isSucceeded()){
                    List<Task> foundTask= result.getSourceAsObjectList(Task.class);
                    taskList.addAll(foundTask);
                }
                else {
                    Log.i ("error","Search query failed to find any thing =/");
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            return taskList;
        }
    }

        public static void verifySettings() {
            if (client == null) {
                DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080");
                DroidClientConfig config = builder.build();

                JestClientFactory factory = new JestClientFactory();
                factory.setDroidClientConfig(config);
                client = (JestDroidClient) factory.getObject();
            }
        }

}
