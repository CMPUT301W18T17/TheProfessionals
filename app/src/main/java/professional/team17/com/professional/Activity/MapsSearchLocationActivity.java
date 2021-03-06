package professional.team17.com.professional.Activity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import professional.team17.com.professional.Adapters.PlaceAutoCompleteAdapter;
import professional.team17.com.professional.R;

/**
 * MapsSearchLocationActivity
 * Finds the location that the user typed in EditText field
 * The following is a spinoff of Mitch Tablian's code in Google Maps & Google Places Course
 */
public class MapsSearchLocationActivity extends MapsActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "MapsSLocationActivity";
    private static final LatLngBounds latLngBounds = new LatLngBounds(new LatLng(-85, -180), new LatLng(85, 180));
    private static final int PLACE_PICKER_REQUEST = 1;
    private Button addLocation;
    private ImageView deleteAllText;
    private ImageView mPlacePicker;
    private AutoCompleteTextView mSearchAddress;
    private LatLng finalLatLng;
    private String finalAddress;
    private GoogleApiClient mGoogleApiClient;
    private PlaceAutoCompleteAdapter pAutoCompleteAdapter;

    /**
     * From GoogleApiClient.OnConnectionFailedListener
     * @param connectionResult gives error message in log if connection fails
     */
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "onConnectionFailed:" + connectionResult.getErrorCode()+","+connectionResult.getErrorMessage());
    }

    /**
     * Show specific map layout
     */
    public void setContentViewFunction(){
        setContentView(R.layout.activity_maps_search_location);
    }

    /**
     * After location found, mark previous address (in TextView of TaskActivity) if exists
     */
    public void afterLocationFoundEvent(){
        Bundle extras = getIntent().getExtras();
        if (extras!= null){
            finalAddress = extras.getString("addressTyped");
            Log.d(TAG, "Num1: MapsSearchEvent: " + finalAddress);
            mSearchAddress.setText(finalAddress);
            finalLatLng = extras.getParcelable("latLonGot");
            if (finalLatLng != null){
                Log.d(TAG, "Num2: MapsSearchEvent: " + finalLatLng);
                moveCamera(finalLatLng, finalAddress);
            }
        }
    }

    /**
     * Initialize on click listeners prior relating to search
     */
    public void MapsSearchEvent(){
        Log.d(TAG, "MapsSearchEvent()");
        mSearchAddress = (AutoCompleteTextView) findViewById(R.id.addressInput);
        addLocation = (Button) findViewById(R.id.addLocation);
        deleteAllText = (ImageView) findViewById(R.id.deleteButton);
        mPlacePicker = (ImageView) findViewById(R.id.placePicker);

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

        mPlacePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    startActivityForResult(builder.build(MapsSearchLocationActivity.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    Log.e(TAG, "onClick: GooglePlayServicesRepairableException" + e.getMessage() );
                } catch (GooglePlayServicesNotAvailableException e) {
                    Log.e(TAG, "onClick: GooglePlayServicesNotAvailableException" + e.getMessage() );
                }
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

        deleteAllText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.clear();
                mSearchAddress.setText("");
                finalLatLng = null;
                finalAddress = "";
            }
        });
    }

    /**
     * Google's PLACE PICKER
     * From Google Places API Dev Guide: https://developers.google.com/places/android-api/placepicker
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();

                PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi.getPlaceById(mGoogleApiClient, place.getId());
                placeResult.setResultCallback(mUpdatePlaceResultCallback);
            }
        }
    }

    /**
     * Find address searched in search bar
     */
    private void geoLocate(){
        Log.d(TAG, "geoLocate");

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
     *  Used in autoCompleteClickListener & onActivityResult
     */
    private ResultCallback<PlaceBuffer> mUpdatePlaceResultCallback = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(@NonNull PlaceBuffer places) {
            if (!places.getStatus().isSuccess()){
                Log.d(TAG, "mUpdatePlaceResultCallback: Place query was unsuccessful: " + places.getStatus().toString());
            } else {
                final Place place = places.get(0);
                try {
                    finalAddress = place.getAddress().toString();
                    finalLatLng = place.getLatLng();

                    Log.d(TAG,"mUpdatePlaceResultCallback: Place Address " + finalAddress );
                    Log.d(TAG,"mUpdatePlaceResultCallback: Place Address " + finalLatLng );

                    mMap.clear();
                    moveCamera(finalLatLng, finalAddress);
                    mSearchAddress.setText(finalAddress);

                } catch (NullPointerException e){
                    Log.e(TAG, "mUpdatePlaceResultCallback: No associated address or LatLng" + e.getMessage());
                }
            }
            places.release(); // From Google Places API: To prevent a memory leak! Must release PlaceBuffer object when app doesn't need it
        }
    };
}
