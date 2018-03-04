package professional.team17.com.professional;

import java.util.ArrayList;

public class BidList {
    private ArrayList<Bid> bids;
    private Bid lowest;

    public BidList(){
        lowest = null;
        bids = new ArrayList<Bid>();
        // Nothing
    }

    public Bid getBid(int index){
        return bids.get(index);
    }

    public boolean hasBid(Bid bid){
        return bids.contains(bid);
    }

    //add bid and keep track of lowest
    public void add(Bid bid){
        if (bids.isEmpty()){
            lowest = bid;
        }
        else if (bid.getAmount()<lowest.getAmount()) {
            lowest =  bid;
        }
        bids.add(bid);
    }

    public void delete(Bid bid){
        bids.remove(bid);
        if (bid==lowest){
            lowest = getLowest();
        }
    }

    //remove all bids bud the one plugged in
    public void deleteAllExcept(Bid bid){


    }

    /**
     *
     * @return the bid with lowest bid.amount in the list
     */
    public Bid getLowest() {

        if (bids.isEmpty()) {
            return null;
        }
        Bid low = bids.get(0);
        for (int i = 1; i < bids.size(); i++) {
            if (bids.get(i).getAmount()<low.getAmount()){
                low = bids.get(i);
            }
        }
        return low;
    }


    public int getSize(){
        return bids.size();
    }
    public boolean isEmpty(){
        return bids.size()==0;
    }
}
