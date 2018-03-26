package professional.team17.com.professional;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsShowALocationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng taskLatLng;
    private String taskAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_show_alocation);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

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
