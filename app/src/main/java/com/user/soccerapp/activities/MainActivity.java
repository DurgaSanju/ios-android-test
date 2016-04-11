package com.user.soccerapp.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.ListView;
import android.widget.Toast;

import com.user.soccerapp.R;
import com.user.soccerapp.adapters.FixtureAdapter;
import com.user.soccerapp.constants.DBConstants;
import com.user.soccerapp.helper.DBHelper;
import com.user.soccerapp.interfaces.SoccerAPI;
import com.user.soccerapp.model.Soccer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import retrofit.RestAdapter;

public class MainActivity extends Activity implements DBConstants {

    //Root URL of our web service
    public static final String ROOT_URL = "http://api.football-data.org/alpha";

    // API Key of web service
    private static final String API_KEY = "24171c118a404654ab40359d9a324047";

    //List view to show data
    private ListView myListView;

    private ArrayList<Soccer.Fixtures> myFixtureArrayList;

    private FixtureAdapter myListAdapter;
    private Context myContext;
    private DBHelper myDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myContext = this.getApplicationContext();

        classAndWidgetInitialize();

        // if data stored in db load data from database
        if (myDBHelper.getSoccerData().size() > 0) {
            myListAdapter = new FixtureAdapter(myContext, myDBHelper.getSoccerData());
            myListView.setAdapter(myListAdapter);
        } else {
            if (isInternetOn(myContext)) {
                //Calling the method that will fetch data online
                BackgroundTask task = new BackgroundTask();
                task.execute();
            } else {
                // if no internet connection
                Toast.makeText(getApplicationContext(), "Please check your internet connection!!!", Toast.LENGTH_LONG).show();
                MainActivity.this.finish();
            }
        }
    }

    /**
     * Background task to fetch data from web service
     */
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
            Soccer aSoccer = methods.getSoccer(API_KEY);

            // create database
            constructNewFileFromResources();

            // Store data in DB
            myDBHelper.storeSoccerData(aSoccer.fixtures);

            return aSoccer;
        }

        @Override
        protected void onPostExecute(Soccer aSoccer) {
            // dismiss progress dialog
            if (loading.isShowing())
                loading.dismiss();
            myFixtureArrayList = aSoccer.fixtures;

            // load data in listview
            myListAdapter = new FixtureAdapter(myContext, myFixtureArrayList);
            myListView.setAdapter(myListAdapter);

        }
    }

    private void classAndWidgetInitialize() {
        //Initializing the listview
        myListView = (ListView) findViewById(R.id.listViewSoccer);

        myDBHelper = new DBHelper(myContext);

    }

    /**
     * Create database
     */
    public void constructNewFileFromResources() {

        try {
            String packageName = getApplicationContext().getPackageName();

            String appDatabaseDirectory = String.format(
                    "/data/data/%s/databases", packageName);

            (new File(appDatabaseDirectory)).mkdir();

            OutputStream dos = new FileOutputStream(appDatabaseDirectory + "/"
                    + DATABASE_NAME);

            InputStream dis = getResources().openRawResource(
                    DB_RAW_RESOURCES_ID);
            byte[] buffer = new byte[1028];
            while ((dis.read(buffer)) > 0) {
                dos.write(buffer);
            }
            dos.flush();
            dos.close();
            dis.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Check status of network connectivity
     *
     * @param aContext
     * @return
     */
    public static boolean isInternetOn(Context aContext) {

        boolean aResult = false;

        ConnectivityManager aConnecMan = (ConnectivityManager) aContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if ((aConnecMan.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED)
                || (aConnecMan.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTING)
                || (aConnecMan.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING)
                || (aConnecMan.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED)) {

            aResult = true;

        } else if ((aConnecMan.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED)
                || (aConnecMan.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED)) {

            aResult = false;
        }

        return aResult;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myDBHelper.close();
    }
}
