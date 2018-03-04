package professional.team17.com.professional;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProviderViewTask extends AppCompatActivity {
    private Profile user;
    private Task task;
    private TextView statusTextField;
    private TextView dateTextField;
    private TextView descriptionTextField;
    private TextView userNameTextField;
    private TextView taskTitleTextField;
    private TextView amountTextField;
    private Button bidButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.provider_view_task);
        user = getUser();
        task = getTask();
        fillTask();
        checkStatus();
    }


    /**
     *
     * @return Returns the subscription sent with the Intent
     */
    public void fillTask() {
        //get view
        TextView statusTextField = (TextView) findViewById(R.id.provider_view_task_statusType);
        TextView userNameTextField = (TextView) findViewById(R.id.provider_view_task_userName);
        TextView taskTitleTextField = (TextView) findViewById(R.id.provider_view_task_title);

        //plug in item to row
        statusTextField.setText(task.getStatus());
        userNameTextField.setText(task.getProfileName());
        taskTitleTextField.setText(task.getName());

    }

    /**
     *
     * @return Returns the subscription sent with the Intent
     */
    public void checkStatus() {
        bidButton = (Button) findViewById(R.id.provider_view_task_bidButton);

        //hide bid button if task is assigned or user has bid on task already
        if (task.isAssigned() || task.getBids().userBidded(user.getUserName())) {
            bidButton.setVisibility(View.INVISIBLE);
        }

        statusTextField = (TextView) findViewById(R.id.provider_view_task_statusType);
        TextView userNameTextField = (TextView) findViewById(R.id.provider_view_task_userName);
        TextView taskTitleTextField = (TextView) findViewById(R.id.provider_view_task_title);

        //plug in item to row
        statusTextField.setText(task.getStatus());
        userNameTextField.setText(task.getProfileName());
        taskTitleTextField.setText(task.getName());

    }

    public void placeBid(){


    }
    /**
     *
     * @return Returns the subscription sent with the Intent
     */
    private Profile getUser() {
        Intent intent = getIntent();
        Profile profile = (Profile) intent.getSerializableExtra("profile");
        return profile;
    }

    /**
     *
     * @return Returns the subscription sent with the Intent
     */
    private Task getTask() {
        Intent intent = getIntent();
        Task task = (Task) intent.getSerializableExtra("Task");
        return task;
    }
}
