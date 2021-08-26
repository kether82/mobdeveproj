package com.mobdeve.s11.lim.ivanjerwin.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvNotes;
    private FloatingActionButton fabAddNote;
    private ImageButton btnHome, btnSearch, btnSort;

    private ArrayList<Note> dataNotes;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRecyclerview();
        initFabAdd();
    }

    private void initRecyclerview(){

        this.rvNotes = findViewById(R.id.rv_notes);
        this.rvNotes.setLayoutManager(new GridLayoutManager(this, 2));

//       this.rvNotes.setAdapter(new NoteAdapter(this.dataNotes));
        SnapHelper helper = new PagerSnapHelper();
        helper.attachToRecyclerView(this.rvNotes);

        dbHelper = new DBHelper(MainActivity.this);

    }

    private void initFabAdd() {

        this.fabAddNote = findViewById(R.id.fab_add_note);
        this.fabAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CRUDActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initMenuButtons() {
        this.btnHome = findViewById(R.id.btn_crud_home);
        this.btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        this.btnSearch = findViewById(R.id.btn_crud_search);
        this.btnSort = findViewById(R.id.btn_crud_more);


    }
}