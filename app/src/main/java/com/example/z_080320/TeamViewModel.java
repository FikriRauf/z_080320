package com.example.z_080320;

import androidx.lifecycle.ViewModel;

public class TeamViewModel extends ViewModel {
    public String users,teamName, teamLeader, allUsers;
    public TeamViewModel(){

    }

    public TeamViewModel(String teamName,String user,String leader,String allUsers){
        this.teamName = teamName;
        this.users = user;
        this.teamLeader = leader;
        this.allUsers = allUsers;
    }
}
