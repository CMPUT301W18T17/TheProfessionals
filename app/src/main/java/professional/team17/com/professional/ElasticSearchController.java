package professional.team17.com.professional;

import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.util.List;

import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;


/**
 *  Class used to connect to server
 */
public class ElasticSearchController {
    private static JestDroidClient client;
    private static String server = "http://cmput301.softwareprocess.es:8080";
    private static String INDEXNAME = "cmput301w18t17";
    private static String TASKTYPE = "task";
    private static String profiletype = "profile";

    /**
     * This AsyncTask will add a profile to the db (can also be used to update profile)
     */
    public static class AddProfile extends AsyncTask<Profile, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Profile... profiles) {
            verifySettings();
            Boolean success = true;
            for (Profile profile : profiles) {
                Index index = new Index.Builder(profile)
                        .index(INDEXNAME)
                        .type(profiletype)
                        .id(profile.getUserName())
                        .build();
                try {
                    DocumentResult result = client.execute(index);
                }
                catch (Exception e) {
                    new OfflineException();
                    Log.i("Error", "The application failed to build and send the profile");
                    success = false;
                }
            }
            return success;
        }
    }

    /**
     * This AsyncTask will retrieve profile from the db matching a username.
     */
    public static class GetProfile extends AsyncTask<String, Void, Profile> {
        @Override
        protected Profile doInBackground(String... id) {
            verifySettings();
            Profile profile = null;
            Get get = new Get.Builder(INDEXNAME, id[0])
                    .type(profiletype)
                    .build();
            try {
                JestResult result = client.execute(get);
                if (result.isSucceeded()) {
                    profile = result.getSourceAsObject(Profile.class);
                }
                else{
                    Log.i("error", "Search query failed to find any thing =/");
                }
            }
            catch (Exception e) {
                new OfflineException();
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }
            return profile;
        }
    }

    /**
     *  This AsyncTask will add a task to the db.
     *  It will then set the task.id equal to the db.id set at time of insertion.
     *
     */
    public String AddTask1(Task task) {
                String id = "r";
        verifySettings();
                Index index = new Index.Builder(task)
                        .index(INDEXNAME)
                        .type(TASKTYPE)
                        .build();
                try {
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()){
                        id = result.getId();
                    }
                    else {
                        Log.i ("Error", "some error = (");
                    }
                }
                catch (Exception e) {
                    Log.i("Error", "The application failed to build and add the task");
                }
            Log.i("ERR", "doInBackground: ADDED ATASK");
            return id;
        }

    /**
     *  This AsyncTask will add a task to the db.
     *  It will then set the task.id equal to the db.id set at time of insertion.
     *
     */
    public static class AddTask extends AsyncTask<Task, Void, String> {
        @Override
        protected String doInBackground(Task... tasks) {
            verifySettings();
            String id = null;

            for (Task task : tasks) {
                Index index = new Index.Builder(task)
                        .index(INDEXNAME)
                        .type(TASKTYPE)
                        .build();
                try {
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()){
                        id = result.getId();
                    }
                    else {
                        Log.i ("Error", "some error = (");
                    }
                }
                catch (Exception e) {
                    new OfflineException();
                    Log.i("Error", "The application failed to build and add the task");
                }
            }
            return id;
        }
    }


    /**
     *  This AsyncTask will update a task from the db based on a taskid
     */
    public static class UpdateTask extends AsyncTask<Task, Void, Void> {

        @Override
        protected Void doInBackground(Task... tasks) {
            verifySettings();

            for (Task task : tasks) {
                Index index = new Index.Builder(task)
                        .index(INDEXNAME)
                        .type(TASKTYPE)
                        .id(task.getUniqueID())
                        .build();

                try {

                    DocumentResult result = client.execute(index);
                }
                catch (Exception e) {
                    new OfflineException();
                    Log.i("Error", "The application failed to build and update the task");

                }
            }
            return null;
        }
    }
    /**
     *  This AsyncTask will update a task from the db based on a taskid
     */
    public static class DeleteProfile extends AsyncTask<Profile, Void, Void> {

        @Override
        protected Void doInBackground(Profile... profiles) {
            verifySettings();

            for (Profile profile : profiles) {
                Delete delete = new Delete.Builder(profile.getUserName())
                        .index(INDEXNAME)
                        .type(profiletype)
                        .build();
                try {
                    DocumentResult result = client.execute(delete);
                }
                catch (Exception e) {
                    new OfflineException();
                    Log.i("Error", "The application failed to build and delete the profile");

                }
            }
            return null;
        }
    }
    /**
     *  This AsyncTask will update a task from the db based on a taskid
     */
    public static class DeleteTask extends AsyncTask<Task, Void, Void> {

        @Override
        protected Void doInBackground(Task... tasks) {
            verifySettings();

            for (Task task : tasks) {
                Delete delete = new Delete.Builder(task.getUniqueID())
                        .index(INDEXNAME)
                        .type(TASKTYPE)
                        .build();
                try {
                    DocumentResult result = client.execute(delete);
                }
                catch (Exception e) {
                    new OfflineException();
                    Log.i("Error", "The application failed to build and delete the task");
                }
            }
            return null;
        }
    }
    /**
     *  This AsyncTask will retrieve a task from the db based on a taskid
     */
    public static class GetTask extends AsyncTask<String, Void, Task> {
        //TODO Complete
        @Override
        protected Task doInBackground(String... id) {
            verifySettings();
            Task task = null;
            Get get = new Get.Builder(INDEXNAME, id[0])
                    .type(TASKTYPE)
                    .build();
            try {
                JestResult result = client.execute(get);
                if (result.isSucceeded()) {
                    task = result.getSourceAsObject(Task.class);
                    task.setId(result.getValue("_id").toString());
                }
                else{
                    Log.i("error", "Search query failed to find any thing =/");
                }
            }
            catch (Exception e) {
                new OfflineException();
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }
            return task;
        }

    }

    /**
     * This async task will return a list of task dependent on the preformatted query
     */
    public static class GetTasks extends AsyncTask<String, Void, TaskList> {
        @Override
        protected TaskList doInBackground(String... search_para) {
            verifySettings();
            TaskList taskList = new TaskList();
            Search search = new Search.Builder(search_para[0])
                    // multiple index or types can be added.
                    .addIndex(INDEXNAME)
                    .addType(TASKTYPE)
                    .build();
            try {
                SearchResult result = client.execute(search);
                if (result.isSucceeded()){
                    List<Task> foundTask= result.getSourceAsObjectList(Task.class);
                    taskList.addAll(foundTask);
                }
                else {
                    Log.i ("error","Search query failed to find any thing =/");
                }
            }
            catch (Exception e) {
                new OfflineException();
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }
            return taskList;
        }
    }

    public static void verifySettings() {
        if (client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder(server);
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }
}

