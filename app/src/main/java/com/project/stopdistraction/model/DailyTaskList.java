package com.project.stopdistraction.model;

public class DailyTaskList {

    private String task;
    private String category;
    private String time;
    private String date;
    private boolean status;

    private Integer id;
    public DailyTaskList(Integer id, String task, String category, String time, String date, boolean status) {
        this.id = id;
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
