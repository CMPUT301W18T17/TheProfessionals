/*
 * AddReview
 *
 * April 02, 2018
 *
 * Copyright @ 2018 Team 17, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behaviour at the University of Alberta.
 * You can find a copy of the license in the github wiki for this project.
 */

package professional.team17.com.professional;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Allows the user to add a review to a user who has completed a task for them
 *
 * @see AddReviewController
 */
public class AddReview extends AppCompatActivity {

    private String profile;

    /**
     * Initializes the activity and sets listeners for buttons
     * @param savedInstanceState holds value from intent "profile"
     */
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

        final ImageButton back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    /**
     * retrieves the values from the view and sends them to the AddReviewController
     *
     * @see AddReviewController
     */
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
