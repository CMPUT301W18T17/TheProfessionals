package professional.team17.com.professional;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
// basically here I keep two arrayList one is for string (to upload) one is for bitmap(to delete)

public class TaskPhotoActivity2 extends AppCompatActivity {
    TextView errorBox;
    private ImageView imageUpload;
    private ImageButton back;
    private Button addphoto;
    Integer k = 0;
    Integer j = 0;
    String a;
    private ArrayList<String> photos;
    private GridView gridView;
    private Bitmap bmp;
    private GridViewAdapter gridViewAdapter;
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int CAMERA_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_view2);
        photos = new ArrayList<String>();
        getPhotos();

        back = (ImageButton) findViewById(R.id.requester_photo_back);
        gridView = (GridView) findViewById(R.id.requester_taskPhotoGrid);
        addphoto = (Button) findViewById(R.id.requesteraddphoto);
        imageUpload = findViewById(R.id.requester_task_photo);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent yourIntent = new Intent();
                yourIntent.putStringArrayListExtra("yourImage", photos);
                setResult(Activity.RESULT_OK, yourIntent);
                finish();
            }
        });

        gridViewAdapter = new GridViewAdapter(this, photos);
        gridView.setAdapter(gridViewAdapter);
        gridViewAdapter.notifyDataSetChanged();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String photo = photos.get(position);
                setImage(photo);

            }
        });

    }

    public void getPhotos(){
        Intent intent = getIntent();
        photos = intent.getStringArrayListExtra("photos");
        //photos = new ArrayList<String>();
    }

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
                bmp = ((BitmapDrawable) imageUpload.getDrawable()).getBitmap();
                ByteArrayOutputStream bao = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 50, bao);
                bmp = compressFunction(bmp);
                String photo1 = toBase64(bmp);
                photos.add(photo1);
        }
    }

    public void addPhoto(View v){
        photoDialog();

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
        Glide.with(TaskPhotoActivity2.this)
                .load(decodedString)
                .asBitmap()
                //.placeholder(R.drawable.ic_broken)
                .into(imageUpload);
    }


    public void photoDialog(){
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

    private void toGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RESULT_LOAD_IMAGE);
    }

    private void toCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST);
    }
}