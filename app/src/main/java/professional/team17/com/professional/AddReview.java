package professional.team17.com.professional;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

public class AddReview extends AppCompatActivity {

    private String profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        profile = getIntent().getStringExtra("profile");

        final Button submit = findViewById(R.id.submitButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSubmit();
            }
        });

        final Button back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void onSubmit(){
        AddReviewController controller = new AddReviewController(this);
        EditText commentBox = findViewById(R.id.commentBox);
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        float rating = ratingBar.getRating();
        String comment = commentBox.getText().toString();

        if (comment.length() > 300){
            TextView errorMessage = findViewById(R.id.errorBox);
            errorMessage.setText(R.string.charLimit);
        } else {
            controller.setReview(profile, rating, comment);

            finish();
        }
    }
}
