package professional.team17.com.professional;

import android.test.ActivityInstrumentationTestCase2;

import java.util.Date;

/**
 * Created by Zhipeng Zhang on 2018-02-21.
 */

public class ReviewTest extends ActivityInstrumentationTestCase2 {

    private double score = 2.3;
    private double newScore = 4.0;
    private String comment = "Comment";
    private String newComment = "New Comment";
    private String profileName = "Name";
    private String reviewTitle = "Title";
    private String newReviewTitle = "New Title";
    private Date date, returnDate;
    private String strDate, strReturnDate;

    public ReviewTest(){
        super(Review.class);
    }

    /* Test for Getters */
    public void testGetScore(){
        Review review = new Review(score, profileName, comment, reviewTitle);
        assertEquals(score, review.getScore());
    }

    public void testGetComment(){
        Review review = new Review(score, profileName, comment, reviewTitle);
        assertEquals(comment, review.getComment());
    }

    public void testGetProfileName(){
        Review review = new Review(score, profileName, comment, reviewTitle);
        assertEquals(profileName, review.getProfileName());
    }

    public void testGetReviewTitle(){
        Review review = new Review(score, profileName, comment, reviewTitle);
        assertEquals(reviewTitle, review.getReviewTitle());
    }

    public void testGetDate(){
        Review review = new Review(score, profileName, comment, reviewTitle);
        returnDate = review.getDate();
        strReturnDate = returnDate.toString();
        date = new Date();
        strDate = date.toString();
        assertEquals(strReturnDate, strDate);
    }

    /* Test for Setters */
    public void testSetScore(){
        Review review = new Review(score, profileName, comment, reviewTitle);
        review.setScore(newScore);
        assertEquals(newScore, review.getScore());
    }

    public void testSetComment(){
        Review review = new Review(score, profileName, comment, reviewTitle);
        review.setComment(newComment);
        assertEquals(newComment, review.getComment());
    }

    public void testSetReviewTitle(){
        Review review = new Review(score, profileName, comment, reviewTitle);
        review.setReviewTitle(newReviewTitle);
        assertEquals(newReviewTitle, review.getReviewTitle());
    }
}
