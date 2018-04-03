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
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class providerCheckImage extends AppCompatActivity {
    ImageView imageUpload;
    Button back;
    Integer k = 0;
    Integer j = 0;
    String a;
    private ArrayList<String> photos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.provider_chech_photo);
        Intent i = getIntent();
        photos = i.getStringArrayListExtra("yourImage");
        j = photos.size();
        //if (getIntent().hasExtra("yourImage")) {
        a = photos.get(k);
        byte[] decodedString = Base64.decode(a, Base64.DEFAULT);
        Glide.with(this)
                .load(decodedString)
                .asBitmap()
                //.placeholder(R.drawable.ic_broken)
                .into(imageUpload);

            //Bitmap bmp = BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("yourImage"), 0, getIntent().getByteArrayExtra("yourImage").length);

        }

    public void toTheBack(View view) {
        finish();
    }
    public void next(){
        k = k + 1;
        a = photos.get(k);
        byte[] decodedString = Base64.decode(a, Base64.DEFAULT);
        Glide.with(this)
                .load(decodedString)
                .asBitmap()
                //.placeholder(R.drawable.ic_broken)
                .into(imageUpload);

    }

}
