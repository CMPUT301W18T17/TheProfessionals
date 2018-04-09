/*
 * SignUpActivity
 *
 * March 9, 2018
 *
 * Copyright @ 2018 Team 17, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behaviour at the University of Alberta.
 * You can find a copy of the license in the github wiki for this project.
 */
package professional.team17.com.professional.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import professional.team17.com.professional.Helpers.Photo;
import professional.team17.com.professional.Entity.Profile;
import professional.team17.com.professional.Entity.ProfilePhoto;
import professional.team17.com.professional.R;
import professional.team17.com.professional.Controllers.ServerHelper;
import professional.team17.com.professional.Controllers.ValidationController;

/**
 * Allows the user to create a new profile
 * @author Logan
 * @see LogInActivity
 * @see Profile
 * @see SearchActivity
 */
public class SignUpActivity extends AppCompatActivity {

    private EditText usernameBox;
    private EditText nameBox;
    private EditText emailBox;
    private EditText phoneNumberBox;
    private TextView errorBox;
    private ImageButton addNewPhotoButton;
    private ServerHelper serverHelper;
    private String infor;
    private String userName;
    private String name;
    private String eMail;
    private String phoneNumber;
    private String path;
    private Photo photo;
    private Bitmap bitmap;
    private String photoString;
    private Bitmap.Config photoConfig;
    private int photoWidth;
    private int photoHeight;
    private ValidationController validationController;
    private ProfilePhoto profilePhoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Get the Intent that started this activity
        Intent intent = getIntent();

        // Get return information
        userName = intent.getStringExtra("userName");
        name = intent.getStringExtra("name");
        eMail = intent.getStringExtra("eMail");
        phoneNumber = intent.getStringExtra("phoneNumber");
        path = intent.getStringExtra("photoPath");
        bitmap = (Bitmap) intent.getParcelableExtra("photoBitmap");

        usernameBox = findViewById(R.id.usernameBox);
        nameBox = findViewById(R.id.fullNameBox);
        emailBox = findViewById(R.id.emailBox);
        phoneNumberBox = findViewById(R.id.phoneNumberBox);
        errorBox = findViewById(R.id.errorText);
        addNewPhotoButton = findViewById(R.id.add_new_photo);
        serverHelper = new ServerHelper(this);

        // Set text back
        setter(usernameBox, userName);
        setter(nameBox, name);
        setter(emailBox, eMail);
        setter(phoneNumberBox, phoneNumber);

        validationController = new ValidationController();
    }

    /**
     * returns the user to the Login Activity
     *
     * @see LogInActivity
     */
    public void back(View view) {
        changeActivity(LogInActivity.class);
    }

    /**
     * Overrides the back button
     */

    @Override
    public void onBackPressed() {
        changeActivity(LogInActivity.class);
    }

    /**
     * Creates the user's profile and moves them
     *
     * @see ServerHelper
     */
    public void saveProfile(View view) {
        String username = usernameBox.getText().toString();
        String name = nameBox.getText().toString();
        String email = emailBox.getText().toString();
        String phoneNumber = phoneNumberBox.getText().toString();


        if ((usernameBox.getText().length() == 0) || (nameBox.getText().length() == 0) ||
                (emailBox.getText().length() == 0) || (phoneNumberBox.getText().length() == 0)){
            errorBox.setText(R.string.fieldsNotFilled);
        } else if (usernameBox.getText().length() < 4) {
            errorBox.setText(R.string.underMinCharacters);
        } else if (serverHelper.profileExists(username)) {
            errorBox.setText(R.string.userExists);
        } else if (!(validationController.validateEmail(email))) {
            errorBox.setText(R.string.invalidEmail);
        } else if (!(validationController.validatePhoneNumber(phoneNumber))) {
            errorBox.setText(R.string.invalidPhoneNumber);
        } else {
                if (path != null) {
                    photo = new Photo(path);
                    photoString = photo.pathToString();
                    photoConfig = photo.pathGetConfig();
                    photoHeight = photo.pathGetHeight();
                    photoWidth = photo.pathGetWidth();
                }

                else if (bitmap != null){
                    photo = new Photo(bitmap);
                    photoString = photo.bitmapToString();
                    photoConfig = photo.bitmapGetConfig();
                    photoHeight = photo.bitmapGetHeight();
                    photoWidth = photo.bitmapGetWidth();
                }

                else{
                    photoString = "-1";
                    photoConfig = null;
                    photoHeight = 0;
                    photoWidth = 0;
                }

                profilePhoto = new ProfilePhoto(photoString, photoConfig, photoWidth, photoHeight);

                Profile profile = new Profile(name, username, email, phoneNumber, profilePhoto);

                if (!(serverHelper.addProfile(profile))) {
                    errorBox.setText(R.string.cannotCreateProfile);
                } else {
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                    SharedPreferences.Editor editor = pref.edit();

                    editor.putString("username", username); // Storing string
                    editor.apply(); // commit changes

                    //String test = pref.getString(username, "not working");
                    Intent intent = new Intent(this, RequesterViewListActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("Status", "Requested");
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        }

        //set profile name as global variable?

    /**
     * Moves the user to add photo activity
     */
    public void photoPicker(View view){
        changeActivity(PhotoPicker.class);
    }

    /**
     * Moves the user to a new activity
     */
    private void changeActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        // Put extra

        // User Name
        infor = usernameBox.getText().toString();
        intent.putExtra("userName", infor);

        // Name
        infor = nameBox.getText().toString();
        intent.putExtra("name", infor);

        // E-mail
        infor = emailBox.getText().toString();
        intent.putExtra("eMail", infor);

        // Phone Number
        infor = phoneNumberBox.getText().toString();
        intent.putExtra("phoneNumber", infor);

        // Photo path
        if (path != null) {
            intent.putExtra("photoPath", path);
        }
        // Bitmap
        else{
            intent.putExtra("photoBitmap", bitmap);
        }
        startActivity(intent);
        finish();
    }

    /**
     * Set info into its corresponding EditText
     * @param name - name of EditText
     * @param information - info to be placed in EditText
     */
    private void setter(EditText name, String information){
        if (information != null){
            name.setText(information);
        }
    }


}

