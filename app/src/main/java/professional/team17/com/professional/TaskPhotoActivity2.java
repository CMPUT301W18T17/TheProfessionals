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
import android.support.v4.app.FragmentManager;
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

public class TaskPhotoActivity2 extends AppCompatActivity implements ConfirmDialog.ConfirmDialogListener{
    private TextView emptyPhoto;
    private ImageView viewImage;
    private ImageButton back;
    private ImageButton editPhoto;
    private ImageButton deletePhoto;
    private Button addphoto;
    private ArrayList<String> photos;
    private GridView gridView;
    private Bitmap bmp;
    private int index =-1;
    private GridViewAdapter gridViewAdapter;
    private static final int FILE_REQUEST = 1;
    private static final int CAMERA_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_view2);
        photos = new ArrayList<String>();

        deletePhoto = findViewById(R.id.requester_photo_delete_photo);
        editPhoto = findViewById(R.id.requester_photo_edit_photo);
        emptyPhoto = findViewById(R.id.requester_photo_text);
        back = (ImageButton) findViewById(R.id.requester_photo_back);
        gridView = (GridView) findViewById(R.id.requester_taskPhotoGrid);
        addphoto = (Button) findViewById(R.id.requesteraddphoto);
        viewImage = findViewById(R.id.requester_task_photo);
        setPhotos();
        setView();
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
                index = position;
                String photo = photos.get(index);
                setImage(photo);
            }
        });

    }


    public void deletePhoto(View v){
        declineBidDialog();

    }

    @Override
    public void onFinishConfirmDialog(Boolean confirmed) {

    }

    public void onFinishConfirmDialog(Boolean confirmed, String dialog) {
        if (confirmed == true){
            photos.remove(index);
            gridViewAdapter.notifyDataSetChanged();
            if (photos.size()>0 && index>0) {
                String photo = photos.get(index - 1);
                setImage(photo);
            }
            setView();
    }}

    private void setView() {
        if (photos.size()==0){
            deletePhoto.setVisibility(View.GONE);
            editPhoto.setVisibility(View.GONE);
        }
        else {
            deletePhoto.setVisibility(View.VISIBLE);
            editPhoto.setVisibility(View.VISIBLE);
        }
    }

    public void setPhotos(){
        Intent intent = getIntent();
        photos = intent.getStringArrayListExtra("photos");
        if (photos.size() > 0){
            setImage(photos.get(0));
            index =0;
            emptyPhoto.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            switch (requestCode) {
                case FILE_REQUEST:
                    Uri selectedImage = data.getData();
                    viewImage.setImageURI(selectedImage);
                    gridViewAdapter.notifyDataSetChanged();
                    break;
                case CAMERA_REQUEST:
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    viewImage.setImageBitmap(photo);
                    gridViewAdapter.notifyDataSetChanged();
                    break;
            }


            bmp = ((BitmapDrawable) viewImage.getDrawable()).getBitmap();
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 50, bao);
            bmp = compressFunction(bmp);
            String photo1 = toBase64(bmp);
            setPhoto(index, photo1);
            emptyPhoto.setVisibility(View.INVISIBLE);
            if (photos.size()==10){
                addphoto.setVisibility(View.GONE);
            }
        }
    }

    private void setPhoto(int index, String photo) {
        if (photos.size()<=index){
            photos.add(photo);
            setView();
        }
        else {
            photos.set(index, photo);
        }
    }

    public void addPhoto(View v){
        photoDialog();
        if (index==-1){
            index =0;
        }
        else {
            index = index + 1;
        }
    }

    public void editPhoto(View v){
        photoDialog();

    }

    /**
     * Handles the "delete photo" dialog fragment (populates it with text).
     */
    private void declineBidDialog(){
        FragmentManager fm = getSupportFragmentManager();
        ConfirmDialog confirmDialog = new ConfirmDialog();
        confirmDialog.show(fm, "Delete Photo");
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
                .into(viewImage);
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
        startActivityForResult(intent, FILE_REQUEST);
    }

    private void toCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST);
    }
}