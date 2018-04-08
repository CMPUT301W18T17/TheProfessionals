/*
 * TaskList
 *
 * February 20, 2018
 *
 * Copyright @ 2018 Team 17, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behaviour at the University of Alberta.
 * You can find a copy of the license in the github wiki for this project.
 */

package professional.team17.com.professional.Entity;

import java.util.ArrayList;

import professional.team17.com.professional.Entity.Task;

/**
 * @author Allie
 * @version 1.0
 * @see Task
 * This function is working for creating an object named TaskList
 * Which is used as an ArrayList<Task>
 */

public class TaskList extends ArrayList<Task>{

    public TaskList(){
        // Nothing
    }

    /**
     * Handling the Task Object
     * 1. hasTask checks if there is a specific task in this taskList
     * 2. addTask adds the parameter task into the taskList
     * 3. deleteTask takes a task type parameter to delete this parameter from this.taskList
     * @param task
     * @return hasTask->boolean
     */
    public boolean hasTask(Task task){
        return this.contains(task);
    }
    public void addTask(Task task){ this.add(task); }
    public void deleteTask(Task task){
        this.remove(task);
    }

    /**
     *getTask return the current TaskList.
     * @return this.taskList
     */
    public ArrayList<Task> getTasks() {
        return this;
    }
}
