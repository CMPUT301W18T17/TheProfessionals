package professional.team17.com.professional.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import professional.team17.com.professional.Helpers.Photo;
import professional.team17.com.professional.R;

public class PhotoPicker extends AppCompatActivity {
    private String userName;
    private String name;
    private String eMail;
    private String phoneNumber;
    private static final int SELECTED_PICTURE = 1;
    private static final int CAMERA_REQUEST = 2;
    private ImageView viewPhoto;
    private Photo photo, oldPhoto;
    private String filePath;
    private String returnPath;
    private String oldPath;
    private TextView viewError;
    private int size, isEditProfile;
    private Bitmap photoBitmap, returnBitmap, oldBitmap;
    private int functionCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_picker);

        viewPhoto = findViewById(R.id.photoView);
        viewError = findViewById(R.id.errorBox);

        // Get those information
        isEditProfile = 0;
        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
        name = intent.getStringExtra("name");
        eMail = intent.getStringExtra("eMail");
        phoneNumber = intent.getStringExtra("phoneNumber");
        filePath = intent.getStringExtra("photoPath");
        isEditProfile = intent.getIntExtra("FromEditProfile", 0);
        oldBitmap = (Bitmap) intent.getParcelableExtra("photoBitmap");

        Log.i("","*******************************************************"+filePath);

        if (filePath != null){
            returnPath = filePath;
            oldPath = filePath;
            oldPhoto = new Photo(filePath);
            viewPhoto.setImageDrawable(oldPhoto.pathToDrawable());
        }

        else{
            Log.i("mesage","---------------------------------------------------------------");
            if (oldBitmap != null){
                Log.i("mesage","#################################################################");
                returnBitmap = oldBitmap;
                oldPhoto = new Photo(oldBitmap);
                viewPhoto.setImageDrawable(oldPhoto.bitmapToDrawable());
            }
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void back(View view) {
        if (isEditProfile == 0) {
            Intent intent = new Intent(this, SignUpActivity.class);

            putExtra(intent);

            // Previous Photo Path
            if (oldPath != null) {
                adder(intent, "photoPath", oldPath);
            }
            else{
                intent.putExtra("photoBitmap", oldBitmap);
            }
            startActivity(intent);
            finish();
        }
        else{
            Intent intent = new Intent(this, EditMyProfileActivity.class);

            putExtra(intent);

            // Previous Photo Path
            if (oldPath != null) {
                adder(intent, "photoPath", oldPath);
            }
            else{
                intent.putExtra("photoBitmap", oldBitmap);
            }

            startActivity(intent);
            finish();
        }
    }

    private void toGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, SELECTED_PICTURE);
    }

    private void toCamera(){
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        functionCode = 0;

        switch (requestCode) {
            case SELECTED_PICTURE:
                if (resultCode == RESULT_OK){
                    functionCode = 1;

                    Uri uri = data.getData();
                    String[]projection = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(projection[0]);
                    filePath = cursor.getString(columnIndex);
                    cursor.close();

                    photo = new Photo(filePath);


                    returnPath = photo.getPath();

                    viewPhoto.setImageDrawable(photo.pathToDrawable());
                }
                break;
            case CAMERA_REQUEST:
                //if (requestCode == RESULT_OK){
                    functionCode = 2;
                    Bundle extras = data.getExtras();
                    photoBitmap = (Bitmap) extras.get("data");
                    photo = new Photo(photoBitmap);
                    viewPhoto.setImageDrawable(photo.bitmapToDrawable());

                    returnBitmap = photoBitmap;
               //}
                break;
        }
    }

    public void seted(View view){

        if (functionCode == 1) {
            photo = new Photo(returnPath);
            size = photo.pathGetSize();
            if (isEditProfile == 0) {
                Intent intent = new Intent(this, SignUpActivity.class);

                putExtra(intent);

                // Photo Path
                adder(intent, "photoPath", returnPath);

                startActivity(intent);
                finish();
            }
            else{
                Intent intent = new Intent(this, EditMyProfileActivity.class);

                putExtra(intent);

                // Photo Path
                adder(intent, "photoPath", returnPath);

                startActivity(intent);
                finish();
            }
        }

        else if (functionCode == 2){
            if (isEditProfile == 0) {
                Intent intent = new Intent(this, SignUpActivity.class);

                putExtra(intent);

                // Photo Path
                intent.putExtra("photoBitmap", returnBitmap);

                startActivity(intent);
                finish();
            }
            else{
                Intent intent = new Intent(this, EditMyProfileActivity.class);

                putExtra(intent);

                // Photo Path
                intent.putExtra("photoBitmap", returnBitmap);

                startActivity(intent);
                finish();
            }
        }

        else{
            viewError.setText("Please pick a photo first.");
        }
    }

    private void adder(Intent intent, String name, String infor){
        if (infor != null){
            intent.putExtra(name, infor);
        }
    }

    private void putExtra(Intent intent){
        // Put Extra

        // User Name
        if (isEditProfile == 0) {
            adder(intent, "userName", userName);
        }

        // Not first time start
        if (isEditProfile == 1){
            intent.putExtra("startTime", 1);
        }

        // Name
        adder(intent, "name", name);

        // E-mail
        adder(intent, "eMail", eMail);

        // Phone Number
        adder(intent, "phoneNumber", phoneNumber);
    }

    public void photoDialog(View view){
        new AlertDialog.Builder(this)
                .setTitle("Attention")
                .setMessage("Please choose your way to pick photo.")
                .setCancelable(true)
                .setPositiveButton("From File", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        toGallery();
                    }
                })
                .setNegativeButton("Using Camera", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        toCamera();
                    }
                })
                .create()
                .show();
    }

}