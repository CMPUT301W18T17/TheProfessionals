package professional.team17.com.professional;

import android.test.ActivityInstrumentationTestCase2;

import professional.team17.com.professional.Entity.Bid;
import professional.team17.com.professional.Entity.BidList;


public class BidListTest extends ActivityInstrumentationTestCase2 {
    public BidListTest(){
        super(BidList.class);
    }

    public void testAddBid(){ //same as the test for hasBid
        BidList bids = new BidList();
        Bid bid = new Bid("Tester", 50.0);
        bids.add(bid);
        assertTrue(bids.hasBid(bid));
    }

    public void testDeleteBid(){
        BidList bids = new BidList();
        Bid bid = new Bid("Tester", 50.0);
        bids.add(bid);
        bids.delete(bid);
        assertFalse(bids.hasBid(bid));
    }

    public void testisEmpty() { //same as the test for hasReview
        BidList bids = new BidList();
        assertTrue(bids.isEmpty());
        Bid bid = new Bid("Tester", 50.0);
        bids.add(bid);
        assertFalse(bids.isEmpty());
    }

    public void testGetLowestAdd(){
        BidList bids = new BidList();
        Bid bid = new Bid("Tester", 50.0);
        bids.add(bid);
        Bid bid1 = new Bid("Tester1", 501.0);
        bids.add(bid1);
        assertEquals(bids.getLowest(), bid);

    }

    public void testGetLowestDelete(){
        BidList bids = new BidList();
        Bid bid = new Bid("Tester", 50.0);
        bids.add(bid);
        Bid bid1 = new Bid("Tester1", 501.0);
        bids.add(bid1);
        Bid bid2 = new Bid("Tester2", 25.0);
        bids.add(bid2);
        assertEquals(bids.getLowest(), bid);
        bids.remove(bid);
        assertEquals(bids.getLowest(), bid2);
    }
}
