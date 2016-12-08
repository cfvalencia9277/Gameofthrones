package com.testcode.gameofthrones.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
    private final OnItemClickListener mlistener;

    public HouseAdapter(Context context, OnItemClickListener listener) {
        super(null);
        mcontext = context;
        mlistener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(String houseid,String name);
    }

    @Override
    public GotHousesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.got_house_row,parent,false);
        return new GotHousesViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(GotHousesViewHolder holder, Cursor cursor) {
        int idpath = cursor.getColumnIndex(HouseColumns.HOUSE_ID_HOUSE);
        final String houseId = cursor.getString(idpath);
        int impathIndex = cursor.getColumnIndex(HouseColumns.HOUSE_IMAGE_URL_HOUSE);
        final String imgpath = cursor.getString(impathIndex);
        int nameIndex = cursor.getColumnIndex(HouseColumns.HOUSE_NAME_HOUSE);
        final String name = cursor.getString(nameIndex);
        if(imgpath.equals("")){
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
            Glide.with(mcontext).load(imgpath).asBitmap().into(holder.imp);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mlistener.onItemClick(houseId,name);
            }
        });
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
