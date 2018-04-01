package professional.team17.com.professional;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RatingBar;

public class AddReview extends AppCompatActivity {

    private EditText commentBox;
    private RatingBar ratingBar;
    private String reviewer;
    private String comment;
    private int rating;
    private String profile;
    private AddReviewController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        profile = getIntent().getStringExtra("Profile");

        SharedPreferences sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        reviewer = sharedpreferences.getString("username", "error");

    }

    private void onSubmit(){
        controller = new AddReviewController();
        commentBox = findViewById(R.id.commentBox);
        ratingBar = findViewById(R.id.ratingBar);

        controller.setReview(profile, rating, comment, reviewer);


    }
}
