/*
 * RequesterAddTaskActivity
 *
 * March 9, 2018
 *
 * Copyright
 */

package professional.team17.com.professional;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.MapView;

import java.util.Calendar;
import java.util.Random;

/**
 *
 * An activity where a user in Requester mode can add a task with status "Requested".
 *
 * @author Kaixiang, Lauren
 * @see RequesterLayout
 */
public class RequesterAddTaskActivity extends RequesterLayout {
    /* Layout objects */
    private EditText nameField;
    private EditText descriptionField;
    private EditText locationField;
    private TextView textualDateView;
    private ImageButton addPhotoButton;
    private ImageButton selectDateButton;
    private MapView mapView;
    private Button submitButton;
    /* other variables */
    private String dateString;
    private String locationString;
    private int year, month, day, id;
    private String yearString,monthString,dayString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requester_add_task);

        /* Set activity title */
        this.setActivityTitle("Add a Task");

        /* Set all view objects */
        //Button submitButton = (Button) findViewById(R.id.button2);
        nameField = (EditText) findViewById(R.id.TaskNameField);
        descriptionField = (EditText) findViewById(R.id.taskDescriptionField);
        locationField = (EditText) findViewById(R.id.textualAddressField);
        textualDateView = (TextView) findViewById(R.id.textualDateView);
        addPhotoButton = (ImageButton) findViewById(R.id.addPhotoButton);
        selectDateButton = (ImageButton) findViewById(R.id.calendarButton);
        mapView = (MapView) findViewById(R.id.mapView);
        submitButton = (Button) findViewById(R.id.submitButton);

        /* Set all onClickListeners */
        addPhotoButton.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO implement photo selection
            }
        });

        selectDateButton.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* Show the DatePickerDialog */
                displayDatePicker();
                /* Get the formatted date */
                dateString = (String) textualDateView.getText();
            }
        });

        submitButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* Convert user entered values to strings */
                String title = nameField.getText().toString();
                String description = descriptionField.getText().toString();
                locationString = locationField.getText().toString();

                /* Create a random ID for the task */
                Random rand = new Random();
                id = rand.nextInt(50) + 1;

                /* Create an intent and bundle and store all task info */
                if (title.length() > 0 && title.length() < 30 && description.length() != 0 && description.length() < 300) {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("title", title);
                    bundle.putString("date", dateString);
                    bundle.putString("description", description);
                    bundle.putString("location", locationString);
                    bundle.putInt("id", id);
                    intent.putExtras(bundle);
                    setResult(RequesterViewListActivity.RESULT_OK, intent);
                }

                /* Activity finished */
                finish();

            }
        });
    }


    /**
     * Displays the DatePickerDialog fragment, allowing the user to select a date.
     */
    private void displayDatePicker(){
        DialogFragment dateFragment = new DatePickerFragment();
        dateFragment.show(getFragmentManager(), "datePicker");
    }


    /** here we still need to handle 2 things
     * 1. google map
     * 2. add photo(since it nevigate to another interface)
     */
    private String handleGoogleMap(){
        //implement soon
        return  locationString;
    }
    //private imageView UserAddImage(){
        //get the idea from Uml.
        //return imageView;
    }



