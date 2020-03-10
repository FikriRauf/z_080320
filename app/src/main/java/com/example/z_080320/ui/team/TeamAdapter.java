package com.example.z_080320.ui.team;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.z_080320.R;

import java.util.ArrayList;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.ViewHolder1> {
    Context mCtx;
    ArrayList<TeamHolder> teamHolderArrayList;
    View view;

    public TeamAdapter(Context mCtx, ArrayList<TeamHolder> task){
        this.mCtx = mCtx;
        this.teamHolderArrayList = task;
    }

    @NonNull
    @Override
    public ViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.team_recycler,parent,false);
        TeamAdapter.ViewHolder1 holder = new TeamAdapter.ViewHolder1(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TeamAdapter.ViewHolder1 holder, int position) {
        final TeamHolder team = teamHolderArrayList.get(position);
        Log.d("team","Team Name is " + team.getTeamName());
        holder.teamName.setText(team.getTeamName());

        holder.teamLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(mCtx);
                LayoutInflater inflater = (LayoutInflater) mCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.team_info_pop, null);
                mBuilder.setView(view);

                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                TextView name,user,leader;
                name = view.findViewById(R.id.teamNameTxt);
                user = view.findViewById(R.id.teamUsersTxt);
                leader = view.findViewById(R.id.teamLeaderTxt);

                name.setText(team.getTeamName());
                user.setText(team.getUsers());
                leader.setText(team.getTeamLeader());
            }
        });

    }

    @Override
    public int getItemCount() {
        return teamHolderArrayList.size();
    }

    public class ViewHolder1 extends RecyclerView.ViewHolder{
        TextView teamName, teamEdit;
        LinearLayout teamLayout;

        public ViewHolder1(@NonNull View itemView) {
            super(itemView);

            teamName = itemView.findViewById(R.id.TeamNameTxt);
            teamEdit = itemView.findViewById(R.id.TeamEditTxt);
            teamLayout = itemView.findViewById(R.id.teamLayout);
        }
    }
}
