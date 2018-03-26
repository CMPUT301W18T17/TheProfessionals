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

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 *
 * An activity where a user in Requester mode can add a task with status "Requested".
 *
 * @author Lauren
 * @see RequesterLayout
 */
public class RequesterAddTaskActivity extends RequesterTaskActivity {

    public void setTitle(){
        this.setActivityTitleRequester("Add a Task");
    }

    public void setSubmitButtonOnClickListener(){
        submitButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* Convert user entered values to strings */
                String title = nameField.getText().toString();
                String description = descriptionField.getText().toString();
                locationString = locationField.getText().toString();
                dateString = (String) textualDateView.getText();

                if (title.length() > 30){
                    /* if the title is too long */
                    message = "Title cannot be longer than 30 characters.";
                    Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if (description.length() > 300) {
                    /* if the description is too long */
                    message = "Description cannot be longer than 300 characters.";
                    Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if (title.isEmpty()){
                    /* if the title is empty */
                    message = "You must include a title.";
                    Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT) ;
                    toast.show();
                }
                else if (description.isEmpty()){
                    /* if the title is empty */
                    message = "You must include a description.";
                    Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT) ;
                    toast.show();
                }
                else {
                    /* Create an intent and bundle and store all task info */
                    addToServer(title, description);

                    /* Activity finished, start RequesterViewListActivity */
                    endActivity();
                }

            }
        });
    }

    /**
     * Add the task to the ElasticSearch server.
     * @param title Task title
     * @param description Task description
     */

    public void addToServer(String title, String description){
        SharedPreferences pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String username = pref.getString("username", "error");
        task = new Task(username, title, description, locationString, dateString, latLng);
        task.setRequested();
        ElasticSearchController elasticSearchController = new ElasticSearchController();
        elasticSearchController.addTasks(task);
    }

    /**
     * Called when the "add" button is pressed. Starts the RequesterViewListActivity.
     */
    public void  endActivity(){
        Bundle bundle = new Bundle(2);
        bundle.putString("ID", task.getUniqueID());
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
    }



