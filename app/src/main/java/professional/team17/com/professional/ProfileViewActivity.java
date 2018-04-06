/*
 * ProfileViewActivity
 *
 * March 12, 2018
 *
 * Copyright @ 2018 Team 17, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behaviour at the University of Alberta.
 * You can find a copy of the license in the github wiki for this project.
 */
package professional.team17.com.professional;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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
    protected ImageButton doneButton;
    protected ReviewsAdaptor reviewsAdaptor;
    protected ListView listView;
    protected RatingBar ratingBar;
    protected ImageView profilePic;
    protected String photoString;
    protected Photo photo;
    protected Bitmap.Config photoConfig;
    protected int photoWidth;
    protected int photoHeight;
    protected ProfilePhoto profilePhoto;

    protected ServerHelper serverHelper;

    /**
     * On selecting a profile
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);
        currentMode = (TextView) findViewById(R.id.currentModeTV);
        doneButton = (ImageButton) findViewById(R.id.doneButton);
        listView = (ListView) findViewById(R.id.listViewID);
        serverHelper = new ServerHelper(this);
    }

    /**
     * Set tags/hints of layout
     * @param aUserName
     */
    // don't call setInfo from here. Call it from MyProfileViewActivity or OtherProfileViewActivity
    protected void setInfo(String aUserName) {
        Profile userProfile = serverHelper.getProfile(aUserName);
        ConnectedState c = ConnectedState.getInstance();
        if (c.isOffline()) {
            offline();
        }
        else {
            username = findViewById(R.id.userNameTV);
            name = findViewById(R.id.nameTV);
            email = findViewById(R.id.emailTV);
            phoneNumber = findViewById(R.id.phoneTV);
            ratingBar = findViewById(R.id.rating2);
            profilePic = findViewById(R.id.profilePicButton);
            username.setText(aUserName);
            name.setText(userProfile.getName());
            email.setText(userProfile.getEmail());
            phoneNumber.setText(userProfile.getPhoneNumber());
            ratingBar.setRating((float) (userProfile.getReviewList().getAvg()));
            reviewsAdaptor = new ReviewsAdaptor(this, R.layout.reviewlist_item_format, userProfile.getReviewList());
            listView.setAdapter(reviewsAdaptor);
            addPhoto(userProfile);
        }
    }

    void addPhoto(Profile userProfile){
        // Photo Part
        profilePhoto = userProfile.getProfilePhoto();
        photoConfig = profilePhoto.getConfig();
        if (photoConfig != null) {
            photoString = profilePhoto.getPhotoString();
            photoHeight = profilePhoto.getHeight();
            photoWidth = profilePhoto.getWidth();
            photo = new Photo(photoString, photoConfig, photoWidth, photoHeight);
            profilePic.setImageDrawable(photo.toDrawable(photo.byteStringToBitMap()));
        }
    }

    void offline() {

            Offline fragment = new Offline();
            getSupportFragmentManager().beginTransaction().replace(R.id.profile_view_layout, fragment).commit();
        }
    }

