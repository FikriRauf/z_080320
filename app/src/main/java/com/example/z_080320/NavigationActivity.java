package com.example.z_080320;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class NavigationActivity extends AppCompatActivity {

    static int fragmentView = 0, teamTaskKey = 0;
    static String allTeamUser,teamKey;
    static FloatingActionButton fab;

    TextView userList;
    EditText teamName, users, taskName,taskDate,taskDesc;
    Button createBtn,searchBtn, creatTask;
    private DatabaseReference mDatabase,mCheck,mTask,mTeamTask;
    String userLists, displayUser;

    String usersAss = "";
    int newFlag = 0;
    int x = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        fab = findViewById(R.id.floatingActionButton);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("team");
        mCheck = FirebaseDatabase.getInstance().getReference().child("users");
        mTask = FirebaseDatabase.getInstance().getReference().child("task");
        mTeamTask = FirebaseDatabase.getInstance().getReference().child("teamTask");

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("fragment","The value of fragmentView is " + fragmentView);
                //1 == GalleryFragment
                //0 == HomeFragment
                if(fragmentView == 1){
                    Log.d("fragment","Gallery Fragment is shown");
                    Toast.makeText(getApplicationContext(),"Gallery Fragment is shown",Toast.LENGTH_SHORT).show();
                    userLists = "";
                    AlertDialog.Builder mBuilder1 = new AlertDialog.Builder(NavigationActivity.this);
                    LayoutInflater inflater1 = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    view = inflater1.inflate(R.layout.team_create_pop, null);
                    mBuilder1.setView(view);

                    final AlertDialog dialog1 = mBuilder1.create();
                    dialog1.show();

                    teamName = view.findViewById(R.id.teamNameEdit);
                    users = view.findViewById(R.id.usersEmailEdit);
                    createBtn = view.findViewById(R.id.createTeamBtn);
                    searchBtn = view.findViewById(R.id.searchUserBtn);
                    userList = view.findViewById(R.id.usersToAdd);

                    searchBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            x = 0;
                            int flag = 0;
                            String[] checkUser = userLists.split(",");
                            displayUser = "";
                            final String user = users.getText().toString().trim().toLowerCase();
                            for(int y = 0;y < checkUser.length;y++){
                                if(checkUser[y].equals(user)){
                                    flag = 1;
                                }
                            }
                            String emailAdd = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                            Log.d("user","Email 1 is " + user + " Email 2 is " + emailAdd);
                            if(emailAdd.equals(user)){
                                flag = 1;
                            }
                            if(flag == 0) {
                                Query query = mCheck;
                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                            for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                                                if (dataSnapshot2.getValue().equals(user.toLowerCase())) {
                                                    userLists = userLists + user + ",";
                                                    x = 1;
                                                    String[] disUser = userLists.split(",");
                                                    for (int i = 0; i < disUser.length; i++) {
                                                        displayUser = displayUser + disUser[i] + "\n";
                                                    }
                                                    userList.setText(displayUser);
                                                    userList.setVisibility(View.VISIBLE);
                                                    Toast.makeText(getApplicationContext(), "User Found", Toast.LENGTH_SHORT).show();
                                                    createBtn.setVisibility(View.VISIBLE);
                                                }
                                            }
                                        }
                                        if (x == 0) {
                                            Toast.makeText(getApplicationContext(), "User not Found", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        Log.d("inTask","err is " + databaseError);
                                    }
                                });
                            }else{
                                Toast.makeText(getApplicationContext(), "User is already Added", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    createBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String teamN = teamName.getText().toString().trim();
                            String leader = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                            String allUser = leader + "," + userLists;
                            TeamViewModel team = new TeamViewModel(teamN,userLists.toLowerCase(),leader,allUser);
                            mDatabase.push().setValue(team);
                            dialog1.dismiss();
                        }
                    });
                }else if(fragmentView == 0){
                    AlertDialog.Builder mBuilder2 = new AlertDialog.Builder(NavigationActivity.this);
                    LayoutInflater inflater2 = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    view = inflater2.inflate(R.layout.task_create_pop, null);
                    mBuilder2.setView(view);

                    final AlertDialog dialog2 = mBuilder2.create();
                    dialog2.show();
                    Toast.makeText(getApplicationContext(),"The Home Fragment is showing",Toast.LENGTH_SHORT).show();

                    taskName = view.findViewById(R.id.taskNameEdit);
                    taskDate = view.findViewById(R.id.taskDateEdit);
                    taskDesc = view.findViewById(R.id.taskDescEdit);
                    creatTask = view.findViewById(R.id.addtaskBtn);

                    creatTask.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String name = taskName.getText().toString().trim();
                            String date = taskDate.getText().toString().trim();
                            String desc = taskDesc.getText().toString().trim();
                            Log.d("name","Name is " + name);
                            Log.d("name","Date is " + date);
                            if(!name.isEmpty() && !date.isEmpty() && !desc.isEmpty()){
                                TaskViewModel task = new TaskViewModel(name,desc,date,"Uncompleted","Undeleted");
                                String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                mTask.child(currentUser).push().setValue(task);

                                Toast.makeText(getApplicationContext(),"Task Created",Toast.LENGTH_SHORT).show();
                                dialog2.dismiss();
                            }
                        }
                    });
                }else if(fragmentView == 2){
                    newFlag = 0;
                    AlertDialog.Builder mBuilder3 = new AlertDialog.Builder(NavigationActivity.this);
                    LayoutInflater inflater3 = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    view = inflater3.inflate(R.layout.team_task_create_pop, null);
                    mBuilder3.setView(view);

                    final AlertDialog dialog3 = mBuilder3.create();
                    dialog3.show();

                    final EditText teamTaskName, teamTaskDate, userAdd, teamTaskDesc;

                    teamTaskName = view.findViewById(R.id.teamTaskNameTxt);
                    teamTaskDate = view.findViewById(R.id.teamTaskDateTxt);
                    teamTaskDesc = view.findViewById(R.id.teamTaskDesc);
                    userAdd = view.findViewById(R.id.teamUserTxt);
                    Button searchTeamUser = view.findViewById(R.id.searchUserAssBtn);
                    Button createTeamTask = view.findViewById(R.id.createTeamTaskBtn);

                    searchTeamUser.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String userInput = userAdd.getText().toString().trim().toLowerCase();
                            if (newFlag == 0) {
                                int x = 0;
                                String[] userArr = allTeamUser.split(",", 0);

                                for (int i = 0; i < userArr.length; i++) {
                                    if (userInput.equals(userArr[i])) {
                                        usersAss = userArr[i];
                                        x++;
                                    }
                                }
                                if (x == 0) {
                                    Toast.makeText(getApplicationContext(), "User not found/User not in team", Toast.LENGTH_SHORT).show();
                                }
                                newFlag++;
                            } else {
                                int x = 0, ff = 0;
                                String[] userArr = allTeamUser.split(",", 0);
                                String[] tempUser = usersAss.split(",",0);
                                for(int m = 0; m < tempUser.length; m++){
                                    if(userInput.equals(tempUser[m])){
                                        ff = 1;
                                    }
                                }
                                if(ff == 0) {
                                    for (int i = 0; i < userArr.length; i++) {
                                        if (userInput.equals(userArr[i])) {
                                            usersAss = usersAss + "," + userArr[i];
                                            x++;
                                        }
                                    }
                                }
                                if (x == 0) {
                                    Toast.makeText(getApplicationContext(), "User not found/User not in team", Toast.LENGTH_SHORT).show();
                                }
                                if(ff == 1){
                                    Toast.makeText(getApplicationContext(), "User already added", Toast.LENGTH_SHORT).show();
                                }
                            }
                            Log.d("allUser", "The users are " + usersAss);
                        }
                    });

                    createTeamTask.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String name = teamTaskName.getText().toString().trim();
                            String date = teamTaskDate.getText().toString().trim();
                            String desc = teamTaskDesc.getText().toString().trim();
                            Log.d("teamTask","Name is " + name);
                            Log.d("teamTask","Date is " + date);
                            Log.d("teamTask","Desc is " + desc);
                            if(!name.isEmpty() && !date.isEmpty() && !desc.isEmpty()){
                                TeamTaskViewModel task = new TeamTaskViewModel(name,desc,date,usersAss,"Uncompleted");
                                mTeamTask.child(teamKey).push().setValue(task);

                                Toast.makeText(getApplicationContext(),"Task Created",Toast.LENGTH_SHORT).show();
                                dialog3.dismiss();
                            }
                        }
                    });
                }

            }
        });

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_team)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    public void setFragmentView(int x){
        Log.d("fragment","Value being passed is " + x);
        fragmentView = x;
    }

    public void clearFab(){
        fab.setVisibility(View.INVISIBLE);
    }

    public void addFab(){
        fab.setVisibility(View.VISIBLE);
    }

    public void setAllUser(String allUser,String teamKey){
        allTeamUser = allUser;
        this.teamKey= teamKey;
        Log.d("allUser","Users are " + allTeamUser);
        Log.d("allUser","keys are " + teamKey);
    }
}
