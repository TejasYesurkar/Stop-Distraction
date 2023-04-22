package com.project.stopdistraction.roomdb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insertUser(DailyTask dailyTask);
    @Delete
    void deleteUser(DailyTask dailyTask);
    @Query("SELECT * FROM dailyTask_table")
    List<DailyTask> getAllUsers();
    @Query("SELECT * from dailyTask_table where task = :task")
    DailyTask getUser(String task);



}
