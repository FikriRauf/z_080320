package com.example.z_080320;

import androidx.lifecycle.ViewModel;

public class TeamTaskViewModel extends ViewModel {
    public String taskTeamName,taskTeamDesc,userAssign,taskTeamDate,taskTeamComplete;

    public TeamTaskViewModel(){}

    public TeamTaskViewModel(String taskName, String taskDesc, String taskDate, String userAssign, String taskComplete){
        this.taskTeamName = taskName;
        this.taskTeamDesc = taskDesc;
        this.taskTeamDate = taskDate;
        this.userAssign = userAssign;
        this.taskTeamComplete = taskComplete;
    }
}
