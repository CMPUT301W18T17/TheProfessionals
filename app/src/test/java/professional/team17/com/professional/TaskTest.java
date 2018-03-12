package professional.team17.com.professional;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Lauren
 * on 2018-02-22.
 */

public class TaskTest extends ActivityInstrumentationTestCase2 {

    private String profileName = "Testing";
    private String name = "Tast Title";
    private String description = "Task Description";
    private String location = "Task Location";
    private Date date = new Date();
    private String uniqueID = "Task ID";
    private ArrayList<String> photos = new ArrayList<>();
    private ArrayList<Bid> bids = new ArrayList<>();
    private String status = "Task Status";

    public TaskTest(){
        super(Task.class);
    }

    public void getProfileNameTest() {
        Task task = new Task(profileName, name, description, location, date);
        assertEquals(profileName, task.getProfileName());
    }

    public void setProfileNameTest() {
        Task task = new Task(profileName, name, description, location, date);
        this.profileName = "Task Requester";
        task.setProfileName(this.profileName);
        assertEquals(profileName, task.getProfileName());
    }

    public void getNameTest() {
        Task task = new Task(profileName, name, description, location, date);
        assertEquals(name, task.getName());
    }

    public void setNameTest() {
        Task task = new Task(profileName, name, description, location, date);
        this.name = "Testing";
        task.setName(name);
        assertEquals(name, task.getName());
    }

    public void getDescriptionTest() {
        Task task = new Task(profileName, name, description, location, date);
        assertEquals(description, task.getDescription());
    }

    public void setDescriptionTest() {
        Task task = new Task(profileName, name, description, location, date);
        this.description = "Testing";
        task.setDescription(description);
        assertEquals(description, task.getDescription());
    }

    public void getLocationTest() {
        Task task = new Task(profileName, name, description, location, date);
        assertEquals(location, task.getLocation());
    }

    public void setLocationTest() {
        Task task = new Task(profileName, name, description, location, date);
        this.location = "Testing";
        task.setLocation(location);
        assertEquals(location, task.getLocation());
    }

    public void getDateTest() {
        Task task = new Task(profileName, name, description, location, date);
        assertEquals(date, task.getDate());
    }

    public void setDateTest() {
        Task task = new Task(profileName, name, description, location, date);
        this.date = new Date();
        task.setDate(date);
        assertEquals(date, task.getDate());
    }

    public void getUniqueIDTest() {
        Task task = new Task(profileName, name, description, location, date);
        assertEquals(uniqueID, task.getUniqueID());
    }

    public void setUniqueIDTest() {
        Task task = new Task(profileName, name, description, location, date);
        this.uniqueID = "Testing";
        task.setId(uniqueID);
        assertEquals(uniqueID, task.getUniqueID());
    }

    public void getPhotosTest() {
        Task task = new Task(profileName, name, description, location, date);
        assertEquals(photos, task.getPhotos());
    }

    public void setPhotosTest() {
        Task task = new Task(profileName, name, description, location, date);
        assertEquals(photos, task.getPhotos());
    }

    public void addPhotoTest(){
        Task task = new Task(profileName, name, description, location, date);
        task.addPhoto("test");
        assertEquals(photos, task.getPhotos());
    }

    public void removePhotoTest(){
        Task task = new Task(profileName, name, description, location, date);
        task.addPhoto("test");
        task.addPhoto("test2");
        task.removePhoto("test");
        assertEquals(photos, task.getPhotos());
    }

    public void getBidsTest() {
        Task task = new Task(profileName, name, description, location, date);
        assertEquals(bids, task.getBids());
    }

    public void setBidsTest() {
        Task task = new Task(profileName, name, description, location, date);
        assertEquals(bids, task.getBids());
    }

    public void addBidTest(){
        Task task = new Task(profileName, name, description, location, date);
        Bid bid = new Bid("name", 0);
        task.addBid(bid);
        assertEquals(bids, task.getBids());
    }

    public void removeBidTest(){
        Task task = new Task(profileName, name, description, location, date);
        Bid bid = new Bid("name", 0);
        task.addBid(bid);
        Bid bid2 = new Bid("name2", 1);
        task.addBid(bid2);
        task.removeBid(bid);
        assertEquals(bids, task.getBids());
    }

    public void clearBidsTest(){
        Task task = new Task(profileName, name, description, location, date);
        Bid bid = new Bid("name", 0);
        task.addBid(bid);
        Bid bid2 = new Bid("name2", 1);
        task.addBid(bid2);
        task.clearBids();
        assertEquals(bids, task.getBids());
    }
/* I think this is pushed to bidlist
    public void getLowestBidTest(){
        Task task = new Task(profileName, name, description, location, date, uniqueID);
        Bid bid = new Bid("name", 0);
        task.addBid(bid);
        Bid bid2 = new Bid("name2", 1);
        task.addBid(bid2);
        int lowestBid = task.getLowestBid();
        assertEquals(lowestBid, task.getLowestBid());
    }
    */

    public void getStatusTest() {
        Task task = new Task(profileName, name, description, location, date);
        assertEquals(status, task.getStatus());
    }

    public void setStatusTest() {
        Task task = new Task(profileName, name, description, location, date);
        this.status = "Bidded";
        task.setBidded();
        assertEquals(status, task.getStatus());
        task.setRequested();
        assertEquals("Requsted", task.getStatus());
        task.setDone();
        assertEquals("Done", task.getStatus());
        task.setAssigned(new Bid("name", 34));
        assertEquals("Assigned", task.getStatus());
    }
}


