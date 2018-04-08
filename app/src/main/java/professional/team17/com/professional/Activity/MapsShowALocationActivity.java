package professional.team17.com.professional.Activity;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import professional.team17.com.professional.R;

/**
 * Show a location relating to a task
 * @author Hailan
 * @version 2.0
 */
public class MapsShowALocationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng taskLatLng;
    private String taskAddress;
    private ImageView closeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_search_tasks);

        ImageView currentLocationButton = findViewById(R.id.ic_currentlocation);
        currentLocationButton.setVisibility(View.GONE);

        closeButton = findViewById(R.id.ic_close);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Once map is ready, show location of task (passed from prior activity)
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Intent intent = getIntent();
        if (intent.getParcelableExtra("aTaskLatLng") != null){
            taskLatLng = intent.getParcelableExtra("aTaskLatLng");
            taskAddress = intent.getStringExtra("aTaskAddress");
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(taskLatLng, 11));
            mMap.addMarker(new MarkerOptions().position(taskLatLng).title(taskAddress));

            //moveCamera(taskLatLng, taskAddress);
        } else {
            Toast.makeText(this, "Something went wrong & we cannot show "+ taskAddress, Toast.LENGTH_SHORT).show();
        }
    }
}
