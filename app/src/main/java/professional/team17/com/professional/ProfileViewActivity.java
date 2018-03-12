package professional.team17.com.professional;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public abstract class ProfileViewActivity extends AppCompatActivity {
    protected TextView username;
    protected TextView name;
    protected TextView email;
    protected TextView phoneNumber;
    protected TextView currentMode;
    protected Button doneButton;
    protected ReviewsAdaptor reviewsAdaptor;
    protected ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);
        currentMode = (TextView) findViewById(R.id.place_bid_fragment_title);
        doneButton = (Button) findViewById(R.id.doneButton);
        listView = (ListView) findViewById(R.id.listViewID);

    }

    // don't call setInfo from here. Call it from MyProfileViewActivity or OtherProfileViewActivity
    protected void setInfo(String aUserName, String aName, String anEmail, String aPhoneNumber) {
        username =findViewById(R.id.userNameTV);
        name =findViewById(R.id.nameTV);
        email = findViewById(R.id.emailTV);
        phoneNumber = findViewById(R.id.phoneTV);

        username.setText(aUserName);
        name.setText(aName);
        email.setText(anEmail);
        phoneNumber.setText(aPhoneNumber);
    }

    @Override
    protected void onStart(){
        super.onStart();

        //MockReviews
        ReviewList aListOfReviews = new ReviewList();
        Review review1 = new Review(3.5, "a1", "t1");
        aListOfReviews.addReview(review1);
        Review review2 = new Review(5.0, "a2", "t2");
        aListOfReviews.addReview(review2);
        Review review3 = new Review(5.0, "a3", "t3");
        aListOfReviews.addReview(review3);

        reviewsAdaptor = new ReviewsAdaptor(this, R.layout.reviewlist_item_format, aListOfReviews);
        listView.setAdapter(reviewsAdaptor);

    }
}
