package professional.team17.com.professional;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.Log;

import java.lang.reflect.Array;
import java.nio.ByteBuffer;

/**
 * Photo - for profile & tasks
 * @author  Zhipeng Zhang
 * 2018/3/21.
 */

public class Photo {
    private String path;
    private Bitmap bitmap, uploadBitmap;
    private Drawable drawable;
    private int size, width, height;
    private ByteBuffer byteBuffer;
    private byte[] byteArray;
    private String byteArrayStr, uploadString;
    private Bitmap.Config config;
    private int newWidth = 128;
    private int newHeight;
    private float ratio, floatHeight, floatWidth;

    /**
     * @param path that leads to the photo
     */
    public Photo(String path){
        this.path = path;
    }

    /**
     * @param bitmap - bitmap of photo
     */
    public Photo(Bitmap bitmap){
        this.bitmap = bitmap;
    }

    /**
     * Constructor for photo
     * @param byteArrayStr - String of photo in bytes
     * @param config - bitmap configuration of photo
     * @param width - width of photo
     * @param height - height of photo
     */
    public Photo(String byteArrayStr, Bitmap.Config config, int width, int height){
        this.byteArrayStr = byteArrayStr;
        this.config = config;
        this.width = width;
        this.height = height;
    }

    /**
     * convert photo to bitmap
     * @return photo after scaling
     */
    public Bitmap toBitMap(){
        bitmap = BitmapFactory.decodeFile(this.path);
        newHeight = getNewHeight();
        Log.i("message", "------------------------------------------------------------------------------width: "+newWidth);
        Log.i("message", "------------------------------------------------------------------------------height: "+newHeight);
        uploadBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
        return uploadBitmap;
    }

    /**
     * convert bitmap to drawable
     * @return drawable bitmap after scaling
     */
    public Drawable bitmapToDrawable() {
        newHeight = getNewHeight();
        uploadBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
        drawable = new BitmapDrawable(uploadBitmap);
        return drawable;
    }

    /**
     * convert bitmap to drawable
     * @param bitmap of photo
     * @return drawable bitmap
     */
    public Drawable toDrawable(Bitmap bitmap){
        drawable = new BitmapDrawable(bitmap);
        return drawable;
    }

    /**
     * Converts picture pointed by path to drawable
     * @return drawable
     */
    public Drawable pathToDrawable(){
        bitmap = toBitMap();
        drawable = new BitmapDrawable(bitmap);
        return drawable;
    }

    /**
     * Get the size of the photo pointed by path
     * @return size
     */
    public int pathGetSize(){
        bitmap = BitmapFactory.decodeFile(this.path);
        size = bitmap.getByteCount();
        return size;
    }

    /**
     * Convert bitmap to a BASE 64 string
     * @param bitmap of photo
     * @return bitmap as a BASE 64 string
     */
    private String toString(Bitmap bitmap){
        width = bitmap.getWidth();
        height = bitmap.getHeight();

        size = bitmap.getRowBytes() * bitmap.getHeight();
        byteBuffer = ByteBuffer.allocate(size);
        bitmap.copyPixelsToBuffer(byteBuffer);
        byteArray = byteBuffer.array();
        byteArrayStr = new String(Base64.encode(byteArray, Base64.DEFAULT));
        return byteArrayStr;
    }

    /**
     * Converts picture pointed by path to BASE 64 string
     * @return - photo in string prior to upload
     */
    public String pathToString(){
        bitmap = BitmapFactory.decodeFile(this.path);
        newHeight = getNewHeight();
        uploadBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
        uploadString = toString(uploadBitmap);
        return uploadString;
    }

    /**
     * convert bitmap to string
     * @return string format of photo prior to upload
     */
    public String bitmapToString(){
        newHeight = getNewHeight();
        uploadBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
        uploadString = toString(uploadBitmap);
        return uploadString;
    }

    /**
     * @return configuration of photo pointed by path
     */
    public Bitmap.Config pathGetConfig(){
        bitmap = BitmapFactory.decodeFile(this.path);
        newHeight = getNewHeight();
        uploadBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
        config = Bitmap.Config.valueOf(uploadBitmap.getConfig().name());
        return config;
    }

    /**
     * @return configuration of photo in bitmap
     */
    public Bitmap.Config bitmapGetConfig(){
        newHeight = getNewHeight();
        uploadBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
        config = Bitmap.Config.valueOf(uploadBitmap.getConfig().name());
        return config;
    }

    /**
     * @return width of photo pointed by path
     */
    public int pathGetWidth(){
        bitmap = BitmapFactory.decodeFile(this.path);
        newHeight = getNewHeight();
        uploadBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
        width = uploadBitmap.getWidth();
        return width;
    }

    /**
     * @return width of photo that is in bitmap
     */
    public int bitmapGetWidth(){
        newHeight = getNewHeight();
        uploadBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
        width = uploadBitmap.getWidth();
        return width;
    }

    /**
     * @return height of photo pointed by the path
     */
    public int pathGetHeight(){
        bitmap = BitmapFactory.decodeFile(this.path);
        newHeight = getNewHeight();
        uploadBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
        height = uploadBitmap.getHeight();
        return height;
    }

    /**
     * @return height of photo that is in bitmap
     */
    public int bitmapGetHeight(){
        newHeight = getNewHeight();
        uploadBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
        height = uploadBitmap.getHeight();
        return height;
    }

    /**
     * @return bitmap that was converted from a string of bytes
     */
    public Bitmap byteStringToBitMap(){
        byteArray = Base64.decode(byteArrayStr, Base64.DEFAULT);
        Bitmap bitmap_tmp = Bitmap.createBitmap(width, height, config);
        ByteBuffer buffer = ByteBuffer.wrap(byteArray);
        bitmap_tmp.copyPixelsFromBuffer(buffer);
        return bitmap_tmp;
    }

    /**
     * @return path that leads to the photo
     */
    public String getPath(){
        return path;
    }

    public int getNewHeight() {
        floatHeight = this.bitmap.getHeight();
        floatWidth = this.bitmap.getWidth();
        ratio = floatHeight / floatWidth;
        newHeight = Math.round(newWidth * ratio);
        return newHeight;
    }

}
