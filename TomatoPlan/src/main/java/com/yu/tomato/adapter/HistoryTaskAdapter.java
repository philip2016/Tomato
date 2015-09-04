package com.yu.tomato.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yu.tomato.R;
import com.yu.tomato.model.TomatoTaskModel;

import java.util.List;

/**
 * Created by YU on 2015/9/4.
 */
public class HistoryTaskAdapter extends BaseAdapter {

    private List<TomatoTaskModel> models = null;
    private Context context = null;
    private LayoutInflater inflater = null;

    public HistoryTaskAdapter(Context context,List<TomatoTaskModel> models){
        this.context = context;
        this.models = models;
        inflater = LayoutInflater.from(context);
    }

    /**
     *  ÷ÿ÷√ ˝æ›
     * @param models
     */
    public void setData(List<TomatoTaskModel> models){
        this.models = models;
    }
    @Override
    public int getCount() {
        if(models == null) return 0;
        return models.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View tempView = view;
        HistoryItemHolder holder = null;

        if(view == null){
            holder = new HistoryItemHolder();
            tempView = inflater.inflate(R.layout.histroty_item_layout,null);
            holder.icon = (ImageView)tempView.findViewById(R.id.image_task);
            holder.theme = (TextView)tempView.findViewById(R.id.text_theme);
            holder.description = (TextView)tempView.findViewById(R.id.text_description);
            holder.startTime = (TextView)tempView.findViewById(R.id.text_start_time);
            holder.endTime = (TextView)tempView.findViewById(R.id.text_end_time);
            tempView.setTag(holder);
        }else{
                holder = (HistoryItemHolder)tempView.getTag();
        }

        TomatoTaskModel model = models.get(i);
        if(model == null) return null;

        holder.theme.setText(model.getTomatoTheme());
        holder.description.setText(model.getTomatoDescription());
        holder.startTime.setText(String.valueOf(model.getTomatoStartTime()));
        holder.endTime.setText(String.valueOf(model.getTomatoEndTime()));

        return tempView;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Object getItem(int i) {
        return models.get(i);
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount();
    }


    private class HistoryItemHolder{
        ImageView icon;
        TextView theme;
        TextView description;
        TextView startTime;
        TextView endTime;
        TextView status;
    }

}
