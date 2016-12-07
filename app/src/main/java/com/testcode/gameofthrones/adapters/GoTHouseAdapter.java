package com.testcode.gameofthrones.adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.testcode.gameofthrones.R;
import com.testcode.gameofthrones.models.GoTHouse;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Fabian on 07/12/2016.
 */

public class GoTHouseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<GoTHouse> gcs;
    private Activity a;

    public GoTHouseAdapter(Activity activity) {
        this.gcs = new ArrayList<>();
        a = activity;
    }

    public void addAll(Collection<GoTHouse> collection) {
        for (int i = 0; i < collection.size(); i++) {
            gcs.add((GoTHouse) collection.toArray()[i]);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GotCharacterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.got_house_row, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        GotCharacterViewHolder gotCharacterViewHolder = (GotCharacterViewHolder) holder;
        gotCharacterViewHolder.render(gcs.get(position));
    }

    @Override
    public int getItemCount() {
        return gcs.size();
    }

    class GotCharacterViewHolder extends RecyclerView.ViewHolder {

        private static final String TAG = "GotCharacterViewHolder";
        ImageView imp;

        public GotCharacterViewHolder(View itemView) {
            super(itemView);
            imp = (ImageView) itemView.findViewById(R.id.ivBackground);
        }

        public void render(final GoTHouse goTHouse) {
            Glide.with(a.getApplicationContext()).load(goTHouse.u).asBitmap().into(imp);
        }
    }

}
