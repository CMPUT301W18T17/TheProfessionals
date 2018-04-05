package professional.team17.com.professional;

import android.test.ActivityInstrumentationTestCase2;

import java.util.Date;


public class ReviewTest extends ActivityInstrumentationTestCase2 {

    private float score = (float) 5.0;
    private String reviewer = "reviewer";
    private String comment = "comment";
    private Review review = new Review(score, reviewer, comment);

    public ReviewTest(){
        super(Review.class);
    }

    /* Test for Getters */
    public void testGetScore(){
        assertEquals(score, review.getScore());
    }

    public void testGetComment(){
        assertEquals(comment, review.getComment());
    }

    public void testGetProfileName(){
        assertEquals(reviewer, review.getProfileName());
    }

    public void testGetDate(){
        Date returnDate = review.getDate();
        Date date = new Date();
        assertEquals(returnDate, date);
    }

    /* Test for Setters */
    public void testSetScore(){
        float score = (float) 3.5;
        review.setScore(score);
        assertEquals(score, review.getScore());
    }

    public void testSetComment(){
        String newComment = "newComment;";
        review.setComment(newComment);
        assertEquals(newComment, review.getComment());
    }
}
