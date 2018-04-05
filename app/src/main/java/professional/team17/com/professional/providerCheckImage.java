package professional.team17.com.professional;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.nio.ByteBuffer;
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
        setContentView(R.layout.photo_view);

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
        setImage(photos.get(0));

        ImageView photoView = (ImageView) findViewById(R.id.gridPhotoView);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("ITEM_CLICKED", "" + (String) (gridView.getItemAtPosition(position)));

                String photo = photos.get(position);
                setImage(photo);

            }
        });
    }

    public void setImage(String photo) {
        ImageView photoView = (ImageView) findViewById(R.id.imageView4);
        byte[] decodedString = Base64.decode(photo, Base64.DEFAULT);
        Glide.with(providerCheckImage.this)
                .load(decodedString)
                .asBitmap()
                //.placeholder(R.drawable.ic_broken)
                .into(photoView);

    }
}



