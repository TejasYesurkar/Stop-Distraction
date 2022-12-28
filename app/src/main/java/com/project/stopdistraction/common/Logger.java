package com.project.stopdistraction.common;

import android.util.Log;

/**
 * Logger class is used to Print Logs
 */
public class Logger {

    private final String TAG = "Logger";
    private static Logger logger = null;

    public static Logger getInstance(){
        if(logger == null)
            logger = new Logger();

        return logger;
    }

    public void printExceptionStackTrace(Exception ex){
        if(ex != null){
            Log.e(TAG,"printExceptionStackTrace " + ex.getLocalizedMessage());
        }
    }

    public void printLoggerMessage(String msg){
        if(msg != null){
            Log.e(TAG, msg);
        }
    }
}
