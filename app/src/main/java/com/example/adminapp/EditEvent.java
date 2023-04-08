package com.example.adminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class EditEvent extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    Button update, reset;
    EditText updateName, updateDept, updateFaculty, updateVenue, updateDate, updateTime, updatePoints;
    DatabaseReference myRef;
    FirebaseDatabase database;
    List<Event> events;
    String name;
    String dept;
    String faculty;
    String venue;
    String date;
    String time;
    String points;
    String key ="";

    boolean isAllFieldsChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        update = findViewById(R.id.update_btn);
        reset = findViewById(R.id.reset_btn2);
        updateName = findViewById(R.id.editEventName_et);
        updateDept = findViewById(R.id.editDept_et);
        updateFaculty = findViewById(R.id.editFaculty_et);
        updateVenue = findViewById(R.id.editVenue_et);
        updateDate = findViewById(R.id.datePickerButtonUpdate);
        updateTime = findViewById(R.id.timePickerButtonUpdate);
        updatePoints = findViewById(R.id.editPoints_et);

        initDatePicker();
        updateDate.setText(getTodaysDate());

        updateDate.setInputType(InputType.TYPE_NULL);
        updateTime.setInputType(InputType.TYPE_NULL);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            updateName.setText(bundle.getString("name"));
            updateDept.setText(bundle.getString("department"));
            updateFaculty.setText(bundle.getString("faculty"));
            updateVenue.setText(bundle.getString("venue"));
            updateDate.setText(bundle.getString("date"));
            updateTime.setText(bundle.getString("time"));
            updatePoints.setText(bundle.getString("points"));
            key = (String) bundle.get("key");
        }

        myRef = FirebaseDatabase.getInstance().getReference("events").child(key);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isAllFieldsChecked = CheckAllFields();

                if (isAllFieldsChecked) {
                    updateData();
                    Intent intent = new Intent(EditEvent.this, ViewEvents.class);
                    startActivity(intent);
                }

            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateName.getText().clear();
                updateDept.getText().clear();
                updateTime.setText("00:00");
                updateFaculty.getText().clear();
                updatePoints.getText().clear();
                updateDate.setText(getTodaysDate());
                updateVenue.getText().clear();
            }
        });

        updateDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDatePicker(view);
            }
        });

        updateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePicker();
            }
        });
    }
    public void updateData(){
        name = updateName.getText().toString().trim();
        dept = updateDept.getText().toString().trim();
        time = updateTime.getText().toString().trim();
        faculty = updateFaculty.getText().toString().trim();
        points = updatePoints.getText().toString().trim();
        date = updateDate.getText().toString().trim();
        venue = updateVenue.getText().toString().trim();

        HashMap<String, Object> insert = new HashMap<>();
        insert.put("name", name);
        insert.put("date", date);
        insert.put("time", time);
        insert.put("department", dept);
        insert.put("faculty", faculty);
        insert.put("venue",venue);
        insert.put("points", points);

        database = FirebaseDatabase.getInstance("https://eccloginmoduletest-default-rtdb.asia-southeast1.firebasedatabase.app/");
        database.getReference("events").child(key)
                .setValue(insert)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(EditEvent.this, "Data updated successfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(EditEvent.this, ViewEvents.class);
                        startActivity(i);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditEvent.this, "Error "+e, Toast.LENGTH_SHORT).show();
                    }
                });

    }

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
                updateDate.setText(date);
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
                updateTime.setText(String.valueOf(hour)+ ":" +String.valueOf(minute));

            }
        }, 15, 30, false);

        timePickerDialog.show();
    }

    private boolean CheckAllFields() {
        if (updateName.length() == 0) {
            updateName.setError("This field is required");
            return false;
        }

        if (updateDept.length() == 0) {
            updateDept.setError("This field is required");
            return false;
        }

        if (updateFaculty.length() == 0) {
            updateFaculty.setError("This field is required");
            return false;
        }

        if (updateVenue.length() == 0) {
            updateVenue.setError("This field is required");
            return false;
        }

        if (updateDate.length() == 0) {
            updateDate.setError("This field is required");
            return false;
        }

        if (updateTime.length() == 0) {
            updateTime.setError("This field is required");
            return false;
        }

        if (updatePoints.length() == 0) {
            updatePoints.setError("This field is required");
            return false;
        }

        return true;
    }

}