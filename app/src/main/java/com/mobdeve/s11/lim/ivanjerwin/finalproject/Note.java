package com.mobdeve.s11.lim.ivanjerwin.finalproject;

import java.util.Comparator;

public class Note {
    private String id;
    private String title;
    private String content;
    private byte[] image;
    private boolean isFav;
    private boolean isLocked;
    private String password;
    private String date;

    public Note(String id, String title, String content, String date) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
    }

    public Note(String id, String title, String content, String date, byte[] img, int isFav, int isLocked, String pw){
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.image = img;
        this.isFav = Utilities.convertInttoBool(isFav);
        this.isLocked = Utilities.convertInttoBool(isLocked);
        this.password = pw;
    }

    public String getId() { return id; }

    public byte[] getImage() {
        return image;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public String getPassword() {
        return password;
    }

    public boolean isFav() {
        return isFav;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public String getDate() {
        return date;
    }

    public int isFavInt(){
        if (isFav) return 1;
        else return 0;
    }

    public int isLockedInt(){
        if (isLocked) return 1;
        else return 0;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setFav(boolean fav) {
        isFav = fav;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //new additions start here
    public static Comparator<Note> test = new Comparator<Note>() {
        @Override
        public int compare(Note o1, Note o2) {
            return returnGreaterVal(o1.isFavInt(), o2.isFavInt());
        }
    };

    public static int returnGreaterVal(int num1, int num2){
        if(num1 > num2)
            return 1;
        else if(num2 > num1)
            return -1;
        else
            return 0;
    }
    //ends here (also added Collections.sort on line 52 of MainActivity.java)
}

/**
 private Context context;
 private static final String DB_NAME ="NAVINotes.db";
 private static final int DB_VER = 1;

 private static final String TABLE_NAME = "notes";
 private static final String COL_ID = "_id";
 private static final String COL_TITLE = "note_title";
 private static final String COL_CONTENT = "note_content";
 private static final String COL_IMG = "note_img";
 private static final String COL_IS_FAV = "note_fav";
 private static final String COL_IS_LOCKED = "note_lock";
 */