/*
 * Bid
 *
 * February 20, 2018
 *
 * Copyright @ 2018 Team 17, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behaviour at the University of Alberta.
 * You can find a copy of the license in the github wiki for this project.
 */

package professional.team17.com.professional.Entity;

import java.io.Serializable;
import java.text.DecimalFormat;


/**
 *
 * This is the entity class for a bid object
 *
 *
 * @author Allison
 * @see BidList
 */
public class Bid implements Serializable{

    private String name;
    private double amount;
   // private String status = "open";

    /**
     *
     * @param name - username of provider placing the bid
     * @param amount - double repr the amount the provider is placing
     */
    public Bid(String name, double amount) {
        this.name = name;
        this.amount = amount;
    }

    /**
     *
     * @return - username of provider as string
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name - the user name of the provider bidding
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return -amount of bid as double
     */
    public double getAmount() {
        return amount;
    }

    /**
     *
     * @return amount as string to two decimal places
     */
    public String getAmountAsString(){
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        return decimalFormat.format(this.amount);
    }

    /**
     *
     * @param amount - the double repre the amount of bid
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

}
