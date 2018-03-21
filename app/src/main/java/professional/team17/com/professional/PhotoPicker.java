package professional.team17.com.professional;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class PhotoPicker extends AppCompatActivity {
    private String userName;
    private String name;
    private String eMail;
    private String phoneNumber;
    private static final int SELECTED_PICTURE = 1;
    private ImageView viewPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_picker);

        viewPhoto = (ImageView) findViewById(R.id.photoView);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            Log.i("Permission", "**********************************************************************");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Get those information
        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
        name = intent.getStringExtra("name");
        eMail = intent.getStringExtra("eMail");
        phoneNumber = intent.getStringExtra("phoneNumber");
    }

    public void back(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        // Put Extra

        // User Name
        adder(intent, "userName", userName);

        // Name
        adder(intent, "name", name);

        // E-mail
        adder(intent, "eMail", eMail);

        // PhoneNumber
        adder(intent, "phoneNumber", phoneNumber);

        startActivity(intent);
        finish();
    }

    public void toGallery(View view){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, SELECTED_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case SELECTED_PICTURE:
                if (resultCode == RESULT_OK){
                    Log.i("Path", "-------------------------------- ");
                    Uri uri = data.getData();
                    String[]projection = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(projection[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();

                    Log.i("Path", "-------------------------------- " + filePath);
                    Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                    Drawable drawable = new BitmapDrawable(bitmap);

                    viewPhoto.setImageDrawable(drawable);
                }
                break;
        }
    }

    private void adder(Intent intent, String name, String infor){
        if (infor != null){
            intent.putExtra(name, infor);
        }
    }
}