package professional.team17.com.professional;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class TaskPhotoActivity extends AppCompatActivity implements ImageView.OnClickListener{
    ImageView imageUpload;
    Button bUpload;
    Button bTakePhoto;
    private String selectedImageName;
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int CAMERA_REQUEST= 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_photo);
        imageUpload = (ImageView)findViewById(R.id.imageUpload);
        bUpload = (Button)findViewById(R.id.bImageUpload);
        bTakePhoto = (Button)findViewById(R.id.bTakePhoto);
        imageUpload.setOnClickListener(this);
        bUpload.setOnClickListener(this);
        bTakePhoto.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.imageUpload:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent,RESULT_LOAD_IMAGE);
                break;
            case R.id.bImageUpload:
                Intent yourIntent = new Intent(this, RequesterAddTaskActivity.class);
                Bitmap bmp = ((BitmapDrawable)imageUpload.getDrawable()).getBitmap(); // store the image in your bitmap
                System.out.println("------------------------------------------------------");
                System.out.println(((BitmapDrawable)imageUpload.getDrawable()).getBitmap());
                System.out.println("------------------------------------------------------");
                ByteArrayOutputStream bao = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 50, bao);
                yourIntent.putExtra("yourImage", bao.toByteArray());
                startActivity(yourIntent);
                break;
            case R.id.bTakePhoto:
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                break;


        }

    }

    //@Override
    //protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        //if((requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) || (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)){
            //Uri selectedImage = data.getData();
            //imageUpload.setImageURI(selectedImage);
            //selectedImageName = selectedImage.getLastPathSegment();

        //}
    //}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            switch (requestCode) {
                case RESULT_LOAD_IMAGE:
                    Uri selectedImage = data.getData();
                    imageUpload.setImageURI(selectedImage);
                    break;
                case CAMERA_REQUEST:
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    imageUpload.setImageBitmap(photo);
                    break;
            }
        }
    }}