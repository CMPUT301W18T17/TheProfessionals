package professional.team17.com.professional;

import android.os.Bundle;
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
    private final ElasticSearchController elasticSearchController = new ElasticSearchController();
    public ProviderTaskController(){}
    // this parts is for TaskListActivity

    //This updates the adapter with the results

    private TaskList createList(String type) {
        TaskList taskList =null;
        Log.i("DOUR", "createList: "+type);

        if (type.equals("Bidded")) {
            taskList = elasticSearchController.getTasksBidded("john123", "Bidded");
            Log.i("DOUR", "createList: "+taskList);

        }

        if (type.equals("Assigned")) {
            taskList = elasticSearchController.getTasksBidded("john123", "Assigned");
            //get assigned list from es
        }
        // dummyDate();
        // taskList = dummyTaskList;


        return taskList;}


}
