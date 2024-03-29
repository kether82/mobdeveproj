package com.mobdeve.s11.lim.ivanjerwin.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;

public class CRUDActivity extends AppCompatActivity {
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int CAMERA_REQUEST = 1888;

    //navbar
    private ImageButton btnHome;
    private ImageButton btnLock;
    private ImageButton btnMore;
    private TextView tvHome;
    private TextView tvSearch;
    private TextView tvMore;


    //note
    private EditText etTitle;
    private EditText etContent;
    private ImageView ivImage;
    private TextView tvDate;
    private EditText etPassword;

    //more options
    private Chip chpSave;
    private Chip chpDelete;
    private Chip chpLock;
    private Chip chpFav;
    private Chip chpEdit;


    //fab
    private FloatingActionButton fabAddImg;

    //layouts
    private LinearLayout llCrudMore;

    String id;
    private DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_crudactivity);

        etTitle = findViewById(R.id.et_crud_title);
        etContent = findViewById(R.id.et_crud_content);
        ivImage = findViewById(R.id.iv_crud_img);
        tvDate = findViewById(R.id.tv_crud_date);
        etPassword = findViewById(R.id.et_crud_password);

        tvHome = findViewById(R.id.tv_crud_home);
        tvSearch = findViewById(R.id.tv_crud_search);
        tvMore = findViewById(R.id.tv_crud_more);
        btnHome = findViewById(R.id.btn_crud_home);
        btnMore = findViewById(R.id.btn_crud_more);
        btnLock = findViewById(R.id.btn_crud_lock);

        fabAddImg = findViewById(R.id.fab_crud_add_img);

        llCrudMore = findViewById(R.id.ll_crud_more);
        chpSave = findViewById(R.id.chp_crud_save);
        chpDelete = findViewById(R.id.chp_crud_delete);
        chpLock = findViewById(R.id.chp_crud_lock);
        chpFav = findViewById(R.id.chp_crud_fav);
//        chpEdit = findViewById(R.id.chp_crud_edit);

        Intent i =  getIntent();

        if(i.hasExtra(NoteAdapter.KEY_TITLE)){
            id = i.getStringExtra(NoteAdapter.KEY_ID);
            String title = i.getStringExtra(NoteAdapter.KEY_TITLE);
            String content = i.getStringExtra(NoteAdapter.KEY_CONTENT);
            String date = i.getStringExtra(NoteAdapter.KEY_DATE);
            Boolean isFav = i.getBooleanExtra(NoteAdapter.KEY_FAV, false);
            Boolean isLock = i.getBooleanExtra(NoteAdapter.KEY_LOCK, false);
            Bitmap bm = null;
            String pw = i.getStringExtra(NoteAdapter.KEY_PW);

            try {
                bm = Utilities.getImage(i.getByteArrayExtra(NoteAdapter.KEY_IMG));
                Log.d("img", "onCreate:" + bm.toString());
            }catch ( Exception e){
                e.printStackTrace();
            }
            Log.d("isfav", "isfav: "+isFav.toString());




            etTitle.setText(title);
            etContent.setText(content);
            tvDate.setText(date);
            chpLock.setChecked(isLock);
            chpFav.setChecked(isFav);
            if(bm != null){
                setImageView(bm);
            }
            etPassword.setText(pw);

        } else{
            chpSave.setText("Add");
            chpSave.setChipIconResource(R.drawable.ic_baseline_add_24);

            tvDate.setText(new Date().toString());
            ivImage.setImageBitmap(null);
        }

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go back to main activity
                Intent i = new Intent(CRUDActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // show 'more' menu
                if(llCrudMore.getVisibility() == View.GONE || llCrudMore.getVisibility() == View.INVISIBLE){
                    llCrudMore.setVisibility(View.VISIBLE);
                    fabAddImg.setVisibility(View.GONE);
                }else{
                    llCrudMore.setVisibility(View.GONE);
                    fabAddImg.setVisibility(View.VISIBLE);
                }

            }
        });

        btnLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etPassword.getVisibility()==View.VISIBLE){
                    etPassword.setVisibility(View.INVISIBLE);
                    if(llCrudMore.getVisibility()==View.INVISIBLE) fabAddImg.setVisibility(View.VISIBLE);
                }else{
                    etPassword.setVisibility(View.VISIBLE);
                    fabAddImg.setVisibility(View.INVISIBLE);
                }
            }
        });

        fabAddImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TAG = "CRUDACTIVITY";
                // open camera(?)
//                Toast.makeText(CRUDActivity.this, "Camera", Toast.LENGTH_SHORT).show();
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    Log.d(TAG, "permi ask ");
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                }
                else
                {
                    Log.d(TAG, "permi have ");
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });

        chpFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // toggle fav
                DBHelper db = new DBHelper(CRUDActivity.this);
                String title = etTitle.getText().toString().trim();
                String content = etContent.getText().toString().trim();
                int lock = Utilities.convertBooltoInt(chpLock.isChecked());

                if(chpFav.isChecked()) {
                    Toast.makeText(CRUDActivity.this, "Favorited", Toast.LENGTH_SHORT).show();
                    db.updateNote(id, title, content, 1, lock);
                }
                else {
                    db.updateNote(id, title, content, 0, lock);
                }

            }
        });

        chpLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // toggle lock
                DBHelper db = new DBHelper(CRUDActivity.this);
                String title = etTitle.getText().toString().trim();
                String content = etContent.getText().toString().trim();
                int fav = Utilities.convertBooltoInt(chpFav.isChecked());

                if(chpLock.isChecked()) {
                    Toast.makeText(CRUDActivity.this, "Locked", Toast.LENGTH_SHORT).show();
                    db.updateNote(id, title, content,  fav, 1);
                    // request pw prolly
                }
                else {
                    db.updateNote(id, title, content, fav, 0);
                }

            }
        });

        chpSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("A", "onClick: ");
                DBHelper db = new DBHelper(CRUDActivity.this);
                String title = etTitle.getText().toString().trim();
                String content = etContent.getText().toString().trim();
                int fav = Utilities.convertBooltoInt(chpFav.isChecked());
                int lock = Utilities.convertBooltoInt(chpLock.isChecked());
                byte[] img = null;
                String pw = etPassword.getText().toString().trim();
                Log.d("PW", "onClick: "+ pw);

                if (ivImage.getDrawable() != null){
                    Bitmap bm = Utilities.drawableToBitmap(ivImage.getDrawable());
                    try {
                        img = Utilities.getBytes(bm);
                    } catch (IOException e) {
                        Log.d("img", "onClick: "+ e.getStackTrace().toString());
                    }
                    Log.d("A", "onClick: "+ img.toString());
                }

                if(chpSave.getText().toString() == "Add"){
                    // add to db
                    Toast.makeText(CRUDActivity.this,"Added" , Toast.LENGTH_SHORT);
                    db.addNote(title, content, img, fav, lock, pw);

                    returnToMain();
                }else{
                    // update
                    DBHelper dbHelper = new DBHelper(CRUDActivity.this);
                    db.updateNote(id, title, content, img, fav, lock, pw);
                    returnToMain();
                }
            }
        });

        chpDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(CRUDActivity.this);
                db.delNote(id);
                returnToMain();
            }
        });



       /* chpEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // allow edit
                etContent.setInputType(InputType.TYPE_CLASS_TEXT);
                etTitle.setInputType(InputType.TYPE_CLASS_TEXT);

                etContent.setEnabled(true);
                etTitle.setEnabled(true);

                llCrudMore.setVisibility(View.GONE);
                fabAddImg.setVisibility(View.VISIBLE);
            }
        }); */





        /** testing DB

        db = new DBHelper(this);
        db.addNote("title", "content", null, 1,1);
        Cursor cursor = db.readAll();

        if(cursor.moveToNext()){
            tvTitle.setText(cursor.getString(1));
            tvContent.setText(cursor.getString(2));
            ivImage.setImageBitmap(ImageConverter.getImage(cursor.getBlob(3)));
            if((cursor.getInt(4) == 1)){
                tvHome.setText("fav");
            }
            if(cursor.getInt(5) == 1){
                tvSearch.setText("locked");
            }

        }
        db.deleteAll();
         */
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            setImageView(photo);
        }
    }

    private void setImageView(Bitmap photo){
        ivImage.setMinimumWidth(photo.getWidth());
        ivImage.setMinimumHeight(photo.getHeight());
        ivImage.setVisibility(View.VISIBLE);
        ivImage.setImageBitmap(photo);
    }


    public void returnToMain() {
        finish();
        Intent intent = new Intent(CRUDActivity.this, MainActivity.class);
        startActivity(intent);
    }

}