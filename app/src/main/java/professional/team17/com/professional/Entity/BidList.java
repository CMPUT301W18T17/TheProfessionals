/*
 * BidList
 *
 * February 20, 2018
 *
 * Copyright @ 2018 Team 17, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behaviour at the University of Alberta.
 * You can find a copy of the license in the github wiki for this project.
 */

package professional.team17.com.professional.Entity;

import java.io.Serializable;
import java.util.ArrayList;


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
        //check if user has bidded before
        if (userBidded(bid.getName())) {
            Bid temp = getBid(bid.getName());
            temp.setAmount(bid.getAmount());
            lowest();
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
     * @param bid  - the bid to be removed from the list
     */
    public void delete(Bid bid){
        this.remove(bid);
        if (bid==lowest){
            lowest = getLowest();
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
     * @param userName representing the user name being looked for
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
     * Will set the lowest attribute of the bidlist
     */
    private void lowest(){
        if (lowest ==null && !this.isEmpty()){
            this.lowest = getLowest();
        }

    }

}
