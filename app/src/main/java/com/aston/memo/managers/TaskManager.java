package com.aston.memo.managers;

import com.aston.memo.common.Constants;
import com.aston.memo.model.Task;
import com.orhanobut.hawk.Hawk;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TaskManager {

    private static TaskManager instance;
    private Boolean showDone = true;

    private List<Task> taskList;

    public static TaskManager getInstance() {
        if (instance == null) {
            instance = new TaskManager();
        }
        return instance;
    }

    public Boolean getShowDone() {
        return showDone;
    }

    public void setShowDone(Boolean showDone) {
        this.showDone = showDone;
    }

    private TaskManager() {
        taskList = Hawk.get(Constants.TASK_LIST, new ArrayList<Task>());
    }

    public int getTaskSize() {
        return getFilteredTasks().size();
    }

    public Task getTaskForPosition(int index) {
        return getFilteredTasks().get(index);
    }

    private List<Task> getFilteredTasks() {
        List<Task> result = new ArrayList<>();
        for (Task t : taskList) {
            if (t.isDone()) {
                if (showDone) {
                    result.add(t);
                }
            }else{
                result.add(t);
            }
        }
        Collections.sort(result, new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                
                return 0;
            }
        });
        return result;
    }

    public void saveTask(Task task) {
        if (getTaskFromId(task.getId()) == null) {
            taskList.add(task);
        } else {
            taskList.set(getTaskPositionFromId(task.getId()), task);
        }
        save();
    }

    public void save() {
        Hawk.put(Constants.TASK_LIST, taskList);
    }

    public Task getTaskFromId(String id) {
        for (Task t : taskList) {
            if (t.getId().equals(id)) {
                return t;
            }
        }
        return null;
    }

    public int getTaskPositionFromId(String id) {
        for (int i = 0; i < taskList.size(); i++) {
            if (StringUtils.equals(taskList.get(i).getId(), id)) {
                return i;
            }
        }
        return 0;
    }

    public void deleteTask(String id) {
        int position = getTaskPositionFromId(id);
        taskList.remove(position);
        save();
    }
}
