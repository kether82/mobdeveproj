package com.mobdeve.s11.lim.ivanjerwin.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvNotes;
    private FloatingActionButton fabAddNote;
    private ImageButton btnHome, btnSearch, btnSort;
    private SearchView svSearch;

    private NoteAdapter noteAdapter;

    private ArrayList<Note> dataNotes = new ArrayList<Note>();

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRecyclerview();
        initFabAdd();
        initMenuButtons();
        storeData();
    }

    private void initRecyclerview(){

        dbHelper = DBHelper.getInstance(MainActivity.this);

        this.rvNotes = findViewById(R.id.rv_notes);
        Collections.sort(dataNotes, Note.test);
        noteAdapter = new NoteAdapter(this.dataNotes);
        this.rvNotes.setAdapter(noteAdapter);
        this.rvNotes.setLayoutManager(new GridLayoutManager(this, 2));

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
        this.btnHome = findViewById(R.id.btn_main_home);
        this.btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
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
        this.svSearch = findViewById(R.id.sv_main_search);
        this.svSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                boolean result = searchData(query);
                svSearch.setVisibility(View.GONE);
                return result;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        this.btnSort = findViewById(R.id.btn_main_sort);
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

    private boolean searchData(String query){
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        };
//        Executor executor = Executors.newSingleThreadExecutor();
//        executor.execute(runnable);
//        return true;
        Cursor cursor = dbHelper.searchNote(query);
        if(cursor.getCount() == 0){
            Toast.makeText(MainActivity.this, "NO DATA FOUND", Toast.LENGTH_SHORT);
            return false;
        }
        else {
            Log.d("Main", "searchData: here");
            ArrayList<Note> searchNotes = new ArrayList<>();
            while(cursor.moveToNext()) {
                searchNotes.add( new Note(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
            }
            noteAdapter.setData(searchNotes);
            return true;
        }

    }
}