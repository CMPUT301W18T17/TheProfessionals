package professional.team17.com.professional;

import java.io.Serializable;

/**
 * Created by Logan Yue on 2018-02-20.
 */

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
