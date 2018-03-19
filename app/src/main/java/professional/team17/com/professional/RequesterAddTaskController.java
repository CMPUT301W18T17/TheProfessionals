/*
 * RequesterAddTaskController
 *
 * March 15, 2018
 *
 * Copyright @ 2018 Team 17, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behaviour at the University of Alberta.
 * You can find a copy of the license in the github wiki for this project.
 */
package professional.team17.com.professional;

import java.util.Date;
//TODO implement for project part 5
/**
 * Created by kaixiangzhang on 2018-03-11.
 * Basically what this class does is to perform the get info from the addTaskActivity, gather them
 * and add them into the TaskList and push them into the server.
 *
 * requesterAddTaskActivity uses RequesterAddTaskController(use user input and elastic) to form a new task.
 *
 * and also we can do the elastic search here and call the method directly in the add task activity
 *
 * !!!!!still not quite sure if this controller does make things complicated however it does make the code
 * more replaceable and more readable
 *
 * basically this controller class is deletable if we don't actually change anything in the addTask activity .
 *
 * and this controller can be set between the Task(Entity) and addTask(activiy) as an aggregate in the UML
 * to address the Ta's feedback.
 *
 */


public class RequesterAddTaskController {
    /* create a new task*/
    private Task task = new Task("profileName","name","description", "location", "date");
    // call
    public RequesterAddTaskController(){}
    /**
     * here the following methods work individually to form a new task.
     */
    public void setProfileName(String profileName){
        task.setProfileName(profileName);
    }
    public void setName(String Name){
        task.setName(Name);
    }
    public void setDescription(String description){
        task.setDescription(description);
    }
    public void setLocation(String location){
        task.setLocation(location);
    }
    public void setDate(Date date){
        task.setDate(date);
    }
    public void addPhotos(String photo){
        task.addPhoto(photo);
    }
    public Task returnTask(){
        return this.task;
    }
    /*
    here I think we can do the elastic search here and the addTaskActivity only need to call this method.
     */
    private void addToServer(){
        //TODO delete this temp variable
        ElasticSearchController elasticSearchController = new ElasticSearchController();
        elasticSearchController.addTasks(task);
    }





}
