package com.mobdeve.s11.lim.ivanjerwin.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class CRUDActivity extends AppCompatActivity {

    //navbar
    private Button btnHome;
    private Button btnSearch;
    private Button btnMore;
    private TextView tvHome;
    private TextView tvSearch;
    private TextView tvMore;
    private SearchView svSearch;

    //note
    private TextView tvTitle;
    private TextView tvContent;
    private ImageView ivImage;

    //more options
    private TextView tvSave;
    private TextView tvDelete;
    private TextView tvLock;
    private TextView tvFav;

    private DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crudactivity);

        /** testing DB
        tvTitle = findViewById(R.id.tv_crud_title);
        tvContent = findViewById(R.id.tv_crud_content);
        ivImage = findViewById(R.id.iv_crud_img);
        tvHome = findViewById(R.id.tv_crud_home);
        tvSearch = findViewById(R.id.tv_crud_search);
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