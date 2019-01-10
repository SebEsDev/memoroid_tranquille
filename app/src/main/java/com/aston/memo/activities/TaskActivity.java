package com.aston.memo.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.aston.memo.R;
import com.aston.memo.managers.TaskManager;
import com.aston.memo.model.Task;

import org.apache.commons.lang3.StringUtils;

import info.hoang8f.android.segmented.SegmentedGroup;

public class TaskActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private EditText title;
    private SegmentedGroup priority;
    private boolean hasChanged;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        title = findViewById(R.id.task_title);
        priority = findViewById(R.id.task_priority);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                hasChanged = true;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        priority.setOnCheckedChangeListener(this);
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
                Task task = new Task(sTitle.trim(), iPriority);
                TaskManager.getInstance().addNewTask(task);
                finish();
            } else {
                Toast.makeText(this, R.string.title_mandatory, Toast.LENGTH_SHORT).show();
            }
            return true;
        } else if (id == android.R.id.home) {
            onBackPressed();
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

    @Override
    public void onBackPressed() {
        if (hasChanged) {
            AlertDialog.Builder builder = new AlertDialog.Builder(TaskActivity.this);
            builder.setTitle(R.string.app_name)
                    .setMessage(R.string.back_task_confirmation)
                    .setNegativeButton(android.R.string.cancel, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
            builder.create().show();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (getPriority()){
            case 1:
                priority.setTintColor(ContextCompat.getColor(this, R.color.red));
                break;
            case 2:
                priority.setTintColor(ContextCompat.getColor(this, R.color.orange));
                break;
            default:
                priority.setTintColor(ContextCompat.getColor(this, R.color.beautiful_green));
                break;
        }
    }
}
