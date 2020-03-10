package com.example.z_080320.ui.dashboard;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.z_080320.R;

import java.util.ArrayList;

public class TeamTaskAdapter extends RecyclerView.Adapter<TeamTaskAdapter.viewHolder2> {
    Context mmCtx;
    ArrayList<TeamTaskHolder> teamTaskHolderArrayList;

    public TeamTaskAdapter(Context mmCtx, ArrayList<TeamTaskHolder> taskTeam){
        this.mmCtx = mmCtx;
        this.teamTaskHolderArrayList = taskTeam;
    }

    @NonNull
    @Override
    public viewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mmCtx);
        View view = inflater.inflate(R.layout.team_task_recycler,parent,false);
        TeamTaskAdapter.viewHolder2 holder = new TeamTaskAdapter.viewHolder2(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TeamTaskAdapter.viewHolder2 holder, int position) {
        TeamTaskHolder team = teamTaskHolderArrayList.get(position);
        holder.date.setText(team.getTeamTaskDate());
        holder.name.setText(team.getTeamTaskName());
        Log.d("textSize","Name is " + team.getTeamTaskName());
        Log.d("textSize","Date is " + team.getTeamTaskName());
    }

    @Override
    public int getItemCount() {
        Log.d("itemSize","holderArray is " + teamTaskHolderArrayList.size());
        return teamTaskHolderArrayList.size();
    }

    public class viewHolder2 extends RecyclerView.ViewHolder{
        TextView date, name;

        public viewHolder2(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.teamTaskDate);
            name = itemView.findViewById(R.id.teamTaskName);
        }
    }
}
