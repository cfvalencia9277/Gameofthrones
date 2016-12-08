package com.testcode.gameofthrones;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

/**
 * Created by Fabian on 07/12/2016.
 */

public class DetailActivity  extends AppCompatActivity {

    private static final String TAG = "DetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final ImageView ivp = (ImageView) findViewById(R.id.iv_photo);
        final TextView tvn = (TextView) findViewById(R.id.tv_name);
        final TextView tvd = (TextView) findViewById(R.id.tv_description);

        final String d = getIntent().getStringExtra("description");
        final String n = getIntent().getStringExtra("name");
        final String i = getIntent().getStringExtra("imageUrl");

        Toolbar toolbar = (Toolbar) findViewById(R.id.t);
        toolbar.setTitle(n);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        tvn.setText(n);
        tvd.setText(d);
        Glide.with(this).load(i).asBitmap().into(ivp);
    }
    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.nothing, R.anim.slide_out_to_bottom);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                overridePendingTransition(R.anim.nothing, R.anim.slide_out_to_bottom);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
