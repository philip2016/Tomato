package com.yu.tomato.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yu.tomato.global.MyAppGlobalData;
import com.yu.tomato.model.TomatoTaskModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YU on 2015/9/4.
 */
public class DatabaseBuilder {
    public static String TOMATO_TABLE = "tomato_table";
    public static String TOMATO_TASK_ID="id";
    public static String TOMATO_TASK_TOMATO_TIME_COUNT="count";
    public static String TOMATO_TASK_THEME = "theme";
    public static String TOMATO_TASK_DESCRIPTION = "description";
    public static String TOMATO_TASK__STATUS = "status";
    public static String TOMATO_TASK_START_TIME = "start_time";
    public static String TOMATO_TASK_END_TIME = "end_time";
    public static String TOMATO_TASK_PRIORITY = "priority";

    private static DatabaseBuilder databaseBuilder = null;
    private SQLiteDatabase db = null;
    private DataBaseOpenHelper openHelper =  null;
    /**
     * 获得单例
     * @param
     * @return
     */
    public static DatabaseBuilder getInstance(){
        if(databaseBuilder == null){
            databaseBuilder = new DatabaseBuilder();
        }
        return databaseBuilder;
    }

    /**
     * 获得db对象
     */
    private void open(){
       if(openHelper == null){
           openHelper = new DataBaseOpenHelper(MyAppGlobalData.getContext());
       }
        db = openHelper.getWritableDatabase();
    }

    /**
     *  关闭db
     */
   private void close(){
        if(db == null ) return;
        db.close();
    }


    public  long  insert(TomatoTaskModel model){
        ContentValues cv = new ContentValues();
        cv.put(TOMATO_TASK_THEME,model.getTomatoTheme());
        cv.put(TOMATO_TASK_DESCRIPTION,model.getTomatoDescription());
        cv.put(TOMATO_TASK_START_TIME,model.getTomatoStartTime());
        cv.put(TOMATO_TASK_END_TIME,model.getTomatoEndTime());
        cv.put(TOMATO_TASK_TOMATO_TIME_COUNT,model.getTomatoCount());
        cv.put(TOMATO_TASK__STATUS,model.getState());
        cv.put(TOMATO_TASK_PRIORITY,model.getPriority());

        return db.insert(TOMATO_TABLE,null,cv);
    }

    /**
     *  存储task
     * @param model
     */
    public void saveNewModel(TomatoTaskModel model){
        open();
        insert(model);
        close();
    }

    /**
     *  获得历史tomatotask
     * @return
     */
    public List<TomatoTaskModel> getAllTask(){
        open();
        String[] columns = new String[]{    TOMATO_TASK_ID,TOMATO_TASK_TOMATO_TIME_COUNT,
                                                                    TOMATO_TASK_THEME,TOMATO_TASK_DESCRIPTION,
                                                                    TOMATO_TASK_START_TIME,TOMATO_TASK_END_TIME,
                                                                    TOMATO_TASK_PRIORITY,TOMATO_TASK__STATUS};

        Cursor cursor = db.query(TOMATO_TABLE, columns, null, null, null, null, null, null);
        List<TomatoTaskModel> models = null;
        if(cursor != null){
            models = new ArrayList<TomatoTaskModel>();
            while (cursor.moveToNext()){
                TomatoTaskModel model = new TomatoTaskModel(cursor.getInt(1),cursor.getString(2),cursor.getString(3),
                                                                                                          cursor.getLong(4),cursor.getLong(5),cursor.getInt(6),cursor.getInt(7));
                models.add(model);
            }
        }

        close();
        return  models;

    }



}
