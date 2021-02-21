package com.example.electricity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Afterlogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}


        setContentView(R.layout.activity_afterlogin);
        BottomNavigationView bottomnav = findViewById(R.id.bottom_navigation);
        bottomnav.setOnNavigationItemSelectedListener(navlis);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new account()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navlis = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()){
                case R.id.complaint:
                    selectedFragment = new complaint();
                    break;
                case R.id.timereport:
                    selectedFragment = new timereport();
                    break;
                case R.id.map:
                    selectedFragment = new map();
                    break;
                case R.id.feedback:
                    selectedFragment = new feedback();
                    break;
                case R.id.account:
                    selectedFragment = new account();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();

            return true;
        }
    };
}
