package professional.team17.com.professional.Activity;

import  android.app.Activity;
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

import professional.team17.com.professional.Dialogs.ConfirmDialog;
import professional.team17.com.professional.Adapters.GridViewAdapter;
import professional.team17.com.professional.R;

/**
 * Task PhotoActivity handles photos when adding them to task
 * @author Allison, Zhipeng
 * @since Apr 6, 2018
 * @version 2.0
 */

public class TaskPhotoActivity extends AppCompatActivity implements ConfirmDialog.ConfirmDialogListener {
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
    private float ratio, floatHeight, floatWidth;

    /**
     *
     * @param savedInstanceState - default parameter
     */
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

    /**
     * @param v - the view that holds the photo to be deleted
     */
    public void deletePhoto(View v){
        declineBidDialog();

    }

    @Override
    public void onFinishConfirmDialog(Boolean confirmed) {

    }

    /**
     *
     * @param confirmed - boolean
     * @param dialog - string
     */
    public void onFinishConfirmDialog(Boolean confirmed, String dialog) {
        if (confirmed == true){
            photos.remove(index);
            gridViewAdapter.notifyDataSetChanged();
            if (photos.size()>0 && index>0) {
                String photo = photos.get(index - 1);
                setImage(photo);
                index = index-1;
            }
            setView();
    }}

    /**
     * Set visibility of view depending on existence of photo
     */
    private void setView() {
        if (photos.size()==0){
            deletePhoto.setVisibility(View.GONE);
            editPhoto.setVisibility(View.GONE);
        }
        else {
            deletePhoto.setVisibility(View.VISIBLE);
            editPhoto.setVisibility(View.VISIBLE);
        }
        if (photos.size()<10){
            addphoto.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Set photo
     */
    public void setPhotos(){
        Intent intent = getIntent();
        photos = intent.getStringArrayListExtra("photos");
        if (photos.size() > 0){
            setImage(photos.get(0));
            index =0;
            emptyPhoto.setVisibility(View.GONE);
        }
    }

    /**
     * @param requestCode - in integer
     * @param resultCode - in interger
     * @param data - the result from prior activity (camera/gallery)
     */
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

    /**
     * Set photo in a view
     * @param index - the index the photo should be in
     * @param photo - the photo
     */
    private void setPhoto(int index, String photo) {
        if (photos.size()<=index){
            photos.add(photo);
            setView();
        }
        else {
            photos.set(index, photo);
        }
    }

    /**
     * @param v - View
     */
    public void addPhoto(View v){
        photoDialog();
        if (index==-1){
            index =0;
        }
        else {
            index = photos.size(); // The last of index
        }
    }

    /**
     * show photo dialog when edit is chosen
     * @param v - View
     */
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

    /**
     * Compress & scale photo
     * @param bitmap
     * @return
     */
    public Bitmap compressFunction(Bitmap bitmap) {
        int newWidth = 128;
        floatHeight = bitmap.getHeight();
        floatWidth = bitmap.getWidth();
        ratio = floatHeight / floatWidth;
        int newHeight = Math.round(newWidth * ratio);
        Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
        return newBitmap;
    }

    /**
     * @param bitmap - bitmap of photo
     * @return - photo encoded to string of bytes
     */
    public String toBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    /**
     * @param photo - photo chosen by requester
     */
    public void setImage(String photo) {
        byte[] decodedString = Base64.decode(photo, Base64.DEFAULT);
        Glide.with(TaskPhotoActivity.this)
                .load(decodedString)
                .asBitmap()
                //.placeholder(R.drawable.ic_broken)
                .into(viewImage);
    }

    /**
     * Dialog to allow user to choose their way to uploading a photo
     */
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

    /**
     * Direct requester to gallery
     */
    private void toGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, FILE_REQUEST);
    }

    /**
     * Direct requester to camera
     */
    private void toCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST);
    }
}