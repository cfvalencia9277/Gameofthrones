package com.testcode.gameofthrones.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.widget.TextView;

import com.testcode.gameofthrones.DetailActivity;
import com.testcode.gameofthrones.R;
import com.testcode.gameofthrones.adapters.CharacterAdapter;
import com.testcode.gameofthrones.data.CharacterColumns;
import com.testcode.gameofthrones.data.GoTProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fabian on 07/12/2016.
 */

public class GoTListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>,SearchView.OnQueryTextListener {
    private static final String TAG = "GoTListFragment";
    private static final int CHARACTER_LOADER = 101;
    CharacterAdapter charAdapter;
    ContentLoadingProgressBar pb;
    private SearchView mSearchView;
    RecyclerView rv;
    TextView tv;

    public GoTListFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        pb = (ContentLoadingProgressBar) rootView.findViewById(R.id.pb);
        rv = (RecyclerView) rootView.findViewById(R.id.rv_characters);
        mSearchView = (SearchView) rootView.findViewById(R.id.search_view);

        getLoaderManager().initLoader(CHARACTER_LOADER,null,this);
        charAdapter = new CharacterAdapter(getContext(), new CharacterAdapter.OnCharacterClickListener() {
            @Override
            public void onCharacterClick(String description, String name, String imgpath) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("description", description);
                intent.putExtra("name", name);
                intent.putExtra("imageUrl", imgpath);
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_from_bottom, R.anim.nothing);
            }
        });

        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setHasFixedSize(true);
        rv.setAdapter(charAdapter);

        setupSearchView();

        return rootView;
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
    /**
     *  callback method called when text in searchview is changing.
     * @param newText text currently on searchview.
     * @return  a filtered list of plants.
     */
    @Override
    public boolean onQueryTextChange(String newText) {
        Bundle args = new Bundle();
        args.putString("Name",newText);
        getLoaderManager().restartLoader(CHARACTER_LOADER, args, this);
        rv.scrollToPosition(0);
        return true;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cl = null;
        if(args != null){
           String name = args.getString("Name","");
            if(name.equals("")){cl = new CursorLoader(getContext(),GoTProvider.Characters.CONTENT_URI,null,null,null,null);}
            else{cl = new CursorLoader(getContext(),GoTProvider.Characters.CONTENT_URI,null,CharacterColumns.NAME+ " like '%"+name+"%'",null,null);}
        }
        else{cl= new CursorLoader(getContext(),GoTProvider.Characters.CONTENT_URI,null,null,null,null);}
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
