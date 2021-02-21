package com.example.electricity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Registerform extends AppCompatActivity {

    EditText usernamein;
    EditText passwordin;
    EditText conpasswordin;

    TextView already;

    Button register;
    DatabaseReference myRef,myReff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerform);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}


        usernamein = (EditText) findViewById(R.id.editText);
        passwordin = (EditText) findViewById(R.id.editText2);
        conpasswordin = (EditText) findViewById(R.id.editText3);
        register = (Button)findViewById(R.id.button);

        already = (TextView) findViewById(R.id.register);
        already.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Login.class);
                startActivity(i);
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String u = usernamein.getText().toString().trim();
                String p = passwordin.getText().toString().trim();
                String c = conpasswordin.getText().toString().trim();

                if (u.equals("") || p.equals("") || c.equals("")) {
                    Toast.makeText(getApplicationContext(), "Fill all details", Toast.LENGTH_LONG).show();
                } else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    myRef = database.getReference();
                    myRef = myRef.child("users");

                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String u = usernamein.getText().toString().trim();
                            String p = passwordin.getText().toString().trim();
                            String c = conpasswordin.getText().toString().trim();

                            if(!dataSnapshot.hasChild(u))
                            {
                                if (p.equals(c)) {
                                    myReff = database.getReference();
                                    myReff.child("users").child(u).child("password").setValue(p);

                                    Toast.makeText(getApplicationContext(), "Registered Successfully ..!! ", Toast.LENGTH_LONG).show();

                                    Intent i = new Intent(getApplicationContext(), Login.class);
                                    startActivity(i);
                                    finish();
                                } else {
                                    passwordin.setText("");
                                    conpasswordin.setText("");
                                    Toast.makeText(getApplicationContext(), "Not matched try again..!", Toast.LENGTH_LONG).show();
                                }
                            }
                            else{
                                Toast.makeText(getApplicationContext(), " already taken", Toast.LENGTH_SHORT).show();
                                usernamein.setText("");
                                passwordin.setText("");
                                conpasswordin.setText("");

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(getApplicationContext(), "Failed try again...sorry", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }
}
