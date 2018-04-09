package professional.team17.com.professional;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;

import professional.team17.com.professional.Entity.Profile;


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
}
