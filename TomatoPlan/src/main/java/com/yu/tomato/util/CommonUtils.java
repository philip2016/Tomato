package com.yu.tomato.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;

import com.yu.tomato.model.ConfigInfo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by YU on 2015/9/8.
 * ���ù���
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
     * �����Ļ�ߴ�
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
     *  ��õ�ǰʱ��
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


    /**
     * SharedPreferences �洢��������   ��û���������Ϣ
     * @return
     */
    public ConfigInfo getConfigInfo(){
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);

        if (sp == null)
            return new ConfigInfo(30,5);

        int timatoTime = sp.getInt("tomotaTime", 30);
        int relaxTime = sp.getInt("relaxTime",5);

        ConfigInfo configInfo = new ConfigInfo(timatoTime,relaxTime);
        return configInfo;

    }



    /**
     * SharedPreferences �洢��������
     * @return
     */
    public void setConfigInfo(ConfigInfo configInfo){
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor =sp.edit();
        editor.putInt("tomotaTime",configInfo.getTomatoTime());
        editor.putInt("relaxTime",configInfo.getRelaxTime());

        editor.commit();

    }


}
