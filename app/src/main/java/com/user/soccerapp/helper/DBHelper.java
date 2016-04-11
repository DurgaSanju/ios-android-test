package com.user.soccerapp.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.user.soccerapp.constants.DBConstants;
import com.user.soccerapp.model.Soccer;

import java.util.ArrayList;

/**
 * DB helper class to store data in database
 */
public class DBHelper implements DBConstants {
    private int DATABASE_VERSION = 1;
    private String TAG = DBHelper.class.getSimpleName();
    private DataBaseHelper myDBHelper;
    private SQLiteDatabase myDataBase;

    public DBHelper(Context context) {
        myDBHelper = new DataBaseHelper(context);
        myDataBase = myDBHelper.getReadableDatabase();
        open();
    }


    public class DataBaseHelper extends SQLiteOpenHelper {

        public DataBaseHelper(Context context) {

            super(context, DATABASE_NAME, null, DATABASE_VERSION);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

    }

    /**
     * Function to make DB as readable and writable
     */
    private void open() {

        try {
            if (myDataBase == null) {
                myDataBase = myDBHelper.getWritableDatabase();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Function to Close the database
     */
    public void close() {

        try {
            Log.d(TAG, "mySQLiteDatabase Closed");

            // ---Closing the database---
            myDBHelper.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Store the soccer details in table_soccer
     *
     * @param aFixturesList
     */
    public void storeSoccerData(ArrayList<Soccer.Fixtures> aFixturesList) {
        try {
            for (int i = 0; i < aFixturesList.size(); i++) {
                ContentValues values = new ContentValues();
                values.put("home_team", aFixturesList.get(i).getHomeTeamName());
                values.put("away_team", aFixturesList.get(i).getAwayTeamName());
                values.put("status", aFixturesList.get(i).getStatus());
                values.put("goalsHomeTeam", aFixturesList.get(i).getResult().getGoalsHomeTeam());
                values.put("goalsAwayTeam", aFixturesList.get(i).getResult().getGoalsAwayTeam());
                values.put("date_time", aFixturesList.get(i).getDate());
                myDataBase.insert(TABLE_SOCCER, null, values);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return Soccer Info
     */
    public ArrayList<Soccer.Fixtures> getSoccerData() {

        ArrayList<Soccer.Fixtures> myFixtures = new ArrayList<Soccer.Fixtures>();
        ArrayList<Soccer.Fixtures.Result> myResult = new ArrayList<Soccer.Fixtures.Result>();

        try {
            String aQuery = "SELECT * FROM " + TABLE_SOCCER;

            Cursor aCursor = myDataBase.rawQuery(aQuery, null);

            aCursor.moveToFirst();

            if (aCursor.getCount() > 0) {

                while (!aCursor.isAfterLast()) {

                    Soccer.Fixtures aFixtures = new Soccer.Fixtures();
                    Soccer.Fixtures.Result aResult = new Soccer.Fixtures.Result();

                    aFixtures.setHomeTeamName(aCursor.getString(aCursor
                            .getColumnIndex("home_team")));
                    aFixtures.setAwayTeamName(aCursor.getString(aCursor.getColumnIndex("away_team")));
                    aFixtures.setStatus(aCursor.getString(aCursor.getColumnIndex("status")));
                    aFixtures.setDate(aCursor.getString(aCursor.getColumnIndex("date_time")));
                    aResult.setGoalsHomeTeam(aCursor.getString(aCursor.getColumnIndex("goalsHomeTeam")));
                    aResult.setGoalsAwayTeam(aCursor.getString(aCursor.getColumnIndex("goalsAwayTeam")));

                    aFixtures.setResult(aResult);
                    myFixtures.add(aFixtures);
                    aCursor.moveToNext();
                }
            }
            aCursor.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return myFixtures;
    }
}
