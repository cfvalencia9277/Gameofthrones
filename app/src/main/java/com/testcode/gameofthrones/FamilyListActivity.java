package com.testcode.gameofthrones;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import com.testcode.gameofthrones.adapters.CharacterAdapter;
import com.testcode.gameofthrones.data.CharacterColumns;
import com.testcode.gameofthrones.data.GoTProvider;

/**
 * Created by Fabian on 08/12/2016.
 */

public class FamilyListActivity extends AppCompatActivity   implements LoaderManager.LoaderCallbacks<Cursor>,SearchView.OnQueryTextListener {
    private static final int CHARACTER_HOUSE_LOADER = 103;
    CharacterAdapter charAdapter;
    ContentLoadingProgressBar pb;
    String houseid;
    String houseName;
    private SearchView mSearchView;
    RecyclerView rv;
    TextView tv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_list);

        rv = (RecyclerView) findViewById(R.id.rv_houses);
        pb = (ContentLoadingProgressBar) findViewById(R.id.pb);
        tv = (TextView)findViewById(R.id.house_title);
        mSearchView = (SearchView) findViewById(R.id.search_view);

        tv.setVisibility(View.VISIBLE);

        houseid = getIntent().getStringExtra("House_Id");
        houseName = getIntent().getStringExtra("House_Name");

        setText();

        getSupportLoaderManager().initLoader(CHARACTER_HOUSE_LOADER,null,this);

        setCharAdapter();

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setHasFixedSize(true);
        rv.setAdapter(charAdapter);

        setupSearchView();
    }

    private void setText(){
        if(houseName.equals("")){
            tv.setText(R.string.char_list_text);
        }
        else{
            tv.setText(houseName);
        }
    }

    public void setCharAdapter(){
        charAdapter = new CharacterAdapter(this, new CharacterAdapter.OnCharacterClickListener() {
            @Override
            public void onCharacterClick(String description, String name, String imgpath) {
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("description", description);
                intent.putExtra("name", name);
                intent.putExtra("imageUrl", imgpath);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_from_bottom, R.anim.nothing);
            }
        });
    }

    private void setupSearchView() {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(false);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Bundle args = new Bundle();
        args.putString("Name",newText);
        getSupportLoaderManager().restartLoader(CHARACTER_HOUSE_LOADER, args, this);
        rv.scrollToPosition(0);
        return true;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cl = null;
        if(args != null){
            String name = args.getString("Name","");
            if(name.equals("")){
                cl = new CursorLoader(this, GoTProvider.Characters.CONTENT_URI,null,
                        CharacterColumns.HOUSE_ID + "= ?",new String[]{houseid},null);
            }
            else{
                cl = new CursorLoader(this,GoTProvider.Characters.CONTENT_URI,null,
                        CharacterColumns.NAME+" like '%"+name+"%' and "+
                                CharacterColumns.HOUSE_ID+" like '%"+houseid+"%'" ,null,null);
            }
        }
        else{
            cl= new CursorLoader(this, GoTProvider.Characters.CONTENT_URI,null,
                    CharacterColumns.HOUSE_ID + "= ?",new String[]{houseid},null);
        }
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

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.nothing, R.anim.slide_out_to_bottom);
    }
}
