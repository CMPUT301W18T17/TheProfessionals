package professional.team17.com.professional;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * Created by Zhipeng Zhang on 2018/3/21.
 */

public class Photo {

    private String path;
    private Bitmap bitmap;
    private Drawable drawable;

    public Photo(String path){
        this.path = path;
    }

    public Photo(Bitmap bitmap){
        this.bitmap = bitmap;
    }

    public Bitmap toBitMap(){
        bitmap = BitmapFactory.decodeFile(this.path);
        return bitmap;
    }

    public Drawable bitMappToDrawable(){
        drawable = new BitmapDrawable(bitmap);
        return drawable;
    }

    public Drawable photoToDrawable(){
        Log.i("***InPhotoPath***","############################################################################################################"+path);
        bitmap = BitmapFactory.decodeFile(this.path);
        Log.i("***InPhotoPath***","############################################################################################################"+path);
        drawable = new BitmapDrawable(bitmap);
        return drawable;
    }

    public String getPath(){
        return path;
    }
}
