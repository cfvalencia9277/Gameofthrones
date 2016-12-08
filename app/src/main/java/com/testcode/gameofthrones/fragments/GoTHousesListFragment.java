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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.testcode.gameofthrones.R;
import com.testcode.gameofthrones.adapters.GoTHouseAdapter;
import com.testcode.gameofthrones.adapters.HouseAdapter;
import com.testcode.gameofthrones.data.GoTProvider;
import com.testcode.gameofthrones.models.GoTCharacter;
import com.testcode.gameofthrones.models.GoTHouse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fabian on 07/12/2016.
 */

public class GoTHousesListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = "GoTHousesListFragment";
    private static final int HOUSE_LOADER = 102;
    HouseAdapter houseAdapter;
    ContentLoadingProgressBar pb;

    public GoTHousesListFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        pb = (ContentLoadingProgressBar) rootView.findViewById(R.id.pb);
        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv);

        getLoaderManager().initLoader(HOUSE_LOADER,null,this);
        houseAdapter = new HouseAdapter(getContext());

        final GoTHouseAdapter adp = new GoTHouseAdapter(getActivity());
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setHasFixedSize(true);
        rv.setAdapter(houseAdapter);
/*
        new Thread(new Runnable() {

            @Override
            public void run() {
                String url = "https://project-8424324399725905479.firebaseio.com/characters.json?print=pretty";

                URL obj = null;
                try {
                    obj = new URL(url);
                    HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                    con.setRequestMethod("GET");
                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                    Type listType = new TypeToken<ArrayList<GoTCharacter>>() {
                    }.getType();
                    final List<GoTCharacter> characters = new Gson().fromJson(response.toString(), listType);
                    GoTHousesListFragment.this.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ArrayList<GoTHouse> hs = new ArrayList<GoTHouse>();
                            for (int i = 0; i < characters.size(); i++) {
                                boolean b = false;
                                for (int j = 0; j < hs.size(); j++) {
                                    if (hs.get(j).n.equalsIgnoreCase(characters.get(i).hn)) {
                                        b = true;
                                    }
                                }
                                if (!b) {
                                    if (characters.get(i).hi != null && !characters.get(i).hi.isEmpty()) {
                                        GoTHouse h = new GoTHouse();
                                        h.i = characters.get(i).hi;
                                        h.n = characters.get(i).hn;
                                        h.u = characters.get(i).hu;
                                        hs.add(h);
                                        b = false;
                                    }
                                }
                            }
                            adp.addAll(hs);
                            adp.notifyDataSetChanged();
                            pb.hide();
                        }
                    });
                } catch (IOException e) {
                    Log.e(TAG, e.getLocalizedMessage());
                }
            }
        }).start();
        */
        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cl = new CursorLoader(getContext(), GoTProvider.Houses.CONTENT_URI,null,null,null,null);
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
