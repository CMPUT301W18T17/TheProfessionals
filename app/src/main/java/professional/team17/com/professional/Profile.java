package professional.team17.com.professional;

import android.provider.ContactsContract;

import java.util.ArrayList;

public class Profile {

    private String name;
    public String userName;
    private String email;
    private String phoneNumber;
    private ReviewList reivewList;
    private ArrayList<String> blackList;
    private ArrayList<String> blackListedBy;

    public Profile(String name, String userName, String email, String phoneNumber) {
        this.name = name;
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.reivewList = new ReviewList();
        this.blackList = new ArrayList<String>();
        this.blackListedBy = new ArrayList<String>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // for methods involving reviewList, please consult the ReviewList class

    public ArrayList<String> getBlackList() {
        return blackList;
    }

    public ArrayList<String> getBlackListedBy() {
        return blackListedBy;
    }

    public void addToBlackList(Profile profile){
        blackList.add(profile.getUserName());

    }

}
