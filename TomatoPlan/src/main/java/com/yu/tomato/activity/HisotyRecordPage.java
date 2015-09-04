package com.yu.tomato.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.yu.tomato.R;
import com.yu.tomato.adapter.HistoryTaskAdapter;
import com.yu.tomato.database.DatabaseBuilder;
import com.yu.tomato.global.MyAppGlobalData;
import com.yu.tomato.model.TomatoTaskModel;

import java.util.List;

/**
 * Created by YU on 2015/9/4.
 */
public class HisotyRecordPage extends Activity {
    private ListView history;
    private List<TomatoTaskModel> models = null;
   private  HistoryTaskAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_history_layout);
        initView();
        initData();
        initListener();
    }

    /**
     *  初始化数据
     *
     */
    private void initData(){
        new AsyncTask<Object,Object,List<TomatoTaskModel>>(){

            @Override
            protected List<TomatoTaskModel> doInBackground(Object... objects) {
                models = DatabaseBuilder.getInstance(HisotyRecordPage.this).getAllTask();
                return models;
            }

            @Override
            protected void onPostExecute(List<TomatoTaskModel> models) {
                super.onPostExecute(models);
                adapter.setData(models);
                adapter.notifyDataSetChanged();
                initListener();
            }
        }.execute();

    }

    /**
     *  初始化view
     */
    private void initView(){
        history = (ListView)findViewById(R.id.list_view_history);
       adapter  = new HistoryTaskAdapter(HisotyRecordPage.this,models);
        history.setAdapter(adapter);
    }

    public void initListener(){
        history.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MyAppGlobalData.getContext(),"hello"+i,Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


}
