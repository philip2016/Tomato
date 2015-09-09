package com.yu.tomato.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.yu.tomato.R;
import com.yu.tomato.global.MyAppGlobalData;
import com.yu.tomato.model.ConfigInfo;
import com.yu.tomato.util.CommonUtils;

/**
 * Created by YU on 2015/9/9.
 */
public class ConfigPage extends Activity {

    private EditText tomatoTime;
    private EditText relaxTime;
    private Button confirm;
    private ConfigInfo configInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
        initListener();
    }


    private void initListener(){
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int work = Integer.parseInt(tomatoTime.getText().toString());
                int relax = Integer.parseInt(relaxTime.getText().toString());

                ConfigInfo configInfo = new ConfigInfo(work, relax);

                CommonUtils.getInstance(ConfigPage.this).setConfigInfo(configInfo);
                // 重置全局存储
                MyAppGlobalData.configInfo = configInfo;
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData(){
        if(MyAppGlobalData.configInfo == null) {
            configInfo = CommonUtils.getInstance(ConfigPage.this).getConfigInfo();
            MyAppGlobalData.configInfo = configInfo;
            return;
        }

        configInfo = MyAppGlobalData.configInfo;

    }

    /**
     * init view
     */
    private void initView(){
        tomatoTime = (EditText)findViewById(R.id.config_edit_tomato_time);
        relaxTime = (EditText)findViewById(R.id.config_edit_relax_time);
        confirm = (Button)findViewById(R.id.button_confirm);

        if(configInfo == null)
            return;

        tomatoTime.setText(configInfo.getTomatoTime());
        relaxTime.setText(configInfo.getRelaxTime());

    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
