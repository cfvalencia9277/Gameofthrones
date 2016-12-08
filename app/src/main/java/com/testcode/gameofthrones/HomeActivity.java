package com.testcode.gameofthrones;

import android.content.ContentProviderOperation;
import android.content.OperationApplicationException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.facebook.stetho.Stetho;
import com.testcode.gameofthrones.adapters.SelectionsPagerAdapter;
import com.testcode.gameofthrones.data.CharacterColumns;
import com.testcode.gameofthrones.data.GoTProvider;
import com.testcode.gameofthrones.data.HouseColumns;
import com.testcode.gameofthrones.models.GoTCharacter;
import com.testcode.gameofthrones.models.GoTHouse;
import com.testcode.gameofthrones.rest.ApiClient;
import com.testcode.gameofthrones.rest.ApiInterface;

import java.util.ArrayList;
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

        Stetho.initializeWithDefaults(this);

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
        Call<List<GoTCharacter>> call2 = apiService.getdata();
        call2.enqueue(new Callback<List<GoTCharacter>>() {
            @Override
            public void onResponse(Call<List<GoTCharacter>> call, Response<List<GoTCharacter>> response) {
                for(int i=0; i < response.body().size(); i++ ){
                    // getting all response from server. need to save them to DB
                    addCharactertodb(response.body().get(i));
                    GoTHouse house = new GoTHouse(response.body().get(i).getHu(),response.body().get(i).getHn(),response.body().get(i).getHi());
                    addHousetodb(house);
                    //feedItemList.add(response.body().getResult().get(1));
                }
            }

            @Override
            public void onFailure(Call<List<GoTCharacter>> call, Throwable t) {
                Log.e("NEWS: ","FAIL"+ t.toString());
            }
        });
    }

    public void addCharactertodb(GoTCharacter item){
        ArrayList<ContentProviderOperation> batchOperations = new ArrayList<>(1);
        ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(
                GoTProvider.Characters.CONTENT_URI);
        builder.withValue(CharacterColumns.NAME,item.getN());
        builder.withValue(CharacterColumns.IMAGE_URL,item.getIu());
        builder.withValue(CharacterColumns.HOUSE_NAME,item.getHn());
        builder.withValue(CharacterColumns.HOUSE_IMG_URL,item.getHu());
        builder.withValue(CharacterColumns.HOUSE_ID,item.getHi());
        builder.withValue(CharacterColumns.DESCRIPTION,item.getD());
        batchOperations.add(builder.build());
        try{
            this.getContentResolver()
                    .applyBatch(GoTProvider.AUTHORITY, batchOperations);
        }
        catch (SQLiteConstraintException e){
            Log.e("EXIST", "EXIST");
        }
        catch (SQLiteException e){
            Log.e("SQLite", "Error ");
        }
        catch(RemoteException | OperationApplicationException e){
            Log.e("DATA", "Error applying batch insert");
        }
        catch (Exception e){
            Log.e("EXCEPTION", "GENERAL");
        }
    }
    public void addHousetodb(GoTHouse item){
        ArrayList<ContentProviderOperation> batchOperations = new ArrayList<>(1);
        ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(
                GoTProvider.Houses.CONTENT_URI);
        builder.withValue(HouseColumns.HOUSE_ID_HOUSE,item.getI());
        builder.withValue(HouseColumns.HOUSE_NAME_HOUSE,item.getN());
        builder.withValue(HouseColumns.HOUSE_IMAGE_URL_HOUSE,item.getU());
        batchOperations.add(builder.build());
        try{
            this.getContentResolver()
                    .applyBatch(GoTProvider.AUTHORITY, batchOperations);
        }
        catch (SQLiteConstraintException e){
            Log.e("EXIST", "EXIST");
        }
        catch (SQLiteException e){
            Log.e("SQLite", "Error ");
        }
        catch(RemoteException | OperationApplicationException e){
            Log.e("DATA", "Error applying batch insert");
        }
        catch (Exception e){
            Log.e("EXCEPTION", "GENERAL");
        }
    }
}
