package professional.team17.com.professional;

import android.test.ActivityInstrumentationTestCase2;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Hailan on 2018-02-20.
 */

public class ReviewListTest extends ActivityInstrumentationTestCase2 {
    public ReviewListTest() {
        super(ReviewList.class);
    }

    public void testisEmpty() { //same as the test for hasReview
        ReviewList reviews = new ReviewList();
        assertTrue(reviews.isEmpty());
        Review review = new Review(5, "reviewer","comment");
        reviews.addReview(review);
        assertFalse(reviews.isEmpty());
    }


    public void testAddReview() { //same as the test for hasReview
        ReviewList reviews = new ReviewList();
        Review review = new Review(5, "reviewer","comment");
        reviews.addReview(review);
        assertTrue(reviews.hasReview(review));
    }

    public void testDeleteReview() {
        ReviewList reviews = new ReviewList();
        Review review = new Review(5.0, "reviewer","comment", "title" );
        reviews.addReview(review);
        reviews.deleteReview(review);
        assertFalse(reviews.hasReview(review));
    }


    public void testAddReviewDuplicate(){
        ReviewList reviews = new ReviewList();
        Review review1 = new Review(3.5, "a1", "t1");
        reviews.addReview(review1);
        try {
            reviews.addReview(review1);
            Assert.fail("Duplicate test failed - Duplicate Review added");
        }
        catch(Exception e){
            String mess= "Duplicate Review - cannot be added";
            Assert.assertEquals( "Duplicate test passed", mess, e.getMessage() );
        }

    }


    public void testHasReview(){
        ReviewList reviews = new ReviewList();
        Review review1 = new Review(3.5, "a1", "t1");
        assertFalse(reviews.hasReview(review1));

        reviews.addReview(review1);
        assertTrue(reviews.hasReview(review1));

        //for list size >1
        Review review2 = new Review(5.0, "a2", "t2");
        assertFalse(reviews.hasReview(review2));

    }

    public void testGetReview(){
        ReviewList reviews = new ReviewList();

        Review review1 = new Review(3.5, "a1", "t1");
        reviews.addReview(review1);

        Review review2 = new Review(5.0, "a2", "t2");
        reviews.addReview(review2);

        Review review3 = new Review(5.0, "a3", "t3");
        reviews.addReview(review3);


        ArrayList<Review> reviewList = new ArrayList<Review>(Arrays.asList(review1, review2, review3));

        assertEquals(reviews.getReviews(), reviewList);

    }

    public void testAvg() {
        ReviewList reviews = new ReviewList();
        Review review1 = new Review(3.5, "a1", "t1");
        reviews.addReview(review1);
        assertEquals(3.5, reviews.getAvg());

        Review review2 = new Review(5.0, "a2", "t2");
        reviews.addReview(review2);
        assertEquals(4.25, reviews.getAvg());

        Review review3 = new Review(5.0, "a3", "t3");
        reviews.addReview(review3);
        assertEquals(4.5, reviews.getAvg());

        reviews.deleteReview(review1);

        assertEquals(5.0, reviews.getAvg());
    }
}
