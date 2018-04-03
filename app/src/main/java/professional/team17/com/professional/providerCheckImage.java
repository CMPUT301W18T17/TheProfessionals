package professional.team17.com.professional;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import com.bumptech.glide.Glide;

public class providerCheckImage extends Navigation {
    private ImageView imageUpload;
    private Button back;
    private Integer k = 0;
    private Integer j = 0;
    private ArrayList<String> photos;
    private String id;
    private Bitmap.Config config;
    private ArrayList<Bitmap.Config> configs = new ArrayList<Bitmap.Config>();
    private String photo;
    //private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.provider_chech_photo);
        Intent i = getIntent();
        //id = i.getStringExtra("yourId");
        photos = i.getStringArrayListExtra("yourIntent");
        photo = photos.get(k);
        byte[] photoList = Base64.decode(photo, Base64.DEFAULT);
        Glide.with(this)
                .load(photoList)
                .asBitmap()
                //.placeholder(R.drawable.ic_broken)
                .into(imageUpload);
        //if (getIntent().hasExtra("yourImage")) {
        //task = serverHelper.getTask(id);
        //photos = task.getPhotos();
        //a = photos.get(k);
        //configs = task.getConfigs();
        //config = configs.get(k);
        //System.out.println("------------------------------------------------------");

        //System.out.println("------------------------------------------------------");
        //byte[] decodedString = Base64.decode(a, Base64.DEFAULT);

        //Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        //if (decodedByte != null){
        //System.out.println("------------------------------------------------------");
        //System.out.println(decodedByte);
        //System.out.println("------------------------------------------------------");}
        //Bitmap bitmap_tmp = Bitmap.createBitmap(64, 64, config);
        //ByteBuffer buffer = ByteBuffer.wrap(decodedString);
        //bitmap_tmp.copyPixelsFromBuffer(buffer);
        //imageUpload.setImageBitmap(bitmap_tmp);

            //Bitmap bmp = BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("yourImage"), 0, getIntent().getByteArrayExtra("yourImage").length);

        }

    public void toTheBack(View view) {
        finish();
    }
    public void next(View view){
        k = k + 1;
        if(photos.get(k)!=null) {
            photo = photos.get(k);
            config = configs.get(k);
            byte[] photoList = Base64.decode(photo, Base64.DEFAULT);
            Glide.with(this)
                    .load(photoList)
                    .asBitmap()
                    //.placeholder(R.drawable.ic_broken)
                    .into(imageUpload);
        }
    }

}
