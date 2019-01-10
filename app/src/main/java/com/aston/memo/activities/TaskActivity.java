package com.aston.memo.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;

import com.aston.memo.R;
import com.aston.memo.managers.TaskManager;
import com.aston.memo.model.Task;

import org.apache.commons.lang3.StringUtils;

import info.hoang8f.android.segmented.SegmentedGroup;

public class TaskActivity extends AppCompatActivity {

    private EditText title;
    private SegmentedGroup priority;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        title = findViewById(R.id.task_title);
        priority = findViewById(R.id.task_priority);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_task, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_task_save) {
            String sTitle = title.getText().toString();
            if (StringUtils.isNotBlank(sTitle)) {
                int iPriority = getPriority();
                Task task = new Task(sTitle, iPriority);
                TaskManager.getInstance().addNewTask(task);
                finish();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private int getPriority() {
        int value = 0;
        if (priority.getCheckedRadioButtonId() == findViewById(R.id.task_priority_1).getId()) {
            value = 1;
        } else if (priority.getCheckedRadioButtonId() == findViewById(R.id.task_priority_2).getId()) {
            value = 2;
        } else if (priority.getCheckedRadioButtonId() == findViewById(R.id.task_priority_3).getId()) {
            value = 3;
        }
        return value;
    }
}
