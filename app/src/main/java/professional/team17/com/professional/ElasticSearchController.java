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
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.searchbox.client.JestResult;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

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



        public static class AddProfile extends AsyncTask<Profile, Void, Void> {

            @Override
            protected Void doInBackground(Profile... profiles) {
                verifySettings();

                for (Profile profile : profiles) {
                    Index index = new Index.Builder(profile).index("cmput301w18t17").type("profile").id(profile.getUserName()).build();

                    try {
                        DocumentResult result = client.execute(index);
                        if (result.isSucceeded()) {
                            Log.i("PREINLAKD", "doInBackground: ");

                        } else {
                            Log.i("Error", "some error = (");

                        }
                    }
                    catch (Exception e) {
                        Log.i("Error", "The application failed to build and send the profile");

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
            Profile profile = null;
            Get get = new Get.Builder("cmput301w18t17", search_parameters[0]).type("profile").build();


            //Search search = new Search.Builder(search_parameters[0]).addIndex("cmput301w18t17").addType("profile").build()
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
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            return profile;
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
                DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080");
                DroidClientConfig config = builder.build();

                JestClientFactory factory = new JestClientFactory();
                factory.setDroidClientConfig(config);
                client = (JestDroidClient) factory.getObject();
            }
        }

}
