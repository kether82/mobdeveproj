package com.mobdeve.s11.lim.ivanjerwin.finalproject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder> {

    public static final String KEY_ID = "KEY_ID";
    public static final String KEY_TITLE = "KEY_TITLE";
    public static final String KEY_CONTENT = "KEY_CONTENT";
    public static final String KEY_DATE = "KEY_DATE";
    public static final String KEY_IMG ="KEY_IMG";
    public static final String KEY_FAV ="KEY_FAV";
    public static final String KEY_LOCK ="KEY_LOCK";
    public static final String KEY_PW ="KEY_PW";

    private ArrayList<Note> dataNote;

    public NoteAdapter(ArrayList<Note> dataNote){ this.dataNote = dataNote;}

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.note_template, parent, false);

        NoteViewHolder noteViewHolder = new NoteViewHolder(itemView);

        noteViewHolder.getNote().setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(itemView.getContext(), CRUDActivity.class);
                intent.putExtra(KEY_ID, dataNote.get(noteViewHolder.getBindingAdapterPosition()).getId());
                intent.putExtra(KEY_TITLE, dataNote.get(noteViewHolder.getBindingAdapterPosition()).getTitle());
                intent.putExtra(KEY_CONTENT, dataNote.get(noteViewHolder.getBindingAdapterPosition()).getContent());
                intent.putExtra(KEY_DATE,dataNote.get(noteViewHolder.getBindingAdapterPosition()).getDate().toString());
                intent.putExtra(KEY_IMG, dataNote.get(noteViewHolder.getBindingAdapterPosition()).getImage());
                intent.putExtra(KEY_FAV, dataNote.get(noteViewHolder.getBindingAdapterPosition()).isFav());
                intent.putExtra(KEY_LOCK, dataNote.get(noteViewHolder.getBindingAdapterPosition()).isLocked());
                intent.putExtra(KEY_PW, dataNote.get(noteViewHolder.getBindingAdapterPosition()).getPassword());
                v.getContext().startActivity(intent);
            }
        });
        return noteViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull NoteViewHolder holder, int position) {
            Note currentNote = this.dataNote.get(position);
            holder.setTitle(currentNote.getTitle());
            holder.setContent(currentNote.getContent());
            holder.setDate(currentNote.getDate());
    }

    @Override
    public int getItemCount() {
        return dataNote.size();
    }

    public void setData(ArrayList<Note> dataNote){
        this.dataNote.clear();
        this.dataNote.addAll(dataNote);
        notifyDataSetChanged();
    }
}
