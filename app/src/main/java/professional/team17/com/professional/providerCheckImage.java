package professional.team17.com.professional;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class providerCheckImage extends Navigation {
    private ImageView imageUpload;
    private ImageButton back;
    Integer k = 0;
    Integer j = 0;
    String a;
    private ArrayList<String> photos;
    private GridView gridView;
    private GridViewAdapter gridViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.provider_check_photo);

        back = (ImageButton) findViewById(R.id.back);
        gridView = (GridView) findViewById(R.id.taskPhotoGrid);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent i = getIntent();
        photos = i.getStringArrayListExtra("yourImage");
        gridViewAdapter = new GridViewAdapter(this, photos);
        gridView.setAdapter(gridViewAdapter);
        gridViewAdapter.notifyDataSetChanged();

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
