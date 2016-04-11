package com.user.soccerapp.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Soccer model class
 */
public class Soccer {

    public ArrayList<Fixtures> fixtures;

    public static class Fixtures {
        //Variables that are in our json
        private String date;
        private String status;
        private String homeTeamName;
        private String awayTeamName;
        private int goalsHomeTeam;
        private int goalsAwayTeam;
        public Array result;

        public Array getResult() {
            return result;
        }

        public void setResult(Array result) {
            this.result = result;
        }

        public int getGoalsHomeTeam() {
            return goalsHomeTeam;
        }

        public void setGoalsHomeTeam(int goalsHomeTeam) {
            this.goalsHomeTeam = goalsHomeTeam;
        }

        public int getGoalsAwayTeam() {
            return goalsAwayTeam;
        }

        public void setGoalsAwayTeam(int goalsAwayTeam) {
            this.goalsAwayTeam = goalsAwayTeam;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getHomeTeamName() {
            return homeTeamName;
        }

        public void setHomeTeamName(String homeTeamName) {
            this.homeTeamName = homeTeamName;
        }

        public String getAwayTeamName() {
            return awayTeamName;
        }

        public void setAwayTeamName(String awayTeamName) {
            this.awayTeamName = awayTeamName;
        }
    }

}
