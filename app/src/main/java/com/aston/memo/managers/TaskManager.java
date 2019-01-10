package com.aston.memo.managers;

import com.aston.memo.common.Constants;
import com.aston.memo.model.Task;
import com.orhanobut.hawk.Hawk;

import org.apache.commons.lang3.StringUtils;

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

    public void saveTask(Task task) {
        if(getTaskFromId(task.getId()) == null){
            taskList.add(task);
        }else{
            taskList.set(getTaskPositionFromId(task.getId()), task);
        }
        save();
    }

    public void save(){
        Hawk.put(Constants.TASK_LIST, taskList);
    }

    public Task getTaskFromId(String id) {
        for(Task t: taskList){
            if(t.getId().equals(id)){
                return t;
            }
        }
        return  null;
    }

    public int getTaskPositionFromId(String id){
        for(int i=0; i<taskList.size();i++){
            if(StringUtils.equals(taskList.get(i).getId(), id)){
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
