/*
 * ElasticSearchController
 *
 * March 5, 2018
 *
 * Copyright @ 2018 Team 17, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behaviour at the University of Alberta.
 * You can find a copy of the license in the github wiki for this project.
 */

package professional.team17.com.professional.Controllers;
import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import professional.team17.com.professional.Helpers.ConnectedState;
import professional.team17.com.professional.Entity.Profile;
import professional.team17.com.professional.Entity.Task;
import professional.team17.com.professional.Entity.TaskList;
import professional.team17.com.professional.Entity.TaskDAO;

/**
 * This class will handle the connection and async tasks to the es server
 * @author Allison
 */
public class ServerHelper {
    private static Context context;

    public ServerHelper(Context c){
        this.context = c;
    }

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
        ConnectedState c = ConnectedState.getInstance();
        if (c.isOffline()){
            TaskDAO db = new TaskDAO(context);
            return db.getTasks();
        }
        else {
            return getTaskList(search);
        }
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
     * @param bottomLeft- the bottomLeft corner of the Circle enclosing 5km
     * @param topRight - the topRight corner of the Circle enclosing 5km
     * @return - the tasklist of all tasks within the #### range of the user
     */
    public TaskList getMapTasks(LatLng bottomLeft, LatLng topRight) {
        String search =
                "{\"query\":{\"bool\":{\"must\":"+
                        "[{\"range\":{\"latitude\""+
                        ":{\"gte\":"+bottomLeft.latitude+
                        ",\"lte\":" +topRight.latitude+
                        "}}},{\"range\":{\"longitude\":"+
                        "{\"gte\":"+bottomLeft.longitude+
                        ",\"lte\":"+topRight.longitude+
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
        //String search = "{\"query\":{\"bool\":{\"must_not\":{\"match\":{\"profileName\":\"Hailan\"}},\"should\":[{\"match\":{\"status\":\"Requested\"}},{\"match\":{\"status\":\"Bidded\"}}]}}}";
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
            TaskDAO db = new TaskDAO(context);
            task = db.getTask(taskid);
            db.close();
            return task;
        }
        else {
            return onlineGetTask(taskid);
        }
    }

    /**
     * get task
     * @param taskid - id of task
     * @return - task
     */
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
     * add task
     */
    public String addTasks(Task task) {
        String id;
        ConnectedState c = ConnectedState.getInstance();
        if (c.isOffline()){
            TaskDAO db = new TaskDAO(context);
            id = db.insertOffline(task);
            db.close();
            return id;
        }
        else {
            id = onlineAddTask(task);
            return id;
        }
    }

    /**
     * add task when online
     * @param task - task
     * @return - task id
     */
    public String onlineAddTask(Task task){
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
        ConnectedState c = ConnectedState.getInstance();
        if (c.isOffline()){
            TaskDAO db = new TaskDAO(context);
            db.updateTaskOffline(task);
            db.close();
        }
        else {
            ElasticSearchController.UpdateTask updatetask = new ElasticSearchController.UpdateTask();
            updatetask.execute(task);
        }


    }

    /**
     *
     * @param task - the task that will be deleted within the ES
     */
    public void deleteTasks(Task task) {
        ConnectedState c = ConnectedState.getInstance();
        if (c.isOffline()){
            TaskDAO db = new TaskDAO(context);
            db.removeOffline(task);
            db.close();
        }
        else {
            ElasticSearchController.DeleteTask deletetask = new ElasticSearchController.DeleteTask();
            deletetask.execute(task);
        }
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
