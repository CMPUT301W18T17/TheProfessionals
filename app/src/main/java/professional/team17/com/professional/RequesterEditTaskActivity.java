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
import android.os.Bundle;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    public void setTitle(){
        this.setActivityTitleRequester("Edit Task");
    }

    public void setController(){
        requesterTaskController = new RequesterTaskController(this);
    }

    public void getTask(){
        /* Get task ID from previous activity, then get task from server */
        try {
            getBundle();
        } catch (Exception e) {
            Log.i("Bundle", "Bundle was empty (no task ID was passed to EditTask)");
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
            String ID = startedIntent.getString("ID");
            getFromServer(ID);
        }
    }

    /**
     * Get the task to edit's information. Also updates the EditText with the task's info.
     */
    private void getFromServer(String taskID) throws Exception{
        Task oldTask = requesterTaskController.getOldTaskFromServer(taskID);
        if (oldTask == null){
            throw new Exception();
        }

        nameField.setText(oldTask.getName());
        descriptionField.setText(oldTask.getDescription());
        locationField.setText(oldTask.getLocation());
        textualDateView.setText(oldTask.getDateAsString());
        latLng = oldTask.getLatLng();
    }


    /**
     * Add the task to the ElasticSearch server.
     * @param title Task title
     * @param description Task description
     */
    public void addToServer(String title, String description){
        requesterTaskController.addOldTaskToServer(title, description, locationString, dateString, latLng, photos);
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

    void checkOffline() {
        ConnectedState c = ConnectedState.getInstance();
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



