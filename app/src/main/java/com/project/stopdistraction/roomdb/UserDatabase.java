package com.project.stopdistraction.roomdb;

import android.app.Application;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.project.stopdistraction.MainActivity;

@Database(entities = {TodoTask.class,DailyTask.class,Message.class},version = 7)
public abstract class UserDatabase extends RoomDatabase {

    private static UserDatabase instance;


    public static UserDatabase getInstance(Context context) {
        if (instance == null)
        {
            instance = Room.databaseBuilder(context
                            ,UserDatabase.class,"User_Databse")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract UserDao userDao();
    public abstract MessageDao msgDao();
    public abstract TodoDao todoDao();

    public  static synchronized UserDatabase getInstance(Application application)
    {
        if (instance == null)
        {
            instance = Room.databaseBuilder(application.getApplicationContext()
                    ,UserDatabase.class,"User_Databse")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
