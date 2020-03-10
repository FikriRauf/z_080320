package com.example.z_080320;

import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    public String email,userName;

    public MainViewModel(){
    }

    public MainViewModel(String email,String user){
        this.userName = user;
        this.email = email;
    }
}
