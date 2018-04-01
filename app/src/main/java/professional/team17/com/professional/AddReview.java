package professional.team17.com.professional;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

public class AddReview extends AppCompatActivity {

    private String reviewer;
    private String profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        profile = getIntent().getStringExtra("profile");

        SharedPreferences sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        reviewer = sharedpreferences.getString("username", "error");

        final Button submit = findViewById(R.id.submitButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSubmit(view);
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

    private void onSubmit(View v){
        AddReviewController controller = new AddReviewController();
        EditText commentBox = v.findViewById(R.id.commentBox);
        RatingBar ratingBar = v.findViewById(R.id.ratingBar);
        int rating = ratingBar.getNumStars();
        String comment = commentBox.getText().toString();

        controller.setReview(profile, rating, comment, reviewer);

        finish();
    }

}
