package com.example.roomdatabaseimageinsert;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.File;

@Entity
public class UserModel {


    @PrimaryKey(autoGenerate = true)
    private int uid;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "photo")
    private String photo;

    @ColumnInfo(name = "image", typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    /*@ColumnInfo(name = "file")
    private File file;*/
    /*public UserModel( String title, String photo, byte[] image) {

        this.title = title;
        this.photo = photo;
        this.image = image;
    }*/
    public UserModel( String title, String photo, byte[] image/*,File file*/) {

        this.title = title;
        this.photo = photo;
        this.image = image;
        //this.file = file;
    }
    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}


