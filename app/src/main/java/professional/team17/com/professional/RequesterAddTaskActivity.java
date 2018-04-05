/*
 * RequesterAddTaskActivity
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

/**
 *
 * An activity where a user in Requester mode can add a task with status "Requested".
 *
 * @author Lauren
 * @see Navigation
 */
public class RequesterAddTaskActivity extends RequesterTaskActivity {
    private static final String TAG = "RequesterADTaskActivity";

    public void setTitle() {
        this.setActivityTitleRequester("Add a Task");
    }

    public void setController(){
        requesterTaskController = new RequesterTaskController(this);
    }

    public void getTask(){}

    /**
     * Add the task to the ElasticSearch server.
     */

    public void addToServer(String title, String description) {
        requesterTaskController.addTaskToServer(title, description, locationString, dateString, latLng, photos);
        requesterTaskController.setTaskToRequested();
    }

    /**
     * Called when the "add" button is pressed. Starts the RequesterViewListActivity.
     */
    public void endActivity() {
        Bundle bundle = new Bundle(2);
        bundle.putString("ID", requesterTaskController.getCurrentTaskId());
        bundle.putString("Status", "Requested");
        Intent intent = new Intent(RequesterAddTaskActivity.this, RequesterViewListActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }


        /**
         * Saves the task locally for offline functionality.
         */
        //private void saveInFile(){
        //TODO offline functionality in project 5
        //}


        //TODO photo and location handling in project 5
        //for photos

}




