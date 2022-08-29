package com.example.roomdatabaseimageinsert;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int CAMERA_REQUEST_CODE=101;

    Button btnCapture, btnAddPhoto;
    EditText etTitle;
    ImageView ivPhoto;
    RecyclerView rvItems;
    AppDatabase db;
    Bitmap bitmap;
    RecyclerView.Adapter adapter;

    TextView sPath;
    Button pickBtn;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        btnCapture=findViewById(R.id.btnCapture);
        btnAddPhoto=findViewById(R.id.btnAddPhoto);
        etTitle=findViewById(R.id.etTitle);
        ivPhoto=findViewById(R.id.ivPhoto);
        rvItems=findViewById(R.id.rvItems);
        db= Room.databaseBuilder(getApplicationContext(), AppDatabase.class, AppConfig.DB_NAME)
                .allowMainThreadQueries()
                .build();
        ivPhoto.setOnClickListener(this);
        btnCapture.setOnClickListener(this);
        btnAddPhoto.setOnClickListener(this);
        btnAddPhoto.setEnabled(false);
        loadList();

        //This is for get file path

//        sPath=findViewById(R.id.setPath);
//        pickBtn=findViewById(R.id.pickBtnId);
//
//        pickBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                intent=new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("*/*");
//                startActivityForResult(intent,10);
//
//            }
//        });


    }





    private void loadList(){
        adapter=new Adapter(db.userDao().getAll(),this);
        rvItems.setAdapter(adapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ivPhoto:
                startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE),CAMERA_REQUEST_CODE);
                break;

            case R.id.btnCapture:
                startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE),CAMERA_REQUEST_CODE);
                break;

            case R.id.btnAddPhoto:
                insertImages();
                break;
        }
    }

    private void insertImages(){
        File mFile = null;
        String title=etTitle.getText().toString();
        String photo= BitmapManager.bitmapToBase64(bitmap);
        byte[] image=BitmapManager.bitmapToByte(bitmap);
        db.userDao().insertAll(new UserModel(title,photo,image/*,mFile*/));
        Toast.makeText(getApplicationContext(),"Image Saved",Toast.LENGTH_SHORT).show();
        etTitle.setText("");

        btnAddPhoto.setEnabled(false);
        loadList();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Get path
       /* switch (requestCode){
            case 10:
                if (resultCode==RESULT_OK){
                    String path=data.getData().getPath();
                    sPath.setText(path);

                }
                break;
        }*/
        bitmap=(Bitmap)data.getExtras().get("data");
        ivPhoto.setImageBitmap(bitmap);
        int lastUID=0;
        try{
            lastUID=db.userDao().last().getUid();
        }catch (Exception e){}
        String title="Image "+String.valueOf(lastUID+1);
        etTitle.setText(title);
        btnAddPhoto.setEnabled(true);



    }
}