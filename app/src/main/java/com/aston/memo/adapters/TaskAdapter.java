package com.aston.memo.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aston.memo.R;
import com.aston.memo.managers.TaskManager;
import com.aston.memo.model.Task;
import com.daimajia.swipe.SwipeLayout;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;

public class TaskAdapter extends BaseAdapter {

    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public TaskAdapter(LayoutInflater mLayoutInflater, Context mContext) {
        this.mLayoutInflater = mLayoutInflater;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return TaskManager.getInstance().getTaskSize();
    }

    @Override
    public Task getItem(int position) {
        return TaskManager.getInstance().getTaskForPosition(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.task_line, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.title = convertView.findViewById(R.id.task_line_title);
            viewHolder.description = convertView.findViewById(R.id.task_line_desc);
            viewHolder.deadLine = convertView.findViewById(R.id.task_line_deadLine);
            viewHolder.done = convertView.findViewById(R.id.task_line_done);
            viewHolder.background = convertView.findViewById(R.id.task_line_background);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Task task = (Task) getItem(position);
        viewHolder.title.setText(task.getTitle());
        viewHolder.description.setText(task.getDescription());
        viewHolder.done.setVisibility(task.isDone() ? View.VISIBLE : View.GONE);
        switch(task.getPriority()){
            case 1:
                viewHolder.background.setBackgroundColor(ContextCompat.getColor(mContext, R.color.red));
                break;
            case 2:
                viewHolder.background.setBackgroundColor(ContextCompat.getColor(mContext, R.color.orange));
                break;
            default:
                viewHolder.background.setBackgroundColor(ContextCompat.getColor(mContext, R.color.beautiful_green));
                break;
        }
        if(task.getDeadLine() != 0){
            PrettyTime p = new PrettyTime();
            viewHolder.deadLine.setText(p.format(new Date(task.getDeadLine())));
        }
        SwipeLayout swipeLayout = convertView.findViewById(R.id.swipe_layout);
        swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        swipeLayout.addDrag(SwipeLayout.DragEdge.Right, convertView.findViewById(R.id.bottom_wrapper));
        swipeLayout.addDrag(SwipeLayout.DragEdge.Left, null);
        return convertView;
    }

    private class ViewHolder {
        TextView title, description, deadLine;
        ImageView done;
        View background;
    }


}
