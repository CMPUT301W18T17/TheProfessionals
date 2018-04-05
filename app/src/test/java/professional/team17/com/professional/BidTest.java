package professional.team17.com.professional;

import android.test.ActivityInstrumentationTestCase2;


public class BidTest extends ActivityInstrumentationTestCase2 {

    public BidTest(){
        super(Bid.class);
    }

    public void testGetName(){
        String name = "Tester";
        double amount = 10.99;
        Bid bid = new Bid(name,amount);

        assertEquals(name, bid.getName());
    }

    public void testSetName(){
        String name = "Tester";
        double amount = 10.99;
        Bid bid = new Bid(name,amount);

        String name2 = "Tester2";
        bid.setName(name2);

        assertEquals(name2, bid.getName());
    }

    public void testGetAmount(){
        String name = "Tester";
        double amount = 10.99;
        Bid bid = new Bid(name,amount);

        assertEquals(amount, bid.getAmount());
    }

    public void testSetAmount(){
        String name = "Tester";
        double amount = 10.99;
        Bid bid = new Bid(name,amount);

        double amount2 = 9.99;
        bid.setAmount(amount2);
        assertEquals(amount2, bid.getAmount());

    }

    public void testAmountString(){
        String name = "Tester";
        double amount = 10.99;
        Bid bid = new Bid(name,amount);
        assertEquals(bid.getAmountAsString(), "10.99");
    }
}
