package professional.team17.com.professional;

import android.test.ActivityInstrumentationTestCase2;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;
import java.util.Date;


public class TaskTest extends ActivityInstrumentationTestCase2 {

    public TaskTest(){
        super(Task.class);
    }



    public void testGetProfileName() {


        Task task = new Task("Testing", "Tast Title", "Task Description");
        assertEquals("Testing", task.getProfileName());
    }

    public void testSetProfileName() {
        Task task = new Task("Testing", "Tast Title", "Task Description");
        task.setProfileName("new Name");
        assertEquals("new Name", task.getProfileName());
    }

    public void testGetNameTest() {
        Task task = new Task("Testing", "Tast Title", "Task Description");
        assertEquals("Tast Title", task.getName());
    }

    public void testSetName() {
        String name  = "Testing";
        Task task = new Task("Testing", "Tast Title", "Task Description");
        task.setName(name);
        assertEquals(name, task.getName());
    }

    public void testGetDescription() {
        Task task = new Task("Testing", "Tast Title", "Task Description");
        assertEquals("Task Description", task.getDescription());
    }

    public void testSetDescription() {
        Task task = new Task("Testing", "Tast Title", "Task Description");
        assertEquals("Task Description", task.getDescription());
        String newDesc = "Testing";
        task.setDescription(newDesc);
        assertEquals(newDesc, task.getDescription());
    }

    public void testGetLocation() {
        String location = "location";
        LatLng coords = new LatLng(0,0);
        ArrayList<String> list = new ArrayList<>();
        Task task = new Task("Testing", "Tast Title", "Task Description", location, coords, list);
        assertEquals(location, task.getLocation());
        assertEquals(coords, task.getLatLng());
    }

    public void testSetLocation() {
        String location = "location";
        LatLng coords = new LatLng(0,0);
        ArrayList<String> list = new ArrayList<>();
        Task task = new Task("Testing", "Tast Title", "Task Description", location, coords, list);
        String location2 = "Testing";
        task.setLocation(location2);
        assertEquals(location2, task.getLocation());
    }



    public void testDateString() {
        Date d = new Date();
        Task task = new Task("Testing", "Tast Title", "Task Description");

        assertEquals(task.getDate(), d);
    }


    public void testGetDate() {
        Date date = new Date();
        Task task = new Task("Testing", "Tast Title", "Task Description");
        assertEquals(date, task.getDate());
    }

    public void testSetDate() {
        Task task = new Task("Testing", "Tast Title", "Task Description");
        Date date = new Date();
        task.setDate(date);
        assertEquals(date, task.getDate());
    }

    public void testGetStatus() {
        Task task = new Task("Testing", "Tast Title", "Task Description");
        assertEquals("Requested", task.getStatus());
    }

    //test setting task to requested
    public void testSetStatusRequested() {
        Task task = new Task("Testing", "Tast Title", "Task Description");
        //requested tests
        assertEquals("Requested", task.getStatus());
        assertTrue(task.isRequested());
        assertFalse(task.isAssigned());
        assertFalse(task.isBidded());
        assertFalse(task.isDone());
    }

    //test setting task to bidded
    public void testSetStatusBidded() {
        Task task = new Task("Testing", "Tast Title", "Task Description");
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
        Task task = new Task("Testing", "Tast Title", "Task Description");
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
        Task task = new Task("Testing", "Tast Title", "Task Description");
        task.setDone();
        assertEquals("Done", task.getStatus());
        assertFalse(task.isRequested());
        assertFalse(task.isAssigned());
        assertFalse(task.isBidded());
        assertTrue(task.isDone());

    }
    //test setting task to assigned
    public void testAssignedBids() {
        Task task = new Task("Testing", "Tast Title", "Task Description");
        Bid bid = new Bid("bid", 34.34);
        Bid bid2 = new Bid("bid22", 34222.34);
        task.addBid(bid);
        task.addBid(bid2);
        task.chooseBid(bid);
        //should only have one bid now and the chosen bid
        assertEquals(1, task.getBids().size());
        assertTrue(task.getBids().hasBid(bid));
    }

    //test changing from assigned to requested
    public void testSetAssignedtoRequested() {
        Task task = new Task("Testing", "Tast Title", "Task Description");
//        Bid bid = new Bid("bid", 34.34);
//        Bid bid2 = new Bid("bid22", 34222.34);
        task.setRequested();
    }

    //test status updates on adding bids
    public void testTaskBidsAdd() {
        Task task = new Task("Testing", "Tast Title", "Task Description");
        Bid bid = new Bid("bid", 34.34);
        assertEquals(task.getBids().getBid("bid"), bid);
        task.clearBids();
        assertTrue(task.getBids().isEmpty());
    }

    //test status updates on clearing bids
    public void testTaskBidsRemove() {
        Task task = new Task("Testing", "Tast Title", "Task Description");
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
