package com.yu.tomato.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by YU on 2015/9/8.
 * 常用工具
 */
public class CommonUtils {
    private static Context context;
    private static CommonUtils commonUtils;

    public static CommonUtils getInstance(Context newContext){
        if(commonUtils == null){
            commonUtils =  new CommonUtils();
        }

        context = newContext;
         return commonUtils;
    }

    /**
     * 获得屏幕尺寸
     * @return
     */
    public int[] getPhoneSize(){
        int[] xy = new int[2];
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        xy[0] = dm.widthPixels;
        xy[1] = dm.heightPixels;

        return xy;
    }

    /**
     *  获得当前时间
     * @return
     */
    public long getNowTime(){
        return System.currentTimeMillis();
    }

    /**
     * long to String
     *  time
     * @param time
     * @return
     */
    public String getTimeString(long time){
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd:HH:mm:ss");

        Date date = new Date(time);
        return df.format(date);
    }

}
