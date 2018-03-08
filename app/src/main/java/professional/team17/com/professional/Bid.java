/*
 * Bid
 *
 * February 20, 2018
 *
 * Copyright goes here
 */

package professional.team17.com.professional;

import java.io.Serializable;
import java.text.DecimalFormat;


public class Bid implements Serializable{

    private String name;
    private double amount;
    private String status = "open";

    public Bid(String name, double amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    /**
     *
     * @return amount as string to two decimal places
     */
    public String getAmountAsString(){
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String amount = decimalFormat.format(this.amount);
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
