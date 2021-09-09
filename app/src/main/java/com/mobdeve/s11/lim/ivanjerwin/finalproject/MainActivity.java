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
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvNotes;
    private FloatingActionButton fabAddNote;
    private ImageButton btnHome, btnSearch, btnSort;
    private SearchView svSearch;

    private ArrayList<Note> dataNotes = new ArrayList<Note>();

    private DBHelper dbHelper;
    private NoteAdapter noteAdapter;

    private boolean clicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRecyclerview();
        storeData();
        initFabAdd();
        initMenuButtons();
    }

    private void initRecyclerview(){

        dbHelper = new DBHelper(MainActivity.this);

        this.rvNotes = findViewById(R.id.rv_notes);
        Collections.sort(dataNotes, Note.test);
        this.rvNotes.setLayoutManager(new GridLayoutManager(this, 2));

        this.noteAdapter = new NoteAdapter(this.dataNotes);
        this.rvNotes.setAdapter(noteAdapter);

        SnapHelper helper = new PagerSnapHelper();
        helper.attachToRecyclerView(this.rvNotes);
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
        //Home Button
        this.btnHome = findViewById(R.id.btn_main_home);
        this.btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //Search button
        this.btnSearch = findViewById(R.id.btn_main_search);

        this.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // show search bar
                svSearch = findViewById(R.id.sv_main_search);
                if ((svSearch.getVisibility() == View.GONE)) {
                    svSearch.setVisibility(View.VISIBLE);
                } else {
                    svSearch.setVisibility(View.GONE);
                }
            }
        });

        //Sort Button
        this.btnSort = findViewById(R.id.btn_main_sort);
        this.btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<Note> sortNotes = new ArrayList<Note>();

                if(!clicked) {

                    Cursor cursor = dbHelper.sortDescending();

                    if(cursor.getCount() == 0){
                        Toast.makeText(MainActivity.this, "NO DATA", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        //Toast.makeText(MainActivity.this, "IN DESC", Toast.LENGTH_SHORT).show();
                        while (cursor.moveToNext()){
                            sortNotes.add( new Note(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
                        }
                    }
                    noteAdapter.setData(sortNotes);
                    clicked = true;

                }

                else {
                    Cursor cursor = dbHelper.sortAscending();

                    if(cursor.getCount() == 0){
                        Toast.makeText(MainActivity.this, "NO DATA", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        //Toast.makeText(MainActivity.this, "IN ASC", Toast.LENGTH_SHORT).show();
                        while (cursor.moveToNext()){
                            sortNotes.add( new Note(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
                        }
                    }
                    noteAdapter.setData(sortNotes);
                    clicked = false;
                }

            }
        });
    }

    private void storeData() {
        Cursor cursor = dbHelper.readAll();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "NO DATA", Toast.LENGTH_SHORT).show();
        }
        else {
            while(cursor.moveToNext()) {
                dataNotes.add( new Note(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
            }
        }
    }
}