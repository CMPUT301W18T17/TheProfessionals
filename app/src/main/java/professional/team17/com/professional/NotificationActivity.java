package professional.team17.com.professional;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

public class NotificationActivity extends AppCompatActivity {

    private ListView notificationListView;
    private Button clearButton;
    private ImageButton backButton;
    private NotificationList notificationList;
    private String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        /* Set layout variables and obtain username from SharedPreferences */
        clearButton = findViewById(R.id.notificationClearButton);
        backButton = findViewById(R.id.notificationBackButton);
        notificationListView = findViewById(R.id.notificationListView);
        SharedPreferences preferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        username = preferences.getString("username", "error");


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
                //TODO implement clearing notifications
            }
        });
    }

    //TODO implement array adapter for notification list
}
