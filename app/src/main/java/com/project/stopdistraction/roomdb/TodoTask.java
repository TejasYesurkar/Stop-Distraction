package com.project.stopdistraction.roomdb;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "todoTask_table")
public class TodoTask {

    private String task;
    private String category;
    private String time;
    private String date;
    private boolean status;
    @PrimaryKey(autoGenerate = true)
    private Integer id;
    public TodoTask(String task, String category, String time, String date, boolean status) {
        this.task = task;
        this.category = category;
        this.time = time;
        this.date = date;
        this.status = status;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
