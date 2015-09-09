package com.yu.tomato.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.yu.tomato.R;
import com.yu.tomato.database.DatabaseBuilder;
import com.yu.tomato.global.MyAppGlobalData;
import com.yu.tomato.global.TaskManager;
import com.yu.tomato.model.TomatoTaskModel;
import com.yu.tomato.util.CommonUtils;

import java.util.List;

/**
 * Created by YU on 2015/9/4.
 */
public class AddPage extends Activity {


    private EditText theme;
    private EditText description;
    private EditText count;
    private EditText priority;
    private Button confirm;
    private Button cancel;
    private BroadcastReceiver br;
    private String TAG = AddPage.class.getCanonicalName().toString();
    private HorizontalScrollView scrollView;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_add_task_layout);
        MyAppGlobalData.setContext(AddPage.this);
        initView();
        initListener();
        setTaskListView();
    }


    /**
     *  初始化view
     */
    private void initView(){
        theme = (EditText)findViewById(R.id.edit_theme);
        description = (EditText)findViewById(R.id.edit_description);
        count = (EditText)findViewById(R.id.edit_count);
        priority = (EditText)findViewById(R.id.edit_priority);
        confirm = (Button)findViewById(R.id.button_confirm);
        cancel = (Button)findViewById(R.id.button_cancel);

        scrollView = (HorizontalScrollView)findViewById(R.id.scroll_view_task_list);
        linearLayout = (LinearLayout)findViewById(R.id.linear_view_task_list);
    }

    /**
     *  初始化listview
     */
    private void setTaskListView(){
        if(TaskManager.models == null || TaskManager.models.isEmpty()){
            new AsyncTask<Object,Object,List<TomatoTaskModel>>(){
                @Override
                protected void onPostExecute(List<TomatoTaskModel> models) {
                    super.onPostExecute(models);
                    TaskManager.models = models;
                }

                @Override
                protected List<TomatoTaskModel> doInBackground(Object... objects) {
                    return DatabaseBuilder.getInstance().getAllTask();
                }
            }.execute();

            TaskManager.getInstance(AddPage.this).resetTaskListView(scrollView,linearLayout);
        }


    }

    /**
     *  初始化按钮监听
     */
    private void initListener(){
        confirm.setOnClickListener(new ButtonOnClickListener(0));
        cancel.setOnClickListener(new ButtonOnClickListener(1));
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        onRegist();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(br != null){
            unregisterReceiver(br);
        }
    }

    /**
     *  注册监听
     */
    private void onRegist(){
    br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    };
        IntentFilter filter = new IntentFilter();
        filter.addAction(MyAppGlobalData.ACTION_ADD_TASK);
        filter.addAction(MyAppGlobalData.ACTION_DEL_TASK);

       registerReceiver(br, filter);

    }


    private class ButtonOnClickListener implements View.OnClickListener{
        private int tag;
        public ButtonOnClickListener(int tag){
            this.tag = tag;
        }
        @Override
        public void onClick(View view) {
            switch (tag){
                case 0:
                    // confirm button
                    // 数据
                    String themeString = theme.getText().toString();
                    String descriptionString = description.getText().toString();
                    int tomatoCount = Integer.parseInt(count.getText().toString());
                    int taskPriority = Integer.parseInt(priority.getText().toString());
                    long neededTime = tomatoCount * TomatoTaskModel.tomatoTime;
                    String ID = String.valueOf(CommonUtils.getInstance(AddPage.this).getNowTime());


                    Log.i(TAG, "save" + themeString);

                    TomatoTaskModel model = new TomatoTaskModel(ID,tomatoCount,themeString,descriptionString,
                                                                                                                0L,neededTime,0L,taskPriority,TomatoTaskModel.TASK_STATUS_READY);
                    // 由任务管理器完成相关数据的改变和视图的改变
                    TaskManager.getInstance(AddPage.this).addNewTask(model,scrollView,linearLayout);

                    // 发送广播 相关界面更改
                    Intent actionIntent = new Intent(MyAppGlobalData.ACTION_ADD_TASK);
                    sendBroadcast(actionIntent);

                    Toast.makeText(AddPage.this,"add complete",Toast.LENGTH_SHORT).show();

                    break;
                case 1:
                    Intent  intent = new Intent(AddPage.this,HisotyRecordPage.class);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    }
}
