package professional.team17.com.professional;


import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public abstract class RequesterTaskActivity extends Navigation{
    /* Layout objects */
    protected EditText nameField;
    protected EditText descriptionField;
    protected EditText locationField;
    protected TextView textualDateView;
    protected TextView photoTextView;
    protected ImageButton addPhotoButton;
    protected ImageButton selectDateButton;
    protected MapView mapView;
    protected Button submitButton;
    /* other variables */
    protected Task task;
    protected String dateString;
    protected String locationString;
    protected LatLng latLng;
    protected String message;
    protected ArrayList<Bitmap> photos;

    /**
     * On creation of the activity, set all view objects and onClickListeners.
     * @param savedInstanceState The activity's previously saved state.
     */
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_requester_task);
        setTitle();
        /* Set all view objects */
        //Button submitButton = (Button) findViewById(R.id.button2);
        nameField = (EditText) findViewById(R.id.TaskNameField);
        descriptionField = (EditText) findViewById(R.id.taskDescriptionField);
        locationField = (EditText) findViewById(R.id.textualAddressField);
        textualDateView = (TextView) findViewById(R.id.textualDateView);
        addPhotoButton = (ImageButton) findViewById(R.id.addPhotoButton);
        selectDateButton = (ImageButton) findViewById(R.id.calendarButton);
        mapView = (MapView) findViewById(R.id.map_task);
        submitButton = (Button) findViewById(R.id.submitButton);
        photoTextView = (TextView) findViewById(R.id.photoTextView);
        photos = new ArrayList<Bitmap>();


        /* Set all onClickListeners */
        addPhotoButton.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( RequesterTaskActivity.this, TaskPhotoActivity.class);
                startActivityForResult(intent, 0);
            }
        });
        if(getIntent().hasExtra("yourImage")) {
            Bitmap bmp = BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("yourImage"), 0, getIntent().getByteArrayExtra("yourImage").length);
            System.out.println("------------------------------------------------------");
            System.out.println(photos);
            System.out.println("------------------------------------------------------");
            photos.add(bmp);
            photoTextView.setText("image1");
            System.out.println("------------------------------------------------------");
            System.out.print(photos);
            System.out.println("------------------------------------------------------");

        }

        selectDateButton.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* Show the DatePickerDialog */
                displayDatePicker();
                /* Get the formatted date */

            }
        });

        locationField.setInputType(InputType.TYPE_NULL);
        locationField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MapsSearchLocationActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        setSubmitButtonOnClickListener();



    }

    public abstract void setSubmitButtonOnClickListener();
    public abstract void endActivity();
    public abstract void setTitle();
    public abstract void addToServer(String title, String description ,ArrayList<Bitmap> photos);
    /**
     * Displays the DatePickerDialog fragment, allowing the user to select a date.
     */
    private void displayDatePicker(){
        DialogFragment dateFragment = new DatePickerFragment();
        dateFragment.show(getFragmentManager(), "datePicker");
    }

    /**
     * Get data from MapsSearchLocationActivity.java
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                latLng = intent.getParcelableExtra("taskLatLng");
                locationField.setText(intent.getStringExtra("taskAddress"));
            }
        }
    }




}
