package professional.team17.com.professional;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Random;

public class RequesterEditTaskActivity extends RequesterLayout {
    private EditText name;
    private EditText description;
    private EditText location;
    private String date1;
    private String location1;
    Button chooseDate;
    Calendar calendar = Calendar.getInstance();
    int year, month, day, id;
    String year1, month1, day1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requester_edit_task);
        Button submitButton = (Button) findViewById(R.id.button2);
        name = (EditText) findViewById(R.id.TaskNameField);
        description = (EditText) findViewById(R.id.taskDescriptionField);
        location = (EditText) findViewById(R.id.textualAddressField);
        //final DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker2);
        //button to choose date
        chooseDate = (Button) findViewById(R.id.button3);
        chooseDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(1);
            }
        });


        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name1 = name.getText().toString();
                String description1 = description.getText().toString();
                String location1 = location.getText().toString();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                year1 = Integer.toString(year);
                month1 = Integer.toString(month);
                day1 = Integer.toString(day);
                date1 = year1 + "-" + month1 + "-" + day1;
                Random rand = new Random();
                id = rand.nextInt(50) + 1;
                //chooseDate.setText(year + "-" + (month + 1) + "-" + day);
                //Toast.makeText(requesterAddTaskActivity.this, datePicker.getDayOfMonth()+""+datePicker.getMonth()+""+datePicker.getYear(),Toast.LENGTH_LONG).show();
                if (name1.length() > 0 && name1.length() < 30 && description1.length() != 0 && description1.length() < 30) {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("name", name1);
                    bundle.putString("date", date1);
                    bundle.putString("description", description1);
                    bundle.putString("location", location1);
                    intent.putExtras(bundle);
                    setResult(RequesterViewTaskActivity.RESULT_OK, intent);
                    finish();
                }
                //adapter.notifyDataSetChanged();
                //saveInFile();
            }
        });
    }

    /**
     * here we still need to handle 2 things
     * 1. google map
     * 2. add photo(since it nevigate to another interface)
     */
    private String handleGoogleMap() {
        //implement soon
        return location1;
    }

    //private imageView UserAddImage() {
        //get the idea from Uml.
        //return imageView;
    }