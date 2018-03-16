package professional.team17.com.professional;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by Logan Yue on 2018-02-20.
 */

public class BidTest extends ActivityInstrumentationTestCase2 {

    public BidTest(){
        super(Bid.class);
    }

    public void testGetName(){
        String name = "person";
        double amount = 10.99;
        Bid bid = new Bid(name,amount);

        assertEquals(name, bid.getName());
    }

    public void testSetName(){
        String name = "person";
        double amount = 10.99;
        Bid bid = new Bid(name,amount);

        name = "notPerson";
        bid.setName(name);

        assertEquals(name, bid.getName());
    }

    public void testGetAmount(){
        String name = "person";
        double amount = 10.99;
        Bid bid = new Bid(name,amount);

        assertEquals(amount, bid.getAmount());
    }

    public void testSetAmount(){
        String name = "person";
        double amount = 10.99;
        Bid bid = new Bid(name,amount);

        amount = 9.99;
        bid.setAmount(amount);
        assertEquals(amount, bid.getAmount());

        boolean exception = false;
        try{
            bid.setAmount(10.2831);
        } catch(IllegalArgumentException e){
            exception = true;
        }

        assertTrue(exception);

    }

    public void testUpdateAmount(){
        String name = "person";
        double amount = 10.99;
        Bid bid = new Bid(name,amount);
        bid.setAmount(34.99);
        assertEquals(34.99, bid.getAmount());
    }

    public void testAmountString(){
        String name = "person";
        double amount = 10.99;
        Bid bid = new Bid(name,amount);
        assertEquals(bid.getAmountAsString(), "10.99");
    }

    public void testGetStatus(){
        String name = "person";
        double amount = 10.99;
        Bid bid = new Bid(name,amount);
        String status = "open";

      //  assertEquals(bid.getStatus(), status);
    }

    public void testSetStatus(){
        String name = "person";
        double amount = 10.99;
        Bid bid = new Bid(name,amount);

       // bid.setStatus("accepted");

       // assertEquals("accepted", bid.getStatus());
    }
}
