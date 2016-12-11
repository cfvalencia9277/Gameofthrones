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
import com.testcode.gameofthrones.FamilyListActivity;
import com.testcode.gameofthrones.R;
import com.testcode.gameofthrones.adapters.HouseAdapter;
import com.testcode.gameofthrones.data.GoTProvider;
import com.testcode.gameofthrones.data.HouseColumns;
import com.testcode.gameofthrones.models.GoTHouse;

/**
 * Created by Fabian on 07/12/2016.
 */

public class GoTHousesListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>,SearchView.OnQueryTextListener {
    private static final int HOUSE_LOADER = 102;
    HouseAdapter houseAdapter;
    ContentLoadingProgressBar pb;
    private SearchView mSearchView;
    RecyclerView rv;

    public GoTHousesListFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_house_list, container, false);
        TextView tv = (TextView) rootView.findViewById(R.id.house_title);
        tv.setVisibility(View.GONE);

        pb = (ContentLoadingProgressBar) rootView.findViewById(R.id.pb);
        rv = (RecyclerView) rootView.findViewById(R.id.rv_houses);
        mSearchView = (SearchView) rootView.findViewById(R.id.search_view);
        int id = mSearchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) mSearchView.findViewById(id);
        textView.setTextColor(Color.WHITE);

        getLoaderManager().initLoader(HOUSE_LOADER,null,this);

        setHouseAdapter();

        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setHasFixedSize(true);
        rv.setAdapter(houseAdapter);

        setupSearchView();

        return rootView;
    }

    public void setHouseAdapter(){
        houseAdapter = new HouseAdapter(getContext(), new HouseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(GoTHouse house) {
                Intent intent = new Intent(getActivity(), FamilyListActivity.class);
                intent.putExtra("House", house);
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_from_bottom, R.anim.nothing);
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
