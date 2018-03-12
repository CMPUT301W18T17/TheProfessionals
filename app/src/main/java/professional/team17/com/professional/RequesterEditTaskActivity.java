/*
 * RequesterEditTaskActivity
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
import android.util.Log;
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
 * An activity where a user in Requester mode can edit a task with status "Requested".
 *
 * @author Lauren
 * @see RequesterLayout
 */
public class RequesterEditTaskActivity extends RequesterLayout {
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
    ElasticSearchController elasticSearchController = new ElasticSearchController();
    private String dateString;
    private String locationString;
    private String ID;


    /**
     * On creation of the activity, set all view objects and onClickListeners.
     * @param savedInstanceState The activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requester_edit_task);

        /* Set activity title */
        this.setActivityTitle("Edit Task");

        /* Set all view objects */
        nameField = (EditText) findViewById(R.id.TaskNameField);
        descriptionField = (EditText) findViewById(R.id.taskDescriptionField);
        locationField = (EditText) findViewById(R.id.textualAddressField);
        textualDateView = (TextView) findViewById(R.id.textualDateView);
        addPhotoButton = (ImageButton) findViewById(R.id.addPhotoButton);
        selectDateButton = (ImageButton) findViewById(R.id.calendarButton);
        mapView = (MapView) findViewById(R.id.mapView);
        submitButton = (Button) findViewById(R.id.submitButton);

        /* Get task ID from previous activity, then get task from server */
        try {
            getBundle();
        } catch (Exception e) {
            Log.i("Bundle", "Bundle was empty (no task ID was passed to EditTask)");
        }
        try {
            getFromServer(ID);
        } catch (Exception e) {
            Log.i("Server", "Server failed to return a task for that ID");
        }


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
     * Gets the task ID from the previous activity. Throws an exception if the ID is not found.
     * @throws Exception If the bundle is empty (the task is not found).
     */
    private void getBundle() throws Exception{
        Intent startedIntent = getIntent();
        Bundle extrasBundle = startedIntent.getExtras();
        if (extrasBundle.isEmpty()){
            throw new Exception();
        }
        else {
            ID = extrasBundle.getString("ID");
        }
    }

    /**
     * Get the task to edit's information. Also updates the EditText with the task's info.
     */
    private void getFromServer(String taskID) throws Exception{
        task = elasticSearchController.getTask(taskID);
        if (task == null){
            throw new Exception();
        }
        /* Update EditTexts */
        nameField.setHint(task.getName());
        descriptionField.setHint(task.getDescription());
        locationField.setHint(task.getLocation());
        textualDateView.setText(task.getDateAsString());

    }

    /**
     * Add the task to the ElasticSearch server.
     * @param title Task title
     * @param description Task description
     */
    private void addToServer(String title, String description){
        task.setName(title);
        task.setDate(parseDate(dateString));
        task.setDescription(description);
        task.setLocation(locationString);

        elasticSearchController.updateTasks(task);
    }

    /**
     * Once the user presses the submit button, finish this activity and start the
     * RequesterViewListActivity with the updated task displayed.
     */
    @Override
    public void finish() {
        Bundle bundle = new Bundle(1);
        bundle.putString("ID", ID);
        Intent intent = new Intent(RequesterEditTaskActivity.this, RequesterViewListActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * Converts a String date into a Date date.
     * @param sdate String date
     * @return Date date
     */
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



