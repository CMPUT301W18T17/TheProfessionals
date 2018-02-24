package professional.team17.com.professional;

import android.test.ActivityInstrumentationTestCase2;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import java.util.Date;

/**
 * Created by Zhipeng Zhang on 2018/2/22.
 */

public class ElasticSearchControllerTest extends ActivityInstrumentationTestCase2 {

    private Date date = new Date();
    Task task1 = new Task("TaskRequester1", "Title1", "Description1", "Location1", date, "Task ID1");
    Task task2 = new Task("TaskRequester2", "Title2", "Description2", "Location2", date, "Task ID2");
    Task task3 = new Task("TaskRequester3", "Title3", "Description3", "Location3", date, "Task ID3");


    Profile profile1 = new Profile("Mary1", "mair1", "mair@email1.ca", "111-1111");
    Profile profile2 = new Profile("Mary2", "mair2", "mair@email2.ca", "111-1111");
    Profile profile3 = new Profile("Mary3", "mair3", "mair@email3.ca", "111-1111");


    public ElasticSearchControllerTest() {
        super(ElasticSearchController.class);
    }

    public void testAddTaskTest() {
        ElasticSearchController.AddTask addTask = new ElasticSearchController.AddTask();
        ElasticSearchController.GetTask getTask = new ElasticSearchController.GetTask();
        addTask.execute(task1);

        String id = task1.getUniqueID(); //this is what we search with
        String search = "{ \"query\": \"term\" : { \"id\" : \"idfill\" } }"; //this is the json standard for searching tasks by id
        JsonParser jsonParser = new JsonParser(); //create a new parser
        JsonObject jsonObject = (JsonObject) jsonParser.parse(search); //parse the string json into a jsonobject
        jsonObject.getAsJsonObject("query").getAsJsonObject("term").addProperty("id", id); //add in the term we are searching for into the json object
        getTask.execute(jsonObject.toString()); //pass to the controller (but as string)
        try {
            Task task = getTask.get(); //the controller should now hold the task, call get() to return it
            assertEquals(task, task1);
        } catch (Exception e) {
        }
    }

    public void testGetTasksTest(){
        ElasticSearchController.AddTask addTask = new ElasticSearchController.AddTask();
        ElasticSearchController.GetTask getTask = new ElasticSearchController.GetTask();
        addTask.execute(task1);
        addTask.execute(task2);
        addTask.execute(task3);

        String id1 = task1.getUniqueID(); //this is what we search with
        String id2 = task2.getUniqueID(); //this is what we search with
        String id3 = task3.getUniqueID(); //this is what we search with
        String search = "{ \"query\": \"term\" : { \"id\" : \"idfill\" } }"; //this is the json standard for searching tasks by id


        // For task 1
        JsonParser jsonParser1 = new JsonParser(); //create a new parser
        JsonObject jsonObject1 = (JsonObject) jsonParser1.parse(search); //parse the string json into a jsonobject
        jsonObject1.getAsJsonObject("query").getAsJsonObject("term").addProperty("id", id1); //add in the term we are searching for into the json object
        getTask.execute(jsonObject1.toString()); //pass to the controller (but as string)
        try {
            Task task = getTask.get(); //the controller should now hold the task, call get() to return it
            assertEquals(task, task1);
        } catch (Exception e) {
        }

        // For task 2
        JsonParser jsonParser2 = new JsonParser(); //create a new parser
        JsonObject jsonObject2 = (JsonObject) jsonParser2.parse(search); //parse the string json into a jsonobject
        jsonObject2.getAsJsonObject("query").getAsJsonObject("term").addProperty("id", id2); //add in the term we are searching for into the json object
        getTask.execute(jsonObject2.toString()); //pass to the controller (but as string)
        try {
            Task task = getTask.get(); //the controller should now hold the task, call get() to return it
            assertEquals(task, task2);
        } catch (Exception e) {
        }

        // For task 3
        JsonParser jsonParser3 = new JsonParser(); //create a new parser
        JsonObject jsonObject3 = (JsonObject) jsonParser3.parse(search); //parse the string json into a jsonobject
        jsonObject3.getAsJsonObject("query").getAsJsonObject("term").addProperty("id", id3); //add in the term we are searching for into the json object
        getTask.execute(jsonObject3.toString()); //pass to the controller (but as string)
        try {
            Task task = getTask.get(); //the controller should now hold the task, call get() to return it
            assertEquals(task, task3);
        } catch (Exception e) {
        }
    }

    public void testGetTaskTest(){
        ElasticSearchController.AddTask addTask = new ElasticSearchController.AddTask();
        ElasticSearchController.GetTask getTask = new ElasticSearchController.GetTask();
        addTask.execute(task1);
        addTask.execute(task2);

        String id = task3.getUniqueID(); //this is what we search with
        String search = "{ \"query\": \"term\" : { \"id\" : \"idfill\" } }"; //this is the json standard for searching tasks by id
        JsonParser jsonParser = new JsonParser(); //create a new parser
        JsonObject jsonObject = (JsonObject) jsonParser.parse(search); //parse the string json into a jsonobject
        jsonObject.getAsJsonObject("query").getAsJsonObject("term").addProperty("id", id); //add in the term we are searching for into the json object
        getTask.execute(jsonObject.toString()); //pass to the controller (but as string)
        try {
            Task task = getTask.get(); //the controller should now hold the task, call get() to return it
            assertEquals(task, null);
        } catch (Exception e) {
        }
    }

    public void testAddProfileTest() {
        ElasticSearchController.AddProfile addProfile = new ElasticSearchController.AddProfile();
        ElasticSearchController.GetProfile getProfile = new ElasticSearchController.GetProfile();

        addProfile.execute(profile1);

        String id = profile1.getUserName(); //this is what we search with
        String search = "{ \"query\": \"term\" : { \"id\" : \"idfill\" } }"; //this is the json standard for searching tasks by id
        JsonParser jsonParser = new JsonParser(); //create a new parser
        JsonObject jsonObject = (JsonObject) jsonParser.parse(search); //parse the string json into a jsonobject
        jsonObject.getAsJsonObject("query").getAsJsonObject("term").addProperty("id", id); //add in the term we are searching for into the json object
        getProfile.execute(jsonObject.toString()); //pass to the controller (but as string)
        try {
            Profile profile = getProfile.get(); //the controller should now hold the task, call get() to return it
            assertEquals(profile, profile1);
        } catch (Exception e) {
        }
    }

    public void testGetProfileTest(){
        ElasticSearchController.AddProfile addProfile = new ElasticSearchController.AddProfile();
        ElasticSearchController.GetProfile getProfile = new ElasticSearchController.GetProfile();

        addProfile.execute(profile1);
        addProfile.execute(profile2);
        addProfile.execute(profile3);

        String id1 = profile1.getUserName(); //this is what we search with
        String id2 = profile2.getUserName(); //this is what we search with
        String id3 = profile3.getUserName(); //this is what we search with
        String search = "{ \"query\": \"term\" : { \"id\" : \"idfill\" } }"; //this is the json standard for searching tasks by id


        // For profile 1
        JsonParser jsonParser1 = new JsonParser(); //create a new parser
        JsonObject jsonObject1 = (JsonObject) jsonParser1.parse(search); //parse the string json into a jsonobject
        jsonObject1.getAsJsonObject("query").getAsJsonObject("term").addProperty("id", id1); //add in the term we are searching for into the json object
        getProfile.execute(jsonObject1.toString()); //pass to the controller (but as string)
        try {
            Profile profile = getProfile.get(); //the controller should now hold the task, call get() to return it
            assertEquals(profile, profile1);
        } catch (Exception e) {
        }

        // For task 2
        JsonParser jsonParser2 = new JsonParser(); //create a new parser
        JsonObject jsonObject2 = (JsonObject) jsonParser2.parse(search); //parse the string json into a jsonobject
        jsonObject2.getAsJsonObject("query").getAsJsonObject("term").addProperty("id", id2); //add in the term we are searching for into the json object
        getProfile.execute(jsonObject2.toString()); //pass to the controller (but as string)
        try {
            Profile profile = getProfile.get(); //the controller should now hold the task, call get() to return it
            assertEquals(profile, profile2);
        } catch (Exception e) {
        }

        // For task 3
        JsonParser jsonParser3 = new JsonParser(); //create a new parser
        JsonObject jsonObject3 = (JsonObject) jsonParser3.parse(search); //parse the string json into a jsonobject
        jsonObject3.getAsJsonObject("query").getAsJsonObject("term").addProperty("id", id3); //add in the term we are searching for into the json object
        getProfile.execute(jsonObject3.toString()); //pass to the controller (but as string)
        try {
            Profile profile = getProfile.get(); //the controller should now hold the task, call get() to return it
            assertEquals(profile, profile3);
        } catch (Exception e) {
        }
    }

    public void testUpdateProfileTest() {
        ElasticSearchController.UpdateProfile updateProfile = new ElasticSearchController.UpdateProfile();
        ElasticSearchController.AddProfile addProfile = new ElasticSearchController.AddProfile();
        ElasticSearchController.GetProfile getProfile = new ElasticSearchController.GetProfile();

        // Add profile 2
        addProfile.execute(profile2);

        // Update it with profile 1
        updateProfile.execute(profile1);

        String id = profile1.getUserName(); //this is what we search with
        String search = "{ \"query\": \"term\" : { \"id\" : \"idfill\" } }"; //this is the json standard for searching tasks by id
        JsonParser jsonParser = new JsonParser(); //create a new parser
        JsonObject jsonObject = (JsonObject) jsonParser.parse(search); //parse the string json into a jsonobject
        jsonObject.getAsJsonObject("query").getAsJsonObject("term").addProperty("id", id); //add in the term we are searching for into the json object
        getProfile.execute(jsonObject.toString()); //pass to the controller (but as string)
        try {
            Profile profile = getProfile.get(); //the controller should now hold the task, call get() to return it
            assertEquals(profile, profile1);
        } catch (Exception e) {
        }
    }

    public void testUpdateTaskTest() {
        ElasticSearchController.UpdateTask updateTask = new ElasticSearchController.UpdateTask();
        ElasticSearchController.AddTask addTask = new ElasticSearchController.AddTask();
        ElasticSearchController.GetTask getTask = new ElasticSearchController.GetTask();

        // Add task 2
        addTask.execute(task2);

        // Update it with task 1
        updateTask.execute(task1);

        String id = task1.getUniqueID(); //this is what we search with
        String search = "{ \"query\": \"term\" : { \"id\" : \"idfill\" } }"; //this is the json standard for searching tasks by id
        JsonParser jsonParser = new JsonParser(); //create a new parser
        JsonObject jsonObject = (JsonObject) jsonParser.parse(search); //parse the string json into a jsonobject
        jsonObject.getAsJsonObject("query").getAsJsonObject("term").addProperty("id", id); //add in the term we are searching for into the json object
        getTask.execute(jsonObject.toString()); //pass to the controller (but as string)
        try {
            Task task = getTask.get(); //the controller should now hold the task, call get() to return it
            assertEquals(task, task1);
        } catch (Exception e) {
        }
    }
}
