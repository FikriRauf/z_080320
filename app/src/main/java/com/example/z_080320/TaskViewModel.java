package com.example.z_080320;

import androidx.lifecycle.ViewModel;

public class TaskViewModel extends ViewModel {
    public String TaskName,TaskDesc,TaskDate,TaskCompletion,TaskDeletion;
    public TaskViewModel(){

    }

    public TaskViewModel(String TaskName, String TaskDesc, String TaskDate, String taskCompletion,String TaskDelete){
        this.TaskDate = TaskDate;
        this.TaskDesc = TaskDesc;
        this.TaskName = TaskName;
        this.TaskCompletion = taskCompletion;
        this.TaskDeletion = TaskDelete;
    }
}
