package professional.team17.com.professional;

import android.test.ActivityInstrumentationTestCase2;

import java.util.List;

/**
 * Created by Hailan on 2018-02-20.
 */

public class TaskListTest extends ActivityInstrumentationTestCase2 {
    public TaskListTest(){
        super(TaskList.class);
    }

    public void testAddTask(){ //same as the test for hasTask
        TaskList tasks = new TaskList();
        //Task task = new Task();
        //tasks.add(task);
        //assertTrue(tasks.hasTask(task));
    }

    public void testDeleteTask(){
        TaskList tasks = new TaskList();
        //Task task = new Task();
        //tasks.add(task);
        //tasks.delete(task);
        //assertFalse(tasks.hasTask(task));
    }
}
