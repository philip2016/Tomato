package com.yu.tomato.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.yu.tomato.model.ConfigInfo;
import com.yu.tomato.model.TomatoTaskModel;
import com.yu.tomato.util.CommonUtils;
import com.yu.tomato.util.SelfTimeCount;

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
    private SelfTimeCount timeCount = null;

    private TomatoTaskModel nowTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_main_page);
        MyAppGlobalData.setContext(MainPage.this);
        CommonUtils.getInstance(MainPage.this).setConfigInfo(new ConfigInfo(10,10));
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
                Log.i(TAG, "start");
                if(nowTask.getState() == TomatoTaskModel.TASK_STATUS_READY) {
                           timeCount.start();
                            confirm.setText("pause");
                            nowTask.setState(TomatoTaskModel.TASK_STATUS_PROCESSING);
                }else{
                    confirm.setText("start");
                    nowTask.setNeededTime(timeCount.getNeedTime());
                    timeCount.cancel();
                    nowTask.setState(TomatoTaskModel.TASK_STATUS_READY);
                    TaskManager.getInstance(MainPage.this).updateTask(nowTask);
                }
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

                MyAppGlobalData.configInfo = CommonUtils.getInstance(MainPage.this).getConfigInfo();
            }
        }.execute();

    }

    /**
     * 设置当前task view
     */
    private void setNowTaskView(){
         nowTask = TaskManager.getInstance(MainPage.this).getNowTask();

        if(nowTask == null)
            return;

        theme.setText(nowTask.getTomatoTheme());
        timeCount = new SelfTimeCount(nowTask.getNeededTime(),1000);
        timeCount.setCallback(new SelfTimeCount.CallBack() {
            @Override
            public void onTick(String nowTime) {
                countDownTimer.setText(nowTime);
            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onPause() {

            }
        });
        countDownTimer.setText(SelfTimeCount.getTimeFromMill(nowTask.getNeededTime()));

        Log.i(TAG, "" + nowTask.getNeededTime());

        switch (nowTask.getState()){
            case TomatoTaskModel.TASK_STATUS_PROCESSING:
                status.setText(getResources().getString(R.string.status_processing));
                confirm.setText(getResources().getString(R.string.status_pause));
                break;
            case TomatoTaskModel.TASK_STATUS_PAUSE:
                status.setText(getResources().getString(R.string.status_pause));
                confirm.setText(getResources().getString(R.string.status_start));
                break;
            case TomatoTaskModel.TASK_STATUS_READY:
                status.setText("Ready");
                confirm.setText("Start");
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
        registerReceiver(br, filter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        setNowTaskView();
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

}
