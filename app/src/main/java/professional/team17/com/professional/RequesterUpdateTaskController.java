/*
 * RequesterUpdateTaskController
 *
 * March 15, 2018
 *
 * Copyright @ 2018 Team 17, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behaviour at the University of Alberta.
 * You can find a copy of the license in the github wiki for this project.
 */

package professional.team17.com.professional;

import java.util.ArrayList;
import java.util.Date;

//TODO implement for project part 5

/**
 * Created by kaixiangzhang on 2018-03-11.
 *
 * Since this controller combine both delete and show methods.
 *
 * It may look a little bit confused
 * I'll make comments for all the methods about what it will be used for and what it need to achieve that.
 *
 * This controller can be placed between Task ---- requesterEditTaskActivity and Task ---- requesterViewTaskActivity
 * as an aggregate of both 2 Activities.
 *
 * !!!!!!!!!Again here, in my point of view, I still think this controller class make things complicated.
 * (which means it is unnecessary)!!!!
 * However it does make our program more flexible and ez to change.
 * And it address the feedback from Ta.
 *
 * some things we still need to mention about is to implement this parts we still need to change the code here.
 */

public class RequesterUpdateTaskController{
    //don't need to initialize the task here. The reason will be explained later on.
    private Task task;
    //since in this controller we have already had a task so we assign that one to our this.task
    public RequesterUpdateTaskController(Task task){
        this.task = task;
    }

    /**
     * the following part is for EditTask
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
    public void delPhotos(String photo){
        task.removePhoto(photo);
    }
    /**
     * this part is for viewTask
     */
    public String viewProfileName(){
        return task.getProfileName();
    }
    public String viewName(){
        return task.getName();
    }
    public String viewDescription(){
        return task.getDescription();
    }
    public String viewDate(){
        return task.getDateAsString();
    }
    public String viewLocation(){
        return task.getLocation();
    }
    public ArrayList<String> viewImage(){
        return task.getPhotos();
    }
    //finially
    public Task returnTask(){
        return this.task;
    }
}
