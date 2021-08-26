package com.mobdeve.s11.lim.ivanjerwin.finalproject;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class NoteViewHolder extends RecyclerView.ViewHolder {

    private TextView tvTitle, tvContent, tvDate;
    private CardView cvNote;


    public NoteViewHolder(@NonNull @NotNull View itemView) {

        super(itemView);
        this.tvTitle = itemView.findViewById(R.id.tv_title);
        this.tvContent = itemView.findViewById(R.id.tv_content);
        this.tvDate = itemView.findViewById(R.id.tv_date);
        this.cvNote = itemView.findViewById(R.id.cv_note);
    }

    public void setTitle(String title) {this.tvTitle.setText(title);}
    public void setContent(String content) {this.tvContent.setText(content);}
    public void setDate(String date) {this.tvDate.setText(date);}
    public CardView getNote(){ return cvNote;}
}
