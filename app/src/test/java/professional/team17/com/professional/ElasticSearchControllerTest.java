package professional.team17.com.professional;

import android.test.ActivityInstrumentationTestCase2;
import com.google.gson.JsonObject;


import java.util.Date;

/**
 * Created by Zhipeng Zhang on 2018/2/22.
 */

public class ElasticSearchControllerTest extends ActivityInstrumentationTestCase2 {

    private Date date = new Date();
    Task task1 = new Task("TaskRequester1", "Title1", "Description1", "Location1", date, "Task ID1");
    Task task2 = new Task("TaskRequester2", "Title2", "Description2", "Location2", date, "Task ID2");
    Task task3 = new Task("TaskRequester3", "Title3", "Description3", "Location3", date, "Task ID3");

    public ElasticSearchControllerTest(){
        super(ElasticSearchController.class);
    }

    public void AddTaskTest(){
        ElasticSearchController.AddTask addTask = new ElasticSearchController.AddTask();
        ElasticSearchController.GetTasks getTasks = new ElasticSearchController.GetTasks();
        addTask.execute(task1);
        assertTrue(getTasks.hasTask(task1));
    }
}
