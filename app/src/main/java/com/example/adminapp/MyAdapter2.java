package com.example.adminapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter2 extends RecyclerView.Adapter<MyViewHolder2>{

    Context context;
    List<Event> events;

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
        holder.faculty.setText((events.get(position).getFaculty()));
        holder.points.setText((events.get(position).getPoints()));
    }



    @Override
    public int getItemCount() {
        return events.size();
    }


}