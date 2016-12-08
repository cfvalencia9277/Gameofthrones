package com.testcode.gameofthrones.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.testcode.gameofthrones.R;
import com.testcode.gameofthrones.data.HouseColumns;
import com.testcode.gameofthrones.utils.RecyclerViewCursorAdapter;

/**
 * Created by Fabian on 08/12/2016.
 */

public class HouseAdapter extends RecyclerViewCursorAdapter<HouseAdapter.GotHousesViewHolder> {
    Context mcontext;

    public HouseAdapter(Context context) {
        super(null);
        mcontext = context;
    }

    @Override
    public GotHousesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.got_house_row,parent,false);
        return new GotHousesViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(GotHousesViewHolder holder, Cursor cursor) {
        int impathIndex = cursor.getColumnIndex(HouseColumns.HOUSE_IMAGE_URL_HOUSE);
        final String imgpath = cursor.getString(impathIndex);
        if(imgpath.equals("")){
            int nameIndex = cursor.getColumnIndex(HouseColumns.HOUSE_NAME_HOUSE);
            final String name = cursor.getString(nameIndex);
            if(name.equals("")){
                holder.houseName.setVisibility(View.VISIBLE);
                holder.houseName.setText("NO NAME OR IMG FOR HOUSE");
                Glide.with(mcontext).load(R.mipmap.ic_launcher).asBitmap().into(holder.imp);
            }
            else{
                holder.houseName.setVisibility(View.VISIBLE);
                holder.houseName.setText(name);
                Glide.with(mcontext).load(R.mipmap.ic_launcher).asBitmap().into(holder.imp);
            }
        }
        else {
            holder.houseName.setVisibility(View.GONE);
            Glide.with(mcontext).load(imgpath).asBitmap().into(holder.imp);}
    }

    class GotHousesViewHolder extends RecyclerView.ViewHolder {

        private static final String TAG = "GotCharacterViewHolder";
        ImageView imp;
        TextView houseName;

        public GotHousesViewHolder(View itemView) {
            super(itemView);
            imp = (ImageView) itemView.findViewById(R.id.ivBackground);
            houseName = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }
}
