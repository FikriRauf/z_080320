package com.example.z_080320.ui.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.z_080320.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {

    Context mCtx;
    ArrayList<TaskHolder> taskHolderArrayList;

    public TaskAdapter(Context mCtx, ArrayList<TaskHolder> task){
        this.mCtx = mCtx;
        this.taskHolderArrayList = task;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.task_recycler,parent,false);
        TaskAdapter.MyViewHolder holder = new TaskAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.MyViewHolder holder, int position) {
        final TaskHolder list = taskHolderArrayList.get(position);

        holder.date.setText(list.getTaskDate());
        holder.name.setText(list.getTaskName());


        Log.d("adapter","The date is " + list.getTaskDate());
        Log.d("adapter","The Name is " + list.getTaskName());
        Log.d("adapter","The key is " + list.getKeyValue());
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        final DatabaseReference updateData = FirebaseDatabase.getInstance().getReference("task").child(currentFirebaseUser.getUid()).child(list.getKeyValue());
        holder.complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData.child("TaskCompletion").setValue("Completed");
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData.child("TaskDeletion").setValue("Deleted");
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d("arrSize","The size is " + taskHolderArrayList.size());
        return taskHolderArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView date, name;
        LinearLayout taskLayout;
        Button complete,delete;
        public MyViewHolder(@NonNull View itemView){
            super (itemView);

            date = itemView.findViewById(R.id.taskDateRecycle);
            name = itemView.findViewById(R.id.taskNameRecycle);
            taskLayout = itemView.findViewById(R.id.taskLayout);
            complete = itemView.findViewById(R.id.completeBtn);
            delete = itemView.findViewById(R.id.deleteBtn);
        }
    }
}
