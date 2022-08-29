package com.example.roomdatabaseimageinsert;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM UserModel")
    List<UserModel> getAll();

    @Query("SELECT * FROM userModel ORDER BY UID DESC LIMIT 0,1")
    UserModel last();

    @Query("SELECT * FROM usermodel ORDER BY UID ASC LIMIT 0,1")
    UserModel first();

    @Insert
    void insertAll(UserModel... userModels);

    @Delete
    void delete(UserModel userModel);
}