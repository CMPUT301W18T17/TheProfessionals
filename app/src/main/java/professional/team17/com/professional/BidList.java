package professional.team17.com.professional;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;


/**
 *
 * This is the entity class for a bid object
 *
 *
 * @author Allison
 * @see Bid
 */
public class BidList extends ArrayList<Bid> implements Serializable{
    private Bid lowest;

    /**
     * instantiate bidList, set lowest to null
     */
    public BidList(){
        lowest = null;
        // Nothing
    }

    /**
     *
     * @param index - the index the bid is located at
     * @return
     */
    public Bid getBid(int index){
        return this.get(index);
    }


    /**
     *
     * @param userName - the string repre the username of the provider who placed the bid
     * @return - the bid the user had placed, or null, if it does not exist.
     */
    public Bid getBid(String userName){
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getName().equals(userName)) {
                return get(i);
            }
        }
        return null;
    }

    /**
     *
     * @param bid - a bit
     * @return - boolean value of true if bids exists in bidlist, false if it does not exist
     */
    public boolean hasBid(Bid bid){
        return this.contains(bid);
    }


    /**
     *
     * @param bid - the bid to be added to the bidlist
     */
    public void addBids(Bid bid){
        lowest();
        if (userBidded(bid.getName())) {
            updateBid(bid.getName(), bid.getAmount());
            return;
        }
        if (this.isEmpty()){
            lowest = bid;
        }
        else if (bid.getAmount()<lowest.getAmount()) {
            lowest =  bid;
        }
        this.add(bid);
    }

    /**
     *
     * @param name - the string repre the provider whose bid is being updated
     * @param amount - the amount to be changed on the bid
     */
    private void updateBid(String name, double amount) {
        Bid bid = getBid(name);
        if (bid != null) {
            bid.setAmount(amount);
            lowest();
        }
    }

    /**
     *
     * @param bid  - the bid to be removed from the list
     */
    public void delete(Bid bid){
        this.remove(bid);
        if (bid==lowest){
            lowest = getLowest();
        }
    }

    /**
     * Will set the lowest attribute of the bidlist
     */
    public void lowest(){
        if (lowest ==null && !this.isEmpty()){
            this.lowest = getLowest();
        }

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


    /**
     *
     * @return the size of the bidlist
     */
    public int getSize(){
        return this.size();
    }

    /**
     *
     * @return - boolean value, true if bidlist is empty, false if not empty
     */
    public boolean isEmpty(){
        return this.size()==0;
    }


    /**
     *
     * @param userName, representing the user name being looked for
     * @return boolean true if user name was found in bidlist, false otherwise
     */
    public boolean userBidded(String userName) {
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getName().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Deletes all bids besides the one supplied in the method call.
     * @param bid The bid to be kept
     */
    public void acceptBid(Bid bid){
        this.clear();
        this.add(bid);
    }
}
