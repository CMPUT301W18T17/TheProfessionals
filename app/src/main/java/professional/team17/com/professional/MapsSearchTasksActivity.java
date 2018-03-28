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
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

/**
 * An activity which shows all tasks with status Requested and Bidded within 5km.
 */
public class MapsSearchTasksActivity extends MapsActivity implements OnMapReadyCallback {
// Calculate tasks within 5km
    private ImageView currentLocationButton;
    private ImageView closeButton;

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
            mMap.addCircle(new CircleOptions()
                    .center(currentLatLng)
                    .radius(5000)
                    .fillColor(0x40ff0000)
                    .strokeColor(Color.TRANSPARENT)
                    .strokeWidth(2));
        }
        getScreenBoundary();

    }

    private void getScreenBoundary(){
        LatLngBounds mapScreen = mMap.getProjection().getVisibleRegion().latLngBounds;
        double topLeftLat = mapScreen.northeast.latitude;
        double topLeftLon = mapScreen.southwest.longitude;
        double bottomRightLat =mapScreen.southwest.latitude;
        double bottomRightLon =mapScreen.northeast.longitude;

        LatLng topLeft = new LatLng(topLeftLat, topLeftLon);
        LatLng bottomRight = new LatLng(bottomRightLat, bottomRightLon);

        Toast.makeText(this, "TOPLEFT: " + topLeft.toString() +
                "BOTTOMRIGHT: " + bottomRight.toString()  , Toast.LENGTH_LONG).show();
    }
    /**
     * Goes to SeachActivity.class
     */
    @Override
    public void onBackPressed() {
        startActivity(new Intent(MapsSearchTasksActivity.this, SearchActivity.class));
    }
}
