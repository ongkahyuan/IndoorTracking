package com.example.indoortracking.tracking.map;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Display;
import android.view.View;
import android.widget.Button;

import com.example.indoortracking.R;

public class map extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Toolbar toolbar = findViewById(R.id.toolbar);

        Button mapNewLocation = findViewById(R.id.mapnewlocation);
        Button viewSavedLocation = findViewById(R.id.viewsavedlocations);

        mapNewLocation.setOnClickListener(this);
        viewSavedLocation.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mapnewlocation:
                NewLocation();
            case R.id.viewsavedlocations:
                viewLocation();
        }


    }

    private void viewLocation() {
        Intent intent2 = new Intent(this, DisplayMap.class);
        startActivity(intent2);
    }

    private void NewLocation() {
        Intent intent1 = new Intent(this, BeginMapping.class);
        startActivity(intent1);
    }
}
