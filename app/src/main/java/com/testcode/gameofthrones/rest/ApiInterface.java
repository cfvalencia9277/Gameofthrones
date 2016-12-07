package com.testcode.gameofthrones.rest;


import com.testcode.gameofthrones.models.GoTCharacter;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


/**
 * Created by Fabian on 07/12/2016.
 */

public interface ApiInterface {

    @GET("/characters.json?print=pretty")
    Call<List<GoTCharacter>> getdata();

}
