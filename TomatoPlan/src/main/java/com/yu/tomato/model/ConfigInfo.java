package com.yu.tomato.model;

/**
 * Created by YU on 2015/9/9.
 */
public class ConfigInfo {
    private int tomatoTime;
    private int relaxTime;


    public ConfigInfo(int tomatoTime, int relaxTime) {
        this.tomatoTime = tomatoTime;
        this.relaxTime = relaxTime;
    }

    public int getTomatoTime() {
        return tomatoTime;
    }

    public int getRelaxTime() {
        return relaxTime;
    }

    public void setTomatoTime(int tomatoTime) {
        this.tomatoTime = tomatoTime;
    }

    public void setRelaxTime(int relaxTime) {
        this.relaxTime = relaxTime;
    }
}
