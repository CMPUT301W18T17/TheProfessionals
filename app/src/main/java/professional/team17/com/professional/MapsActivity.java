/*
 * MapsActivity
 *
 * March 15, 2018
 *
 * Copyright @ 2018 Team 17, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behaviour at the University of Alberta.
 * You can find a copy of the license in the github wiki for this project.
 */
package professional.team17.com.professional;

import android.Manifest;

import android.content.pm.PackageManager;

import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

/**
 * Based on: Mitch Tabian's Google Maps & Google Places Course - https://codingwithmitch.com/courses/ and https://www.youtube.com/watch?v=Vt6H9TOmsuo
 */
public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback{
    private static final String TAG = "MapActivity";
    //Permissions
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    private Boolean mLocatePermissionGranted = false;
    private GoogleMap mMap;
    private LatLng currentLatLng;
    private Location currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);
        getLocationPermissions();

    }

    private void getLocationPermissions() {
        Log.d(TAG, "getLocationPermissions");
        String[] permissions = {FINE_LOCATION, COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //Toast.makeText(this, "FINE_LOCATION Permission Granted", Toast.LENGTH_SHORT).show();
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                //Toast.makeText(this, "COARSE_LOCATION Permission Granted", Toast.LENGTH_SHORT).show();
                mLocatePermissionGranted = true;
                mapInitialization();
            } else {
                ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady");
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        if (mLocatePermissionGranted) {
            getCurrentLocation();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MapsActivity.this, "Show Current Location", Toast.LENGTH_SHORT).show();
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
        }
    }

    private void mapInitialization(){
        Log.d(TAG, "mapInitialization");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapsActivity.this);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPersmissionsResult - check permssions");
        mLocatePermissionGranted = false;

        //Check all map permissions
        switch(requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0) {
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocatePermissionGranted = false;
                            Log.d(TAG, "At least one permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "Acquired all permissions");
                    mLocatePermissionGranted = true;
                    // map initialization
                    mapInitialization();
                }
            }
        }
    }

    private void getCurrentLocation(){
        Log.d(TAG, "getCurrentLocation");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try{
            if(mLocatePermissionGranted){
                Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener((new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "getCurrentLocation (OnComplete) got location");
                            currentLocation = (Location) task.getResult();
                            if(currentLocation == null){
                                Toast.makeText(MapsActivity.this, "Please turn on Location for your phone!", Toast.LENGTH_SHORT).show();
                            } else{
                                currentLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                                moveCamera(currentLatLng);
                                //mMap.addMarker(new MarkerOptions().position(currentLatLng).title("YOU"));
                            }
                        } else {
                            Toast.makeText(MapsActivity.this, "Current location is not found", Toast.LENGTH_SHORT).show();
                        }
                    }
                }));
            }
        } catch(SecurityException e){
            Log.e(TAG, "getCurrentLocation - SecurityException" + e.getMessage());
        }
    }

    private void moveCamera(LatLng latLng){
        Log.d(TAG,"moveCamera: lat -> " + latLng.latitude + ", lng" + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,11));
    }

//    //implement method - 1
//    @Override
//    public void onConnected(@Nullable Bundle bundle) {
//
//    }
//
//    //implement method - 2
//    @Override
//    public void onConnectionSuspended(int i) {
//
//    }
//
//    //implement method - 3
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
//    }
}
