package professional.team17.com.professional;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.MapView;

/**
 * Created by ag on 2018-03-11.
 */

public class ProviderViewTaskController {
    private Context context;
    private String status;
    private Activity activity;
    private Task task;
    private String taskid;
    private String low;
    private final ElasticSearchController elasticSearchController = new ElasticSearchController();

    public void ProviderViewTaskController(Context context, Activity activity, String taskid){
        this.context = context;
        this.status = "default";
        this.activity = activity;
        this.taskid = taskid;
        this.task = getTask(taskid);
        setLow();


    }

    public void setLow(){
        this.low = "No bids placed yet";
        TextView taskLowBidTextField = (TextView) activity.findViewById(R.id.provider_view_task_lowBidInput);
        if (task.isBidded()){
            this.low = task.getBids().getLowest().getAmountAsString();
            taskLowBidTextField.setText(this.low);
        }
    }


    public Task getTask(String taskid){
        return elasticSearchController.getTask(taskid);
    }

    public void setMyBidButtons() {
        this.status = "My Bids";
        ImageButton deleteButton = (ImageButton) activity.findViewById(R.id.provider_view_task_removeBid);
        ImageButton addButton = (ImageButton) activity.findViewById(R.id.provider_view_task_AddBid);
        ImageButton editButton = (ImageButton) activity.findViewById(R.id.provider_view_task_manageBid);
        editButton.setVisibility(View.VISIBLE);
        addButton.setVisibility(View.GONE);
        deleteButton.setVisibility(View.VISIBLE);
    }


    public void setLowest(){






    }


}
