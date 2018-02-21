package professional.team17.com.professional;

public class Review {

    private Double score;
    private String comment;
    private String profileName; //person who gave review
    private String reviewTitle;

    public Review(Double score, String reviewer, String comment, String title) {
        this.score = score;
        this.profileName = reviewer;
        this.comment = comment;
        this.reviewTitle = title;

    }

    public Review(Double score, String reviewer, String title) {
        this.score = score;
        this.profileName = reviewer;
        this.reviewTitle = title;
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

}






