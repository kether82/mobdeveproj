package com.mobdeve.s11.lim.ivanjerwin.finalproject;

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
                v.getContext().startActivity(intent);
            }
        });
        return noteViewHolder;
    }

    public void setData(ArrayList<Note> data){
        String TAG = "noteadapter";
        this.dataNote.clear();
        this.dataNote = data;
        Log.d(TAG, "setData: "+data.size());
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull NoteViewHolder holder, int position) {
            Note currentNote = this.dataNote.get(position);
            holder.setTitle(currentNote.getTitle());
            holder.setContent(currentNote.getContent());
            holder.setDate(currentNote.getDate().toString());
    }

    @Override
    public int getItemCount() {
        return dataNote.size();
    }
}
