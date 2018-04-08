package professional.team17.com.professional.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import professional.team17.com.professional.Adapters.GridViewAdapter;
import professional.team17.com.professional.R;

/**
 * This activity is used to display the image from the download task.photos()
 * basically its the same as the requester add photos
 * but just for check no edit
 */
public class providerCheckImage extends Navigation {
    private ImageButton back;
    private ArrayList<String> photos;
    private GridView gridView;
    private GridViewAdapter gridViewAdapter;

    /**
     * onCreate
     * 1. get intent from the Task
     * 2. initialize the imageView
     * @param savedInstanceState The activity's previously saved state.
     */

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

    /**
     * This activity is used for set Image to the photoView in order to let user check the photos.
     * @param photo
     */

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



