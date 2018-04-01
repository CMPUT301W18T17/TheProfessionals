package professional.team17.com.professional;

import android.content.Context;
import android.content.SharedPreferences;

public class AddReviewController {

    private Review review;

    public AddReviewController(){}

    public void setReview(String profile, int rating, String comment, String reviewer) {

        SharedPreferences sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String username = sharedpreferences.getString("username", "error");
        review = new Review(rating, reviewer, comment);
    }
}
