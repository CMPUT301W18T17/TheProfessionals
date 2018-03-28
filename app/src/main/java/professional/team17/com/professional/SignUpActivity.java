/*
 * SignUpActivity
 *
 * March 9, 2018
 *
 * Copyright @ 2018 Team 17, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behaviour at the University of Alberta.
 * You can find a copy of the license in the github wiki for this project.
 */
package professional.team17.com.professional;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.commons.validator.routines.EmailValidator;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Allows the user to create a new profile
 *
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
    private ElasticSearchController elasticSearchController;
    private String infor;
    private String userName;
    private String name;
    private String eMail;
    private String phoneNumber;
    private String path;
    private Photo photo;
    private Bitmap bitmap;
    private byte[] photoArray;
    private Bitmap.Config photoConfig;
    private int photoWidth;
    private int photoHeight;
    private EmailValidator emailValidator;


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

        usernameBox = (EditText) findViewById(R.id.usernameBox);
        nameBox = (EditText) findViewById(R.id.fullNameBox);
        emailBox = (EditText) findViewById(R.id.emailBox);
        phoneNumberBox = (EditText) findViewById(R.id.phoneNumberBox);
        errorBox = (TextView) findViewById(R.id.errorText);
        addNewPhotoButton = findViewById(R.id.add_new_photo);
        elasticSearchController = new ElasticSearchController();

        // Set text back
        setter(usernameBox, userName);
        setter(nameBox, name);
        setter(emailBox, eMail);
        setter(phoneNumberBox, phoneNumber);
    }

    /**
     * returns the user to the Login Activity
     *
     * @see LogInActivity
     * @param view
     */
    public void back(View view) {
        changeActivity(LogInActivity.class);
    }

    /**
     * Creates the user's profile and moves them
     *
     * @see ElasticSearchController
     * @param view
     */
    public void saveProfile(View view) {
        String username = usernameBox.getText().toString();
        String name = nameBox.getText().toString();
        String email = emailBox.getText().toString();
        String phoneNumber = phoneNumberBox.getText().toString();


        if ((usernameBox.getText().length() == 0) || (nameBox.getText().length() == 0) ||
                (emailBox.getText().length() == 0) || (phoneNumberBox.getText().length() == 0)){
            errorBox.setText("Please make sure all fields are filled");
        } else if (usernameBox.getText().length() < 4) {
            errorBox.setText("Username must be at least 4 characters");
        } else if (elasticSearchController.profileExists(username) == true) {
            errorBox.setText("Username is already taken");
        } else if (!(validateEmail(email))) {
            errorBox.setText("Must enter a valid email");
        } else if (!(validatePhoneNumber(phoneNumber))) {
            errorBox.setText("must enter a valid phone number");
        } else {
                if (path != null) {
                    photo = new Photo(path);
                    photoArray = photo.pathToByteArray();
                    photoConfig = photo.pathGetConfig();
                    photoHeight = photo.pathGetHeight();
                    photoWidth = photo.pathGetWidth();
                }

                else{
                    photoArray = new byte[] {-1};
                    photoConfig = null;
                    photoHeight = 0;
                    photoWidth = 0;
                }

                Profile profile = new Profile(name, username, email, phoneNumber, photoArray, photoConfig, photoWidth, photoHeight);

                if (!(elasticSearchController.addProfile(profile))) {
                    errorBox.setText("Something went wrong! We are unable to create profile");
                } else {
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                    SharedPreferences.Editor editor = pref.edit();

                    editor.putString("username", username); // Storing string
                    editor.commit(); // commit changes

                    //String test = pref.getString(username, "not working");
                    changeActivity(SearchActivity.class);
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
        //can change navigation, this is just a stand in
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
        intent.putExtra("photoPath", path);

        startActivity(intent);
        finish();
    }

    private void setter(EditText name, String information){
        if (information != null){
            name.setText(information);
        }
    }

    /**
     * Uses Apache's EmailValidator
     * https://commons.apache.org/proper/commons-validator/apidocs/org/apache/commons/validator/routines/EmailValidator.html
     * Compares the string email to the regular expression
     * @param email string email the user entered
     * @return true or false if it is valid or not
     */
    public boolean validateEmail(String email){
        emailValidator = emailValidator.getInstance();
        return emailValidator.isValid(email);
    }

    public boolean validatePhoneNumber(String phoneNumber){
        if (phoneNumber.length() > 10) {
            return android.util.Patterns.PHONE.matcher(phoneNumber).matches();
        } else {
            return false;
        }
    }
}

