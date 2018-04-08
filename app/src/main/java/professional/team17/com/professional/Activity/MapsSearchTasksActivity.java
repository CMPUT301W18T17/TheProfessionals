/*
 * MapsSearchTasksActivity
 *
 * March 15, 2018
 *
 * Copyright @ 2018 Team 17, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behaviour at the University of Alberta.
 * You can find a copy of the license in the github wiki for this project.
 */

package professional.team17.com.professional.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.SphericalUtil;

import professional.team17.com.professional.R;
import professional.team17.com.professional.Controllers.ServerHelper;
import professional.team17.com.professional.Entity.Task;
import professional.team17.com.professional.Entity.TaskList;

/**
 * An activity which shows all tasks with status Requested and Bidded within 5km.
 * @author Hailan
 * @version 2.0
 */
public class MapsSearchTasksActivity extends MapsActivity implements OnMapReadyCallback {
    // Init variables
    private static final String TAG = "MapsSearchTasksActivity";
    private ImageView currentLocationButton;
    private ImageView closeButton;
    private Circle circle;
    private LatLng topRight;
    private LatLng bottomLeft;
    private ServerHelper serverHelper = new ServerHelper(this);
    private TaskList tasks;
    private String username;

    public void setContentViewFunction(){
        setContentView(R.layout.activity_maps_search_tasks);
    }

    /**
     * All initialization of OnClickListeners to prepare for tasks
     * @see MapsActivity's method: OnMapReady
     */
    public void MapsSearchEvent(){
        SharedPreferences pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        username = pref.getString("username", "error");
        currentLocationButton = (ImageView) findViewById(R.id.ic_currentlocation);
        closeButton = (ImageView) findViewById(R.id.ic_close);

        currentLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        });
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MapsSearchTasksActivity.this, SearchActivity.class));
            }
        });

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                Context context = MapsSearchTasksActivity.this;

                LinearLayout info = new LinearLayout(context);
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(context);
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                //title.setText(marker.getTitle());
                title.setText(Html.fromHtml(marker.getTitle()));

                TextView snippet = new TextView(context);
                snippet.setTextColor(Color.BLACK);
                snippet.setText(Html.fromHtml(marker.getSnippet()));

                info.addView(title);
                info.addView(snippet);

                return info;
            }
        });
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                try{
                    String breakTaskTitle[] = marker.getTitle().split("\n");
                    String taskID = breakTaskTitle[1];
                    Log.d(TAG, "onInfoWindowClick: "+ taskID);

                    Intent intent = new Intent(getBaseContext(), ProviderViewTask.class);
                    intent.putExtra("ID", taskID);
                    startActivity(intent);
                } catch (NullPointerException e) {
                    Log.e(TAG, "OnInfoWindowClickListener:" + e.getMessage());
                }

            }
        });
    }

    /**
     * Draw/Color 5km radius with user's current position as the centre
     */
    public void afterLocationFoundEvent(){
        if (currentLatLng != null){
            circle = mMap.addCircle(new CircleOptions()
                    .center(currentLatLng)
                    .radius(5000)
                    .fillColor(0x40ff0000)
                    .strokeColor(Color.TRANSPARENT)
                    .strokeWidth(2));
        }
        getCircleLatLngBounds(circle);
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(new LatLngBounds(bottomLeft, topRight), 0));
        tasks = serverHelper.getMapTasks(bottomLeft, topRight);
        Log.d(TAG, "afterLocationFoundEvent: Tasks: " + tasks);
        //Toast.makeText()
        for(Task task: tasks) {
            if (!task.getProfileName().equals(username) &&
                    (task.status.equals("Requested") || task.status.equals("Bidded"))) {
                markSpot(task);
            }
        }
    }

    /**
     * Mark the spot of a task
     * @param task a task that is within provider's 5km radius/box
     */
    private void markSpot(Task task) {
        try{
            String taskInfo = "<b>" + "Task Description: " + "</b>" + task.getDescription() + "<br/>" +
                    "<b>" + "Task Due By: " + "</b>" + task.getDateAsString() + "<br/>" +
                    "<b>" + "Task Location: " + "</b>"+ task.getLocation() + "<br/>";

            MarkerOptions markerOptions = new MarkerOptions()
                    .position(task.getLatLng())
                    .title("<big><b>" + task.getName() + "</b></big>"+ "<br/>" +"<small>TaskID: \n" + task.getUniqueID() + "\n<br/></small>")
                    .snippet(taskInfo)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
            mMap.addMarker(markerOptions);
        } catch (NullPointerException e){
            Log.e(TAG, "markSpot: NullPointer: " + e.getMessage());
        }
    }

    /**
     * Method getCircleLatLngBounds is referenced from: https://stackoverflow.com/a/37304415
     * @param circle circle that has a 5km radius with provider's current location as centre
     */
    private void getCircleLatLngBounds(Circle circle){
        topRight = SphericalUtil.computeOffset(circle.getCenter(), circle.getRadius() * Math.sqrt(2), 45);
        bottomLeft = SphericalUtil.computeOffset(circle.getCenter(), circle.getRadius() * Math.sqrt(2), 225);
    }

    /**
     * Goes to SeachActivity.class
     */
    @Override
    public void onBackPressed() {
        startActivity(new Intent(MapsSearchTasksActivity.this, SearchActivity.class));
    }
}
