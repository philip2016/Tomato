package com.yu.tomato.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.yu.tomato.R;
import com.yu.tomato.global.MyAppGlobalData;
import com.yu.tomato.global.TaskManager;
import com.yu.tomato.model.TomatoTaskModel;

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
    private List<TomatoTaskModel> models = null;
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
    }


    /**
     *  ³õÊ¼»¯view
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
     *  ³õÊ¼»¯°´Å¥¼àÌý
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
     *  ×¢²á¼àÌý
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
                    String themeString = theme.getText().toString();
                    String descriptionString = description.getText().toString();
                    int tomatoCount = Integer.getInteger(count.getText().toString());
                    int taskPriority = Integer.getInteger(priority.getText().toString());
                    long neededTime = tomatoCount * TomatoTaskModel.tomatoTime;
                    Log.i(TAG, "save" + themeString);

                    TomatoTaskModel model = new TomatoTaskModel(tomatoCount,themeString,descriptionString,
                                                                                                                0L,neededTime,0L,taskPriority,TomatoTaskModel.TASK_STATUS_READY);
                    TaskManager.getInstance(AddPage.this).addNewTask(model,scrollView,linearLayout);

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
