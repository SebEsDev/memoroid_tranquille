package com.aston.memo.adapters;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aston.memo.R;

public class TaskAdapter extends BaseAdapter {

    private LayoutInflater mLayoutInflater;

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
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
            viewHolder.desc = convertView.findViewById(R.id.task_line_desc);
            viewHolder.deadLine = convertView.findViewById(R.id.task_line_deadLine);
            viewHolder.done = convertView.findViewById(R.id.task_line_done);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        return null;
    }

    private class ViewHolder {
        TextView title, desc, deadLine;
        ImageView done;
    }


}
