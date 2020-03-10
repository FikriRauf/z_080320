package com.example.z_080320.ui.dashboard;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.snapshot.LeafNode;
import com.google.firebase.database.snapshot.StringNode;

public class TeamTaskHolder {
    private String taskTeamDate, taskTeamName, taskTeamDesc, taskTeamComplete, userAssign;

    public TeamTaskHolder() {
    }

    public TeamTaskHolder(String taskTeamComplete, String taskTeamDate, String taskTeamDesc, String taskTeamName, String userAssign) {
        Log.d("holder", "Teamtask date is " + taskTeamDate);
        this.taskTeamDate = taskTeamDate;
        this.taskTeamName = taskTeamName;
        this.taskTeamDesc = taskTeamDesc;
        this.taskTeamComplete = taskTeamComplete;
        this.userAssign = userAssign;
    }

    public TeamTaskHolder(DataSnapshot dataSnapshot) {
//        Log.d("holder","Teamtask date is " + taskTeamDate);
        this.taskTeamDate = dataSnapshot.child("taskTeamDate").getValue().toString();
        this.taskTeamName = dataSnapshot.child("taskTeamName").getValue().toString();
        this.taskTeamDesc = dataSnapshot.child("taskTeamDesc").getValue().toString();
        this.taskTeamComplete = dataSnapshot.child("taskTeamComplete").getValue().toString();
        this.userAssign = dataSnapshot.child("userAssign").getValue().toString();
    }

    public void setTaskTeamComplete(String taskTeamComplete) {
        this.taskTeamComplete = taskTeamComplete;
    }

    public void setTaskTeamDate(String taskTeamDate) {
        Log.d("setter", "The value is " + taskTeamDate);
        this.taskTeamDate = taskTeamDate;
    }

    public void setTaskTeamDesc(String taskTeamDesc) {
        this.taskTeamDesc = taskTeamDesc;
    }

    public void setTaskTeamName(String taskTeamName) {
        this.taskTeamName = taskTeamName;
    }

    public String getTeamTaskDate() {
        Log.d("holder", "The teamtaskHOlder date is " + taskTeamDate);
        return taskTeamDate;
    }

    public String getTeamTaskName() {
        return taskTeamName;
    }

    public String getTaskComplete() {
        return taskTeamComplete;
    }

    public String getUserAssign() {
        return userAssign;
    }

    public void setUserAssign(String userAssign) {
        this.userAssign = userAssign;
    }

    public String getTaskDesc() {
        return taskTeamDesc;
    }
}
