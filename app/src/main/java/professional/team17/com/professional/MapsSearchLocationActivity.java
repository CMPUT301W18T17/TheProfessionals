package professional.team17.com.professional;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsSearchLocationActivity extends MapsActivity implements OnMapReadyCallback {
    private static final String TAG = "MapsSLocationActivity";
    private EditText mSearchAddress;
    private Marker taskLocationMarker;

    public void MapsSearchEvent(){
        mSearchAddress = (EditText) findViewById(R.id.addressInput);

        Log.d(TAG, "MapsSearchEvent()");

        mSearchAddress.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_SEARCH || i == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER){
                    mMap.clear();
                    geoLocate();
                }
                return false;
            }
        });
    }

    private void geoLocate(){
        Log.d(TAG, "geoLocate: geolocating");

        String searchString = mSearchAddress.getText().toString();
        Geocoder geocoder = new Geocoder(MapsSearchLocationActivity.this);
        List<Address> listOfAddresses = new ArrayList<>();
        try{
            listOfAddresses = geocoder.getFromLocationName(searchString, 1);
        } catch (IOException e){
            Log.e(TAG, "geoLocate: IOException " + e.getMessage());
        }

        if(listOfAddresses.size()>0){
            Address address = listOfAddresses.get(0);
            Log.d(TAG, "geoLocate: location found: " + address.toString());
            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), address.getAddressLine(0));

        }
    }

    public void setContentViewFunction(){
        setContentView(R.layout.activity_maps_search_location);
    }

}
