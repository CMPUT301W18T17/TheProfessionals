package professional.team17.com.professional;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;


import java.util.Date;

/**
 * Created by Zhipeng Zhang on 2018/2/22.
 */

public class ServerHelperTest extends ActivityInstrumentationTestCase2 {

    private final ServerHelper serverHelper = new ServerHelper(getContext());
    private Date date = new Date();
    Task task1 = new Task("TaskRequester1", "Title1", "Description1");
    Task task2 = new Task("TaskRequester2", "Title2", "Description2");
    Task task3 = new Task("TaskRequester3", "Title3", "Description3");


    Profile profile1 = new Profile("Mary1", "mair1", "mair@email1.ca", "111-1111");
    Profile profile2 = new Profile("Mary2", "mair2", "mair@email2.ca", "111-1111");
    Profile profile3 = new Profile("Mary3", "mair3", "mair@email3.ca", "111-1111");


    public ServerHelperTest() {
        super(ServerHelper.class);
    }

    public void testAddTaskTest() {
        String id = serverHelper.addTasks(task1);
        Task temp = serverHelper.getTask(id);
        try {
            assertEquals(temp.getUniqueID(), id);
            assertEquals(temp.getDateAsString(), task1.getDateAsString());
            assertEquals(temp.getLocation(), task1.getLocation());
            assertEquals(temp.getName(), task1.getName());
            assertEquals(temp.getProfileName(), task1.getProfileName());
        }
        //force fail
        catch (Exception e) {
            assertEquals(0,1);
        }
        serverHelper.deleteTasks(task1);
    }


    public void testGetTaskListTest() {
        serverHelper.addTasks(task2);
        serverHelper.addTasks(task3);
        TaskList temp = serverHelper.getTasksStatus("Requested");
        assertEquals(temp.size(), 2);
        serverHelper.deleteTasks(task2);
        serverHelper.deleteTasks(task3);
    }


    public void testAddProfileTest() {
        serverHelper.addProfile(profile1);

        Profile temp = serverHelper.getProfile(profile1.userName);
        try {
            assertEquals(temp.getName(), profile1.getName());
            assertEquals(temp.getEmail(), profile1.getEmail());
            assertEquals(temp.getPhoneNumber(), profile1.getPhoneNumber());
            assertEquals(temp.getUserName(), profile1.getUserName());
        }
        //force fail
        catch (Exception e)
        {
            assertEquals(0,1);
        }
        serverHelper.deleteProfile(profile1);
    }


    public void testUpdateProfileTest() {
        String phone = "123";
        serverHelper.addProfile(profile1);

        profile1.setPhoneNumber(phone);
        serverHelper.addProfile(profile1);


        Profile temp = serverHelper.getProfile(profile1.userName);
        try {

            assertEquals(temp.getPhoneNumber(), profile1.getPhoneNumber());
        }
        //force fail
        catch (Exception e) {
            assertEquals(0, 1);
        }
        serverHelper.deleteProfile(profile1);
    }

    public void testUpdateTaskTest() {
        String name = "newname";
        String id = serverHelper.addTasks(task1);
        task1.setName(name);
        serverHelper.updateTasks(task1);
        Task temp = serverHelper.getTask(id);
        try {
            assertEquals(temp.getName(), task1.getName());
        }
        //force fail
        catch (Exception e) {
            assertEquals(0, 1);
        }
        serverHelper.deleteTasks(task1);
    }
}
