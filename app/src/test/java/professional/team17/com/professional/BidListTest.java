package professional.team17.com.professional;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by Hailan on 2018-02-20.
 */

public class BidListTest extends ActivityInstrumentationTestCase2 {
    public BidListTest(){
        super(BidList.class);
    }

    public void testAddBid(){ //same as the test for hasBid
        BidList bids = new BidList();
        Bid bid = new Bid("Bob&Mary", 50.0);
        bids.add(bid);
        assertTrue(bids.hasBid(bid));
    }

    public void testDeleteBid(){
        BidList bids = new BidList();
        Bid bid = new Bid("Bob&Mary", 50.0);
        bids.add(bid);
        bids.delete(bid);
        assertFalse(bids.hasBid(bid));
    }

    //test and make sure clear is working
    public void testClearBids(){
        BidList bids = new BidList();
        Bid bid = new Bid("Bob&Mary", 50.0);
        bids.add(bid);
        Bid bid1 = new Bid("Bob&Mary1", 501.0);
        bids.add(bid1);
        bids.deleteAllExcept(bid1);
        //TODO assertEquals(bids.getSize(), 1);
    }

    public void testisEmpty() { //same as the test for hasReview
        BidList bids = new BidList();
        assertTrue(bids.isEmpty());
        Bid bid = new Bid("Bob&Mary", 50.0);
        bids.add(bid);
        assertFalse(bids.isEmpty());
    }

    public void testGetLowest(){
        BidList bids = new BidList();
        Bid bid = new Bid("Bob&Mary", 50.0);
        bids.add(bid);
        Bid bid1 = new Bid("Bob&Mary1", 501.0);
        bids.add(bid1);
        assertEquals(bids.getLowest(), bid);

    }


}
