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

}
