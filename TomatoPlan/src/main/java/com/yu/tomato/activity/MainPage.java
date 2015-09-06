package com.yu.tomato.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
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
    private LinearLayout taskLitView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_main_page);
        MyAppGlobalData.setContext(MainPage.this);
        initView();
        initData();
    }

    /**
     * ≥ı ºªØview
     */
    private void initView(){
            countDownTimer = (TextView)findViewById(R.id.text_timer);
            theme = (TextView)findViewById(R.id.text_now_task_theme);
            status = (TextView)findViewById(R.id.text_now_task_stutas);
            taskLitView = (LinearLayout)findViewById(R.id.scroll_view_task_list);
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
                TaskManager.getInstance(MainPage.this).resetTaskListView(taskLitView);
            }
        }.execute();

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
    }
}
