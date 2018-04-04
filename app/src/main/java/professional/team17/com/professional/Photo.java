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
 * Created by Zhipeng Zhang on 2018/3/21.
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
    private int newWidth = 64;
    private int newHeight = 64;

    public Photo(String path){
        this.path = path;
    }

    public Photo(Bitmap bitmap){
        this.bitmap = bitmap;
    }

    public Photo(String byteArrayStr, Bitmap.Config config, int width, int height){
        this.byteArrayStr = byteArrayStr;
        this.config = config;
        this.width = width;
        this.height = height;
    }

    public Bitmap toBitMap(){
        bitmap = BitmapFactory.decodeFile(this.path);
        uploadBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
        return uploadBitmap;
    }

    public Drawable bitmapToDrawable() {
        uploadBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
        drawable = new BitmapDrawable(uploadBitmap);
        return drawable;
    }

    public Drawable toDrawable(Bitmap bitmap){
        drawable = new BitmapDrawable(bitmap);
        return drawable;
    }

    public Drawable pathToDrawable(){
        bitmap = BitmapFactory.decodeFile(this.path);
        uploadBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
        drawable = new BitmapDrawable(uploadBitmap);
        return drawable;
    }

    public int pathGetSize(){
        bitmap = BitmapFactory.decodeFile(this.path);
        size = bitmap.getByteCount();
        return size;
    }

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

    public String pathToString(){
        bitmap = BitmapFactory.decodeFile(this.path);
        uploadBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
        uploadString = toString(uploadBitmap);
        return uploadString;
    }

    public String bitmapToString(){
        uploadBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
        uploadString = toString(uploadBitmap);
        return uploadString;
    }

    public Bitmap.Config pathGetConfig(){
        bitmap = BitmapFactory.decodeFile(this.path);
        uploadBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
        config = Bitmap.Config.valueOf(uploadBitmap.getConfig().name());
        return config;
    }

    public Bitmap.Config bitmapGetConfig(){
        uploadBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
        config = Bitmap.Config.valueOf(uploadBitmap.getConfig().name());
        return config;
    }

    public int pathGetWidth(){
        bitmap = BitmapFactory.decodeFile(this.path);
        uploadBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
        width = uploadBitmap.getWidth();
        return width;
    }

    public int bitmapGetWidth(){
        uploadBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
        width = uploadBitmap.getWidth();
        return width;
    }

    public int pathGetHeight(){
        bitmap = BitmapFactory.decodeFile(this.path);
        uploadBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
        height = uploadBitmap.getHeight();
        return height;
    }

    public int bitmapGetHeight(){
        uploadBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
        height = uploadBitmap.getHeight();
        return height;
    }

    public Bitmap byteStringToBitMap(){
        byteArray = Base64.decode(byteArrayStr, Base64.DEFAULT);
        Bitmap bitmap_tmp = Bitmap.createBitmap(width, height, config);
        ByteBuffer buffer = ByteBuffer.wrap(byteArray);
        bitmap_tmp.copyPixelsFromBuffer(buffer);
        return bitmap_tmp;
    }

    public String getPath(){
        return path;
    }
}
