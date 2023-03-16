package com.example.adminapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Home extends AppCompatActivity {

    Button view, create, classwise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        view = findViewById(R.id.viewEvent_btn);
        create = findViewById(R.id.createEvent_btn);
        classwise = findViewById(R.id.classwise_btn);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, ViewEvents.class);
                startActivity(i);
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, CreateForm.class);
                startActivity(i);
            }
        });

        classwise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, ClasswiseView.class);
                startActivity(i);
            }
        });
    }

//    public void showDialog(){
//        Dialog dialog = new Dialog(this);
//        dialog.setContentView(R.layout.event_dialog);
//
//        EditText eventNumber;
//        Button submit, cancel;
//
//        eventNumber = dialog.findViewById(R.id.eventdialog_et);
//        submit = dialog.findViewById(R.id.submitDialog_btn);
//        cancel = dialog.findViewById(R.id.cancelDialog_btn);
//
//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(getApplicationContext(), Create.class).putExtra("number", eventNumber.getText().toString());
//                startActivity(i);
//            }
//        });
//
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
//
//        dialog.show();
//    }
}