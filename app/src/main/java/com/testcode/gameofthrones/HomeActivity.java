package com.testcode.gameofthrones;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.testcode.gameofthrones.adapters.SelectionsPagerAdapter;
import com.testcode.gameofthrones.models.GoTCharacter;
import com.testcode.gameofthrones.rest.ApiClient;
import com.testcode.gameofthrones.rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Fabian on 07/12/2016.
 */

public class HomeActivity extends AppCompatActivity {

    SelectionsPagerAdapter spa;
    ViewPager vp;
    Toolbar toolbar;
    TabLayout tabLayout;
    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setSpa(new SelectionsPagerAdapter(getSupportFragmentManager()));

        setVp((ViewPager) findViewById(R.id.container));
        getVp().setAdapter(getSpa());

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(getVp());
        fetchdata();
    }
    public SelectionsPagerAdapter getSpa() {
        return spa;
    }

    public void setSpa(SelectionsPagerAdapter spa) {
        this.spa = spa;
    }

    public ViewPager getVp() {
        return vp;
    }

    public void setVp(ViewPager vp) {
        this.vp = vp;
    }

    public void fetchdata(){
        Log.e("NEWS: ","RETROFIT");
        Call<List<GoTCharacter>> call2 = apiService.getdata();
        call2.enqueue(new Callback<List<GoTCharacter>>() {
            @Override
            public void onResponse(Call<List<GoTCharacter>> call, Response<List<GoTCharacter>> response) {
                for(int i=0; i < response.body().size(); i++ ){
                    // getting all response from server. need to save them to DB
                   Log.e("NEWS: ","NEWS 0:  "+response.body().get(i).getN());
                    //feedItemList.add(response.body().getResult().get(1));
                }
            }

            @Override
            public void onFailure(Call<List<GoTCharacter>> call, Throwable t) {
                Log.e("NEWS: ","FAIL"+ t.toString());
            }
        });
    }
}
