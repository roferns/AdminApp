package com.example.adminapp;

import android.media.Image;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyViewHolder2 extends RecyclerView.ViewHolder{
    TextView name, department, venue, faculty, date, time, points;
    Button deleteBtn, editBtn;
    View cl, cl2;

    ImageView arrow;

    List<Event> events;

    public MyViewHolder2(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.nameTV);
        department = itemView.findViewById(R.id.deptNameTV);
        faculty = itemView.findViewById(R.id.facultyNameTV);
        venue=itemView.findViewById(R.id.venueNameTV);
        date = itemView.findViewById(R.id.dateTV);
        time = itemView.findViewById(R.id.timeTV);
        points = itemView.findViewById(R.id.pointsTV);
        deleteBtn=itemView.findViewById(R.id.deleteBtn);
        editBtn = itemView.findViewById(R.id.editBtn);
        cl = itemView.findViewById(R.id.cl);
        arrow = itemView.findViewById(R.id.imgbtn);
    }
}