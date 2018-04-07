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
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 *
 *The local db used to ensure the requesters requested tasks match
 * with the server. As well it function to take over in offline state so
 * the user can add/edit tasks in requested state.
 * @author Allison
 * @see TaskList (associated)
 * @see Task  (associated)
 */
public class  TaskDAO extends SQLiteOpenHelper {
    private static String TASKTABLE = "task";

    /**
     *
     * @param context - the context opening the db
     */
    public TaskDAO(Context context) {
        super(context, "task", null, 1);
    }

    /**
     *
     * @param db - the db object
     * @param i - the version
     * @param i1 - the new version
     */
    public void onUpgrade(SQLiteDatabase db, int i, int i1){
    }

    /**
     *
     * @param db - the db being created
     *           Since we need the local to reflect the most
     *           current state of the server, we delete and createnew
     */
    @Override
    public void onCreate(SQLiteDatabase db){
        delete(db);
        createnew(db);
    }

    /**
     *
     * @param db the db where tables are being dropped - to clear every time connectivity is set up
     */
    private void delete(SQLiteDatabase db){
        String query = "Drop TABLE if exists " +TASKTABLE;
        db.execSQL(query);
    }

    /**
     *
     * @param db - the db where we are creating the new table
     */
    private void createnew(SQLiteDatabase db){
        String query = "CREATE TABLE "+ TASKTABLE+
                " (id Text Primary Key, profileName Text, name Text not Null, location Text,"+
                " description Text, status Text, date Text, lat double, lon double, photos Text, actionType Integer, online Boolean)";
        db.execSQL(query);
    }



    /**
     *
     * @param taskList - the tasklist to be inserted into the DB
     */
    public void insertAll(TaskList taskList){
        SQLiteDatabase db = getWritableDatabase();
        //delete/create because we are essentially updating the entire table
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
        taskdata.put("actionType", ActionType.ADD_ONLINE.getValue());
        db.insert(TASKTABLE, null, taskdata);
        db.close();
    }

    /**
     * |
     * @param task - the task being added while in offline status
     *             The state of insert is tracked with 'actionType' column
     */
    public String insertOffline(Task task) {
        int id = getId();
        task.setId(String.valueOf(id));
        ContentValues taskdata = task.toContent();
        SQLiteDatabase db = getWritableDatabase();
        taskdata.put("actionType", ActionType.ADD_NO_CONNECTION.getValue());
        db.insert(TASKTABLE, null, taskdata);
        db.close();
        return String.valueOf(id);
    }

    /**
     *
     * @return this returns an id that can be used while in offline status
     * It uses the size of the table to ensure a unique id, once synced online
     * the id will be overwritten.
     */
    private int getId(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * from "+TASKTABLE, null);
        int count = c.getCount()+1;
        c.close();
        db.close();
        return count;
    }

    /**
     *
     * @param task  - this removes the task offline
     *              This uses isOffline() to see if it was added offline,
     *              If added offline, simply remove,
     *              If added originally online, tombstone it, to be
     *              synced later.
     */
    public void removeOffline(Task task){
        SQLiteDatabase db = getWritableDatabase();
        String[] id = {task.getUniqueID()+""};
        //see if added offline - if so, remove
        if (isOffline(task.getUniqueID())){
            db.delete(TASKTABLE, "id=?", id);
        }
        else {
            //tombstone
            ContentValues taskdata = new ContentValues();
            taskdata.put("actionType", ActionType.DELETE_NO_CONNECTION.getValue());
            db.update(TASKTABLE, taskdata, "id=?", id);
        }
        db.close();
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
        db.close();
    }

    /**
     *
     * @return tasklist  of all tasks in requested state
     */
    public TaskList getTasks() {
        TaskList tasklist = new TaskList();
        SQLiteDatabase db = getReadableDatabase();
        String value = String.valueOf(ActionType.DELETE_NO_CONNECTION.getValue());
        Cursor c = db.rawQuery("SELECT * from " +TASKTABLE + " where actionType <> ?",new String[] {value});
        while (c.moveToNext()) {
            Task task = createTask(c);
            tasklist.add(task);
        }
        c.close();
        db.close();
        return tasklist;

    }

    /**
     *
     * @param id - the unique id of the task to be searched for
     * @return task - the task matching the id
     */
    public Task getTask(String id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * from " +TASKTABLE + " where id = ?", new String[] {id});
       while (c.moveToNext()) {
            Task task = createTask(c);
            c.close();
            return task;
        }
        c.close();
        db.close();
        return null;
    }

    /**
     *
     * @param c the cursor object
     * @return Task - the task created from the db
     */
    private Task createTask(Cursor c){
        Task temp;
        String id = c.getString(c.getColumnIndex("id"));
        String profileName = c.getString(c.getColumnIndex("profileName"));
        String name = c.getString(c.getColumnIndex("name"));
        String location = c.getString(c.getColumnIndex("location"));
        String description = c.getString(c.getColumnIndex("description"));
        String date = c.getString(c.getColumnIndex("date"));
        String photosstr = c.getString(c.getColumnIndex("photos"));
        Double lon = c.getDouble(c.getColumnIndex("lon"));
        Double lat = c.getDouble(c.getColumnIndex("lat"));
        LatLng latLon = new LatLng(lat, lon);
        ArrayList<String> photos =  convertStringToArray(photosstr);
        temp =  new Task(profileName, name, description, location, date, latLon,  photos);
        temp.setId(id);
        return temp;
    }

    /**
     *
     * @param photostr - convert photo string back to array
     * @return
     */
    public static ArrayList<String> convertStringToArray(String photostr){
        String strSeparator = ",";
        String[] arr = photostr.split(strSeparator);
        ArrayList<String> photos = new ArrayList( Arrays.asList( arr) );
        return photos;
    }

    /**
     *
     * @return a list of ids that were added offline
     */
    public ArrayList<String> newTasks() {
        ArrayList<String> list = new ArrayList<String>();
        return getList(ActionType.ADD_NO_CONNECTION);
    }

    /**
     *
     * @return a list of ids that were deleted offline
     */
    public ArrayList<String> deletedTasks() {
        ArrayList<String> list = new ArrayList<String>();
        return getList(ActionType.DELETE_NO_CONNECTION);
    }

    /**
     *
     * @return  a list of ids that were edited offline
     */
    public ArrayList<String> updatedTasks() {
        ArrayList<String> list = new ArrayList<String>();
        return getList(ActionType.EDIT_NO_CONNECTION);
    }

    /**
     *
     * @param actiontype - the enum type of offline edit/update/delete of task
     * @return - a list of ids that match the actionType
     */
    private ArrayList<String> getList(ActionType actiontype) {
        ArrayList<String>  list = new ArrayList<String>();
        SQLiteDatabase db = getReadableDatabase();
        String value = String.valueOf(actiontype.getValue());
        Cursor c = db.rawQuery("SELECT * from " +TASKTABLE + " where actionType = ?",  new String[] {value});
        while (c.moveToNext()) {
            String id = c.getString(c.getColumnIndex("id"));
            list.add(id);
        }
        c.close();
        return list;
    }

    /**
     * \
     * @param id - the task being search for
     * @return
     */
    private boolean isOffline(String id) {
        int online=1;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * from " +TASKTABLE + " where id = ?", new String[] {id});
        while (c.moveToNext()) {
            online = c.getInt(c.getColumnIndex("online"));
        }
        c.close();
        return (online ==0);
    }

    /**
     * The type of add, absolutely necessary for syncing offline/online
     * Learning how to return int was learned from
     * https://stackoverflow.com/questions/8157755/how-to-convert-enum-value-to-int
     */
    private enum ActionType {
        DELETE_NO_CONNECTION(-1), ADD_NO_CONNECTION(1), EDIT_NO_CONNECTION(2), ADD_ONLINE(-2);

        private final int value;
        private ActionType(int value) {
            this.value = value;
        }
         public int getValue(){
            return value;
        }
    }
}
