package com.example.z_080320.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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
import java.util.List;

public class DashboardFragment extends Fragment {
    DatabaseReference mDatabase,teamTask,teamLeader;
    ArrayList<TeamTaskHolder> taskLists;
    String email;
    int flag;
    Button aearch;
    Spinner sItems;
    RecyclerView recycleView;
    TeamTaskAdapter adapter1;
    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("team");
        final List<String> spinnerArray =  new ArrayList<String>();
        final ArrayList<String> teamKey = new ArrayList<String>();
        email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        final NavigationActivity plan = new NavigationActivity();
        plan.setFragmentView(2);
        plan.clearFab();

        recycleView = root.findViewById(R.id.teamTaskRecycle);
        recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        taskLists = new ArrayList<TeamTaskHolder>();

        sItems = root.findViewById(R.id.teamSpinner);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!spinnerArray.isEmpty()){
                    spinnerArray.clear();
                    teamKey.clear();
                }
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    int x = 0;
                    for(DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()){
                        if(dataSnapshot2.getKey().equals("allUsers")){
                            Log.d("team","datSnapshot 2 " + dataSnapshot2.getValue());
                            String allUser = dataSnapshot2.getValue().toString();
                            String[] afterUser = allUser.split(",",0);
                            for(int i = 0; i < afterUser.length;i++){
                                if(afterUser[i].equals(email)){
                                    Log.d("team","User is found");
                                    x = 1;
                                    Log.d("team", "onDataChange: " + dataSnapshot1.getValue());
                                }
                            }
                        }

                        if(x == 1){
                            if(dataSnapshot2.getKey().equals("teamName")){
                                spinnerArray.add(dataSnapshot2.getValue().toString());
                            }
                        }
                    }
                    if(x == 1){
                        teamKey.add(dataSnapshot1.getKey());
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, spinnerArray);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sItems.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        sItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position2, long id) {
                flag = 0;
                Log.d("item","Item Selected " + sItems.getSelectedItemPosition());
                Log.d("item","The Value is " + sItems.getSelectedItem().toString());
                int position = sItems.getSelectedItemPosition();
                Log.d("teamTask","Pos value is " + teamKey.get(position));
                teamTask = FirebaseDatabase.getInstance().getReference().child("teamTask").child(teamKey.get(position));
                teamLeader = FirebaseDatabase.getInstance().getReference().child("team");
                final String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

                teamLeader.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                            for(DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()){
                                Log.d("teamTask","key " + dataSnapshot2.getKey());
                                if(dataSnapshot2.getKey().equals("teamLeader")){
                                    Log.d("teamTask","The Vvalue is " + dataSnapshot2.getValue());
                                    if(email.equals(dataSnapshot2.getValue())){
                                        plan.addFab();
                                    }
                                }

                                if(dataSnapshot2.getKey().equals("allUsers")){
                                    plan.setAllUser(dataSnapshot2.getValue().toString(),dataSnapshot1.getKey());
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getContext(),"Error in retrieving Teams",Toast.LENGTH_SHORT).show();
                    }
                });
                teamTask.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(!taskLists.isEmpty()){
                            taskLists.clear();
                        }
                        for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                            Log.d("teamTask","Snapshot 1" + dataSnapshot1.getValue());
                            for(DataSnapshot dataSnapshot2: dataSnapshot1.getChildren()) {
                                Log.d("teamTask", "Snapshot 2" + dataSnapshot2.getValue());
                                Log.d("teamTask", "Key value is " + dataSnapshot2.getKey());
                                if (dataSnapshot2.getValue().equals("Uncompleted")) {
                                    Log.d("teamTask","The value is " + dataSnapshot1.getValue());
//                                    TeamTaskHolder task = dataSnapshot1.(TeamTaskHolder.class);
                                    TeamTaskHolder task = new TeamTaskHolder(dataSnapshot1);
                                    taskLists.add(task);
                                }
                            }
                        }
                        adapter1 = new TeamTaskAdapter(getContext(),taskLists);
                        recycleView.setAdapter(adapter1);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getContext(),"Retrieving error",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getContext(),"Error",Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }
}
