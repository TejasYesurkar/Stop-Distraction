package com.project.stopdistraction.roomdb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MessageDao {

    @Insert
    void insertUser(Message msg);
    @Delete
    void deleteUser(Message dailyTask);
    @Query("SELECT * FROM message_table")
    List<Message> getAllUsers();

}
