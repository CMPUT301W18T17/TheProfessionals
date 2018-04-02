package professional.team17.com.professional;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    private String mCurrentPhotoPath;
;

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
        if (filePath != null){
            returnPath = filePath;
            oldPath = filePath;
            oldPhoto = new Photo(filePath);
            viewPhoto.setImageDrawable(oldPhoto.pathToDrawable());
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
            adder(intent, "photoPath", oldPath);

            startActivity(intent);
            finish();
        }
        else{
            Intent intent = new Intent(this, EditMyProfileActivity.class);

            putExtra(intent);

            // Previous Photo Path
            adder(intent, "photoPath", oldPath);

            startActivity(intent);
            finish();
        }
    }

    private void toGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, SELECTED_PICTURE);
    }

    private void toCamera(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case SELECTED_PICTURE:
                if (resultCode == RESULT_OK){
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
                if (requestCode == RESULT_OK){
                    Log.i("message","----------------------------------------------------"+mCurrentPhotoPath);
                    photo = new Photo(mCurrentPhotoPath);
                    returnPath = photo.getPath();
                    viewPhoto.setImageDrawable(photo.pathToDrawable());
                }
                break;
        }
    }

    public void seted(View view){

        if (returnPath != null) {
            photo = new Photo(returnPath);
            size = photo.pathGetSize();
            if (size <= 65536) {
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
            else{
                viewError.setText("This photo is too big please pick a smaller one.");
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

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
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