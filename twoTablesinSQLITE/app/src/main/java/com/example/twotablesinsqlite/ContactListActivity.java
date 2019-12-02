package com.example.twotablesinsqlite;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ContactListActivity extends AppCompatActivity {

    private ArrayList<Integer> contactIdList;
    private ArrayList<String> empNameArrayList;
    private ArrayList<String> designationArrayList;
    private ArrayList<String> mobileArrayList;
    private ArrayList<String> landlineOfficerrayList;
    private ArrayList<String> landlineResArrayList;
    private ArrayList<String> faxArrayList;
    private ArrayList<String> emailArrayList;



    private ListView listView2;
    ContactsAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        //Toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        DatabaseHelper db=new DatabaseHelper(ContactListActivity.this);

        //listView2=findViewById(R.id.listview2);
        contactIdList=new ArrayList<Integer>();
        empNameArrayList=new ArrayList<String>();
        designationArrayList=new ArrayList<String>();
        mobileArrayList=new ArrayList<String>();
        landlineOfficerrayList=new ArrayList<String>();
        landlineResArrayList=new ArrayList<String>();
        faxArrayList=new ArrayList<String>();
        emailArrayList=new ArrayList<String>();

        listView2=findViewById(R.id.contactlv);


        Intent intent = getIntent();
        int dId = intent.getExtras().getInt("id");
        String Sid = new String(String.valueOf(dId));
        Context context = getApplicationContext();
        Toast.makeText(context, "" + dId, Toast.LENGTH_SHORT).show();

        final List<Contacts> contactsList =db.getAllContacts(dId);

        for (Contacts Contacts: contactsList){

            contactIdList.add(Contacts.getContactId());
            empNameArrayList.add(Contacts.getEmpName());
            designationArrayList.add(Contacts.getDesignation());
            mobileArrayList.add(Contacts.getMobile());
            landlineResArrayList.add(Contacts.getLandlineOffice());
            landlineOfficerrayList.add(Contacts.getLandlineRes());
            faxArrayList.add(Contacts.getFax());
            emailArrayList.add(Contacts.getEmail());

            mAdapter=new ContactsAdapter();
            listView2.setAdapter(mAdapter);
        }
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent contactIntent=new Intent(ContactListActivity.this,ContactDisplay.class);
                contactIntent.putExtra("contactId",contactIdList.get(position));
                contactIntent.putExtra("empName",empNameArrayList.get(position));
                contactIntent.putExtra("designation",designationArrayList.get(position));
                contactIntent.putExtra("mobile",mobileArrayList.get(position));
                contactIntent.putExtra("office",landlineOfficerrayList.get(position));
                contactIntent.putExtra("resi",landlineResArrayList.get(position));
                contactIntent.putExtra("fax",faxArrayList.get(position));
                contactIntent.putExtra("email",emailArrayList.get(position));
                startActivity(contactIntent);

            }
        });


    }

    class ContactsAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return empNameArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView=getLayoutInflater().inflate(R.layout.model,null);

            TextView textView1=(TextView)convertView.findViewById(R.id.empNametv);
            TextView textView2=(TextView)convertView.findViewById(R.id.designationtv);


            textView1.setText(empNameArrayList.get(position));
            textView2.setText(designationArrayList.get(position));

            return convertView;

        }
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
