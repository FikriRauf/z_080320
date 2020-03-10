package com.example.z_080320.ui.home;

import android.util.Log;

public class TaskHolder {
    private String TaskDate, TaskName, TaskCompletion,TaskDesc;

    private String keyValue;

    public TaskHolder(){}

    public TaskHolder(String date, String name,String desc, String completion){
        Log.d("holder1","The Date being passed is " + date);
        Log.d("holder1","The NAME being stored is " + name);
        this.TaskName = name;
        this.TaskDate = date;
        this.TaskCompletion = completion;
        this.TaskDesc = desc;
    }

    public void setKeyValue(String key){this.keyValue = key;}

    public void setTaskName(String taskName) {
        this.TaskName = taskName;
    }

    public void setTaskCompletion(String taskCompletion) {
        TaskCompletion = taskCompletion;
    }

    public void setTaskDesc(String taskDesc) {
        TaskDesc = taskDesc;
    }

    public void setTaskDate(String taskDate) {
        TaskDate = taskDate;
    }

    public String getTaskDate() {
        return TaskDate;
    }

    public String getTaskName() {
        return TaskName;
    }

    public String getKeyValue(){return keyValue;}
}
