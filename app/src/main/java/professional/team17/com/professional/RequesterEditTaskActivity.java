/*
 * RequesterEditTaskActivity
 *
 * March 9, 2018
 *
 * Copyright @ 2018 Team 17, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behaviour at the University of Alberta.
 * You can find a copy of the license in the github wiki for this project.
 */

package professional.team17.com.professional;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 *
 * An activity where a user in Requester mode can edit a task with status "Requested".
 *
 * @author Lauren
 * @see Navigation
 * @see RequesterViewListActivity
 * @see RequesterAddTaskActivity
 */
public class RequesterEditTaskActivity extends RequesterTaskActivity {

    private String ID;
    private String title;
    private String description;

    public void setTitle(){
        this.setActivityTitleRequester("Edit Task");
    }

    public void setSubmitButtonOnClickListener(){
        submitButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (nameField.getText().toString().isEmpty()) {
                    /* If the user did not change the title, keep the old one */
                    title = task.getName();
                }
                else {
                    title = nameField.getText().toString();
                }
                if (descriptionField.getText().toString().isEmpty()){
                    /* If the user did not change the description, keep the old one */
                    description = task.getDescription();
                }
                else {
                    description = descriptionField.getText().toString();
                }
                if (locationField.getText().toString().isEmpty()){
                    locationString = "";
                }
                else {
                    locationString = locationField.getText().toString();
                }
                dateString = textualDateView.getText().toString();


                /* Create an intent and bundle and store all task info */
                addToServer(title, description,photos);


                /* Activity finished, start RequesterViewListActivity */
                endActivity();
            }
        });

        getTask();
    }


    private void getTask(){
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
    }


    /**
     * Gets the task ID from the previous activity. Throws an exception if the ID is not found.
     * @throws Exception If the bundle is empty (the task is not found).
     */
    private void getBundle() throws Exception{
        Bundle startedIntent = getIntent().getExtras();
        if (startedIntent.isEmpty()){
            throw new Exception();
        }
        else {
            ID = startedIntent.getString("ID");
        }
    }

    /**
     * Get the task to edit's information. Also updates the EditText with the task's info.
     */
    private void getFromServer(String taskID) throws Exception{
        task = serverHelper.getTask(taskID);
        if (task == null){
            throw new Exception();
        }
        /* Update EditTexts */
        nameField.setHint(task.getName());
        descriptionField.setHint(task.getDescription());
        locationField.setText(task.getLocation());
        textualDateView.setText(task.getDateAsString());
        latLng = task.getLatLng();

    }

    /**
     * Add the task to the ElasticSearch server.
     * @param title Task title
     * @param description Task description
     */
    public void addToServer(String title, String description,ArrayList<String> photos){
        task.setName(title);
        task.setDate(parseDate(dateString));
        task.setDescription(description);
        task.setLocation(locationString);
        task.setLatLng(latLng);

        serverHelper.updateTasks(task);
    }

    /**
     * Once the user presses the submit button, finish this activity and start the
     * RequesterViewListActivity with the updated task displayed.
     */
    public void endActivity() {
        Bundle bundle = new Bundle(1);
        bundle.putString("Status", "Requested");
        Intent intent = new Intent(RequesterEditTaskActivity.this, RequesterViewListActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
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


    void checkOffline() {        ConnectedState c = ConnectedState.getInstance();
        if(c.isOffline()) {
            Offline fragment = new Offline();
            getSupportFragmentManager().beginTransaction().replace(R.id.requester_task_list_frame, fragment).commit();
        }

    }

    /**
     * Saves the task locally for offline functionality.
     */
    //private void saveInFile(){
    //TODO offline functionality in project 5
    //}



    //TODO photo and location handling in project 5
}



