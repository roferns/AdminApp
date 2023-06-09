package com.example.adminapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Collections;
import java.util.List;

public class MyAdapter2 extends RecyclerView.Adapter<MyViewHolder2>{

    Context context;
    List<Event> events;
    DatabaseReference myRef;
    FirebaseDatabase database;


    public MyAdapter2(Context context, List<Event> events) {
        this.context = context;
        this.events = events;
    }

    public MyAdapter2() {

    }

    @NonNull
    @Override
    public MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder2(LayoutInflater.from(context).inflate(R.layout.event_view,parent,false));
    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder2 holder, int position) {
        holder.name.setText(events.get(position).getEventName());
        holder.department.setText(events.get(position).getDepartment());
        holder.date.setText((events.get(position).getDate()));
        holder.time.setText((events.get(position).getTime()));
        holder.venue.setText((events.get(position).getVenue()));
        holder.faculty.setText((events.get(position).getFaculty()));
        holder.points.setText((events.get(position).getPoints()));
        holder.deleteBtn.setOnClickListener(view -> {
            Log.d("indexx", events.get(position).getId()+"");
            database = FirebaseDatabase.getInstance("https://eccloginmoduletest-default-rtdb.asia-southeast1.firebasedatabase.app/");
            myRef = database.getReference("events");
            myRef.child(events.get(position).getId()).removeValue();
        });

        int pos = holder.getBindingAdapterPosition();

        holder.editBtn.setOnClickListener(view -> {
            Log.d("index1", events.get(position).getId()+"");
            database = FirebaseDatabase.getInstance("https://eccloginmoduletest-default-rtdb.asia-southeast1.firebasedatabase.app/");
            myRef = database.getReference("events");
            myRef.child(events.get(position).getId());
            Intent i = new Intent(context, EditEvent.class);
            i.putExtra("name", events.get(pos).getEventName());
            i.putExtra("department", events.get(pos).getDepartment());
            i.putExtra("venue", events.get(pos).getVenue());
            i.putExtra("faculty", events.get(pos).getFaculty());
            i.putExtra("points", events.get(pos).getPoints());
            i.putExtra("date", events.get(pos).getDate());
            i.putExtra("time", events.get(pos).getTime());
            i.putExtra("key", events.get(pos).getId());
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        });

        holder.arrow.setOnClickListener(view -> {
            if(events.get(pos).expanded){
                holder.cl.setVisibility(View.GONE);
                events.get(pos).expanded = false;
            }
            else {
                holder.cl.setVisibility(View.VISIBLE);
                events.get(pos).expanded = true;

            }
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }
}