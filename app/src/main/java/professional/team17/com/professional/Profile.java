/*
 * Profile
 *
 * February 20, 2018
 *
 * Copyright @ 2018 Team 17, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behaviour at the University of Alberta.
 * You can find a copy of the license in the github wiki for this project.
 */
package professional.team17.com.professional;

import android.graphics.Bitmap;
import android.provider.ContactsContract;

import java.io.Serializable;
import java.util.ArrayList;



/**
 *
 * This is the entity class for a profile object
 *
 *
 * @author Allison
 * @see ReviewList
 */
public class Profile implements Serializable {

    private String name;
    public String userName;
    private String email;
    private String phoneNumber;
    private ReviewList reviewList;
    private NotificationList notificationList;
    private ArrayList<String> blackList;
     private ArrayList<String> blackListedBy;
     private String photoString;
     private Bitmap.Config config;
     private  int width;
     private  int height;
     private ProfilePhoto profilePhoto;

    /**
     *
     * @param name - the string name of the user
     * @param userName - the unique string username of the user
     * @param email  - the string email of the user
     * @param phoneNumber - the string phone number of the user
     */
    public Profile(String name, String userName, String email, String phoneNumber, ProfilePhoto profilePhoto) {
        this.name = name;
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.reviewList = new ReviewList();
        this.blackList = new ArrayList<String>();
        this.blackListedBy = new ArrayList<String>();
        this.profilePhoto = profilePhoto;
        notificationList = new NotificationList();
    }

    public Profile(String name, String userName, String email, String phoneNumber) {
        this.name = name;
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.reviewList = new ReviewList();
        this.blackList = new ArrayList<String>();
        this.blackListedBy = new ArrayList<String>();
        this.photoString = "-1";
        this.config = null;
        this.width = 0;
        this.height = 0;
        profilePhoto = new ProfilePhoto(photoString,config,width,height);
        notificationList = new NotificationList();
    }

    /**
     *
     * @return a string repr the users full name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name - the string repre the name of the user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return a string repr the users unique username
     */
    public String getUserName() {
        return userName;
    }


    /**
     *
     * @param userName  a string repr the users unique username
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     *
     * @return a string repr the users email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email a string repr the users email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return  a string repr the users phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     *
     * @param phoneNumber a string repr the users phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }



    /**
     *
     * @return the review list of review placed against the user
     * for methods involving reviewList, please consult the ReviewList class
     */
    public ReviewList getReviewList(){
        return reviewList;
    }

    /**
     *
     * @return - an array list of usernames - those that the user has blacklisted
     */
    public ArrayList<String> getBlackList() {
        return blackList;
    }

    /**
     *
     * @return - - an array list of usernames - those that have blacklisted the user
     */
    public ArrayList<String> getBlackListedBy() {
        return blackListedBy;
    }

    /**
     *
     * @param profile - the user being blacklisted
     */
    public void addToBlackList(Profile profile){
        blackList.add(profile.getUserName());

    }

    /**
     *
     * @param username - the username of the user blacklisting the profile
     */
    public void addtoGetBlackListedBy(String username){
        blackListedBy.add(username);
    }

    /**
     *
     * @param username - the username of the profile that had blacklisted the user
     */
    public void removeFromBlackListed(String username){
        blackList.remove(username);
    }


    /**
     *
     * @param profile - the profile being removed from the blacklist
     */
    public void removeFromBlackList(Profile profile){
        blackList.remove(profile);
    }

    public void setProfilePhoto(ProfilePhoto profilePhoto){
        this.profilePhoto = profilePhoto;
    }

    public ProfilePhoto getProfilePhoto(){
        return this.profilePhoto;
    }


    public void setNotificationList(NotificationList notificationList){
        this.notificationList = notificationList;
    }

    public void setReviewList(ReviewList reviewList) {
        this.reviewList = reviewList;
    }

    public NotificationList getNotificationList(){
        return notificationList;
    }


}
