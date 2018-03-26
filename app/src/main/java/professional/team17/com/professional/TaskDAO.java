package professional.team17.com.professional;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ag on 2018-03-25.
 */

public class TaskDAO extends SQLiteOpenHelper {
    private static String TASKTABLE = "task";
    private static String ACTIONTABLE = "action";


    public TaskDAO(Context context) {
        super(context, "Task", null, 1);
    }


    public void onUpgrade(SQLiteDatabase db, int i, int i1){

    }

    //as offline activity is restricted to add or edit tasks, and these tasks must be in status
    //requested then
    @Override
    public void onCreate(SQLiteDatabase db){
        delete(db);
        String query = "CREATE TABLE"+ TASKTABLE+
                "(id Integer Primary Key, profileName Text, name Text not Null, location text,"+
                "description text, Status text,Date text, lon text, lat text)";
        String query2 = "CREATE TABLE"+ACTIONTABLE+
        "(id Integer primary Key, actionType int)";
        db.execSQL(query);
        db.execSQL(query2);
    }

    public void delete(SQLiteDatabase db){
        String query = "Drop TABLE if exists" +TASKTABLE;
        String query2 = "Drop TABLE if exists "+ACTIONTABLE;
        db.execSQL(query);
        db.execSQL(query2);
    }

    public void insertAll(TaskList taskList){
        for (Task task : taskList) {
            insert(task);
        }
    }

    public void insert(Task task) {
        ContentValues taskdata = task.toContent();
        SQLiteDatabase db = getWritableDatabase();
        String[] id = {task.getUniqueID()+""};

        db.insert(TASKTABLE, null, taskdata);
        ContentValues actiondata = getType(id[0], ActionType.MATCH_SERVER);
        db.insert(ACTIONTABLE, null, actiondata);
    }

    public void insertOffline(Task task) {
        ContentValues taskdata = task.toContent();
        SQLiteDatabase db = getWritableDatabase();
        String[] id = {getId()+""};
        db.insert(TASKTABLE, null, taskdata);
        ContentValues actiondata = getType(id[0], ActionType.ADD_NO_CONNECTION);
        db.insert(ACTIONTABLE, null, actiondata);
    }

    //return unique id for offline connections
    private int getId(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * from"+TASKTABLE, null);
        return c.getCount();
    }

    public void removeOnline(Task task){
        SQLiteDatabase db = getWritableDatabase();
        String[] id = {task.getUniqueID()+""};
        db.delete(TASKTABLE, "id=?", id);
    }

    public void removeOffline(Task task){
        SQLiteDatabase db = getWritableDatabase();
        String[] id = {task.getUniqueID()+""};
        ContentValues data = getType(id[0], ActionType.DELETE_NO_CONNECTION);
        db.update(ACTIONTABLE,data,  "id=?", id);
    }

    public void updateTaskOnline(Task task){
        ContentValues data = task.toContent();
        SQLiteDatabase db = getWritableDatabase();
        String[] id = {task.getUniqueID()+""};
        db.update(TASKTABLE, data, "id=?", id);
    }

    public void updateTaskOffline(Task task){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues taskdata = task.toContent();
        String[] id = {task.getUniqueID()+""};
        ContentValues actiondata = getType(id[0], ActionType.EDIT_NO_CONNECTION);
        db.update(TASKTABLE, taskdata, "id=?", id);
        db.update(ACTIONTABLE,actiondata,  "id=?", id);
    }


    /**
     *
     * @return data set that works with local storage
     */
    private ContentValues getType(String id,  ActionType type){
        ContentValues data = new ContentValues();
        data.put("id", id);
        data.put("actionType", type.getValue());
        return data;
    }

    public TaskList getTasks() {
        TaskList tasklist = new TaskList();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * from " +TASKTABLE + " where status = Requested;", null);
        while (c.moveToNext()) {
            Task task = createTask(c);
            tasklist.add(task);
        }
        return tasklist;
    }

    public Task getTask(String id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * from " +TASKTABLE + " where id = " +id, null);
        Task task = createTask(c);
        return task;
    }

    private Task createTask(Cursor c){
        Task temp;
        String id = c.getString(c.getColumnIndex("id"));
        String profileName = c.getString(c.getColumnIndex("profileName"));
        String name = c.getString(c.getColumnIndex("name"));
        String location = c.getString(c.getColumnIndex("location"));
        String description = c.getString(c.getColumnIndex("description"));
        String Status = c.getString(c.getColumnIndex("status"));
        String Date = c.getString(c.getColumnIndex("date"));
        Double lon = c.getDouble(c.getColumnIndex("lon"));
        Double lat = c.getDouble(c.getColumnIndex("lat"));
        LatLng latLon = new LatLng(lat, lon);
        temp =  new Task(profileName, name, location, description, Date, latLon);
        temp.setId(id);
        return temp;
    }

    public List<String> newTasks() {
        ArrayList<String> list = new ArrayList<String>();
        return getList(ActionType.ADD_NO_CONNECTION);
    }

    public List<String> deletedTasks() {
        ArrayList<String> list = new ArrayList<String>();
        return getList(ActionType.DELETE_NO_CONNECTION);
    }

    public List<String> updateTasks() {
        ArrayList<String> list = new ArrayList<String>();
        return getList(ActionType.EDIT_NO_CONNECTION);
    }

    private ArrayList<String> getList(ActionType actiontype) {
        ArrayList<String>  list = new ArrayList<String>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * from " + ACTIONTABLE + " where actionType =" + actiontype.getValue(), null);
        while (c.moveToNext()) {
            String id = c.getString(c.getColumnIndex("id"));
            list.add(id);
        }
        return list;
    }


    //https://stackoverflow.com/questions/8157755/how-to-convert-enum-value-to-int
    public enum ActionType {
        DELETE_NO_CONNECTION(-1), ADD_NO_CONNECTION(1), EDIT_NO_CONNECTION(2), MATCH_SERVER(0);

        private final int value;
        private ActionType(int value) {
            this.value = value;
        }
         public int getValue(){
            return value;
        }
    }
}
