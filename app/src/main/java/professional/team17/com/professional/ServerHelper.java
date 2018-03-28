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


import java.util.List;

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
public class ServerHelper {
    private static String tasktype = "task";
    private static String profiletype = "profile";


//TODO - all methods (not async should be placed in some other class at some point


    /**
     *
     * @param search - the jsons  query
     * @return
     */
    private TaskList getTaskList (String search){
        TaskList tasklist = null;
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
     * @param lat - the lat of the user
     * @param lon - the lon of the user
     * @return - the tasklist of all tasks within the #### range of the user
     */
    public TaskList getMapTasks(Float lat, Float lon) {
        //TODO = calculate the range of lat/lon based on the 40 km distance using paramter belows
        //just dummy values for now
        float latMin = 53;
        float latMax =54;
        float lonMin = -115;
        float lonMax = -112;
        String search =
                "{\"query\":{\"bool\":{\"must\":"+
                        "[{\"range\":{\"latitude\""+
                        ":{\"gte\":"+latMin+
                        ",\"lte\":" +latMax+
                        "}}},{\"range\":{\"longitude\":"+
                        "{\"gte\":"+lonMin+
                        ",\"lte\":"+lonMax+
                        "}}}],\"should\":[{\"match\":"+
                        "{\"status\":\"Requested\"}},"+
                        "{\"match\":{\"status\":\"Bidded\"}}]}}}";
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
        ConnectedState c = ConnectedState.getInstance();
        if (c.isOffline()){
            //get from database
            return null;
        }
        else {
            return onlineGetTask(taskid);
        }
    }

    private Task onlineGetTask(String taskid){
        Task task;
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
        ConnectedState c = ConnectedState.getInstance();
        if (c.isOffline()){
            //get from database
            return null;
        }
        else {
            return onlineAddTask(task);
        }
    }

    private String onlineAddTask(Task task){
        String id;
        ElasticSearchController.AddTask addtask = new ElasticSearchController.AddTask();
        addtask.execute(task);
        try {
            id = addtask.get();
            task.setId(id);
            ElasticSearchController.UpdateTask updateTask = new ElasticSearchController.UpdateTask();
            updateTask.execute(task);
        }
        catch (Exception e) {
            id = null;
        }

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


}