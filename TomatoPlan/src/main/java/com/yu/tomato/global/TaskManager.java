package com.yu.tomato.global;

import android.content.Context;
import android.util.Log;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.yu.tomato.database.DatabaseBuilder;
import com.yu.tomato.model.TomatoTaskModel;
import com.yu.tomato.util.CommonUtils;
import com.yu.tomato.view.TaskItemView;

import java.util.ArrayList;
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
    private  TomatoTaskModel nowTask;
    private static Context context= null;
    private static int[] phoneSize;
    private static String TAG = TaskManager.class.getCanonicalName().toString();

    /**
     * ��ȡ����
     * @param newContext
     * @return
     */
    public static TaskManager getInstance(Context newContext){
        if(taskManager == null){
            taskManager =  new TaskManager();
            models  = new ArrayList<TomatoTaskModel>();
            taskQueue = new LinkedList<TomatoTaskModel>();
        }
        context = newContext;
        phoneSize = CommonUtils.getInstance(context).getPhoneSize();
        return taskManager;
    }


    /**
     * ���������б� �� �����б���ͼ
     * @param taskListView
     */
    public synchronized void resetTaskListView(HorizontalScrollView scrollView,LinearLayout taskListView){
        if(models == null || models.isEmpty())
            return;
       // �Ƴ�֮ǰ������view
        taskListView.removeAllViews();
        // ������task�������ȼ�����
        Collections.sort(models);
        taskQueue.clear();
        // ���õ�ǰ������к���ͼ
        for(TomatoTaskModel model:models){
            taskQueue.add(model);
            TaskItemView itemView = new TaskItemView(context,model);
            // ÿ����view����һ��tag view =��task
            itemView.setTag(model);
            taskListView.addView(itemView);
        }

        int moveTo = 0;
        if(taskListView.getChildCount() > 0)
                moveTo = taskListView.getChildAt(taskListView.getChildCount()-1).getRight();

        Log.i(TAG, "move to " + moveTo);
        if(moveTo > phoneSize[0]){
            scrollView.scrollTo(moveTo,5);
        }
    }

    /**
     * �����µ�����
     * @param model
     * @param taskListView
     */
    public synchronized void addNewTask(TomatoTaskModel model,HorizontalScrollView scrollView,LinearLayout taskListView){
        if(model == null) return;
        // ���ݿ�
        DatabaseBuilder.getInstance().saveNewModel(model);
        // ȫ����ʱ�洢
        models.add(model);
        // �����������view
        resetTaskListView(scrollView, taskListView);

    }


    /**
     * �Ƴ�����
     * @param model
     * @param taskListView
     */
    public synchronized void removeTask(TomatoTaskModel model,HorizontalScrollView scrollView,LinearLayout taskListView){
        if(model == null) return;
        // ���ݿ�
        DatabaseBuilder.getInstance().saveNewModel(model);
        // ȫ����ʱ�洢
        models.remove(model);
        // �����������view
        resetTaskListView(scrollView,taskListView);
    }

    /**
     * ��������
     * @param model
     */
    public synchronized void updateTask(TomatoTaskModel model){
        if(model == null) return;
        DatabaseBuilder.getInstance().updateModel(model);
    }

    /**
     * ��ȡ��ǰtask�����ڴ����е�task �������ȼ���ߵ�task
     * @return
     */
    public TomatoTaskModel getNowTask(){
        nowTask  = null;
        for(TomatoTaskModel model : models){
            if(model.getState() == TomatoTaskModel.TASK_STATUS_PROCESSING){
                nowTask =  model;
               return nowTask;
            }
        }
        // ���û�д������������е�����ȡ��ǰ��������еĵ�һ��
        // ��Ϊ������о�����������firstΪ���ȼ���ߵ�
        if(taskQueue.isEmpty())
            return nowTask;

        Log.i(TAG,taskQueue.getFirst().getTomatoID());
        Log.i(TAG,taskQueue.getFirst().getTomatoTheme());
        Log.i(TAG,taskQueue.getFirst().getNeededTime()+"");


        return taskQueue.getFirst();
    }
}
