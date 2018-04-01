package professional.team17.com.professional;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RatingBar;

public class AddReview extends AppCompatActivity {

    private EditText commentBox;
    private RatingBar ratingBar;
    private String comment;
    private int rating;
    private String profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        profile = getIntent().getStringExtra("Profile");
    }

    private void onSubmit(){
        commentBox = findViewById(R.id.commentBox);
        ratingBar = findViewById(R.id.ratingBar);

    }
}
