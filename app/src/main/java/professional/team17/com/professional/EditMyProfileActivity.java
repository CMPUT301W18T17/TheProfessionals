/*
 * EditMyProfileActivity
 *
 * March 13, 2018
 *
 * Copyright @ 2018 Team 17, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behaviour at the University of Alberta.
 * You can find a copy of the license in the github wiki for this project.
 */

package professional.team17.com.professional;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * EditMyProfileActivity starts after user selects the UserMenuButton --> "Edit My Profile"
 * @author Hailan
 * @version 3.0 Last updated: Mar 13, 2018
 * @see ServerHelper
 * @see ProfileViewActivity
 */
public class EditMyProfileActivity extends AppCompatActivity {
    private TextView showUserName;
    private EditText editName;
    private EditText editEmail;
    private EditText editPhone;
    private ImageView photoButton;

    private Profile userProfile;

    private String photoString;
    private Photo photo;
    private Bitmap.Config photoConfig;
    private Bitmap bitmap;
    private int photoWidth;
    private int photoHeight;
    private String information;
    private String path, name, eMail, phoneNumber;
    private  int startTime;
    private ProfilePhoto profilePhoto;

    private ServerHelper serverHelper;

    /**
     * Upon creation, EditText will be set with relevant user info grabbed from ES
     *
     * Select 'save' to update info for user and go back to last activity
     * Select 'cancel' to go back to last activity
     *
     * @param savedInstanceState default parameter
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serverHelper = new ServerHelper(this);
        /* initialization of objects on layout and user's info*/
        setContentView(R.layout.activity_edit_my_profile);
        Button saveButton = (Button) findViewById(R.id.saveButton);
        ImageButton cancelButton = (ImageButton) findViewById(R.id.cancelButton);
        photoButton = findViewById(R.id.imageButton);

        showUserName = findViewById(R.id.showUserName);
        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editPhone = findViewById(R.id.editPhone);

        SharedPreferences pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String theUserName = pref.getString("username", "error");
        userProfile = serverHelper.getProfile(theUserName);

        showUserName.setText(theUserName);
        editName.setText(userProfile.getName());
        editEmail.setText(userProfile.getEmail());
        editPhone.setText(userProfile.getPhoneNumber());

        // Display photo Part
        // Photo Part
        profilePhoto = userProfile.getProfilePhoto();
        photoConfig = profilePhoto.getConfig();
        if (photoConfig != null) {
            photoString = profilePhoto.getPhotoString();
            photoHeight = profilePhoto.getHeight();
            photoWidth = profilePhoto.getWidth();
            photo = new Photo(photoString, photoConfig, photoWidth, photoHeight);
            photoButton.setImageDrawable(photo.toDrawable(photo.byteStringToBitMap()));
        }

        // Get the Intent that started this activity
        Intent intent = getIntent();

        // Get return information
        name = intent.getStringExtra("name");
        eMail = intent.getStringExtra("eMail");
        phoneNumber = intent.getStringExtra("phoneNumber");
        path = intent.getStringExtra("photoPath");
        bitmap = (Bitmap) intent.getParcelableExtra("photoBitmap");
        startTime = intent.getIntExtra("startTime", 0);

        if (startTime == 1 && path != null) {
            photo = new Photo(path);
            photoButton.setImageDrawable(photo.pathToDrawable());
        }
        else {
            if (startTime == 1 && bitmap != null) {
                photo = new Photo(bitmap);
                photoButton.setImageDrawable(photo.bitmapToDrawable());
            }
        }

        if (startTime == 1) {
            editName.setText(name);
            editEmail.setText(eMail);
            editPhone.setText(phoneNumber);
        }

        /* OnClickListeners for save and cancel */
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userProfile.setName(editName.getText().toString());
                userProfile.setEmail(editEmail.getText().toString());
                userProfile.setPhoneNumber(editPhone.getText().toString());
                if (startTime == 1 && path != null){
                    photoString = photo.pathToString();
                    photoConfig = photo.pathGetConfig();
                    photoHeight = photo.pathGetHeight();
                    photoWidth = photo.pathGetWidth();

                    profilePhoto = new ProfilePhoto(photoString, photoConfig, photoWidth, photoHeight);
                    userProfile.setProfilePhoto(profilePhoto);
                }
                else{
                    if (startTime == 1 && bitmap != null){
                        photoString = photo.bitmapToString();
                        photoConfig = photo.bitmapGetConfig();
                        photoHeight = photo.bitmapGetHeight();
                        photoWidth = photo.bitmapGetWidth();

                        profilePhoto = new ProfilePhoto(photoString, photoConfig, photoWidth, photoHeight);
                        userProfile.setProfilePhoto(profilePhoto);
                    }
                }
                serverHelper.addProfile(userProfile);
                finish();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * Put new/edited info into extra for activity:My Profile
     * @param view
     */
    public void toPicker(View view){
        Intent intent = new Intent(this, PhotoPicker.class);
        // Put extra

        // From edit profile
        intent.putExtra("FromEditProfile", 1);

        // Name
        information = editName.getText().toString();
        intent.putExtra("name", information);

        // E-mail
        information = editEmail.getText().toString();
        intent.putExtra("eMail", information);

        // Phone Number
        information = editPhone.getText().toString();
        intent.putExtra("phoneNumber", information);

        // Photo path
        if (path != null) {
            intent.putExtra("photoPath", path);
        }
        else{
            intent.putExtra("photoBitmap", bitmap);
        }
        startActivity(intent);
        finish();
    }
}
