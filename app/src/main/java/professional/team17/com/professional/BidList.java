package professional.team17.com.professional;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class BidList extends ArrayList<Bid> implements Serializable{
    private Bid lowest;

    public BidList(){
        lowest = null;
        // Nothing
    }

    public Bid getBid(int index){
        return this.get(index);
    }

    public boolean hasBid(Bid bid){
        return this.contains(bid);
    }

    //add bid and keep track of lowest
    public void addBids(Bid bid){
        if (this.isEmpty()){
            lowest = bid;
        }
        else if (bid.getAmount()<lowest.getAmount()) {
            lowest =  bid;
        }
        this.add(bid);
    }

    public void delete(Bid bid){
        this.remove(bid);
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
        if (this.isEmpty()) {
            return null;
        }
        Bid low = this.get(0);
        for (int i = 1; i < this.size(); i++) {
            if (this.get(i).getAmount()<low.getAmount()){
                low = this.get(i);
            }
        }
        return low;
    }


    public int getSize(){
        return this.size();
    }
    public boolean isEmpty(){
        return this.size()==0;
    }


    /**
     *
     * @param userName, representing the user name being looked for
     * @return boolean
     */
    public boolean userBidded(String userName) {
        for (int i = 0; i < this.size(); i++) {
            if (Objects.equals(this.get(i).getName(),userName)) {
                return true;
            }
        }
        return false;
    }
}
