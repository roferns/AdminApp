package com.example.adminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.invoke.WrongMethodTypeException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateForm extends BaseActivity {

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;

    EditText eventName, department, faculty, venue, pointsAlloted, date_, time;
    Button submit, reset;
    DatabaseReference myRef;
    FirebaseDatabase database;

    boolean isAllFieldsChecked = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_form);

        eventName = findViewById(R.id.eventName_et);
        department = findViewById(R.id.dept_et);
        faculty = findViewById(R.id.faculty_et);
        venue=findViewById(R.id.venue_et);
        pointsAlloted = findViewById(R.id.pointsAllotted_et);
        submit = findViewById(R.id.submit_btn);
        reset = findViewById(R.id.reset_btn);
        time = findViewById(R.id.timePickerButton);
        time.setInputType(InputType.TYPE_NULL);


        //date picker
        initDatePicker();
        date_ = findViewById(R.id.datePickerButton);
        date_.setText(getTodaysDate());
        date_.setInputType(InputType.TYPE_NULL);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isAllFieldsChecked = CheckAllFields();

                if (isAllFieldsChecked) {
                    insertData();
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventName.getText().clear();
                department.getText().clear();
                time.setText("00:00");
                faculty.getText().clear();
                pointsAlloted.getText().clear();
                date_.setText(getTodaysDate());
                venue.getText().clear();
                }
        });

        date_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDatePicker(view);
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePicker();
            }
        });
    }

    public void insertData(){

        HashMap<String, Object> insert = new HashMap<>();
//        insert.put("archived",0);
        insert.put("name", eventName.getText().toString());
        insert.put("date", date_.getText().toString());
        insert.put("time", time.getText().toString());
        insert.put("department", department.getText().toString());
        insert.put("faculty", faculty.getText().toString());
        insert.put("venue",venue.getText().toString());
        insert.put("points", pointsAlloted.getText().toString());

        database = FirebaseDatabase.getInstance("https://eccloginmoduletest-default-rtdb.asia-southeast1.firebasedatabase.app/");
        database.getReference().child("events").push()
                .setValue(insert)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(CreateForm.this, "Data inserted successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CreateForm.this, "Error "+e, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //date picker
    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                date_.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);


        datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private String makeDateString(int day, int month, int year)
    {
        return day + "-" + month + "-" + year;
    }
    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }

    //timepicker
    private void timePicker(){
        timePickerDialog = new TimePickerDialog(this,  new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {

                //Showing the picked value in the textView
                time.setText(String.valueOf(hour)+ ":" +String.valueOf(minute));

            }
        }, 15, 30, false);

        timePickerDialog.show();
    }

    private boolean CheckAllFields() {
        if (eventName.length() == 0) {
            eventName.setError("This field is required");
            return false;
        }

        if (department.length() == 0) {
            department.setError("This field is required");
            return false;
        }

        if (faculty.length() == 0) {
            faculty.setError("Email is required");
            return false;
        }

        if (venue.length() == 0) {
            venue.setError("Email is required");
            return false;
        }

        if (date_.length() == 0) {
            date_.setError("Email is required");
            return false;
        }

        if (time.length() == 0) {
            time.setError("Email is required");
            return false;
        }

        if (pointsAlloted.length() == 0) {
            pointsAlloted.setError("Email is required");
            return false;
        }

        return true;
    }




}