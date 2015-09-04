package com.yu.tomato.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by YU on 2015/9/4.
 */
public class DataBaseOpenHelper extends SQLiteOpenHelper {

    private static  int version = 1;
    private static String dbName = "Tomato";
    private static String createTomatoTaskTable = "Create Table if not exists  "  + DatabaseBuilder.TOMATO_TABLE + " (  "
                                                                                        + DatabaseBuilder.TOMATO_TASK_ID + " integer primary key autoincrement,"
                                                                                        + DatabaseBuilder.TOMATO_TASK_THEME + " varchar(32),"
                                                                                        +DatabaseBuilder.TOMATO_TASK_TOMATO_TIME_COUNT + " int,"
                                                                                        + DatabaseBuilder.TOMATO_TASK_DESCRIPTION + " varchar(60),"
                                                                                        +DatabaseBuilder.TOMATO_TASK_START_TIME + " long,"
                                                                                        +DatabaseBuilder.TOMATO_TASK_END_TIME + " long,"
                                                                                        +DatabaseBuilder.TOMATO_TASK__STATUS + " int,"
                                                                                        +DatabaseBuilder.TOMATO_TASK_PRIORITY + " int )";

    public DataBaseOpenHelper(Context context){
        super(context,dbName,null,version);
    }

    public DataBaseOpenHelper(Context context, SQLiteDatabase.CursorFactory factory){
        super(context,dbName,factory,version);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(createTomatoTaskTable);
    }

    /**
     *  Êý¾Ý¿âÉý¼¶
     * @param sqLiteDatabase
     * @param i
     * @param i1
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }



}
