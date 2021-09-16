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

import java.lang.reflect.Array;
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
        Log.d("ARL", "initRecyclerview: "+dataNotes.size());
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
                        sortNotes = retrieveDataFromCursor(cursor);
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
                        sortNotes = retrieveDataFromCursor(cursor);
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
                // id title content date img fav lock
//                dataNotes.add( new Note(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
                dataNotes = retrieveDataFromCursor(cursor);
                noteAdapter.setData(dataNotes);
//            Log.d("pw", "pw: "+dataNotes.get(cursor.getCount()-2).getPassword());
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
        ArrayList<Note> searchNotes = new ArrayList<>();
//        Log.d("Main", "searchData: here" + cursor.getCount());
        if(cursor.getCount() == 0){
//            Log.d("Main1", "searchData: here" + cursor.getCount());
            noteAdapter.setData(searchNotes);
            Toast.makeText(MainActivity.this, "NO DATA FOUND", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
//            Log.d("Main2", "searchData: here" + cursor.getCount());
            searchNotes = retrieveDataFromCursor(cursor);
            noteAdapter.setData(searchNotes);

            return true;
        }

    }

    private ArrayList<Note> retrieveDataFromCursor(Cursor cursor){
        ArrayList<Note> alNote = new ArrayList<>();
        while (cursor.moveToNext() && cursor.getCount() != 0){
            alNote.add( new Note(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COL_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COL_TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COL_CONTENT)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COL_DATE)),
                    cursor.getBlob(cursor.getColumnIndexOrThrow(DBHelper.COL_IMG)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COL_IS_FAV)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COL_IS_LOCKED)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COL_PASSWORD))));
        }
//        Log.d("Arraylist", "retrieveDataFromCursor: "+alNote.size());
        return alNote;
    }
}