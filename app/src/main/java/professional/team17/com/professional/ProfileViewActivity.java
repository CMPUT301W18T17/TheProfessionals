package professional.team17.com.professional;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Abstract class ProfileViewActivity generalizes MyProfileViewActivity and OtherProfileViewActivity
 * @version 2.0 Last Updated: Mar 12, 2018
 * @author Hailan
 * @see MyProfileViewActivity
 * @see OtherProfileViewActivity
 * @see ReviewsAdaptor
 */
public abstract class ProfileViewActivity extends AppCompatActivity{
    protected TextView username;
    protected TextView name;
    protected TextView email;
    protected TextView phoneNumber;
    protected TextView currentMode;
    protected Button doneButton;
    protected ReviewsAdaptor reviewsAdaptor;
    protected ListView listView;
    protected RatingBar ratingBar;
    protected ImageButton profilePic;

    private final ElasticSearchController elasticSearchController = new ElasticSearchController();

    /**
     * On selecting a profile
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);
        currentMode = (TextView) findViewById(R.id.currentModeTV);
        doneButton = (Button) findViewById(R.id.doneButton);
        listView = (ListView) findViewById(R.id.listViewID);

    }

    /**
     * Set tags/hints of layout
     * @param aUserName
     */
    // don't call setInfo from here. Call it from MyProfileViewActivity or OtherProfileViewActivity
    protected void setInfo(String aUserName) {
        username =findViewById(R.id.userNameTV);
        name =findViewById(R.id.nameTV);
        email = findViewById(R.id.emailTV);
        phoneNumber = findViewById(R.id.phoneTV);
        ratingBar = findViewById(R.id.rating2);

        Profile userProfile = elasticSearchController.getProfile(aUserName);
        username.setText(aUserName);
        name.setText(userProfile.getName());
        email.setText(userProfile.getEmail());
        phoneNumber.setText(userProfile.getPhoneNumber());
        ratingBar.setRating((float)(userProfile.getReviewList().getAvg()));
        reviewsAdaptor = new ReviewsAdaptor(this, R.layout.reviewlist_item_format, userProfile.getReviewList());
    }


}
