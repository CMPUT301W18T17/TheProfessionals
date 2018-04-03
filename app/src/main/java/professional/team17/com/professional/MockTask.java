/*
 * MockTask
 *
 * March 16, 2018
 *
 * Copyright @ 2018 Team 17, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behaviour at the University of Alberta.
 * You can find a copy of the license in the github wiki for this project.
 */

package professional.team17.com.professional;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * A mock task object for testing purposes.
 */

public class MockTask extends Task {

    /**
     * Constructs a mock task object.
     * @param profileName Testing username
     * @param name Task name
     * @param description Task description
     * @param location Task location
     * @param date Task date
     */
    public MockTask(String profileName, String name, String description, String location, String date, LatLng latLng, ArrayList<String> photos, ArrayList<Bitmap.Config> configs){
        super(profileName, name, description, location, date, latLng ,photos,configs);
    }
}
