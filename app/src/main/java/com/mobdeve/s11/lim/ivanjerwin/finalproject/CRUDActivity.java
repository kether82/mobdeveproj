package com.mobdeve.s11.lim.ivanjerwin.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CRUDActivity extends AppCompatActivity {

    //navbar
    private ImageButton btnHome;
    private ImageButton btnSearch;
    private ImageButton btnMore;
    private TextView tvHome;
    private TextView tvSearch;
    private TextView tvMore;
    private SearchView svSearch;


    //note
    private TextView tvTitle;
    private TextView tvContent;
    private ImageView ivImage;

    //more options
    private Chip chpSave;
    private Chip chpDelete;
    private Chip chpLock;
    private Chip chpFav;


    //fab
    private FloatingActionButton fabAddImg;

    //layouts
    private LinearLayout llCrudMore;


    private DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_crudactivity);

        tvTitle = findViewById(R.id.tv_crud_title);
        tvContent = findViewById(R.id.tv_crud_content);
        ivImage = findViewById(R.id.iv_crud_img);

        tvHome = findViewById(R.id.tv_crud_home);
        tvSearch = findViewById(R.id.tv_crud_search);
        svSearch = findViewById(R.id.sv_crud_search);
        tvMore = findViewById(R.id.tv_crud_more);
        btnHome = findViewById(R.id.btn_crud_home);
        btnMore = findViewById(R.id.btn_crud_more);
        btnSearch = findViewById(R.id.btn_crud_search);

        fabAddImg = findViewById(R.id.fab_crud_add_img);

        llCrudMore = findViewById(R.id.ll_crud_more);
        chpSave = findViewById(R.id.chp_crud_save);
        chpDelete = findViewById(R.id.chp_crud_delete);
        chpLock = findViewById(R.id.chp_crud_lock);
        chpFav = findViewById(R.id.chp_crud_fav);


        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go back to main activity
                Intent i = new Intent(CRUDActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // show 'more' menu
                if(llCrudMore.getVisibility() == View.GONE){
                    llCrudMore.setVisibility(View.VISIBLE);
                    fabAddImg.setVisibility(View.GONE);
                }else{
                    llCrudMore.setVisibility(View.GONE);
                    fabAddImg.setVisibility(View.VISIBLE);
                }

            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // show search bar
                if ((svSearch.getVisibility() == View.GONE)) {
                    svSearch.setVisibility(View.VISIBLE);
                } else {
                    svSearch.setVisibility(View.GONE);
                }
            }
        });

        fabAddImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open camera(?)
                Toast.makeText(getApplicationContext(),"Camera", Toast.LENGTH_SHORT);
            }
        });

        chpFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // toggle fav
                if(chpFav.isChecked()) {
                }
                else {
                    Log.d("fav", "fav");
                }

            }
        });

        chpLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // toggle lock
                if(chpLock.isChecked()) {
                }
                else {
                    Log.d("lock", "lock");
                }

            }
        });





        /** testing DB

        db = new DBHelper(this);
        db.addNote("title", "content", null, 1,1);
        Cursor cursor = db.readAll();

        if(cursor.moveToNext()){
            tvTitle.setText(cursor.getString(1));
            tvContent.setText(cursor.getString(2));
            ivImage.setImageBitmap(ImageConverter.getImage(cursor.getBlob(3)));
            if((cursor.getInt(4) == 1)){
                tvHome.setText("fav");
            }
            if(cursor.getInt(5) == 1){
                tvSearch.setText("locked");
            }

        }
        db.deleteAll();
         */
    }

}