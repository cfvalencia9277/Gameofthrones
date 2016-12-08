package com.testcode.gameofthrones.fragments;

import android.database.Cursor;
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

import com.testcode.gameofthrones.R;
import com.testcode.gameofthrones.adapters.CharacterAdapter;
import com.testcode.gameofthrones.data.GoTProvider;

/**
 * Created by Fabian on 07/12/2016.
 */

public class GoTListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = "GoTListFragment";
    private static final int CHARACTER_LOADER = 101;
    CharacterAdapter charAdapter;
    ContentLoadingProgressBar pb;

    public GoTListFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        pb = (ContentLoadingProgressBar) rootView.findViewById(R.id.pb);
        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv);

        getLoaderManager().initLoader(CHARACTER_LOADER,null,this);
        charAdapter = new CharacterAdapter(getContext());

        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setHasFixedSize(true);
        rv.setAdapter(charAdapter);

        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cl = new CursorLoader(getContext(), GoTProvider.Characters.CONTENT_URI,null,null,null,null);
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
