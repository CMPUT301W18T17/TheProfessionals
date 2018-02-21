package professional.team17.com.professional;

import java.util.ArrayList;

/**
 * Created by Hailan on 2018-02-20.
 */

public class ReviewList {
    private ArrayList<Review> reviews = new ArrayList<Review>();

    public ReviewList(){
        // Nothing
    }

    public boolean hasReview(Review review){
        return reviews.contains(review);
    }

    public void addReview(Review review){ reviews.add(review);}

    public void deleteReview(Bid bid){
        reviews.remove(bid);
    }
}
