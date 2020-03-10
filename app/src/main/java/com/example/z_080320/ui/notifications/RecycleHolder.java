package com.example.z_080320.ui.notifications;

public class RecycleHolder {
    private String TaskDate, TaskName, TaskCompletion,TaskDesc;
    private String keyValue;

    public RecycleHolder(){ }

    public RecycleHolder(String date,String name){
        this.TaskDate = date;
        this.TaskName = name;
    }

    public void setTaskName(String taskName) {
        TaskName = taskName;
    }

    public void setTaskDate(String taskDate) {
        TaskDate = taskDate;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public String getTaskDate() {
        return TaskDate;
    }

    public String getTaskName() {
        return TaskName;
    }
}
