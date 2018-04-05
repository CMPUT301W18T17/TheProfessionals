package professional.team17.com.professional;

import android.graphics.Bitmap;

/**
 * Created by zhipeng4 on 4/4/18.
 */

public class ProfilePhoto {

    private String photoString;
    private Bitmap.Config config;
    private int width;
    private int height;

    public ProfilePhoto(String photoString, Bitmap.Config config, int width, int height){
        this.photoString = photoString;
        this.config = config;
        this.width = width;
        this.height = height;
    }

    public String getPhotoString(){
        return this.photoString;
    }

    public Bitmap.Config getConfig(){
        return this.config;
    }

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
        return this.height;
    }

}
