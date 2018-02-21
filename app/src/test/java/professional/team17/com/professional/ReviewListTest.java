package professional.team17.com.professional;

import android.test.ActivityInstrumentationTestCase2;

import java.util.List;

/**
 * Created by Hailan on 2018-02-20.
 */

public class ReviewListTest extends ActivityInstrumentationTestCase2 {
    public ReviewListTest(){
        super(ReviewList.class);
    }

    public void testAddReview(){ //same as the test for hasReview
        ReviewList reviews = new ReviewList();
        //Review review = new Review();
        //reviews.add(review);
        //assertTrue(reviews.hasReview(review));
    }

    public void testDeleteReview(){
        ReviewList reviews = new ReviewList();
        //Review review = new Review();
        //reviews.add(review);
        //reviews.delete(review);
        //assertFalse(reviews.hasReview(review));
    }
}
