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

    /**
     * Constructor for Review
     * @param score - score given by the reviewer
     * @param reviewer - the task requester
     * @param comment - comment given by the reviewer
     */
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

    /**
     *
     * @return score
     */
    public float getScore() {
        return this.score;
    }

    /**
     *
     * @return comment of review
     */
    public String getComment() {
        return this.comment;
    }

    /**
     *
     * @return reviewer
     */
    public String getProfileName() {
        return this.profileName;
    }

    /**
     *
     * @return date the review was made
     */
    public Date getDate(){return this.date;}

    /**
     * Set setters for updating all the attributes of the review object
     * @param score - set score for this review
     */

    /*SETTERS*/
    public void setScore(float score) {
        this.score = score;
    }

    /**
     *
     * @param comment - comment for this review
     */
    public void setComment(String comment) {
        this.comment = comment;

    }

    /**
     * @param name name of reviewer
     */
    private void setProfileName(String name) {
        this.profileName = name;
    }


}






