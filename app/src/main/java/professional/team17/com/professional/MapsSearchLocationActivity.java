package professional.team17.com.professional;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsSearchLocationActivity extends MapsActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "MapsSLocationActivity";
    private static final LatLngBounds latLngBounds = new LatLngBounds(new LatLng(-85, -180), new LatLng(85, 180));
    private Button addLocation;
    private AutoCompleteTextView mSearchAddress;
    private LatLng finalLatLng;
    private String finalAddress;
    private GoogleApiClient mGoogleApiClient;
    private PlaceAutoCompleteAdapter pAutoCompleteAdapter;

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void setContentViewFunction(){
        setContentView(R.layout.activity_maps_search_location);
    }

    public void afterLocationFoundEvent(){return;}

    public void MapsSearchEvent(){
        Log.d(TAG, "MapsSearchEvent()");
        mSearchAddress = (AutoCompleteTextView) findViewById(R.id.addressInput);
        addLocation = (Button) findViewById(R.id.addLocation);

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        pAutoCompleteAdapter = new PlaceAutoCompleteAdapter(this, mGoogleApiClient, latLngBounds, null);

        mSearchAddress.setAdapter(pAutoCompleteAdapter);
        mSearchAddress.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_SEARCH || i == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER){
                    mMap.clear();
                    geoLocate();
                    hideKeyBoard();
                }
                return false;
            }
        });

        addLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putParcelable("taskLatLng", finalLatLng);
                intent.putExtras(bundle);
                intent.putExtra("taskAddress", finalAddress);
                setResult(RESULT_OK, intent);
                finish();
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
            finalLatLng= new LatLng(address.getLatitude(), address.getLongitude());
            finalAddress = address.getAddressLine(0);
            moveCamera(finalLatLng, finalAddress);
        }
    }

    public void hideKeyBoard(){
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(this.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(mSearchAddress.getWindowToken(),0);
    }


}
