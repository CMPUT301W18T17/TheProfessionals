package professional.team17.com.professional;

import java.util.Date;

public class Review {

    private Double score;
    private String comment;
    private String profileName; //person who gave review
    private String reviewTitle;
    private Date date;

    public Review(Double score, String reviewer, String comment, String title) {
        this.score = score;
        this.profileName = reviewer;
        this.comment = comment;
        this.reviewTitle = title;
        this.date = new Date();
    }

    public Review(Double score, String reviewer, String title) {
        this.score = score;
        this.profileName = reviewer;
        this.reviewTitle = title;
        this.date = new Date();
    }
    /* GETTERS */

    public Double getScore() {
        return this.score;
    }

    public String getComment() {
        return this.comment;
    }

    public String getProfileName() {
        return this.profileName;
    }

    public String getReviewTitle() {
        return this.reviewTitle;
    }


    /*SETTERS*/
    public void setScore(Double score) {
        this.score = score;
    }


    public void setComment(String comment) {
        this.comment = comment;

    }

    private void setProfileName(String name) {
        this.profileName = name;
    }

    public void setReviewTitle(String title) {
        this.reviewTitle = title;
    }

    public Date getDate(){
        return this.date;

    }



}






