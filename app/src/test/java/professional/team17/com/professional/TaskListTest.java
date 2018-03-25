package professional.team17.com.professional;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Hailan on 2018-02-20.
 */

public class TaskListTest extends ActivityInstrumentationTestCase2 {
    public TaskListTest(){
        super(TaskList.class);
    }

    public void testAddTask(){ //same as the test for hasTask
        TaskList tasks = new TaskList();
        Task task = new Task("TaskRequester1", "Title1", "Description1", "Location1", );
        tasks.addTask(task);
        assertTrue(tasks.hasTask(task));
    }

    public void testDeleteTask(){
        TaskList tasks = new TaskList();
        Task task = new Task("TaskRequester2", "Title2", "Description2", "Location2", );
        tasks.addTask(task);
        tasks.deleteTask(task);
        assertFalse(tasks.hasTask(task));
    }

    public void testGetTasks(){
        TaskList tasks = new TaskList();

        Task task1 = new Task("TaskRequester1", "Title1", "Description1", "Location", );
        Task task2 = new Task("TaskRequester2", "Title2", "Description2", "Location2", );
        Task task3 = new Task("TaskRequester3", "Title3", "Description3", "Location3", );

        tasks.addTask(task1);
        tasks.addTask(task2);
        tasks.addTask(task3);

        ArrayList<Task> taskList = new ArrayList<Task>(Arrays.asList(task1, task2, task3));
        assertEquals(tasks.getTasks(), taskList);
    }
}
