package com.yu.tomato.global;

import android.content.Context;
import android.widget.LinearLayout;

import com.yu.tomato.database.DatabaseBuilder;
import com.yu.tomato.model.TomatoTaskModel;
import com.yu.tomato.view.TaskItemView;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by YU on 2015/9/6.
 */
public class TaskManager {
    public static List<TomatoTaskModel> models = null;
    private static TaskManager taskManager;
    private static LinkedList<TomatoTaskModel> taskQueue = null;
    private static Context context= null;

    /**
     * 获取单例
     * @param newContext
     * @return
     */
    public static TaskManager getInstance(Context newContext){
        if(taskManager == null){
            taskManager =  new TaskManager();
            taskQueue = new LinkedList<TomatoTaskModel>();
        }
        context = newContext;
        return taskManager;
    }


    /**
     * 重置任务列表 和 任务列表视图
     * @param taskListView
     */
    public synchronized void resetTaskListView(LinearLayout taskListView){
        taskListView.removeAllViews();
        Collections.sort(models);
        for(TomatoTaskModel model:models){
            taskQueue.add(model);
            TaskItemView itemView = new TaskItemView(context,model);
            taskListView.addView(itemView);
        }
    }

    /**
     * 增加新的任务
     * @param model
     * @param taskListView
     */
    public synchronized void addNewTask(TomatoTaskModel model,LinearLayout taskListView){
        if(model == null) return;
        // 数据库
        DatabaseBuilder.getInstance().saveNewModel(model);
        // 全局临时存储
        models.add(model);
        // 重置任务队列view
        resetTaskListView(taskListView);

    }


    /**
     * 移除任务
     * @param model
     * @param taskListView
     */
    public synchronized void removeTask(TomatoTaskModel model,LinearLayout taskListView){
        if(model == null) return;
        // 数据库
        DatabaseBuilder.getInstance().saveNewModel(model);
        // 全局临时存储
        models.remove(model);
        // 重置任务队列view
        resetTaskListView(taskListView);
    }
}
