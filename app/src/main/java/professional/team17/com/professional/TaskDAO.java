package professional.team17.com.professional;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ag on 2018-03-25.
 */

public class TaskDAO extends SQLiteOpenHelper {
    private static String TASKTABLE = "task";


    public TaskDAO(Context context) {
        super(context, "task", null, 1);
    }


    public void onUpgrade(SQLiteDatabase db, int i, int i1){

    }


    @Override
    public void onCreate(SQLiteDatabase db){
        Log.i("WRR", "CREWSAT  DB: ");
        delete(db);
        createnew(db);
    }

    /**
     *
     * @param db the db where tables are being dropped - to clear every time connectivity is set up
     */
    private void delete(SQLiteDatabase db){
        Log.i("DROPOPEDZ", "delete: ");
        String query = "Drop TABLE if exists " +TASKTABLE;
        db.execSQL(query);
    }

    private void createnew(SQLiteDatabase db){
        String query = "CREATE TABLE "+ TASKTABLE+
                " (id Text Primary Key, profileName Text, name Text not Null, location Text,"+
                " description Text, status Text, date Text, lon Text, lat Text, actionType Integer, online Boolean)";
        Log.i("TAG", "create: "+query);
        db.execSQL(query);
    }
    private void create(){
        SQLiteDatabase db = getWritableDatabase();
        createnew(db);
    }


    /**
     *
     * @param taskList - the tasklist to be inserted into the DB
     */
    public void insertAll(TaskList taskList){
        SQLiteDatabase db = getWritableDatabase();
        delete(db);
        createnew(db);
        for (Task task : taskList) {
            insert(task);
        }
    }

    /**
     *
     * @param task the task to be inserted into the DB while in online status
     */
    public void insert(Task task) {
        ContentValues taskdata = task.toContent();
        SQLiteDatabase db = getWritableDatabase();
        String[] id = {task.getUniqueID()+""};
        taskdata.put("online", 1);
        db.insert(TASKTABLE, null, taskdata);
    }

    /**
     * |
     * @param task - the task being added while in offline status
     */
    public String insertOffline(Task task) {
        int id = getId();
        task.setId(String.valueOf(id));
        ContentValues taskdata = task.toContent();
        SQLiteDatabase db = getWritableDatabase();
        taskdata.put("actionType", ActionType.ADD_NO_CONNECTION.getValue());
        db.insert(TASKTABLE, null, taskdata);
        return String.valueOf(id);
    }

    /**
     *
     * @return this returns an id that can be used while in offline status
     */
    private int getId(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * from "+TASKTABLE, null);
        return c.getCount()+1;
    }

    /**
     *
     * @param task this removes the task from the offline
     */
    public void removeOnline(Task task){
        SQLiteDatabase db = getWritableDatabase();
        String[] id = {task.getUniqueID()+""};
        db.delete(TASKTABLE, "id=?", id);
    }

    /**
     *
     * @param task  - this removes the task offline - via  a marker in the action table
     */
    public void removeOffline(Task task){
        SQLiteDatabase db = getWritableDatabase();
        String[] id = {task.getUniqueID()+""};
        //see if added offline - if so, remove
        if (isOffline(task.getUniqueID())){
            db.delete(TASKTABLE, "id=?", id);
        }
        else {
            ContentValues taskdata = new ContentValues();
            taskdata.put("actionType", ActionType.EDIT_NO_CONNECTION.getValue());
            db.update(TASKTABLE, taskdata, "id=?", id);
        }
    }

    /**
     *
     * @param task - this is the task that is being updated
     */
    public void updateTaskOnline(Task task){
        ContentValues data = task.toContent();
        SQLiteDatabase db = getWritableDatabase();
        String[] id = {task.getUniqueID()+""};
        db.update(TASKTABLE, data, "id=?", id);
    }

    /**
     *
     * @param task the task being updated while offline (requiring a marker be set)
     */
    public void updateTaskOffline(Task task){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues taskdata = task.toContent();
        String[] id = {task.getUniqueID()+""};
        if (!isOffline(task.getUniqueID())){
            taskdata.put("actionType", ActionType.EDIT_NO_CONNECTION.getValue());
        }
        db.update(TASKTABLE, taskdata, "id=?", id);
    }


    /**
     *
     * @return data set that works with local storage
     */
    private ContentValues getType(ContentValues c,  ActionType type){
        c.put("actionType", type.getValue());
        return c;
    }

    public TaskList getTasks() {
        TaskList tasklist = new TaskList();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * from " +TASKTABLE + " where status = \"Requested\";", null);
        while (c.moveToNext()) {
            Task task = createTask(c);
            tasklist.add(task);
        }
        return tasklist;
    }


    public Task getTask(String id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * from " +TASKTABLE + " where id = ?", new String[] {id});
       while (c.moveToNext()) {
            Task task = createTask(c);
            return task;
        }
        return null;
    }


    private Task createTask(Cursor c){
        Task temp;
        String id = c.getString(c.getColumnIndex("id"));
        String profileName = c.getString(c.getColumnIndex("profileName"));
        String name = c.getString(c.getColumnIndex("name"));
        String location = c.getString(c.getColumnIndex("location"));
        String description = c.getString(c.getColumnIndex("description"));
        String status = c.getString(c.getColumnIndex("status"));
        String date = c.getString(c.getColumnIndex("date"));
        Double lon = c.getDouble(c.getColumnIndex("lon"));
        Double lat = c.getDouble(c.getColumnIndex("lat"));
        LatLng latLon = new LatLng(lat, lon);
        int offlien = c.getInt(c.getColumnIndex("actionType"));
        Log.i("offlien", "createTask: "+offlien+name);
        ArrayList<Bitmap> photos = new ArrayList<Bitmap>();
        temp =  new Task(profileName, name, description, location, date, latLon , photos);
        temp.setId(id);
        return temp;
    }

    public ArrayList<String> newTasks() {
        ArrayList<String> list = new ArrayList<String>();
        return getList(ActionType.ADD_NO_CONNECTION);
    }

    public ArrayList<String> deletedTasks() {
        ArrayList<String> list = new ArrayList<String>();
        return getList(ActionType.DELETE_NO_CONNECTION);
    }

    public ArrayList<String> updatedTasks() {
        ArrayList<String> list = new ArrayList<String>();
        return getList(ActionType.EDIT_NO_CONNECTION);
    }

    private ArrayList<String> getList(ActionType actiontype) {
        ArrayList<String>  list = new ArrayList<String>();
        SQLiteDatabase db = getReadableDatabase();
        String value = String.valueOf(actiontype.getValue());
        Cursor c = db.rawQuery("SELECT * from " +TASKTABLE + " where actionType = ?",  new String[] {value});
        while (c.moveToNext()) {
            String id = c.getString(c.getColumnIndex("id"));
            list.add(id);
        }
        Log.i("SSD", "getList: "+list);
        return list;
    }

    public boolean isOffline(String id) {
        int online=1;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * from " +TASKTABLE + " where id = ?", new String[] {id});
        while (c.moveToNext()) {
            online = c.getInt(c.getColumnIndex("online"));
        }
        return (online ==0);
    }



    //https://stackoverflow.com/questions/8157755/how-to-convert-enum-value-to-int
    public enum ActionType {
        DELETE_NO_CONNECTION(-1), ADD_NO_CONNECTION(1), EDIT_NO_CONNECTION(2);

        private final int value;
        private ActionType(int value) {
            this.value = value;
        }
         public int getValue(){
            return value;
        }
    }
}
