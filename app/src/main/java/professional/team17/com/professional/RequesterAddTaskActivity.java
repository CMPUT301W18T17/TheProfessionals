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
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.MapView;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

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
    private Task task;
    private String dateString;
    private String locationString;
    private int year, month, day, id;
    private String yearString,monthString,dayString;

    /**
     * On creation of the activity, set all view objects and onClickListeners.
     * @param savedInstanceState The activity's previously saved state.
     */
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

                /* Create an intent and bundle and store all task info */
               addToServer(title, description);

                /* Activity finished, start RequesterViewListActivity */
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

    /**
     * Add the task to the ElasticSearch server.
     * @param title Task title
     * @param description Task description
     */
    private void addToServer(String title, String description){
        SharedPreferences pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String username = pref.getString("username", "error");
        java.util.Date inputDate = parseDate(dateString);
        task = new Task(username, title, description, locationString, dateString);
        task.setRequested();
        ElasticSearchController elasticSearchController = new ElasticSearchController();
        elasticSearchController.addTasks(task);
    }


    @Override
    public void  finish(){
        Bundle bundle = new Bundle(1);
        bundle.putString("ID", task.getUniqueID());
        bundle.putString("Status", "Requested");
        Intent intent = new Intent(RequesterAddTaskActivity.this, RequesterViewListActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public java.util.Date parseDate(String sdate) {
        String myFormat = "dd/MM/yyyy";
        java.util.Date input = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        if (sdate != null) {
            try {
                input = dateFormat.parse(sdate);
            } catch (ParseException e) {
                //do nothing
            }
        }
        return input;
    }
    /**
     * Saves the task locally for offline functionality.
     */
    //private void saveInFile(){
    //TODO offline functionality in project 5
    //}



    //TODO photo and location handling in project 5
    }



