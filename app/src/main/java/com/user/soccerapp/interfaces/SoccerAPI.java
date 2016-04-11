package com.user.soccerapp.interfaces;

import com.user.soccerapp.model.Soccer;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * To fetch data from server
 */
public interface SoccerAPI {
    /*Retrofit get annotation with our URL
      And our method that will return us the list ob Book
   */
    @GET("/soccerseasons/398/fixtures")
    Soccer getSoccer(@Query("X-Auth-Token") String key);
}
