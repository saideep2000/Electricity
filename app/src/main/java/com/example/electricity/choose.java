package com.example.electricity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class choose extends AppCompatActivity {

    TextView run;
    Button cus,ele;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        run = (TextView) findViewById(R.id.run);
        run.setSelected(true);

        cus = (Button) findViewById(R.id.cus);
        ele = (Button) findViewById(R.id.ele);

        SharedPreferences sh = getSharedPreferences("Myprefs", MODE_PRIVATE);
        SharedPreferences ls = getSharedPreferences("dataremeber", MODE_PRIVATE);
        SharedPreferences ps = getSharedPreferences("rem", MODE_PRIVATE);

        boolean log = sh.getBoolean("login", false);
        String blog = ls.getString("user", "");
        String vlog = ps.getString("cat", "");

        if (vlog.equals("ele") & !blog.equals("") & log) {

            Intent i = new Intent(getApplicationContext(), Afterlogin.class);
            startActivity(i);
            finish();

        }
        else if(vlog.equals("cus") & !blog.equals("") & log)
        {
            Intent i = new Intent(getApplicationContext(), Afterlogin2.class);
            startActivity(i);
            finish();
        }

        cus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), " Logging in as Customer", Toast.LENGTH_LONG).show();

                SharedPreferences ps = getSharedPreferences("rem", MODE_PRIVATE);
                SharedPreferences.Editor editor = ps.edit();
                editor.putString("cat", "cus");
                editor.commit();

                Intent i = new Intent(getApplicationContext(), Login.class);
                startActivity(i);
                finish();
            }
        });
        ele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), " Logging in as Officer", Toast.LENGTH_LONG).show();

                SharedPreferences ps = getSharedPreferences("rem", MODE_PRIVATE);
                SharedPreferences.Editor editor = ps.edit();
                editor.putString("cat", "ele");
                editor.commit();

                Intent i = new Intent(getApplicationContext(), Login.class);
                startActivity(i);
                finish();
            }
        });

    }
}