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

import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.maps.OnMapReadyCallback;

/**
 * An activity which shows all tasks with status Requested and Bidded within 5km.
 */
public class MapsSearchTasksActivity extends MapsActivity implements OnMapReadyCallback {
// Calculate tasks within 4km
    private ImageView currentLocationButton;
    public void setContentViewFunction(){
        setContentView(R.layout.activity_maps_search_tasks);
    }
    public void MapsSearchEvent(){
        currentLocationButton = (ImageView) findViewById(R.id.ic_currentlocation);
        currentLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCurrentLocation();
            }
        });
    }


}
