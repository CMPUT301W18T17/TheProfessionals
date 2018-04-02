package professional.team17.com.professional;


import android.content.Context;
import android.content.SharedPreferences;

public class AddReviewController {

    private Context context;

    private ServerHelper serverHelper;

    AddReviewController(Context context){
        this.context = context;
        serverHelper = new ServerHelper(context);
    }

    public void setReview(String profileName, float rating, String comment) {
        SharedPreferences sharedpreferences = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String reviewer = sharedpreferences.getString("username", "error");
        Review review = new Review(rating, reviewer, comment);
        Profile profile = serverHelper.getProfile(profileName);
        ReviewList list = profile.getReviewList();

        list.addReview(review);
        profile.setReviewList(list);

        serverHelper.addProfile(profile);
    }
}
