package com.yu.tomato.util;

import android.os.CountDownTimer;
import android.util.Log;

/**
 * Created by YU on 2015/11/5.
 */
public class SelfTimeCount extends CountDownTimer {

    private static String TAG = SelfTimeCount.class.getCanonicalName().toString();

    private  CallBack callback;
    private long needTime;


    public SelfTimeCount(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    public  void setCallback(CallBack callback) {
       this.callback = callback;
    }

    public long getNeedTime() {
        return needTime;
    }

    public interface CallBack{
        public void onTick(String nowTime);
        public void onFinish();
        public void onPause();
    }

    @Override
    public void onTick(long l) {
            this.needTime = l;
            callback.onTick(getTimeFromMill(l));
    }

    @Override
    public void onFinish() {
        callback.onFinish();
    }


    /**
     * 获得倒计时时间
     * @param millTime
     * @return
     */
    public static  String getTimeFromMill(long millTime){
        StringBuffer time = new StringBuffer();
        long hour  = millTime / (60 * 60 * 1000);
        long minute = (millTime - hour * 60 * 60 * 1000) / (60 * 1000);
        long seconds = (millTime  - hour * 60 * 60 * 1000 - minute * 60 * 1000) / 1000;

        time.append(formateTime(hour,true)).append(formateTime(minute,true)).append(formateTime(seconds,false));

        Log.i(TAG, "TIME:" + hour + " : " + minute + " : " + seconds);
        Log.i(TAG, time.toString());
        return time.toString();

    }

    /**
     * 格式化时间  00  01 10
     * @param time
     * @return
     */
    private static String  formateTime(long time,boolean addDot){
        if(time > 9){
            return addDot ? String.valueOf(time) + " : " : String.valueOf(time);
        }

        if(time == 0){
            return addDot ?  "00 : " : "00";
        }
        return addDot ? "0" + String.valueOf(time) + " : " : "0" + String.valueOf(time);
    }
}
