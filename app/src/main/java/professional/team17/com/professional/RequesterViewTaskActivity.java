package professional.team17.com.professional;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class RequesterViewTaskActivity extends RequesterLayout {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requester_view_task2);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //fab.setOnClickListener(new View.OnClickListener() {
            //@Override
            //public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String name = bundle.getString("string1");
        String date = bundle.getString("string2");
        String description = bundle.getString("string3");
        String location = bundle.getString("string4");
        TextView textView1 = (TextView) findViewById(R.id.textView);
        textView1.setText(String.valueOf(name));
        TextView textView2 = (TextView) findViewById(R.id.textView1);
        textView2.setText(String.valueOf(date));
        TextView textView3 = (TextView) findViewById(R.id.textView2);
        textView3.setText(String.valueOf(description));
        TextView textView4 = (TextView) findViewById(R.id.textView3);
        textView4.setText(String.valueOf(location));
            }
        }

