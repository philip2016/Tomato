package com.yu.tomato.global;

import android.content.Context;

/**
 * Created by YU on 2015/9/4.
 */
public class MyAppGlobalData {
    private static Context context;
    private static String MainPageName = "com.yu.tomato";
    public static String ACTION_ADD_TASK = MainPageName + "action_add_task";
    public static String ACTION_DEL_TASK = MainPageName + "action_del_task";
    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        MyAppGlobalData.context = context;
    }





}
