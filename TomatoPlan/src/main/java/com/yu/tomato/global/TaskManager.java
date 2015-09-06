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
        return taskManager;
    }


    /**
     * ���������б� �� �����б���ͼ
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
     * �����µ�����
     * @param model
     * @param taskListView
     */
    public synchronized void addNewTask(TomatoTaskModel model,LinearLayout taskListView){
        if(model == null) return;
        // ���ݿ�
        DatabaseBuilder.getInstance().saveNewModel(model);
        // ȫ����ʱ�洢
        models.add(model);
        // �����������view
        resetTaskListView(taskListView);

    }


    /**
     * �Ƴ�����
     * @param model
     * @param taskListView
     */
    public synchronized void removeTask(TomatoTaskModel model,LinearLayout taskListView){
        if(model == null) return;
        // ���ݿ�
        DatabaseBuilder.getInstance().saveNewModel(model);
        // ȫ����ʱ�洢
        models.remove(model);
        // �����������view
        resetTaskListView(taskListView);
    }
}
