package professional.team17.com.professional;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

public class NotificationActivity extends AppCompatActivity {

    private ListView notificationListView;
    private Button clearButton;
    private ImageButton backButton;
    private NotificationList notificationList;
    private String username;
    private Profile profile;
    private NotificationListAdapter notificationAdapter;
    private ServerHelper serverHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        serverHelper = new ServerHelper(this);
        /* Set layout variables and obtain username from SharedPreferences */
        clearButton = findViewById(R.id.notificationClearButton);
        backButton = findViewById(R.id.notificationBackButton);
        notificationListView = findViewById(R.id.notificationListView);
        SharedPreferences preferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        username = preferences.getString("username", "error");

        /* populate notificationList */
        getFromServer();
        notificationAdapter = new NotificationListAdapter(this, notificationList.getList());
        notificationListView.setAdapter(notificationAdapter);
        notificationListView.setOnItemClickListener(clickListener);
        notificationAdapter.notifyDataSetChanged();

        /* onclicklisteners */
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            notificationList.clearList();
            profile.setNotificationList(notificationList);
            serverHelper.addProfile(profile);
            notificationAdapter.notifyDataSetChanged();
            }
        });


    }

    private void getFromServer(){
        profile = serverHelper.getProfile(username);
        notificationList = profile.getNotificationList();
    }

    private AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener(){
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            Notification notification = notificationList.get(position);
            if (notification.getType().equals("New Bid")) {
                Intent intent = new Intent(NotificationActivity.this, RequesterViewTaskActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("task", notification.getNotificationTaskID());
                intent.putExtras(bundle);
                startActivity(intent);
            }
            else if (notification.getType().equals("Task Assigned")){
                Intent intent = new Intent(NotificationActivity.this, ProviderViewTask.class);
                Bundle bundle = new Bundle();
                bundle.putString("Task", notification.getNotificationTaskID());
                intent.putExtras(bundle);
                startActivity(intent);
            }

        }

    };
}
