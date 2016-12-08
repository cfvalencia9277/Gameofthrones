package com.testcode.gameofthrones;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.testcode.gameofthrones.adapters.CharacterAdapter;
import com.testcode.gameofthrones.data.CharacterColumns;
import com.testcode.gameofthrones.data.GoTProvider;

/**
 * Created by Fabian on 08/12/2016.
 */

public class FamilyListActivity extends AppCompatActivity   implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int CHARACTER_HOUSE_LOADER = 103;
    CharacterAdapter charAdapter;
    ContentLoadingProgressBar pb;
    String houseid;
    String houseName;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_list);
        Log.e("ONCRESTE","EXIST");
        final RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        pb = (ContentLoadingProgressBar) findViewById(R.id.pb);
        TextView tv = (TextView)findViewById(R.id.house_title);

        houseid = getIntent().getStringExtra("House_Id");
        houseName = getIntent().getStringExtra("House_Name");

        tv.setText("CHARACTERS OF:  "+houseName);

        getSupportLoaderManager().initLoader(CHARACTER_HOUSE_LOADER,null,this);
        charAdapter = new CharacterAdapter(this);

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setHasFixedSize(true);
        rv.setAdapter(charAdapter);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cl = new CursorLoader(this, GoTProvider.Characters.CONTENT_URI,null, CharacterColumns.HOUSE_ID + "= ?",new String[]{houseid},null);
        return cl;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        pb.setVisibility(View.GONE);
        charAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        charAdapter.swapCursor(null);
    }
}
