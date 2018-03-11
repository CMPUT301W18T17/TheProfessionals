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

//TODO - all methods (not async should be placed in some other class at some point
    /**
     *
     * @param username - the username to be matched against the task requester for each task
     * @param status - the status of the task
     * @return - TaskList of all tasks that match query
     */
    public TaskList getTasksRequester(String username, String status) {
        TaskList tasklist = null;
        String search = "{ \"query\": {" +
                " \"bool\": {" +
                "\"must\": [ " +
                "{\"match\": {\"profileName\": \"" + username + "\"}}," +
                "{\"match\": {\"status\": \"" + status + "\"}}]}}}";
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse(search);
        ElasticSearchController.GetTasks getTasks = new ElasticSearchController.GetTasks();
        getTasks.execute(jsonObject.toString());
        try {
            tasklist = getTasks.get();
        } catch (Exception e) {

        }
        return tasklist;
    }

    /**
     *
     * @param username - the username to be matched against the task bidder username
     * @param status - the status of the task
     * @return - TaskList of all tasks that match query
     */
    public TaskList getTasksBidded(String username, String status) {
        TaskList tasklist = null;
        String search = "{ \"query\": {" +
                " \"bool\": {" +
                "\"must\": [ " +
                "{\"match\": {\"bids.name\": \"" + username + "\"}}," +
                "{\"match\": {\"status\": \"" + status + "\"}}]}}}";
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse(search);
        ElasticSearchController.GetTasks getTasks = new ElasticSearchController.GetTasks();
        getTasks.execute(jsonObject.toString());
        try {
            tasklist = getTasks.get();
        } catch (Exception e) {
        }
        return tasklist;
    }

    /**
     *
     * @param status - the task status to be matched against
     * @return - Tasklist - the results matched from elasticSearch
     */
    public TaskList getTasksStatus(String status) {
        TaskList tasklist = null;
        String search = "{ \"query\": {\"match\" : { \"status\": \""+status+"\"  }} }";
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse(search);
        ElasticSearchController.GetTasks getTasks = new ElasticSearchController.GetTasks();
        getTasks.execute(jsonObject.toString());
        try {
            tasklist = getTasks.get();
        } catch (Exception e) {

        }
        return tasklist;
    }

    /**
     *
     * @param profile - the name that will be matched against the task requester
     * @return   Tasklist - the results matched from elasticSearch
     */
    public TaskList getTasksUsername(String profile) {
        TaskList tasklist = null;
        String search = "{ \"query\": {\"match\" : { \"profile\": \""+profile+"\"  }} }";
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse(search);
        Log.i("werwer", "jsonObject" + jsonObject);
        ElasticSearchController.GetTasks getTasks = new ElasticSearchController.GetTasks();
        getTasks.execute(jsonObject.toString());
        try {
            tasklist = getTasks.get();
            Log.i("WEEW", "providerGetTasks: " + tasklist.toString());
        } catch (Exception e) {

        }
        return tasklist;
    }


    /**
     * @param taskid - the unique id being matched against by task
     * @return Task task - the task returned from the server that matches, null otherwise
     */
    public Task getTask(String taskid) {
        Task task= null;
        ElasticSearchController.GetTask gettask = new ElasticSearchController.GetTask();
        gettask.execute(taskid);
        try {
            task = gettask.get();
        } catch (Exception e) {
            return null;
        }
        return task;
    }


    /**
     *
     * @param task - the task that will be added/updated within the ES
     */
    public void addTasks(Task task) {
        ElasticSearchController.AddTask addtask = new ElasticSearchController.AddTask();
        addtask.execute(task);

        //now that the id is set, we need to update it into the db
        //TODO RESEARCH BETTER WAY
        ElasticSearchController.UpdateTask updateTask = new ElasticSearchController.UpdateTask();
        updateTask.execute(task);
    }


    /**
     * @param username - the username to be searched for in the server
     * @return Profile profile  - the profile matching the user name, or null if no match
     */
    public Profile getProfile(String username) {
        Profile profile = null;
        ElasticSearchController.GetProfile getprofile = new ElasticSearchController.GetProfile();
        getprofile.execute(username);
        try {
            profile = getprofile.get();
        } catch (Exception e) {
            return null;
        }
        return profile;
    }


    /**
     * @param profile - the profile to be added
     * @return Boolean value - true means profile was added successfully, false means unsuccessful
     */
    public Boolean addProfile(Profile profile) {

        boolean result;
        ElasticSearchController.AddProfile addProfile = new ElasticSearchController.AddProfile();
        addProfile.execute(profile);
        try {
            result = addProfile.get();
        } catch (Exception e) {
            return false;
        }
        return result;
    }


    /**
     * @param username - the username that is being looked for in the db
     * @return boolean value, true meaning the username does exist, false otherwise
     */
    public Boolean profileExists(String username) {
        boolean result = true;
        ElasticSearchController.GetProfile getProfile = new ElasticSearchController.GetProfile();
        getProfile.execute(username);
        try {
            Profile profile = getProfile.get();
            // return false if no profile found
            if (profile != null || username.isEmpty()) {
                result = false;
            }
        } catch (Exception e) {
            result = false;
        }
        return result;
    }








    /**
     * This AsyncTask will add a profile to the db (can also be used to update profile)
     */
        public static class AddProfile extends AsyncTask<Profile, Void, Boolean> {

            @Override
            protected Boolean doInBackground(Profile... profiles) {
                verifySettings();
                Boolean success = true;
                for (Profile profile : profiles) {
                    Index index = new Index.Builder(profile)
                            .index("cmput301w18t17")
                            .type("profile")
                            .id(profile.getUserName())
                            .build();

                    try {
                        DocumentResult result = client.execute(index);
                    }
                    catch (Exception e) {
                        Log.i("Error", "The application failed to build and send the profile");
                        success = false;

                    }
                }
                return success;
            }
        }

    /**
     * This AsyncTask will retrieve profile from the db matching a username.
     */
    public static class GetProfile extends AsyncTask<String, Void, Profile> {
        @Override
        protected Profile doInBackground(String... id) {
            verifySettings();
            Profile profile = null;
            Get get = new Get.Builder("cmput301w18t17", id[0])
                    .type("profile")
                    .build();

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


    /**
     *  This AsyncTask will add a task to the db.
     *  It will then set the task.id equal to the db.id set at time of insertion.
     *
     */
    public static class AddTask extends AsyncTask<Task, Void, String> {

        @Override
        protected String doInBackground(Task... tasks) {
            verifySettings();
            String id = null;

            for (Task task : tasks) {
                Index index = new Index.Builder(task)
                        .index("cmput301w18t17")
                        .type("task")
                        .build();
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


    /**
     *  This AsyncTask will update a task from the db based on a taskid
     */
    public static class UpdateTask extends AsyncTask<Task, Void, Void> {

        @Override
        protected Void doInBackground(Task... tasks) {
            verifySettings();

            for (Task task : tasks) {
                Index index = new Index.Builder(task)
                        .index("cmput301w18t17")
                        .type("task")
                        .id(task.getUniqueID())
                        .build();

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


    /**
     *  This AsyncTask will retrieve a task from the db based on a taskid
     */
    public static class GetTask extends AsyncTask<String, Void, Task> {
        //TODO Complete
        @Override
        protected Task doInBackground(String... id) {
            verifySettings();
            Task task = null;

            Get get = new Get.Builder("cmput301w18t17", id[0])
                    .type("task")
                    .build();

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

    /**
     * This async task will return a list of task dependent on the preformatted query
     */
    public static class GetTasks extends AsyncTask<String, Void, TaskList> {
        //TODO Complete
        @Override
        protected TaskList doInBackground(String... search_para) {
            verifySettings();
            TaskList taskList = new TaskList();

            Search search = new Search.Builder(search_para[0])
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
