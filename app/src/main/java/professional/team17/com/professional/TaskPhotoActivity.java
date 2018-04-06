package professional.team17.com.professional;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
// basically here I keep two arrayList one is for string (to upload) one is for bitmap(to delete)

public class TaskPhotoActivity extends AppCompatActivity implements ImageView.OnClickListener {
    ImageView imageUpload;
    TextView errorBox;
    Button bUpload;
    Button bTakePhoto;
    Button bImageDelete;
    Button bImageNext;
    Button bImageBack;
    Button bConfirm;
    private static final int RESULT_LOAD_IMAGE = 1;
    private String title, description, location, date;
    private static final int CAMERA_REQUEST = 2;
    private ArrayList<String> photos;
    private ArrayList<Bitmap> RPhotos;
    private Bitmap bmp;
    private String photo;
    // tack the current index;
    private int a = 0;
    // set the status;
    private int b = 1000;
    // tack the size;
    private int c = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_photo);

        Intent intent = getIntent();
        title = intent.getStringExtra("Title");
        description = intent.getStringExtra("Description");
        location = intent.getStringExtra("Location");
        date = intent.getStringExtra("Date");
        imageUpload = (ImageView) findViewById(R.id.imageUpload);
        errorBox = (TextView) findViewById(R.id.errorText);
        bUpload = (Button) findViewById(R.id.bImageUpload);
        bTakePhoto = (Button) findViewById(R.id.bTakePhoto);
        bImageDelete = (Button) findViewById(R.id.bImageDelete);
        bImageNext = (Button) findViewById(R.id.bImageNext);
        bImageBack = (Button) findViewById(R.id.bImageBack);
        bConfirm = (Button) findViewById(R.id.bConfirm);
        photos = new ArrayList<String>();
        RPhotos = new ArrayList<Bitmap>();
        imageUpload.setOnClickListener(this);
        bUpload.setOnClickListener(this);
        bTakePhoto.setOnClickListener(this);
        bConfirm.setOnClickListener(this);
        bImageDelete.setOnClickListener(this);

        bImageNext.setOnClickListener(this);
        bImageBack.setOnClickListener(this);
        a = 0;
        c = 0;

    }

    @Override
/*<<<<<<< Updated upstream
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.imageUpload:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent,RESULT_LOAD_IMAGE);
                break;
            case R.id.bImageUpload:
                Intent yourIntent = new Intent(this, RequesterAddTaskActivity.class);
                putInfor(yourIntent);
                Bitmap bmp = ((BitmapDrawable)imageUpload.getDrawable()).getBitmap(); // store the image in your bitmap
                //System.out.println("------------------------------------------------------");
                //System.out.println(((BitmapDrawable)imageUpload.getDrawable()).getBitmap());
                //System.out.println("------------------------------------------------------");
                ByteArrayOutputStream bao = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 50, bao);
                yourIntent.putExtra("yourImage", bao.toByteArray());
                startActivity(yourIntent);
                break;
            case R.id.bTakePhoto:
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                break;
=======*/
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.imageUpload:
                    c = c + 1;
                    if(a!=b){

                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                        }

                    else
                        errorBox.setText("you have confirmed plz delete first.");
                    break;
                case R.id.bConfirm:
                        if(a!=b){
                            if(a!=c){
                    bmp = ((BitmapDrawable) imageUpload.getDrawable()).getBitmap(); // store the image in your bitmap
                    RPhotos.add(bmp);
                    ByteArrayOutputStream bao = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 50, bao);
                    bmp = compressFunction(bmp);
                    photo = toBase64(bmp);
                    photos.add(photo);
                   //System.out.println("------------------------------------------------------");
                    //System.out.println(RPhotos);
                    //System.out.println("a = " + a);
                    //System.out.println("------------------------------------------------------");
                    b = a;
                    c = a + 1;}
                    else
                                errorBox.setText("Can u select photo before u press confirm.");}
                    else
                        errorBox.setText("you already confirmed.");
                    break;
                case R.id.bImageUpload:
                    Intent yourIntent = new Intent();
                    //putInfor(yourIntent);
                    yourIntent.putStringArrayListExtra("yourImage", photos);
                    setResult(Activity.RESULT_OK, yourIntent);
                    //Bitmap bmp = ((BitmapDrawable) imageUpload.getDrawable()).getBitmap(); // store the image in your bitmap
                    //ByteArrayOutputStream bao = new ByteArrayOutputStream();
                    //bmp.compress(Bitmap.CompressFormat.PNG, 50, bao);
                    //yourIntent.putStringArrayListExtra("yourImage",photos);
                    finish();
                case R.id.bTakePhoto:
                    c = c + 1;
                    if(a!=b){

                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    }
                    else
                        errorBox.setText("you have confirmed plz delete first.");
                    break;
                case R.id.bImageDelete:
                    if (photos.size()!=0){
                        if (a < photos.size()-1){
                            photos.remove(a);
                            RPhotos.remove(a);
                            //a = a +1 ;
                            imageUpload.setImageResource(0);
                            bmp = RPhotos.get(a);
                            imageUpload.setImageBitmap(bmp);
                            b = 100000;
                            c = c -1;
                        }
                        else{
                    imageUpload.setImageResource(0);
                    photos.remove(a);
                    RPhotos.remove(a);
                    //System.out.println("------------------------------------------------------");
                    //System.out.println(RPhotos);
                    //System.out.println("a = " + a);
                    //System.out.println("------------------------------------------------------");
                    b = 100000;
                        c = c -1;}}

                    else
                        errorBox.setText("you have not selected anything yet !");
                    break;
                case R.id.bImageNext:
                    if((a == b)&&(a == (photos.size()-1))){
                        if (photos.size() < 5){
                    imageUpload.setImageResource(0);
                            System.out.println("------------------------------------------------------");
                            System.out.println(photos);
                            System.out.println("a = " + a);
                            System.out.println("------------------------------------------------------");

                    a = a + 1;}

                        else
                        {errorBox.setText("u already input 5 images plz stop");}}
                    else if (a < photos.size()-1){
                        a = a + 1;
                        imageUpload.setImageResource(0);
                        bmp = RPhotos.get(a);
                        imageUpload.setImageBitmap(bmp);
                        b = a;}


                    else
                        errorBox.setText("Plz select a photo and confirm first.");
                    //largest part c
                    break;
                case R.id.bImageBack:
                    if ((a!=0)) {
                            a = a - 1;
                            bmp = RPhotos.get(a);
                            imageUpload.setImageResource(0);
                            imageUpload.setImageBitmap(bmp);
                            //System.out.println("------------------------------------------------------");
                            //System.out.println(RPhotos);
                            //System.out.println("a = " + a);
                            //System.out.println("------------------------------------------------------");}}

                        }
                    else
                        errorBox.setText("U can't back to anything");
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
    }

/*
    private void putInfor(Intent intent) {
        // Task Title
        intent.putExtra("Title", title);

        // Task Description
        intent.putExtra("Description", description);

        // Location
        intent.putExtra("Location", location);

        // Date
        intent.putExtra("Date", date);
        // photos

    }
*/

    public Bitmap compressFunction(Bitmap bitmap) {
        int newWidth = 64;
        int newHeight = 64;
        Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
        return newBitmap;
    }
    public String toBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
}