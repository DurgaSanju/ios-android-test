package com.user.soccerapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.user.soccerapp.R;
import com.user.soccerapp.model.Soccer;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Adapter to set values in list
 */
public class FixtureAdapter extends BaseAdapter {

    ArrayList<Soccer.Fixtures> myFixtures;
    Context myContext;
    private static LayoutInflater inflater = null;

    public FixtureAdapter(Context activity, ArrayList<Soccer.Fixtures> aFixtures) {
        this.myContext = activity;
        this.myFixtures = aFixtures;
    }

    @Override
    public int getCount() {
        return myFixtures.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        ViewHolder holder;

        if (convertView == null) {

            // custom row layout
            vi = LayoutInflater.from(myContext).inflate(R.layout.layout_list_item, null);

            holder = new ViewHolder();
            holder.homeTeam = (TextView) vi.findViewById(R.id.homeTeamTV);
            holder.awayTeam = (TextView) vi.findViewById(R.id.awayTeamTV);
            holder.status = (TextView) vi.findViewById(R.id.statusTV);
            holder.homeTeamGoals = (TextView) vi.findViewById(R.id.homeTeamGoalTV);
            holder.awayTeamGoals = (TextView) vi.findViewById(R.id.awayTeamGoalTV);
            holder.dateTime = (TextView) vi.findViewById(R.id.dateTV);

            /************  Set holder with LayoutInflater ************/
            vi.setTag(holder);
        } else
            holder = (ViewHolder) vi.getTag();

        if (myFixtures.size() <= 0) {
            holder.homeTeam.setText("No Data");

        } else {
            /************  Set Model values in Holder elements ***********/
            holder.homeTeam.setText(myFixtures.get(position).getHomeTeamName());
            holder.awayTeam.setText(myFixtures.get(position).getAwayTeamName());
            holder.status.setText(myFixtures.get(position).getStatus());
            holder.homeTeamGoals.setText(myFixtures.get(position).getResult().getGoalsHomeTeam());
            holder.awayTeamGoals.setText(myFixtures.get(position).getResult().getGoalsAwayTeam());
            holder.dateTime.setText(getDate(myFixtures.get(position).getDate()));
        }
        return vi;
    }

    private String getDate(String aDateTime) {
        DateFormat inputFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date date = null;
        try {
            date = inputFormatter.parse(aDateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DateFormat outputFormatter = new SimpleDateFormat("MM/dd/yyyy");
        String output = outputFormatter.format(date); // Output : 01/20/2012
        return output;
    }

    /*********
     * Create a holder Class to contain inflated xml file elements
     *********/
    public static class ViewHolder {
        public TextView homeTeam;
        public TextView awayTeam;
        public TextView status;
        public TextView dateTime;
        public TextView homeTeamGoals;
        public TextView awayTeamGoals;
    }

}