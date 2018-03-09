package professional.team17.com.professional;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;

/**
 * Created by ag on 2018-02-22.
 */

/**
* TODO Below is build with assumption of two indexes (one for profile, one for tasks)
 * We could also potentially combine profile and tasks into one completely denormalized index
 * http://cmput301.softwareprocess.es:8080/cmput301w18t17
 */


public class ElasticSearchController {
    private static JestDroidClient client;
    private static String server = "http://cmput301.softwareprocess.es:8080";
    private static String task = "http://cmput301.softwareprocess.es:8080/CMPUT301W18T17/task"; //TODO COMPLETE name








    public static class AddTask extends AsyncTask<Task, Void, Void> {
//TODO Complete
        @Override
        protected Void doInBackground(Task... Tasks) {
            verifySettings();
            return null;
        }
    }


    public static class GetTasks extends AsyncTask<String, Void, TaskList> {
        //TODO Complete
        @Override
        protected TaskList doInBackground(String... TaskList) {
            verifySettings();
            TaskList tasks= new TaskList();
            return tasks;
        }
    }


    public static class GetTask extends AsyncTask<String, Void, Task> {
        //TODO Complete
        @Override
        protected Task doInBackground(String... Task) {
            verifySettings();
            //TODO currently null for stub
            Task task = null;
            return task;
        }
    }

    public static class AddProfile extends AsyncTask<Profile, Void, Void> {
        //TODO Complete
        @Override
        protected Void doInBackground(Profile... Profiles) {
            verifySettings();
            for (Profile profile : Profiles) {
                Index index = new Index.Builder(profile).index("cmput301w18t17").type("profile").id("1").build();

                try {
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()) {
                        Log.i("PREINLAKD", "doInBackground: ");

                    } else {
                        Log.i("Error", "some error = (");

                    }
                }
                catch (Exception e){
                        Log.i("Error", "The application failed to build and send the tweets");

                    }
                }
            return null;
            }
    }


    public static class GetProfile extends AsyncTask<String, Void, Profile> {
        //TODO Complete
        @Override
        protected Profile doInBackground(String... search_parameters) {
            verifySettings();
          //  Profile profile = new Profile();
        //    return profile;
            return null;
        }

    }

//TODO Updates may be redundant and can be absorbed into adds
    public static class UpdateProfile extends AsyncTask<Profile, Void, Void> {
    //TODO Complete
        @Override
        protected Void doInBackground(Profile... Profiles) {
            verifySettings();
            return null;
        }
    }


    public static class UpdateTask extends AsyncTask<Task, Void, Void> {
        //TODO Complete
        @Override
        protected Void doInBackground(Task... Profiles) {
            verifySettings();
            return null;
        }
    }

    public static void verifySettings() {
            if (client == null) {
                DroidClientConfig.Builder builder = new DroidClientConfig.Builder(server);
                DroidClientConfig  config = builder.build();
                JestClientFactory factory = new JestClientFactory();
                factory.setDroidClientConfig(config);
                client = (JestDroidClient) factory.getObject();
            }
        }
    }

