package professional.team17.com.professional;

import android.test.ActivityInstrumentationTestCase2;

import java.util.Date;

/**
 * Created by Richard on 2018/2/21.
 */

public class ReviewTest extends ActivityInstrumentationTestCase2 {

    private Double score, returnScore, newScore;
    private String comment, returnComment, newComment;
    private String profileName, returnProfileName; //person who gave review
    private String reviewTitle, returnReviewTitle, newReviewTitle;
    private Date date, returnDate;
    String strDate, strReturnDate;

    public ReviewTest(){
        super(Review.class);
    }
    /* Test for Getters */
    public void TestGetScore(){
        Review review = new Review(this.score, this.profileName, this.comment, this.reviewTitle);
        returnScore = review.getScore();
        assertEquals(returnScore, this.score);
    }

    public void TestGetComment(){
        Review review = new Review(this.score, this.profileName, this.comment, this.reviewTitle);
        returnComment = review.getComment();
        assertEquals(returnComment, this.comment);
    }

    public void TestGetProfileName(){
        Review review = new Review(this.score, this.profileName, this.comment, this.reviewTitle);
        returnProfileName = review.getProfileName();
        assertEquals(returnProfileName, this.profileName);
    }

    public void TestGetReviewTitle(){
        Review review = new Review(this.score, this.profileName, this.comment, this.reviewTitle);
        returnReviewTitle = review.getReviewTitle();
        assertEquals(returnReviewTitle, this.reviewTitle);
    }

    public void TestGetDate(){
        Review review = new Review(this.score, this.profileName, this.comment, this.reviewTitle);
        returnDate = review.getDate();
        strReturnDate = returnDate.toString();
        date = new Date();
        strDate = date.toString();
        assertEquals(strReturnDate, this.strDate);
    }

    /* Test for Setters */
    public void TestSetScore(){
        Review review = new Review(this.score, this.profileName, this.comment, this.reviewTitle);
        review.setScore(this.newScore);
        returnScore = review.getScore();
        assertEquals(returnScore, this.newScore);
    }

    public void TestSetComment(){
        Review review = new Review(this.score, this.profileName, this.comment, this.reviewTitle);
        review.setComment(this.newComment);
        returnComment = review.getComment();
        assertEquals(returnComment, this.newComment);
    }

    public void TestSetReviewTitle(){
        Review review = new Review(this.score, this.profileName, this.comment, this.reviewTitle);
        review.setReviewTitle(this.newReviewTitle);
        returnReviewTitle = review.getReviewTitle();
        assertEquals(returnReviewTitle, this.newReviewTitle);
    }

}
