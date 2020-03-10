package com.example.z_080320.ui.team;

public class TeamHolder {
    private String allUsers,teamName,users,teamLeader;

    public TeamHolder(){ }

    public TeamHolder(String allUsers,String teamLeader,String name, String Users ){
        this.teamName = name;
        this.users = Users;
        this.teamLeader = teamLeader;
        this.allUsers = allUsers;
    }

    public void setTeamName(String name) {
        this.teamName = name;
    }

    public void setTeamLeader(String leader) {
        this.teamLeader = leader;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public void setAllUsers(String allUsers) {
        this.allUsers = allUsers;
    }

    public String getAllUsers() {
        return allUsers;
    }

    public String getUsers() {
        return users;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getTeamLeader() {
        return teamLeader;
    }
}
