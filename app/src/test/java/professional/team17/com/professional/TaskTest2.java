package professional.team17.com.professional;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ag on 2018-02-24.
 */

public class TaskTest2 extends ActivityInstrumentationTestCase2 {

    public TaskTest2(){
        super(Task.class);
    }



    public void testgetProfileName() {


        Task task = new Task("Testing", "Tast Title", "Task Description", "location", new Date(), "Task ID");
        assertEquals("Testing", task.getProfileName());
    }

    public void testsetProfileName() {
        Task task = new Task("Testing", "Tast Title", "Task Description", "location", new Date(), "Task ID");
        task.setProfileName("new Name");
        assertEquals("new Name", task.getProfileName());
    }

    public void testgetNameTest() {
        Task task = new Task("Testing", "Tast Title", "Task Description", "location", new Date(), "Task ID");
        assertEquals("Tast Title", task.getName());
    }

    public void testsetName() {
        String name  = "Testing";
        Task task = new Task("Testing", "Tast Title", "Task Description", "location", new Date(), "Task ID");
        task.setName(name);
        assertEquals(name, task.getName());
    }

    public void testgetDescription() {
        Task task = new Task("Testing", "Tast Title", "Task Description", "location", new Date(), "Task ID");
        assertEquals("Task Description", task.getDescription());
    }

    public void testsetDescription() {
        Task task = new Task("Testing", "Tast Title", "Task Description", "location", new Date(), "Task ID");
        assertEquals("Task Description", task.getDescription());
        String newDesc = "Testing";
        task.setDescription(newDesc);
        assertEquals(newDesc, task.getDescription());
    }

    public void getLocationTest() {
        String location = "location";
        Task task = new Task("Testing", "Tast Title", "Task Description", location,  new Date(), "Task ID");
        assertEquals(location, task.getLocation());
    }

    public void setLocationTest() {
        String location = "location";
        Task task = new Task("Testing", "Tast Title", "Task Description", location,  new Date(), "Task ID");
        String location2 = "Testing";
        task.setLocation(location);
        assertEquals(location, task.getLocation());
    }

    public void getDateTest() {
        Date date = new Date();
        Task task = new Task("Testing", "Tast Title", "Task Description", "location", date, "Task ID");
        assertEquals(date, task.getDate());
    }

    public void setDateTest() {
        Task task = new Task("Testing", "Tast Title", "Task Description", "location", new Date(), "Task ID");
        Date date = new Date();
        task.setDate(date);
        assertEquals(date, task.getDate());
    }

    public void getUniqueIDTest() {
        //TODO implement with id generator once implemented perhaps deprecate as id maybe should be static

        Task task = new Task("Testing", "Tast Title", "Task Description", "location", new Date(), "Task ID");
        assertEquals("Task ID", task.getUniqueID());
    }

    public void setUniqueIDTest() {
        //TODO implement with id generator once implemented perhaps deprecate as id maybe should be static
        Task task = new Task("Testing", "Tast Title", "Task Description", "location", new Date(), "Task ID");
        assertEquals("Task ID", task.getUniqueID());
    }

    public void getPhotosTest() {
        //TODO implement with photo repr (string or mem location)
        Task task = new Task("Testing", "Tast Title", "Task Description", "location", new Date(), "Task ID");
        ArrayList<String> photos = new ArrayList<>();
        assertEquals(photos, task.getPhotos());
    }

    public void setPhotosTest() {
        //TODO implement with photo repr (string or mem location)
        ArrayList<String> photos = new ArrayList<>();
        Task task = new Task("Testing", "Tast Title", "Task Description", "location", new Date(), "Task ID");
        task.setPhotos(photos);
        assertEquals(photos, task.getPhotos());
    }

    public void addPhotoTest(){
        //TODO implement with photo repr (string or mem location)
        Task task = new Task("Testing", "Tast Title", "Task Description", "location", new Date(), "Task ID");
        task.addPhoto("test");
        assertEquals("test", task.getPhotos());
    }

    public void removePhotoTest(){
        //TODO implement with photo repr (string or mem location)
        Task task = new Task("Testing", "Tast Title", "Task Description", "location", new Date(), "Task ID");
        task.addPhoto("test");
        task.addPhoto("test2");
        task.removePhoto("test");
        assertEquals("test2", task.getPhotos());
    }



    public void getStatusTest() {
        Task task = new Task("Testing", "Tast Title", "Task Description", "location", new Date(), "Task ID");
        assertEquals("Requested", task.getStatus());
    }

    public void setStatusTest() {
        Task task = new Task("Testing", "Tast Title", "Task Description", "location", new Date(), "Task ID");
        task.setStatus("Testing");
        assertEquals("Testing", task.getStatus());
    }
}