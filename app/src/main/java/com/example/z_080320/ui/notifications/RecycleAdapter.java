package com.example.z_080320.ui.notifications;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.z_080320.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder3> {
    Context ctx;
    ArrayList<RecycleHolder> recycleHolderArrayList;

    public RecycleAdapter(Context ctx,ArrayList<RecycleHolder> recycleHolders){
        this.ctx = ctx;
        this.recycleHolderArrayList = recycleHolders;
    }

    @NonNull
    @Override
    public MyViewHolder3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.recyclebin_recycler,parent,false);
        RecycleAdapter.MyViewHolder3 holder = new RecycleAdapter.MyViewHolder3(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleAdapter.MyViewHolder3 holder, int position) {
        final RecycleHolder list = recycleHolderArrayList.get(position);
        holder.date.setText(list.getTaskDate());
        holder.name.setText(list.getTaskName());

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        final DatabaseReference updateData = FirebaseDatabase.getInstance().getReference("task").child(currentFirebaseUser.getUid()).child(list.getKeyValue());
        holder.undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData.child("TaskDeletion").setValue("Undeleted");
            }
        });

        holder.permDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData.removeValue();
            }
        });
    }

    @Override
    public int getItemCount() {
        return recycleHolderArrayList.size();
    }

    public class MyViewHolder3 extends RecyclerView.ViewHolder{
        TextView date,name;
        Button undo,permDel;

        public MyViewHolder3(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.DateRecycle);
            name = itemView.findViewById(R.id.NameRecycle);
            undo = itemView.findViewById(R.id.unDeleteBtn);
            permDel = itemView.findViewById(R.id.deletePermBtn);
        }
    }
}
