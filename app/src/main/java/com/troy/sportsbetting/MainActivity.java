package com.troy.sportsbetting;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }


    public void BtnBetClick(View view) {

        startActivity(new Intent(MainActivity.this, BetActivity.class));

    }

    public void BtnStatsClick(View view) {
        startActivity(new Intent(MainActivity.this, StatsActivity.class));
    }
}