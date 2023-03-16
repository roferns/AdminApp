package com.example.adminapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder2 extends RecyclerView.ViewHolder{
    TextView name, department, faculty, date, time, points;

    public MyViewHolder2(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.eventView_tv);
        department = itemView.findViewById(R.id.deptView_tv);
        faculty = itemView.findViewById(R.id.facultyView_tv);
        date = itemView.findViewById(R.id.dateView_tv);
        time = itemView.findViewById(R.id.timeView_tv);
        points = itemView.findViewById(R.id.pointsView_tv);
    }
}