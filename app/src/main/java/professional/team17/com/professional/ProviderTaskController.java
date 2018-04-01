/*
 * ProviderTaskController
 *
 * March 17, 2018
 *
 * Copyright @ 2018 Team 17, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behaviour at the University of Alberta.
 * You can find a copy of the license in the github wiki for this project.
 */
package professional.team17.com.professional;

import android.util.Log;
//TODO implement this for project part 5
/**
 * Created by kaixiangzhang on 2018-03-11.
 *
 * Here since I am not very familiar with the provider part
 * However i gonna follow the Design Patterns - MVC Pattern:
 * Which is model--controller--view :
 * 1. controller use model to achieve goal
 * 2. the activity class only do the view(get info/ show info)
 */

public class ProviderTaskController {
   // private final ServerHelper serverHelper = new ServerHelper();
    public ProviderTaskController(){}
    // this parts is for TaskListActivity

    //This updates the adapter with the results

    private TaskList createList(String type) {
        TaskList taskList =null;
        Log.i("DOUR", "createList: "+type);

        if (type.equals("Bidded")) {
           // taskList = serverHelper.getTasksBidded("john123", "Bidded");
            Log.i("DOUR", "createList: "+taskList);

        }

        if (type.equals("Assigned")) {
            //taskList = serverHelper.getTasksBidded("john123", "Assigned");
            //get assigned list from es
        }
        // dummyDate();
        // taskList = dummyTaskList;


        return taskList;}


}
