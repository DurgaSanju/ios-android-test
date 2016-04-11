package com.user.soccerapp.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;

/**
 * Soccer model class
 */
public class Soccer {

    public ArrayList<Fixtures> getFixtures() {
        return fixtures;
    }

    public void setFixtures(ArrayList<Fixtures> fixtures) {
        this.fixtures = fixtures;
    }

    public ArrayList<Fixtures> fixtures;


    public static class Fixtures {
        //Variables that are in our json
        private String date;
        private String status;
        private String homeTeamName;
        private String awayTeamName;

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

        @Expose
        private Result result;

        public Result getResult() {
            return result;
        }

        public void setResult(Result result) {
            this.result = result;
        }

        public static class Result {

            public void setGoalsHomeTeam(String goalsHomeTeam) {
                this.goalsHomeTeam = goalsHomeTeam;
            }

            public void setGoalsAwayTeam(String goalsAwayTeam) {
                this.goalsAwayTeam = goalsAwayTeam;
            }

            @Expose
            private String goalsHomeTeam;
            @Expose
            private String goalsAwayTeam;

            public String getGoalsHomeTeam() {
                return this.goalsHomeTeam;
            }

            public String getGoalsAwayTeam() {
                return this.goalsAwayTeam;
            }
        }
    }

}
