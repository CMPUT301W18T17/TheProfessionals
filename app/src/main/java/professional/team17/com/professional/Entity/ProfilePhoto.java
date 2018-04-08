package professional.team17.com.professional.Entity;

import android.graphics.Bitmap;

/**
 * ProfilePhoto
 * @author zhipeng4
 * @since 4/4/18.
 */

public class ProfilePhoto {

    private String photoString;
    private Bitmap.Config config;
    private int width;
    private int height;

    /**
     * ProfilePhoto constructor
     * @param photoString - photo string that makes up the photo
     * @param config - bitmap configuration
     * @param width - width of photo
     * @param height - height of photo
     */
    public ProfilePhoto(String photoString, Bitmap.Config config, int width, int height){
        this.photoString = photoString;
        this.config = config;
        this.width = width;
        this.height = height;
    }

    /**
     * @return photoString that makes up the photo
     */
    public String getPhotoString(){
        return this.photoString;
    }

    /**
     * @return photo configuration
     */
    public Bitmap.Config getConfig(){
        return this.config;
    }

    /**
     * @return width of photo
     */
    public int getWidth(){
        return this.width;
    }

    /**
     * @return height of photo
     */
    public int getHeight(){
        return this.height;
    }

}
