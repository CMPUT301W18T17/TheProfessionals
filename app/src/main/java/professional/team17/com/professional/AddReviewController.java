package professional.team17.com.professional;

import android.content.Context;
import android.content.SharedPreferences;

public class AddReviewController {

    private Review review;
    private Profile profile;

    public AddReviewController(){}

    public void setReview(String reviewee, int rating, String comment, String reviewer) {
        review = new Review(rating, reviewer, comment);

    }
}
