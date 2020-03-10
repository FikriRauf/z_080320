package com.example.z_080320.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.z_080320.NavigationActivity;
import com.example.z_080320.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    RecyclerView recycleView;
    ArrayList<TaskHolder> taskList;
    TaskAdapter adapter;
    String uid;
    private DatabaseReference mDatabase;

    Button complete,unComplete;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        NavigationActivity plan = new NavigationActivity();
        plan.setFragmentView(0);
        plan.addFab();

        recycleView = root.findViewById(R.id.taskRecycle);
        recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        mDatabase = FirebaseDatabase.getInstance().getReference().child("task");

        Log.d("inTask","Current user is " + FirebaseAuth.getInstance().getCurrentUser().getUid());
        taskList = new ArrayList<TaskHolder>();
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!taskList.isEmpty()){
                    taskList.clear();
                }
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Log.d("inTask","Snapshot 1" + dataSnapshot1.getValue());
                    if(dataSnapshot1.getKey().equals(uid)){
                        for(DataSnapshot dataSnapshot2: dataSnapshot1.getChildren()){
                            int y = 0;
                            Log.d("inTask","Snapshot 2" + dataSnapshot2.getValue());
                            Log.d("inTask","Key value is " + dataSnapshot2.getKey());
                            for(DataSnapshot dataSnapshot3 : dataSnapshot2.getChildren()){
                                Log.d("inTask","The CHILD Value " + dataSnapshot3.getValue());
                                Log.d("inTask","The Key value is " + dataSnapshot3.getKey());
                                if(dataSnapshot3.getValue().equals("Uncompleted")){
                                    y = 1;
                                }else if(dataSnapshot3.getValue().equals("Undeleted") && y == 1){
                                    TaskHolder task =  dataSnapshot2.getValue(TaskHolder.class);
                                    task.setKeyValue(dataSnapshot2.getKey());
                                    taskList.add(task);
                                }
                            }
                        }
                    }

                }

                adapter = new TaskAdapter(getContext(),taskList);
                recycleView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(),"Error in retrieving tasks",Toast.LENGTH_SHORT).show();
                Log.d("inTask","Error is : " + databaseError);
            }
        });

        complete = root.findViewById(R.id.completeBtn);
        unComplete = root.findViewById(R.id.unCompletedBtn);
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(!taskList.isEmpty()){
                            taskList.clear();
                        }
                        for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                            Log.d("inTask","Snapshot 1" + dataSnapshot1.getValue());
                            if(dataSnapshot1.getKey().equals(uid)){
                                for(DataSnapshot dataSnapshot2: dataSnapshot1.getChildren()){
                                    Log.d("inTask","Snapshot 2" + dataSnapshot2.getValue());
                                    Log.d("inTask","Key value is " + dataSnapshot2.getKey());
                                    for(DataSnapshot dataSnapshot3 : dataSnapshot2.getChildren()){
                                        Log.d("inTask","The CHILD Value " + dataSnapshot3.getValue());
                                        Log.d("inTask","The Key value is " + dataSnapshot3.getKey());
                                        if(dataSnapshot3.getValue().equals("Completed")){
                                            TaskHolder task =  dataSnapshot2.getValue(TaskHolder.class);
                                            task.setKeyValue(dataSnapshot2.getKey());
                                            taskList.add(task);
                                        }
                                    }
                                }
                            }

                        }

                        adapter = new TaskAdapter(getContext(),taskList);
                        recycleView.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getActivity(),"Error in retrieving tasks",Toast.LENGTH_SHORT).show();
                        Log.d("inTask","Error is : " + databaseError);
                    }
                });
            }
        });

        unComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(!taskList.isEmpty()){
                            taskList.clear();
                        }
                        for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                            Log.d("inTask","Snapshot 1" + dataSnapshot1.getValue());
                            if(dataSnapshot1.getKey().equals(uid)){
                                for(DataSnapshot dataSnapshot2: dataSnapshot1.getChildren()){
                                    int y = 0;
                                    Log.d("inTask","Snapshot 2" + dataSnapshot2.getValue());
                                    Log.d("inTask","Key value is " + dataSnapshot2.getKey());
                                    for(DataSnapshot dataSnapshot3 : dataSnapshot2.getChildren()){
                                        Log.d("inTask","The CHILD Value " + dataSnapshot3.getValue());
                                        Log.d("inTask","The Key value is " + dataSnapshot3.getKey());
                                        if(dataSnapshot3.getValue().equals("Uncompleted")){
                                            y = 1;
                                        }else if(dataSnapshot3.getValue().equals("Undeleted") && y == 1){
                                            TaskHolder task =  dataSnapshot2.getValue(TaskHolder.class);
                                            task.setKeyValue(dataSnapshot2.getKey());
                                            taskList.add(task);
                                        }
                                    }
                                }
                            }

                        }

                        adapter = new TaskAdapter(getContext(),taskList);
                        recycleView.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getActivity(),"Error in retrieving tasks",Toast.LENGTH_SHORT).show();
                        Log.d("inTask","Error is : " + databaseError);
                    }
                });
            }
        });
        return root;
    }
}
