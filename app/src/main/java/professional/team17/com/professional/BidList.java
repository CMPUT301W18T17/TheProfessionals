package professional.team17.com.professional;

import java.util.ArrayList;

public class BidList {
    private ArrayList<Bid> bids = new ArrayList<Bid>();

    public BidList(){
        // Nothing
    }

    public Bid getBid(int index){
        return bids.get(index);
    }

    public boolean hasBid(Bid bid){
        return bids.contains(bid);
    }

    public void add(Bid bid){
        bids.add(bid);
    }

    public void delete(Bid bid){
        bids.remove(bid);
    }
}
