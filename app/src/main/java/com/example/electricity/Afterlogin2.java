package com.example.electricity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Afterlogin2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}


        setContentView(R.layout.activity_afterlogin2);
        BottomNavigationView bottomnav = findViewById(R.id.bottom_navigation);
        bottomnav.setOnNavigationItemSelectedListener(navlis);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containerr,new account()).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navlis = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()){
                case R.id.home:
                    selectedFragment = new home();
                    break;
                case R.id.complaintt:
                    selectedFragment = new complaintt();
                    break;
                case R.id.step:
                    selectedFragment = new step();
                    break;
                case R.id.bill:
                    selectedFragment = new bill();
                    break;
                case R.id.account:
                    selectedFragment = new account();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containerr,selectedFragment).commit();

            return true;
        }
    };
}
