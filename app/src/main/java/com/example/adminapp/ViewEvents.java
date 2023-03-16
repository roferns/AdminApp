package com.example.adminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewEvents extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    DatabaseReference myRef;
    FirebaseDatabase database;
    List<Event> events = new ArrayList<Event>();
    RecyclerView recyclerView;

    int spinnerValue=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_events);

        recyclerView = findViewById(R.id.recyclerview2);

        Spinner dropdown = findViewById(R.id.spinner2);
        //String[] ViewString = new String[]{"Archived Events", "Today's Events", "Future Events"};
        String[] events2 = new String[]{"All Events"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, events2);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(this);

        database = FirebaseDatabase.getInstance("https://eccloginmoduletest-default-rtdb.asia-southeast1.firebasedatabase.app/");
        myRef = database.getReference("events");

    }

        public void updateRecycler2() {

            //this is used to read db once
            myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    }
                    //if sucessful then,
                    else {
                        HashMap value = (HashMap) task.getResult().getValue();

                        events.clear();  //clearing the array, basically the screen first using this
                        for (Object i : value.keySet()) {
                            events.add(new Event(((HashMap) value.get(i)).get("name") + "",
                                    ((HashMap) value.get(i)).get("department") + "",
                                    ((HashMap) value.get(i)).get("faculty") + "",
                                    ((HashMap) value.get(i)).get("points") + "",
                                    ((HashMap) value.get(i)).get("date")+"",
                                    ((HashMap) value.get(i)).get("time") + ""));

                        }
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recyclerView.setAdapter(new MyAdapter2(getApplicationContext(), events));
                    }
                }
            });
        }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            String choice=adapterView.getItemAtPosition(i).toString();
            if (choice=="All Events"){
                spinnerValue=100;
            }
//                spinnerValue=100;
//            if (choice=="Archived Events"){
//                spinnerValue=100;
//            }else if(choice=="Today's Events"){
//                spinnerValue=101;
//            }else if(choice=="Future Events"){
//                spinnerValue=102;
//            }
            updateRecycler2();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}