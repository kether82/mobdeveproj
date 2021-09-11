package com.mobdeve.s11.lim.ivanjerwin.finalproject;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());

                builder.setView(R.layout.note_password).setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Dialog dialogObj = Dialog.class.cast(dialog);
                        EditText etPassword = dialogObj.findViewById(R.id.et_dialog_pw);
                        String password = etPassword.getText().toString().trim();
                        if(password.equals(dataNote.get(noteViewHolder.getBindingAdapterPosition()).getPassword())){
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
                        }else{
                            Log.d("PW:", dataNote.get(noteViewHolder.getBindingAdapterPosition()).getPassword());
                            Toast.makeText(builder.getContext(), "Incorrect Password", Toast.LENGTH_LONG).show();
                        }
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
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
            holder.setIvLock(currentNote.isLocked());
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
