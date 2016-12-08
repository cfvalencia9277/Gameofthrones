package com.testcode.gameofthrones.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.testcode.gameofthrones.DetailActivity;
import com.testcode.gameofthrones.R;
import com.testcode.gameofthrones.data.CharacterColumns;
import com.testcode.gameofthrones.utils.RecyclerViewCursorAdapter;

/**
 * Created by Fabian on 08/12/2016.
 */

public class CharacterAdapter extends RecyclerViewCursorAdapter<CharacterAdapter.GotCharacterViewHolder> {


    Context mcontext;
    public CharacterAdapter(Context context) {
        super(null);
        this.mcontext = context;
    }

    @Override
    public GotCharacterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.got_character_row,parent,false);
        return new GotCharacterViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(GotCharacterViewHolder holder, Cursor cursor) {
        int nameIndex = cursor.getColumnIndex(CharacterColumns.NAME);
        final String name = cursor.getString(nameIndex);
        int pathIndex = cursor.getColumnIndex(CharacterColumns.IMAGE_URL);
        final String imgpath = cursor.getString(pathIndex);
        int despath = cursor.getColumnIndex(CharacterColumns.DESCRIPTION);
        final String descrip = cursor.getString(despath);
        holder.tvn.setText(name);
        Glide.with(mcontext).load(imgpath).asBitmap().into(holder.imp);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext, DetailActivity.class);
                intent.putExtra("description", descrip);
                intent.putExtra("name", name);
                intent.putExtra("imageUrl", imgpath);
                mcontext.startActivity(intent);
            }
        });
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
    }
}
