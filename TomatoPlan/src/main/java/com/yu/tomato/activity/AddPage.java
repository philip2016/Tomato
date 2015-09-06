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

import com.yu.tomato.R;
import com.yu.tomato.database.DatabaseBuilder;
import com.yu.tomato.global.MyAppGlobalData;
import com.yu.tomato.model.TomatoTaskModel;

import java.util.List;

/**
 * Created by YU on 2015/9/4.
 */
public class AddPage extends Activity {


    private EditText theme;
    private EditText description;
    private Button confirm;
    private Button cancel;
    private BroadcastReceiver br;
    private List<TomatoTaskModel> models = null;
    private String TAG = AddPage.class.getCanonicalName().toString();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_add_task_layout);
        MyAppGlobalData.setContext(AddPage.this);
        initView();
        initListener();
    }


    /**
     *  初始化view
     */
    private void initView(){
        theme = (EditText)findViewById(R.id.edit_theme);
        description = (EditText)findViewById(R.id.edit_description);
        confirm = (Button)findViewById(R.id.button_confirm);
        cancel = (Button)findViewById(R.id.button_cancel);
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

    /**
     *  获得当前时间
     * @return
     */
    public long getNowTime(){
        return System.currentTimeMillis();
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

                    Log.i(TAG,"save" + themeString);

                    TomatoTaskModel model = new TomatoTaskModel(1,themeString,descriptionString,getNowTime(),0L,0,0);
                    DatabaseBuilder.getInstance().saveNewModel(model);

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
