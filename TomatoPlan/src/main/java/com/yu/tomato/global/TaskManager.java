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
     * ��ȡ����
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
     * ���������б� �� �����б���ͼ
     * @param taskListView
     */
    public synchronized void resetTaskListView(HorizontalScrollView scrollView,LinearLayout taskListView){
       // �Ƴ�֮ǰ������view
        taskListView.removeAllViews();
        // ������task�������ȼ�����
        Collections.sort(models);
        // ���õ�ǰ������к���ͼ
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
     * ��ȡ��ǰtask�����ڴ����е�task �������ȼ���ߵ�task
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
