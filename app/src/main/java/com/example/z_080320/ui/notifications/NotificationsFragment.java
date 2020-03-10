package com.example.z_080320.ui.notifications;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.z_080320.ui.home.TaskAdapter;
import com.example.z_080320.ui.home.TaskHolder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment {
    RecyclerView recycleView;
    ArrayList<RecycleHolder> taskList;
    RecycleAdapter adapter;
    String uid;
    private DatabaseReference mDatabase;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        NavigationActivity plan = new NavigationActivity();
        plan.clearFab();
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        recycleView = root.findViewById(R.id.recycleBinRecycle);
        recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        mDatabase = FirebaseDatabase.getInstance().getReference().child("task").child(uid);

        taskList = new ArrayList<RecycleHolder>();

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!taskList.isEmpty()){
                    taskList.clear();
                }
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Log.d("inTask","Snapshot 1" + dataSnapshot1.getValue());

                        for(DataSnapshot dataSnapshot2: dataSnapshot1.getChildren()){
                            int y = 0;
                            Log.d("inTask","Snapshot 2" + dataSnapshot2.getValue());
                            Log.d("inTask","Key value is " + dataSnapshot2.getKey());
                                if(dataSnapshot2.getValue().equals("Deleted")){
                                    RecycleHolder task =  dataSnapshot1.getValue(RecycleHolder.class);
                                    task.setKeyValue(dataSnapshot1.getKey());
                                    taskList.add(task);
                                }
                        }

                }

                adapter = new RecycleAdapter(getContext(),taskList);
                recycleView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(),"Error in retrieving tasks",Toast.LENGTH_SHORT).show();
                Log.d("inTask","Error is : " + databaseError);
            }
        });
        return root;
    }
}
