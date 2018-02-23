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

    public ElasticSearchControllerTest() {
        super(ElasticSearchController.class);
    }

    public void AddTaskTest() {
        ElasticSearchController.AddTask addTask = new ElasticSearchController.AddTask();
        ElasticSearchController.GetTask getTask = new ElasticSearchController.GetTask();
        addTask.execute(task1);

        String id = task1.getUniqueID(); //this is what we search with
        String search1 = "{ \"query\": \"term\" : { \"id\" : \"idfill\" } }"; //this is the json standard for searching tasks by id
        JsonParser jsonParser = new JsonParser(); //create a new parser
        JsonObject jsonObject = (JsonObject) jsonParser.parse(search1); //parse the string json into a jsonobject
        jsonObject.getAsJsonObject("query").getAsJsonObject("term").addProperty("id", id); //add in the term we are searching for into the json onbjoect
        getTask.execute(jsonObject.toString()); //pass to the controller (but as string)
        try {
            Task task = getTask.get(); //the controller should now hold the task, call get() to return it
            assertEquals(task, task1);
        } catch (Exception e) {
        }
    }
}
