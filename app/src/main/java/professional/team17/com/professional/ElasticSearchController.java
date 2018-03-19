/*
 * ElasticSearchController
 *
 * March 5, 2018
 *
 * Copyright @ 2018 Team 17, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behaviour at the University of Alberta.
 * You can find a copy of the license in the github wiki for this project.
 */

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
import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;



/**
 * This class will handle the connection and async tasks to the es server
 */
public class ElasticSearchController {
    private static JestDroidClient client;
    private static String server = "http://cmput301.softwareprocess.es:8080";
    private static String indexname = "cmput301w18t17";
    private static String tasktype = "task";
    private static String profiletype = "profile";


    public void ElasticSearchController(){

    }
//TODO - all methods (not async should be placed in some other class at some point


    /**
     *
     * @param search - the jsons  query
     * @return
     */
    private TaskList getTaskList (String search){
        TaskList tasklist = null;
        JsonParser jsonParser = new JsonParser();
        Log.i("WRWR", "getTaskList: "+search);
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
     * @param username - the username to be matched against the task requester for each task
     * @param status - the status of the task
     * @return - TaskList of all tasks that match query
     */
    public TaskList getTasksRequester(String username, String status) {
        String search = "{ \"query\": {" +
                " \"bool\": {" +
                "\"must\": [ " +
                "{\"match\": {\"profileName\": \"" + username + "\"}}," +
                "{\"match\": {\"status\": \"" + status + "\"}}]}}}";
        return getTaskList(search);
    }




    /**
     *
     * @param username - the username to be matched against the task bidder username
     * @param status - the status of the task
     * @return - TaskList of all tasks that match query
     */
    public TaskList getTasksBidded(String username, String status) {;
        String search = "{ \"query\": {" +
                " \"bool\": {" +
                "\"must\": [ " +
                "{\"match\": {\"bids.name\": \"" + username + "\"}}," +
                "{\"match\": {\"status\": \"" + status + "\"}}]}}}";
        return getTaskList(search);
    }

    /**
     *
     * @param query - the list of words to be searched
     * @return - TaskList of all tasks that match ANY word in query and are
     * in either status bidded or requested
     *
     */
    public TaskList getSearch(String query) {
        String search =
                "{\"query\":{\"bool\":{\"must\":"+
                        "{\"match\":{\"description\":\"" + query+ "\"}}," +
                        "\"should\":[{\"match\":{\"status\":\"Requested\"}},"+
                        "{\"match\":{\"status\":\"Bidded\"}}]}}}";
        return getTaskList(search);
    }

    /**
     *
     * @param username - the username to be matched against the task bidder username
     * @return - TaskList of all tasks that match query, (in requested/bidded" and
     * NOT with the username
     */
    public TaskList getTasksSearch(String username) {
        String search =
                "{\"query\":{\"bool\":"+
                "{\"must_not\":{\"match\":{\"profileName\":\"" + username + "\"}}," +
                        "\"should\":[{\"match\":{\"status\":\"Requested\"}},"+
                        "{\"match\":{\"status\":\"Bidded\"}}]}}}";
        return getTaskList(search);
    }

    /**
     *
     * @param status - the task status to be matched against
     * @return - Tasklist - the results matched from elasticSearch
     */
    public TaskList getTasksStatus(String status) {
        String search = "{ \"query\": {\"match\" : { \"status\": \""+status+"\"  }} }";
        return getTaskList(search);
    }

    /**
     *
     * @param status - the task status to be matched against
     * @return - Tasklist - the results matched from elasticSearch
     */
    public TaskList getTasksStatus(String status, String username) {
        String search = "{ \"query\": {\"match\" : { \"status\": \""+status+"\"  }} }";
        return getTaskList(search);
    }


    /**
     *
     * @param profile - the name that will be matched against the task requester
     * @return   Tasklist - the results matched from elasticSearch
     */
    public TaskList getTasksUsername(String profile) {
        String search = "{ \"query\": {\"match\" : { \"profile\": \""+profile+"\"  }} }";
        return getTaskList(search);
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
     */
    public String addTasks(Task task) {
        String id;
        ElasticSearchController.AddTask addtask = new ElasticSearchController.AddTask();
        addtask.execute(task);
        try {
            id = addtask.get();
            task.setId(id);
            ElasticSearchController.UpdateTask updateTask = new ElasticSearchController.UpdateTask();
            updateTask.execute(task);
        }
        catch (Exception e){
            id = null;
        }
        //now that the id is set, we need to update it into the db
        //TODO RESEARCH BETTER WAY

        return id;
    }


    /**
     *
     * @param task - the task that will be added within the ES
     */
    public void updateTasks(Task task) {
        ElasticSearchController.UpdateTask updatetask = new ElasticSearchController.UpdateTask();
        updatetask.execute(task);

    }

    /**
     *
     * @param task - the task that will be deleted within the ES
     */
    public void deleteTasks(Task task) {
        ElasticSearchController.DeleteTask deletetask = new ElasticSearchController.DeleteTask();
        deletetask.execute(task);
    }

    /**
     *
     * @param profile - the profile that will be deleted within the ES (for testing purposes)
     */
    public void deleteProfile(Profile profile) {
        ElasticSearchController.DeleteProfile deleteprofile= new ElasticSearchController.DeleteProfile();
        deleteprofile.execute(profile);

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
            if (profile == null || username.isEmpty()) {
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
                            .index(indexname)
                            .type(profiletype)
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
            Get get = new Get.Builder(indexname, id[0])
                    .type(profiletype)
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
                        .index(indexname)
                        .type(tasktype)
                        .build();
                try {
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()){
                        id = result.getId();
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
                        .index(indexname)
                        .type(tasktype)
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
     *  This AsyncTask will update a task from the db based on a taskid
     */
    public static class DeleteProfile extends AsyncTask<Profile, Void, Void> {

        @Override
        protected Void doInBackground(Profile... profiles) {
            verifySettings();

            for (Profile profile : profiles) {
                Delete delete = new Delete.Builder(profile.getUserName())
                        .index(indexname)
                        .type(profiletype)
                        .build();
                try {
                    DocumentResult result = client.execute(delete);
                }
                catch (Exception e) {
                    Log.i("Error", "The application failed to build and delete the profile");

                }
            }
            return null;
        }
    }
    /**
     *  This AsyncTask will update a task from the db based on a taskid
     */
    public static class DeleteTask extends AsyncTask<Task, Void, Void> {

        @Override
        protected Void doInBackground(Task... tasks) {
            verifySettings();

            for (Task task : tasks) {
                Delete delete = new Delete.Builder(task.getUniqueID())
                        .index(indexname)
                        .type(tasktype)
                        .build();
                try {
                    DocumentResult result = client.execute(delete);
                }
                catch (Exception e) {
                    Log.i("Error", "The application failed to build and delete the task");

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
            Get get = new Get.Builder(indexname, id[0])
                    .type(tasktype)
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
        @Override
        protected TaskList doInBackground(String... search_para) {
            verifySettings();
            TaskList taskList = new TaskList();
            Search search = new Search.Builder(search_para[0])
                    // multiple index or types can be added.
                    .addIndex(indexname)
                    .addType(tasktype)
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
                DroidClientConfig.Builder builder = new DroidClientConfig.Builder(server);
                DroidClientConfig config = builder.build();

                JestClientFactory factory = new JestClientFactory();
                factory.setDroidClientConfig(config);
                client = (JestDroidClient) factory.getObject();
            }
        }

}
