package com.aston.memo.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.aston.memo.R;
import com.aston.memo.common.Constants;
import com.aston.memo.managers.TaskManager;
import com.aston.memo.model.Task;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.util.Calendar;

import info.hoang8f.android.segmented.SegmentedGroup;

public class TaskActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener, View.OnClickListener {

    private EditText title, description;
    private SegmentedGroup priority;
    private boolean hasChanged;
    private Button date, time;
    private Calendar calendarDeadLine;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        title = findViewById(R.id.task_title);
        description = findViewById(R.id.task_description);
        priority = findViewById(R.id.task_priority);
        date = findViewById(R.id.task_deadLine_date);
        time = findViewById(R.id.task_deadLine_time);
        date.setOnClickListener(this);
        time.setOnClickListener(this);
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
        if (getIntent().hasExtra(Constants.TASK_ID)) {
            String id = getIntent().getStringExtra(Constants.TASK_ID);
            Task task = TaskManager.getInstance().getTaskFromId(id);
            if (task != null) {
                title.setText(task.getTitle());
                description.setText(task.getDescription());
                if (task.getDeadLine() != 0) {
                    calendarDeadLine = Calendar.getInstance();
                    calendarDeadLine.setTimeInMillis(task.getDeadLine());
                }
                priority.check(getRadioButtonFromPriority(task.getPriority()));
            }
        }
    }

    private int getRadioButtonFromPriority(int iPriority){
        switch (iPriority){
            case 1:
                return findViewById(R.id.task_priority_1).getId();
            case 2:
                return findViewById(R.id.task_priority_2).getId();
            default:
                return findViewById(R.id.task_priority_3).getId();
        }
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
                task.setDescription(description.getText().toString());
                if (calendarDeadLine != null) {
                    task.setDeadLine(calendarDeadLine.getTimeInMillis());
                }
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
        switch (getPriority()) {
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
        hasChanged = true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == time.getId()) {
            if (calendarDeadLine == null) {
                calendarDeadLine = Calendar.getInstance();
            }
            int hour = calendarDeadLine.get(Calendar.HOUR_OF_DAY);
            int minute = calendarDeadLine.get(Calendar.MINUTE);
            AlertDialog alertDialog = new TimePickerDialog(this, this, hour, minute, android.text.format.DateFormat.is24HourFormat(this));
            alertDialog.show();
        } else if (v.getId() == date.getId()) {
            if (calendarDeadLine == null) {
                calendarDeadLine = Calendar.getInstance();
            }
            int day = calendarDeadLine.get(Calendar.DAY_OF_MONTH);
            int month = calendarDeadLine.get(Calendar.MONTH);
            int year = calendarDeadLine.get(Calendar.YEAR);
            AlertDialog alertDialog = new DatePickerDialog(this, this, year, month, day);
            alertDialog.show();
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        calendarDeadLine.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendarDeadLine.set(Calendar.MINUTE, minute);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendarDeadLine.set(Calendar.YEAR, year);
        calendarDeadLine.set(Calendar.MONTH, month);
        calendarDeadLine.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        showDeadLine();
    }

    private void showDeadLine() {
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
        date.setText(df.format(calendarDeadLine.getTime()));
        DateFormat dfTime = DateFormat.getTimeInstance(DateFormat.SHORT);
        time.setText(dfTime.format(calendarDeadLine.getTime()));
    }

}