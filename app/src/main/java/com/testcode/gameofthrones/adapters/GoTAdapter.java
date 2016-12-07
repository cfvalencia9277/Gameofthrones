package com.testcode.gameofthrones.adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.testcode.gameofthrones.DetailActivity;
import com.testcode.gameofthrones.R;
import com.testcode.gameofthrones.models.GoTCharacter;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Fabian on 07/12/2016.
 */

public class GoTAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

private final List<GoTCharacter> gcs;
private Activity a;

public GoTAdapter(Activity activity) {
        this.gcs = new ArrayList<>();
        a = activity;
        }

        public void addAll(Collection<GoTCharacter> collection) {
        for (int i = 0; i < collection.size(); i++) {
        gcs.add((GoTCharacter) collection.toArray()[i]);
        }
        }

@Override
public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GotCharacterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.got_character_row, parent, false));
        }

@Override
public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        GotCharacterViewHolder gotCharacterViewHolder = (GotCharacterViewHolder) holder;
        gotCharacterViewHolder.render(gcs.get(position));
        ((GotCharacterViewHolder) holder).imp.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(final View v) {
        Intent intent = new Intent(((GotCharacterViewHolder) holder).itemView.getContext(), DetailActivity.class);
        intent.putExtra("description", gcs.get(position).d);
        intent.putExtra("name", gcs.get(position).n);
        intent.putExtra("imageUrl", gcs.get(position).iu);
        ((GotCharacterViewHolder) holder).itemView.getContext().startActivity(intent);
        }
        });
        }

@Override
public int getItemCount() {
        return gcs.size();
        }

class GotCharacterViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "GotCharacterViewHolder";
    ImageView imp;
    TextView tvn;

    public GotCharacterViewHolder(View itemView) {
        super(itemView);
        imp = (ImageView) itemView.findViewById(R.id.ivBackground);
        tvn = (TextView) itemView.findViewById(R.id.tv_name);
    }

    public void render(final GoTCharacter goTCharacter) {
        tvn.setText(goTCharacter.n);
        Glide.with(a.getApplicationContext()).load(goTCharacter.iu).asBitmap().into(imp);
    }
}

}