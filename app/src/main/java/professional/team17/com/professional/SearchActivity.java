/*
 * SearchActivity
 *
 * March 10, 2018
 *
 * Copyright @ 2018 Team 17, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behaviour at the University of Alberta.
 * You can find a copy of the license in the github wiki for this project.
 */
package professional.team17.com.professional;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;


/**
 *
 * An activity where the provider can search for tasks in either requested or bidded states ('open to bidding')
 * On default it will display the most recently requested tasks/
 *
 *
 * @author Allison
 * @see ProviderCustomArrayAdapter , TaskList, ElasticSearchController
 */
public class SearchActivity extends Navigation {
    private ProviderCustomArrayAdapter searchAdapterHelper;
    private ListView listView;
    private SearchView searchView;
    private TaskListController taskListController;

    /**
     * On creation of the activity, set all view objects and onClickListeners.
     * @param savedInstanceState The activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        /* Change activity title */
        this.setActivityTitleProvider("Task Search");
        taskListController = new TaskListController(this);
        taskListController.addAllOpen();

        if (taskListController.checkOffline()){
            Offline fragment = new Offline();
            getSupportFragmentManager().beginTransaction().replace(R.id.constraintLayoutsearch, fragment).commit();
        }

        searchAdapterHelper = new ProviderCustomArrayAdapter(this, taskListController.tasklist);
        listView =findViewById(R.id.provider_taskList_view_list);
        listView.setAdapter(searchAdapterHelper);
        listView.setOnItemClickListener(clickListener);

        //initialize search input
        searchView = (SearchView) findViewById(R.id.Search_Activity_Input);
        searchView.setQueryHint("Enter search");


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search(query);
                searchView.clearFocus(); //remove focus on submit
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    /**
     * This will search the server for a match against the search input,
     * and update the tasklist with the results. If there are no results, it
     * will display an empty message.
     * @param query - the string representing the task being searched for
     */
    private void search(String query) {
        taskListController.search(query);
        searchAdapterHelper.notifyDataSetChanged();
    }

    /**
     * This is an anonymous method to create a click listener for the listview rows. If the row
     * is selected, it packages up the task selected and the position to ViewTaskBidded
     */
    private AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener(){
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            Intent intention = new Intent(SearchActivity.this, ProviderViewTask.class);
            intention = taskListController.findTask(position, intention);
            startActivity(intention);
        }

    };

}