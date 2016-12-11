package com.testcode.gameofthrones.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.testcode.gameofthrones.DetailActivity;
import com.testcode.gameofthrones.R;
import com.testcode.gameofthrones.adapters.CharacterAdapter;
import com.testcode.gameofthrones.data.CharacterColumns;
import com.testcode.gameofthrones.data.GoTProvider;
import com.testcode.gameofthrones.models.GoTCharacter;

/**
 * Created by Fabian on 07/12/2016.
 */

public class GoTListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>,SearchView.OnQueryTextListener {
    private static final int CHARACTER_LOADER = 101;
    CharacterAdapter charAdapter;
    ContentLoadingProgressBar pb;
    private SearchView mSearchView;
    RecyclerView rv;


    public GoTListFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        pb = (ContentLoadingProgressBar) rootView.findViewById(R.id.pb);
        rv = (RecyclerView) rootView.findViewById(R.id.rv_characters);
        mSearchView = (SearchView) rootView.findViewById(R.id.search_view);
        int id = mSearchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) mSearchView.findViewById(id);
        textView.setTextColor(Color.WHITE);

        getLoaderManager().initLoader(CHARACTER_LOADER,null,this);

        setCharAdapter();

        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setHasFixedSize(true);
        rv.setAdapter(charAdapter);

        setupSearchView();

        return rootView;
    }

    public void setCharAdapter(){
        charAdapter = new CharacterAdapter(getContext(), new CharacterAdapter.OnCharacterClickListener() {
            @Override
            public void onCharacterClick(GoTCharacter character) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("Character",character);
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_from_right, R.anim.nothing);
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
        getLoaderManager().restartLoader(CHARACTER_LOADER, args, this);
        rv.scrollToPosition(0);
        return true;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cl = null;
        if(args != null){
           String name = args.getString("Name","");
            if(name.equals("")){
                cl = new CursorLoader(getContext(),GoTProvider.Characters.CONTENT_URI,null,
                        null,null,null);
            }
            else{
                cl = new CursorLoader(getContext(),GoTProvider.Characters.CONTENT_URI,null,
                        CharacterColumns.NAME+ " like '%"+name+"%'",null,null);
            }
        }
        else{
            cl= new CursorLoader(getContext(),GoTProvider.Characters.CONTENT_URI,null,null,
                    null,null);
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
}
