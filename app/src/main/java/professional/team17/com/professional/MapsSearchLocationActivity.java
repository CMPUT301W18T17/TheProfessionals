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
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
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

/**
 * The following is a spinoff of Mitch Tablian's code in Google Maps & Google Places Course
 */
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

        mSearchAddress.setOnItemClickListener(autoCompleteClickListener);
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

    /**
     * Hides keyboard after clicking enter or clicking on address in suggestions
     */
    public void hideKeyBoard(){
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(this.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(mSearchAddress.getWindowToken(),0);
    }

    /**
     *  Treatments for when user click on an address in suggestion list
     */
    private AdapterView.OnItemClickListener autoCompleteClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            hideKeyBoard();
            final AutocompletePrediction place = pAutoCompleteAdapter.getItem(i);
            final String placeId = place.getPlaceId();

            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi.getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceResultCallback);
        }
    };

    /**
     *  Get place address and latLng
     */
    private ResultCallback<PlaceBuffer> mUpdatePlaceResultCallback = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(@NonNull PlaceBuffer places) {
            if (!places.getStatus().isSuccess()){
                Log.d(TAG, "mUpdatePlaceResultCallback: Place query was unsuccessful: " + places.getStatus().toString());
            } else {
                final Place place = places.get(0);
                try {
                    Log.d(TAG,"mUpdatePlaceResultCallback: Place Address " + place.getAddress() );
                    Log.d(TAG,"mUpdatePlaceResultCallback: Place Address " + place.getLatLng() );

                    moveCamera(place.getLatLng(), place.getAddress().toString());
                } catch (NullPointerException e){
                    Log.e(TAG, "mUpdatePlaceResultCallback: No associated address or LatLng" + e.getMessage());
                }
            }
            places.release(); // From Google Places API: To prevent a memory leak! Must release PlaceBuffer object when app doesn't need it
        }
    };


}
