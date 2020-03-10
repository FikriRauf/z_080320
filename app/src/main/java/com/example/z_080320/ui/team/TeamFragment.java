package com.example.z_080320.ui.team;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

public class TeamFragment extends Fragment {
    DatabaseReference mDatabase;
    RecyclerView recyclerView;
    TeamAdapter adapter;

    int x;
    ArrayList<TeamHolder> teamHolderArrayList;
    String email;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_team, container, false);

        NavigationActivity plan = new NavigationActivity();
        plan.setFragmentView(1);
        plan.addFab();

        recyclerView = root.findViewById(R.id.TeamRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mDatabase = FirebaseDatabase.getInstance().getReference().child("team");
        teamHolderArrayList = new ArrayList<TeamHolder>();
        email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!teamHolderArrayList.isEmpty()){
                    teamHolderArrayList.clear();
                }
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Log.d("team","child is " + dataSnapshot.getChildren());
                    for(DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()){
                        if(dataSnapshot2.getKey().equals("allUsers")){
                            Log.d("team","datSnapshot 2 " + dataSnapshot2.getValue());
                            String allUser = dataSnapshot2.getValue().toString();
                            String[] afterUser = allUser.split(",",0);
                            for(int i = 0; i < afterUser.length;i++){
                                if(afterUser[i].equals(email)){
                                    Log.d("team","User is found");
                                    TeamHolder team =  dataSnapshot1.getValue(TeamHolder.class);
                                    teamHolderArrayList.add(team);
                                    Log.d("team", "onDataChange: " + dataSnapshot1.getValue());
                                }
                            }
                        }
                    }
                }
                adapter = new TeamAdapter(getContext(),teamHolderArrayList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(),"Error in viewing teams",Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }
}


