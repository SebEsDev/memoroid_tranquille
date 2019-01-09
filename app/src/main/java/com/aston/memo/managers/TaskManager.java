package com.aston.memo.managers;

import com.aston.memo.common.Constants;
import com.aston.memo.model.Task;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

public class TaskManager {

    private static TaskManager instance;

    private List<Task> taskList;

    public static TaskManager getInstance() {
        if (instance == null) {
            instance = new TaskManager();
        }
        return instance;
    }

    private TaskManager() {
        taskList = Hawk.get(Constants.TASK_LIST, new ArrayList<Task>());
    }

    public int getTaskSize() {
        return taskList.size();
    }

    public Task getTaskForPosition(int index) {
        return taskList.get(index);
    }

    public void addnewTask(Task task) {
        taskList.add(task);
    }
}
