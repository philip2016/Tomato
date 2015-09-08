package com.yu.tomato.global;

import android.content.Context;
import android.util.Log;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.yu.tomato.database.DatabaseBuilder;
import com.yu.tomato.model.TomatoTaskModel;
import com.yu.tomato.util.CommonUtils;
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
    private static TomatoTaskModel nowTask;
    private static Context context= null;
    private static int[] phoneSize;
    private static String TAG = TaskManager.class.getCanonicalName().toString();

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
        phoneSize = CommonUtils.getInstance(context).getPhoneSize();
        return taskManager;
    }


    /**
     * 重置任务列表 和 任务列表视图
     * @param taskListView
     */
    public synchronized void resetTaskListView(HorizontalScrollView scrollView,LinearLayout taskListView){
       // 移除之前所有子view
        taskListView.removeAllViews();
        // 对现有task按照优先级排序
        Collections.sort(models);
        // 重置当前任务队列和视图
        for(TomatoTaskModel model:models){
            taskQueue.add(model);
            TaskItemView itemView = new TaskItemView(context,model);
            taskListView.addView(itemView);
        }

        int moveTo = taskListView.getChildAt(taskListView.getChildCount()-1).getRight();

        Log.i(TAG, "move to " + moveTo);
        if(moveTo > phoneSize[0]){
            scrollView.scrollTo(moveTo,5);
        }
    }

    /**
     * 增加新的任务
     * @param model
     * @param taskListView
     */
    public synchronized void addNewTask(TomatoTaskModel model,HorizontalScrollView scrollView,LinearLayout taskListView){
        if(model == null) return;
        // 数据库
        DatabaseBuilder.getInstance().saveNewModel(model);
        // 全局临时存储
        models.add(model);
        // 重置任务队列view
        resetTaskListView(scrollView, taskListView);

    }


    /**
     * 移除任务
     * @param model
     * @param taskListView
     */
    public synchronized void removeTask(TomatoTaskModel model,HorizontalScrollView scrollView,LinearLayout taskListView){
        if(model == null) return;
        // 数据库
        DatabaseBuilder.getInstance().saveNewModel(model);
        // 全局临时存储
        models.remove(model);
        // 重置任务队列view
        resetTaskListView(scrollView,taskListView);
    }

    /**
     * 获取当前task，正在处理中的task 或者优先级最高的task
     * @return
     */
    public TomatoTaskModel getNowTask(){
        for(TomatoTaskModel model : models){
            if(model.getState() == TomatoTaskModel.TASK_STATUS_PROCESSING){
                return model;
            }
        }

        return taskQueue.getFirst();
    }
}
