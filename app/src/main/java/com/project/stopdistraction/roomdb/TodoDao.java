package com.project.stopdistraction.roomdb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TodoDao {

    @Insert
    void insertUser(TodoTask dailyTask);
    @Delete
    void deleteUser(TodoTask dailyTask);
    @Query("SELECT * FROM todoTask_table")
    List<TodoTask> getAllUsers();
    @Query("SELECT * from todoTask_table where task = :task")
    TodoTask getUser(String task);

    @Query("update todoTask_table set status=:status where id = :id")
    void updateStatus(int id,boolean status);



}
