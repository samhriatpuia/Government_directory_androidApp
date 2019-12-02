package com.example.twotablesinsqlite;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ContactDisplay extends AppCompatActivity {

    TextView empNameTextView;
    TextView designationTextView;
    TextView mobileTextView;
    TextView officeTExtView;
    TextView resiTextView;
    TextView faxTextView;
    TextView emailTextView;

    ListView listView;
    int tempContactId;
    String tempEmpName;
    String tempDesignation;
    String tempMobile;
    String tempOffice;
    String tempRes;
    String tempFax;
    String tempEmail;
    //theContactAdapter madapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_display);

        //Toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        isPermissionGranted();
        Intent intent = getIntent();
        tempContactId = intent.getExtras().getInt("contactId");
        tempEmpName=intent.getExtras().getString("empName");
        tempDesignation=intent.getExtras().getString("designation");
        tempMobile=intent.getExtras().getString("mobile");
        tempOffice=intent.getExtras().getString("office");
        tempRes=intent.getExtras().getString("resi");
        tempFax=intent.getExtras().getString("fax");
        tempEmail=intent.getExtras().getString("email");


        empNameTextView=findViewById(R.id.empNametv);
        empNameTextView.setText(tempEmpName);

        designationTextView=findViewById(R.id.designationtv);
        designationTextView.setText(tempDesignation);

        empNameTextView=findViewById(R.id.empNametv);
        empNameTextView.setText(tempEmpName);

        mobileTextView=findViewById(R.id.mobiletv);
        mobileTextView.setPaintFlags((mobileTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG));
        mobileTextView.setText(tempMobile);



        officeTExtView=findViewById(R.id.officetv);
        officeTExtView.setPaintFlags((officeTExtView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG));
        officeTExtView.setText(tempOffice);

        resiTextView=findViewById(R.id.resitv);
        resiTextView.setPaintFlags((resiTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG));
        resiTextView.setText(tempRes);

        faxTextView=findViewById(R.id.faxtv);
        faxTextView.setText(tempFax);


        emailTextView=findViewById(R.id.emailtv);
        emailTextView.setText(tempEmail);


        Context context = getApplicationContext();
        Toast.makeText(context, "" + tempEmpName, Toast.LENGTH_SHORT).show();


        mobileTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tempMobile));
                startActivity(intent);

            }
        });


        officeTExtView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tempOffice));
                startActivity(intent);
            }
        });


        resiTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tempRes));
                startActivity(intent);
            }
        });



    }
    public  boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG","Permission is granted");
                return true;
            } else {

                Log.v("TAG","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG","Permission is granted");
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {

            case 1: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                  //  call_action();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    //TOOLBAR CLICK LISTENER
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

}
