package professional.team17.com.professional;

import android.test.ActivityInstrumentationTestCase2;

import java.util.List;

/**
 * Created by Hailan on 2018-02-20.
 */

public class ReviewListTest extends ActivityInstrumentationTestCase2 {
    public ReviewListTest() {
        super(ReviewList.class);
    }

    public void testAddReview() { //same as the test for hasReview
        ReviewList reviews = new ReviewList();
        //Review review = new Review();
        //reviews.add(review);
        //assertTrue(reviews.hasReview(review));
    }

    public void testDeleteReview() {
        ReviewList reviews = new ReviewList();
        //Review review = new Review();
        //reviews.add(review);
        //reviews.delete(review);
        //assertFalse(reviews.hasReview(review));
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
