/*
 * Profile
 *
 * February 20, 2018
 *
 * Copyright @ 2018 Team 17, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behaviour at the University of Alberta.
 * You can find a copy of the license in the github wiki for this project.
 */
package professional.team17.com.professional.Entity;

import java.io.Serializable;


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
        this.profilePhoto = profilePhoto;
        notificationList = new NotificationList();
    }

    public Profile(String name, String userName, String email, String phoneNumber) {
        this.name = name;
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.reviewList = new ReviewList();
        profilePhoto = new ProfilePhoto("-1", null, 0, 0);
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
     * @param profilePhoto - the photo of current profile
     */
    public void setProfilePhoto(ProfilePhoto profilePhoto){
        this.profilePhoto = profilePhoto;
    }

    /**
     *
     * @return - the photo of current profile
     */
    public ProfilePhoto getProfilePhoto(){
        return this.profilePhoto;
    }

    /**
     *
     * @param notificationList - list of notifications of current profile
     */
    public void setNotificationList(NotificationList notificationList){
        this.notificationList = notificationList;
    }

    /**
     *
     * @param reviewList - set reviews for current profile
     */
    public void setReviewList(ReviewList reviewList) {
        this.reviewList = reviewList;
    }

    /**
     *
     * @return - get all notifications of current profile
     */
    public NotificationList getNotificationList(){
        return notificationList;
    }


}
