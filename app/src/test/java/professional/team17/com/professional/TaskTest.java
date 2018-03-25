package professional.team17.com.professional;

import android.test.ActivityInstrumentationTestCase2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ag on 2018-02-24.
 */

public class TaskTest extends ActivityInstrumentationTestCase2 {

    public TaskTest(){
        super(Task.class);
    }



    public void testGetProfileName() {


        Task task = new Task("Testing", "Tast Title", "Task Description", "location", new Date(), );
        assertEquals("Testing", task.getProfileName());
    }

    public void testSetProfileName() {
        Task task = new Task("Testing", "Tast Title", "Task Description", "location", new Date(), );
        task.setProfileName("new Name");
        assertEquals("new Name", task.getProfileName());
    }

    public void testGetNameTest() {
        Task task = new Task("Testing", "Tast Title", "Task Description", "location", new Date(), );
        assertEquals("Tast Title", task.getName());
    }

    public void testSetName() {
        String name  = "Testing";
        Task task = new Task("Testing", "Tast Title", "Task Description", "location", new Date(), );
        task.setName(name);
        assertEquals(name, task.getName());
    }

    public void testGetDescription() {
        Task task = new Task("Testing", "Tast Title", "Task Description", "location", new Date(), );
        assertEquals("Task Description", task.getDescription());
    }

    public void testSetDescription() {
        Task task = new Task("Testing", "Tast Title", "Task Description", "location", new Date(), );
        assertEquals("Task Description", task.getDescription());
        String newDesc = "Testing";
        task.setDescription(newDesc);
        assertEquals(newDesc, task.getDescription());
    }

    public void testGetLocation() {
        String location = "location";
        Task task = new Task("Testing", "Tast Title", "Task Description", location,  new Date(), );
        assertEquals(location, task.getLocation());
    }

    public void testSetLocation() {
        String location = "location";
        Task task = new Task("Testing", "Tast Title", "Task Description", location,  new Date(), );
        String location2 = "Testing";
        task.setLocation(location);
        assertEquals(location, task.getLocation());
    }



    public void testDateString() {
        String date = "12/12/2017";
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            d = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Task task = new Task("Testing", "Tast Title", "Task Description", "location", date, );
        Bid bid = new Bid("bid", 34.34);
        assertEquals(task.getDateAsString(), date);
        assertEquals(task.getDate(), d);
    }


    public void testGetDate() {
        Date date = new Date();
        Task task = new Task("Testing", "Tast Title", "Task Description", "location", date, );
        assertEquals(date, task.getDate());
    }

    public void testSetDate() {
        Task task = new Task("Testing", "Tast Title", "Task Description", "location", new Date(), );
        Date date = new Date();
        task.setDate(date);
        assertEquals(date, task.getDate());
    }

    public void testGetUniqueID() {
        //TODO implement with id generator once implemented perhaps deprecate as id maybe should be static

        Task task = new Task("Testing", "Tast Title", "Task Description", "location", new Date(), );
        assertEquals("Task ID", task.getUniqueID());
    }

    public void testSetUniqueID() {
        //TODO implement with id generator once implemented perhaps deprecate as id maybe should be static
        Task task = new Task("Testing", "Tast Title", "Task Description", "location", new Date(), );
        assertEquals("Task ID", task.getUniqueID());
    }

    public void testGetPhotos() {
        //TODO implement with photo repr (string or mem location)
        Task task = new Task("Testing", "Tast Title", "Task Description", "location", new Date(), );
        ArrayList<String> photos = new ArrayList<>();
        assertEquals(photos, task.getPhotos());
    }

    public void testSetPhotos() {
        //TODO implement with photo repr (string or mem location)
        ArrayList<String> photos = new ArrayList<>();
        Task task = new Task("Testing", "Tast Title", "Task Description", "location", new Date(), );
        task.setPhotos(photos);
        assertEquals(photos, task.getPhotos());
    }

    public void testAddPhoto(){
        //TODO implement with photo repr (string or mem location)
        Task task = new Task("Testing", "Tast Title", "Task Description", "location", new Date(), );
        task.addPhoto("test");
        assertEquals("test", task.getPhotos());
    }

    public void testRemovePhoto(){
        //TODO implement with photo repr (string or mem location)
        Task task = new Task("Testing", "Tast Title", "Task Description", "location", new Date(), );
        task.addPhoto("test");
        task.addPhoto("test2");
        task.removePhoto("test");
        assertEquals("test2", task.getPhotos());
    }

    public void testGetStatus() {
        Task task = new Task("Testing", "Tast Title", "Task Description", "location", new Date(), );
        assertEquals("Requested", task.getStatus());
    }

    //test setting task to requested
    public void testSetStatusRequested() {
        Task task = new Task("Testing", "Tast Title", "Task Description", "location", new Date(), );
        //requested tests
        assertEquals("Requested", task.getStatus());
        assertTrue(task.isRequested());
        assertFalse(task.isAssigned());
        assertFalse(task.isBidded());
        assertFalse(task.isDone());
    }

    //test setting task to bidded
    public void testSetStatusBidded() {
        Task task = new Task("Testing", "Tast Title", "Task Description", "location", new Date(), );
        //bidded tests
        Bid bid = new Bid("bid", 34.34);
        task.addBid(bid);
        assertEquals(task.getStatus(), "Bidded");
        assertFalse(task.isRequested());
        assertFalse(task.isAssigned());
        assertTrue(task.isBidded());
        assertFalse(task.isDone());
    }

    //test setting task to assigned
    public void testSetStatusAssigned() {
        Task task = new Task("Testing", "Tast Title", "Task Description", "location", new Date(), );
        Bid bid = new Bid("bid", 34.34);
        task.addBid(bid);
        task.chooseBid(bid);
        assertEquals(task.getStatus(), "Assigned");
        assertFalse(task.isRequested());
        assertTrue(task.isAssigned());
        assertFalse(task.isBidded());
        assertFalse(task.isDone());
    }


    //test setting task to done
    public void testSetStatusDone() {
        Task task = new Task("Testing", "Tast Title", "Task Description", "location", new Date(), );
        task.setDone();
        assertEquals("Done", task.getStatus());
        assertFalse(task.isRequested());
        assertFalse(task.isAssigned());
        assertFalse(task.isBidded());
        assertTrue(task.isDone());

    }
    //test setting task to assigned
    public void testAssignedBids() {
        Task task = new Task("Testing", "Tast Title", "Task Description", "location", new Date(), );
        Bid bid = new Bid("bid", 34.34);
        Bid bid2 = new Bid("bid22", 34222.34);
        task.addBid(bid);
        task.addBid(bid2);
        task.chooseBid(bid);
        //should only have one bid now and the chosen bid
        assertEquals(1, task.getBids().size());
        assertEquals(task.getBids().hasBid(bid), bid);
    }

    //test changing from assigned to requested
    public void testSetAssignedtoRequested() {
        Task task = new Task("Testing", "Tast Title", "Task Description", "location", new Date(), );
        Bid bid = new Bid("bid", 34.34);
        Bid bid2 = new Bid("bid22", 34222.34);
        task.setRequested();
        assertTrue(task.getBids().isEmpty());
    }

    //test status updates on adding bids
    public void testTaskBidsAdd() {
        Task task = new Task("Testing", "Tast Title", "Task Description", "location", new Date(), );
        Bid bid = new Bid("bid", 34.34);
        assertEquals(task.getBids().getBid("bid"), bid);
        task.clearBids();
        assertTrue(task.getBids().isEmpty());
    }

    //test status updates on clearing bids
    public void testTaskBidsRemove() {
        Task task = new Task("Testing", "Tast Title", "Task Description", "location", new Date(), );
        Bid bid = new Bid("bid", 34.34);
        task.removeBid(bid);
        assertTrue(task.getBids().isEmpty());
        assertTrue(task.isRequested());
        task.addBid(bid);
        task.clearBids();
        assertTrue(task.getBids().isEmpty());
        assertTrue(task.isRequested());
    }



}
