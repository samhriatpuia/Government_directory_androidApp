package com.example.twotablesinsqlite;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class DeparmentList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deparment_list);

        Intent intent = getIntent();
        int dId = intent.getExtras().getInt("id");
        String Sid = new String(String.valueOf(dId));
        Context context = getApplicationContext();
        Toast.makeText(context, "" + dId, Toast.LENGTH_SHORT).show();
    }
}
