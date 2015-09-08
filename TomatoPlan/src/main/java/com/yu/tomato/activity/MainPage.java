package com.yu.tomato.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yu.tomato.R;
import com.yu.tomato.database.DatabaseBuilder;
import com.yu.tomato.global.MyAppGlobalData;
import com.yu.tomato.global.TaskManager;
import com.yu.tomato.model.TomatoTaskModel;

import java.util.List;

/**
 * Created by YU on 2015/9/6.
 */
public class MainPage extends Activity {
    private TextView countDownTimer;
    private TextView theme;
    private TextView status;
    private LinearLayout taskListView;
    private HorizontalScrollView taskListScrollView;
    private Button jump;
    private Button confirm;
    private BroadcastReceiver br = null;
    private String TAG = MainPage.class.getCanonicalName().toString();
    private TimeCount timeCount = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_main_page);
        MyAppGlobalData.setContext(MainPage.this);
        initView();
        initData();
        onRegist();
    }

    /**
     * 初始化view
     */
    private void initView(){
            countDownTimer = (TextView)findViewById(R.id.text_timer);
            theme = (TextView)findViewById(R.id.text_now_task_theme);
            status = (TextView)findViewById(R.id.text_now_task_stutas);
             taskListView = (LinearLayout)findViewById(R.id.linear_view_task_list);
            taskListScrollView = (HorizontalScrollView)findViewById(R.id.scroll_view_task_list);
            jump = (Button)findViewById(R.id.button_jump);
            confirm = (Button)findViewById(R.id.button_start);

        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPage.this, AddPage.class);
                startActivity(intent);
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeCount.start();
            }
        });
    }

    private void initData(){
        new AsyncTask<Object,Object,List<TomatoTaskModel>>(){
            @Override
            protected List<TomatoTaskModel> doInBackground(Object... objects) {
                return DatabaseBuilder.getInstance().getAllTask();
            }

            @Override
            protected void onPostExecute(List<TomatoTaskModel> models) {
                super.onPostExecute(models);
                TaskManager.models = models;
                //  任务管理器TaskList update
                TaskManager.getInstance(MainPage.this).resetTaskListView(taskListScrollView,taskListView);
                setNowTaskView();
            }
        }.execute();

    }

    /**
     * 设置当前task view
     */
    private void setNowTaskView(){
        TomatoTaskModel nowTask = TaskManager.getInstance(MainPage.this).getNowTask();
        theme.setText(nowTask.getTomatoTheme());
        timeCount = new TimeCount(nowTask.getNeededTime(),1000);

        switch (nowTask.getState()){
            case TomatoTaskModel.TASK_STATUS_PROCESSING:
                status.setText(getResources().getString(R.string.status_processing));
                confirm.setText(getResources().getString(R.string.status_pause));
                break;
            case TomatoTaskModel.TASK_STATUS_PAUSE:
                status.setText(getResources().getString(R.string.status_pause));
                confirm.setText(getResources().getString(R.string.status_start));
                break;
            default:
                break;
        }
    }

    /**
     * 注册监听
     */
    private void onRegist(){
        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if(TextUtils.equals(action,MyAppGlobalData.ACTION_ADD_TASK)
                    ||  TextUtils.equals(action,MyAppGlobalData.ACTION_DEL_TASK)){
                    initData();
                }
            }
        };

        IntentFilter filter = new IntentFilter();
        filter.addAction(MyAppGlobalData.ACTION_ADD_TASK);
        filter.addAction(MyAppGlobalData.ACTION_DEL_TASK);
        registerReceiver(br,filter);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(br != null){
            unregisterReceiver(br);
        }
    }

    /**
     * 倒计时
     */
    class TimeCount extends CountDownTimer{
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long unfinishMillTime) {
            String time = getTimeFromMill(unfinishMillTime);
            countDownTimer.setText(time);
        }

        @Override
        public void onFinish() {

        }

        /**
         * 获得倒计时时间
         * @param millTime
         * @return
         */
        private String getTimeFromMill(long millTime){
                StringBuffer time = new StringBuffer();
                long hour  = millTime / (60 * 60 * 1000);
                long minute = (millTime - hour * 60 * 60 * 1000) / (60 * 1000);
                long seconds = (millTime  - hour * 60 * 60 * 1000 - minute * 60 * 1000) / 1000;

            time.append(formateTime(hour,true)).append(formateTime(minute,true)).append(formateTime(seconds,false));

            Log.i(TAG, "TIME:" + hour + " : " + minute + " : " + seconds);
            Log.i(TAG,time.toString());
            return time.toString();

        }

        /**
         * 格式化时间  00  01 10
         * @param time
         * @return
         */
        private String  formateTime(long time,boolean addDot){
            if(time > 9){
                return addDot ? String.valueOf(time) + " : " : String.valueOf(time);
            }

            if(time == 0){
                return addDot ?  "00 : " : "00";
            }
            return addDot ? "0" + String.valueOf(time) + " : " : "0" + String.valueOf(time);
        }
    }
}
