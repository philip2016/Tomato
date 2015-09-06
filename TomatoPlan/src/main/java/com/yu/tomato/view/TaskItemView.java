package com.yu.tomato.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yu.tomato.R;
import com.yu.tomato.model.TomatoTaskModel;

/**
 * Created by YU on 2015/9/6.
 */
public class TaskItemView extends LinearLayout {
    private TextView theme;
    private ImageView imageView;
    private LayoutInflater inflater = null;
    private TomatoTaskModel model;
    public TaskItemView(Context context,TomatoTaskModel model) {
        super(context);
        this.model = model;

        inflater = LayoutInflater.from(context);
        init();
    }

    public TaskItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void init(){
        View itemView = inflater.inflate(R.layout.task_item_layout,null);
        theme = (TextView)itemView.findViewById(R.id.text_task_theme);
        imageView = (ImageView)itemView.findViewById(R.id.iamge_task);

        theme.setText(model.getTomatoTheme());

        addView(itemView);
    }
}
