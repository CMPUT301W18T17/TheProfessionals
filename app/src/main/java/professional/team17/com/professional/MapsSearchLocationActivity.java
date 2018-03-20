package professional.team17.com.professional;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
public class MapsSearchLocationActivity extends MapsActivity implements OnMapReadyCallback {
    private static final String TAG = "MapsSLocationActivity";


    public void setContentViewFunction(){
        setContentView(R.layout.activity_maps_search_location);
    }


}
