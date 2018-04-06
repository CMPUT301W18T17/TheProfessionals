/*
 * Review
 *
 * February 20, 2018
 *
 * Copyright @ 2018 Team 17, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behaviour at the University of Alberta.
 * You can find a copy of the license in the github wiki for this project.
 */
package professional.team17.com.professional;

import java.util.Date;

/**
 * @author Logan
 * @version 1.0
 * @see ReviewList
 * this class is mainly created for describe a new review object
 */
public class Review {

    private float score;
    private String comment;
    private String profileName; //person who gave review

    private Date date;

    public Review(float score, String reviewer, String comment) {
        this.score = score;
        this.profileName = reviewer;
        this.comment = comment;
        this.date = new Date();
    }

//    public Review(double score, String reviewer, String title) {
//        this.score = score;
//        this.profileName = reviewer;
//        this.reviewTitle = title;
//        this.date = new Date();
//    }
    /* GETTERS */

    /**
     * Set getters for getting all the attributes from the review object
     * @return String or Double
     */

    public float getScore() {
        return this.score;
    }

    public String getComment() {
        return this.comment;
    }

    public String getProfileName() {
        return this.profileName;
    }

    public Date getDate(){return this.date;}

    /**
     * Set setters for updating all the attributes of the review object
     * @param score
     */

    /*SETTERS*/
    public void setScore(float score) {
        this.score = score;
    }


    public void setComment(String comment) {
        this.comment = comment;

    }

    private void setProfileName(String name) {
        this.profileName = name;
    }


}






