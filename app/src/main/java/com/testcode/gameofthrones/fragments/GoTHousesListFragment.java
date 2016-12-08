package com.testcode.gameofthrones.fragments;

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
import android.widget.SearchView;

import com.testcode.gameofthrones.FamilyListActivity;
import com.testcode.gameofthrones.R;
import com.testcode.gameofthrones.adapters.HouseAdapter;
import com.testcode.gameofthrones.data.CharacterColumns;
import com.testcode.gameofthrones.data.GoTProvider;
import com.testcode.gameofthrones.data.HouseColumns;

/**
 * Created by Fabian on 07/12/2016.
 */

public class GoTHousesListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>,SearchView.OnQueryTextListener {
    private static final String TAG = "GoTHousesListFragment";
    private static final int HOUSE_LOADER = 102;
    HouseAdapter houseAdapter;
    ContentLoadingProgressBar pb;
    private SearchView mSearchView;
    RecyclerView rv;

    public GoTHousesListFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        pb = (ContentLoadingProgressBar) rootView.findViewById(R.id.pb);
        rv = (RecyclerView) rootView.findViewById(R.id.rv);
        mSearchView = (SearchView) rootView.findViewById(R.id.search_view);

        getLoaderManager().initLoader(HOUSE_LOADER,null,this);
        houseAdapter = new HouseAdapter(getContext(), new HouseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String houseid, String name) {
                Intent intent = new Intent(getActivity(), FamilyListActivity.class);
                intent.putExtra("House_Id", houseid);
                intent.putExtra("House_Name", name);
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_from_bottom, R.anim.nothing);
            }
        });


        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setHasFixedSize(true);
        rv.setAdapter(houseAdapter);

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
        getLoaderManager().restartLoader(HOUSE_LOADER, args, this);
        rv.scrollToPosition(0);
        return true;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cl = null;
        if(args != null){
            String name = args.getString("Name","");
            if(name.equals("")){cl = new CursorLoader(getContext(),GoTProvider.Houses.CONTENT_URI,null,null,null,null);}
            else{cl = new CursorLoader(getContext(),GoTProvider.Houses.CONTENT_URI,null, HouseColumns.HOUSE_NAME_HOUSE+ " like '%"+name+"%'",null,null);}
        }
        else{cl = new CursorLoader(getContext(),GoTProvider.Houses.CONTENT_URI,null,null,null,null);}
        return cl;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        pb.setVisibility(View.GONE);
        houseAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        houseAdapter.swapCursor(null);
    }
}
