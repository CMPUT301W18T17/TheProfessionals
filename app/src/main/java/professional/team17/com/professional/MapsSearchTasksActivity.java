/*
 * MapsSearchTasksActivity
 *
 * March 15, 2018
 *
 * Copyright @ 2018 Team 17, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behaviour at the University of Alberta.
 * You can find a copy of the license in the github wiki for this project.
 */

package professional.team17.com.professional;

import android.content.Intent;
import android.graphics.Camera;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.android.SphericalUtil;

import java.util.ArrayList;

/**
 * An activity which shows all tasks with status Requested and Bidded within 5km.
 */
public class MapsSearchTasksActivity extends MapsActivity implements OnMapReadyCallback {
// Calculate tasks within 5km
    private ImageView currentLocationButton;
    private ImageView closeButton;
    private Circle circle;
    private LatLng topRight;
    private LatLng bottomLeft;
    private ServerHelper serverHelper = new ServerHelper(getApplicationContext());
    private TaskList tasks;

    public void setContentViewFunction(){
        setContentView(R.layout.activity_maps_search_tasks);
    }
    public void MapsSearchEvent(){
        currentLocationButton = (ImageView) findViewById(R.id.ic_currentlocation);
        closeButton = (ImageView) findViewById(R.id.ic_close);

        currentLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCurrentLocation();
            }
        });
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MapsSearchTasksActivity.this, SearchActivity.class));
            }
        });
    }

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
        //Toast.makeText()
        for(Task task: tasks) {
            markSpot(task.getLatLng(), task.getLocation());
        }
    }

    /**
     * Method getCircleLatLngBounds is referenced from: https://stackoverflow.com/a/37304415
     * @param circle
     * @return
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
