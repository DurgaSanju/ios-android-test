package com.user.soccerapp.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.user.soccerapp.R;
import com.user.soccerapp.adapters.FixtureAdapter;
import com.user.soccerapp.interfaces.SoccerAPI;
import com.user.soccerapp.model.Soccer;

import java.util.ArrayList;

import retrofit.RestAdapter;

public class MainActivity extends Activity {

    //Root URL of our web service
    public static final String ROOT_URL = "http://api.football-data.org/alpha";
    private static final String API_KEY = "24171c118a404654ab40359d9a324047";

    //List view to show data
    private ListView myListView;

    private ArrayList<Soccer.Fixtures> myFixtureArrayList;

    private FixtureAdapter myListAdapter;
    private Context myContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myContext = this.getApplicationContext();

        classAndWidgetInitialize();

        //Calling the method that will fetch data
        BackgroundTask task = new BackgroundTask();
        task.execute();
    }

    private class BackgroundTask extends AsyncTask<Void, Void,
            Soccer> {
        RestAdapter restAdapter;
        ProgressDialog loading;

        @Override
        protected void onPreExecute() {
            //While the app fetched data we are displaying a progress dialog
            loading = ProgressDialog.show(MainActivity.this, "Fetching Data", "Please wait...", false, false);
            restAdapter = new RestAdapter.Builder()
                    .setEndpoint(ROOT_URL)
                    .build();
        }

        @Override
        protected Soccer doInBackground(Void... params) {
            SoccerAPI methods = restAdapter.create(SoccerAPI.class);
            Soccer fixtures = methods.getSoccer(API_KEY);

            return fixtures;
        }

        @Override
        protected void onPostExecute(Soccer aSoccer) {
            if (loading.isShowing())
                loading.dismiss();
            myFixtureArrayList = aSoccer.fixtures;

            myListAdapter = new FixtureAdapter(myContext, myFixtureArrayList);
            myListView.setAdapter(myListAdapter);

        }
    }

    private void classAndWidgetInitialize() {
        //Initializing the listview
        myListView = (ListView) findViewById(R.id.listViewSoccer);

    }


}
