package professional.team17.com.professional;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;

/**
 * Created by ag on 2018-02-24.
 */

public class ProfileTest extends ActivityInstrumentationTestCase2 {

    public ProfileTest(){
        super(Profile.class);
    }

    private String name = "John Smith";
    public String userName = "john123";
    private String email = "johnSmith@email.ca";
    private String phoneNumber = "123-4567";

    private String newEmail = "imJohn@another.ca";
    private String newPhoneNumber = "765-4321";

    private ArrayList<String> aList = new ArrayList<String>();

    /* Test for Getters */
    public void testGetName(){
        Profile profile = new Profile(name, userName, email, phoneNumber);
        assertEquals(name, profile.getName());
    }
    public void testGetUserName(){
        Profile profile = new Profile(name, userName, email, phoneNumber);
        assertEquals(userName, profile.getUserName());
    }
    public void testGetEmail(){
        Profile profile = new Profile(name, userName, email, phoneNumber);
        assertEquals(email, profile.getEmail());
    }
    public void testGetPhoneNumber(){
        Profile profile = new Profile(name, userName, email, phoneNumber);
        assertEquals(phoneNumber, profile.getEmail());
    }
    /* Test for Setters */
    public void testSetEmail(){
        Profile profile = new Profile(name, userName, email, phoneNumber);
        profile.setEmail(newEmail);
        assertEquals(newEmail, profile.getEmail());
    }
    public void testSetPhoneNumber(){
        Profile profile = new Profile(name, userName, email, phoneNumber);
        profile.setEmail(newEmail);
        assertEquals(newPhoneNumber, profile.getEmail());
    }

    public void testAddToBlacklist(){
        Profile profile1 = new Profile(name, userName, email, phoneNumber);
        Profile profile2 = new Profile("Mary", "mair", "mair@email.ca", "111-1111");
        profile1.addToBlackList(profile2);

        aList.add("mair");
        assertEquals(aList, profile1.getBlackList());
    }
}
