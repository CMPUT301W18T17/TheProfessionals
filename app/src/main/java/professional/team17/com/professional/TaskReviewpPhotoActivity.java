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

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class TaskReviewpPhotoActivity extends AppCompatActivity implements ImageView.OnClickListener{
    ImageView imageUpload;
    TextView errorBox;
    Button bUpload;
    Button bTakePhoto;
    Button bImageDelete;
    Button bImageNext;
    Button bImageBack;
    Button bConfirm;
    Button letGo;
    private static final int RESULT_LOAD_IMAGE = 1;
    private String title, description, location, date;
    private static final int CAMERA_REQUEST = 2;
    private ArrayList<String> photos;
    //private ArrayList<Bitmap> RPhotos;
    private Bitmap bmp;
    private String photo;
    // tack the current index;
    private int a = 0;
    // set the status;
    private int b = 1000;
    // tack the photos.size();
    private int c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_reviewp_photo);

        //System.out.println("------------------------------------------------------");
        //System.out.println(photos.size());
        //System.out.println("a = " + a);
        //System.out.println("------------------------------------------------------");
        imageUpload = (ImageView) findViewById(R.id.imageUpload);
        errorBox = (TextView) findViewById(R.id.errorText);
        bUpload = (Button) findViewById(R.id.bImageUpload);
        bTakePhoto = (Button) findViewById(R.id.bTakePhoto);
        bImageDelete = (Button) findViewById(R.id.bImageDelete);
        bImageNext = (Button) findViewById(R.id.bImageNext);
        bImageBack = (Button) findViewById(R.id.bImageBack);
        bConfirm = (Button) findViewById(R.id.bConfirm);
        letGo = (Button) findViewById(R.id.letGo);

        imageUpload.setOnClickListener(this);
        bUpload.setOnClickListener(this);
        letGo.setOnClickListener(this);
        bTakePhoto.setOnClickListener(this);
        bConfirm.setOnClickListener(this);
        bImageDelete.setOnClickListener(this);

        bImageNext.setOnClickListener(this);
        bImageBack.setOnClickListener(this);
        bUpload.setVisibility(bUpload.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        bTakePhoto.setVisibility(bTakePhoto.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        bConfirm.setVisibility(bConfirm.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        bImageDelete.setVisibility(bImageDelete.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        bImageNext.setVisibility(bImageNext.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        bImageBack.setVisibility(bImageBack.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);

        //a = 0;
        //c = 0;

    }
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.letGo:
                Intent intent = getIntent();
                title = intent.getStringExtra("Title");
                description = intent.getStringExtra("Description");
                location = intent.getStringExtra("Location");
                date = intent.getStringExtra("Date");
                photos = intent.getStringArrayListExtra("photos");
                //photos = new ArrayList<String>();
                photo = photos.get(0);
                //RPhotos = new ArrayList<Bitmap>();
                setImage(photo);
                c = photos.size()-1;
                bUpload.setVisibility(bUpload.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                bTakePhoto.setVisibility(bTakePhoto.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                bConfirm.setVisibility(bConfirm.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                bImageDelete.setVisibility(bImageDelete.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                bImageNext.setVisibility(bImageNext.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                bImageBack.setVisibility(bImageBack.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                letGo.setVisibility(letGo.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);



            case R.id.imageUpload:

                if(b==10000){
                    // c is use for check confirm status c == a + 1 means need to confirm, c == a-1 means have confirmed
                    c = a + 1;
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                }

                else
                    errorBox.setText("you have confirmed plz delete first.");
                break;
            case R.id.bConfirm:

                    if(c==(a + 1)){
                        bmp = ((BitmapDrawable) imageUpload.getDrawable()).getBitmap(); // store the image in your bitmap
                        ByteArrayOutputStream bao = new ByteArrayOutputStream();
                        bmp.compress(Bitmap.CompressFormat.PNG, 50, bao);
                        bmp = compressFunction(bmp);
                        photo = toBase64(bmp);
                        photos.add(photo);
                        //System.out.println("------------------------------------------------------");
                        //System.out.println(RPhotos);
                        //System.out.println("a = " + a);
                        //System.out.println("------------------------------------------------------");
                        // now b = a not equal to 10000 , disable user to add
                        b = a;
                        c = a - 1;}
                    else if(c==(a-1)){

                        errorBox.setText("you already confirmed.");}
                    else
                        errorBox.setText("you already have a photo here or you have not selected yet.");
                break;
            case R.id.bImageUpload:
                Intent yourIntent = new Intent(this, RequesterAddTaskActivity.class);
                putInfor(yourIntent);
                yourIntent.putStringArrayListExtra("yourImage", photos);
                //Bitmap bmp = ((BitmapDrawable) imageUpload.getDrawable()).getBitmap(); // store the image in your bitmap
                //ByteArrayOutputStream bao = new ByteArrayOutputStream();
                //bmp.compress(Bitmap.CompressFormat.PNG, 50, bao);
                //yourIntent.putStringArrayListExtra("yourImage",photos);
                startActivity(yourIntent);
                break;
            case R.id.bTakePhoto:

                if(b==10000){
                    c = a + 1;
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
                        //a = a +1 ;
                        imageUpload.setImageResource(0);
                        photo = photos.get(a);
                        setImage(photo);
                        //bmp = RPhotos.get(a);
                        //b = 100000;
                        //c = c -1;
                    }
                    else{
                        imageUpload.setImageResource(0);
                        photos.remove(a);
                        //System.out.println("------------------------------------------------------");
                        //System.out.println(RPhotos);
                        //System.out.println("a = " + a);
                        //System.out.println("------------------------------------------------------");
                        // now user can add image
                        b = 100000;
                        //c = c -1;
                        }}

                else
                    errorBox.setText("you have not selected anything yet !");
                break;
            case R.id.bImageNext:
                if((a!=b)&&(a == (photos.size()-1))){
                    if (photos.size() < 10){
                        imageUpload.setImageResource(0);
                        //System.out.println("------------------------------------------------------");
                        //System.out.println(photos);
                        //System.out.println("a = " + a);
                        //System.out.println("------------------------------------------------------");
                        a = a + 1;
                        // when b == 10000 , we allow user to add photo!!!!!!
                        b = 10000;}

                    else
                    {errorBox.setText("u already input 10 images plz stop!!!");}}
                else if ((a==b)&&(a == (photos.size()-1))){
                    if (photos.size() < 10){
                        imageUpload.setImageResource(0);
                        //System.out.println("------------------------------------------------------");
                        //System.out.println(photos);
                        //System.out.println("a = " + a);
                        //System.out.println("------------------------------------------------------");
                        a = a + 1;
                        // when b == 10000 , we allow user to add photo!!!!!!
                        b = 10000;}

                    else
                    {errorBox.setText("u already input 10 images plz stop!!!");}}
                else if (a < photos.size()-1){
                    a = a + 1;
                    imageUpload.setImageResource(0);
                    //bmp = RPhotos.get(a);
                    //imageUpload.setImageBitmap(bmp);
                    photo = photos.get(a);
                    setImage(photo);
                    //b = a;
                }


                else
                    errorBox.setText("Plz select a photo and confirm first.");
                //largest part c
                break;
            case R.id.bImageBack:
                if ((a!=0)) {
                    a = a - 1;
                    //bmp = RPhotos.get(a);
                    photo = photos.get(a);
                    imageUpload.setImageResource(0);
                    setImage(photo);
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
    public void setImage(String photo) {
        byte[] decodedString = Base64.decode(photo, Base64.DEFAULT);
        Glide.with(TaskReviewpPhotoActivity.this)
                .load(decodedString)
                .asBitmap()
                //.placeholder(R.drawable.ic_broken)
                .into(imageUpload);

    }

}
