/*
 * AddReviewController
 *
 * April 02, 2018
 *
 * Copyright @ 2018 Team 17, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behaviour at the University of Alberta.
 * You can find a copy of the license in the github wiki for this project.
 */

package professional.team17.com.professional;


import android.content.Context;
import android.content.SharedPreferences;

/**
 * Takes the values for a review and saves it to the reviewed profile
 *
 * @see Review
 * @see ReviewList
 * @see Profile
 */
public class AddReviewController {

    private Context context;

    private ServerHelper serverHelper;

    /**
     * Constructs an AddReviewController object
     *
     */
    AddReviewController(Context context){
        this.context = context;
        serverHelper = new ServerHelper(context);
    }

    /**
     * Creates a review object and attaches it to the reviewed profile.
     *
     * @param profileName name of the profile being reviewed
     * @param rating rating given by the user
     * @param comment comment provided by the user
     */
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
