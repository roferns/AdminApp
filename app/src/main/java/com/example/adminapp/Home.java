package com.example.adminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Home extends BaseActivity {

    TextView accountTV;
    EditText volunteer;
    Button view, create, classwise, search;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    DatabaseReference myRef;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        view = findViewById(R.id.viewEvent_btn);
        create = findViewById(R.id.createEvent_btn);
        classwise = findViewById(R.id.classwise_btn);
        search = findViewById(R.id.searchVol);
        accountTV = findViewById(R.id.accountTV);
        volunteer = findViewById(R.id.searchName_et);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);


//        start working on sxc check here
        database = FirebaseDatabase.getInstance("https://eccloginmoduletest-default-rtdb.asia-southeast1.firebasedatabase.app/");
        myRef = database.getReference("users");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                HashMap value = (HashMap) snapshot.getValue();

//                inefficient method, change this later
                int flag = 0;
                for (Object s : value.keySet()) {
                    HashMap switchMap = (HashMap) value.get(s);
                    if (switchMap.containsValue(account.getEmail())) {
                        if ((Long) switchMap.get("admin") == 1) {
                            accountTV.setText("You are logged in using " + switchMap.get("email"));
                            flag = 1;
                        }
                    }
                }
                if (flag == 0) {
                    Toast.makeText(getApplicationContext(), "not a valid sxc acc", Toast.LENGTH_SHORT).show();
                    logOut();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("database error", "Failed to read value.", error.toException());
            }
        });

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

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),ResultPage.class);
                i.putExtra("uid",volunteer.getText()+"");
                startActivity(i);
            }
        });
    }

}

