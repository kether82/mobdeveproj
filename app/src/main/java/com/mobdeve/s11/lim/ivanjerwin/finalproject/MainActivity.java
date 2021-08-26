package com.mobdeve.s11.lim.ivanjerwin.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvNotes;

    private ArrayList<Note> dataNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        this.dataNotes =
        this.initComponents();
    }

    private void initComponents(){
        this.rvNotes = findViewById(R.id.rv_notes);
        this.rvNotes.setLayoutManager(new GridLayoutManager(this, 2));
//        this.rvNotes.setAdapter(new NoteAdapter(this.dataNotes));
    }
}