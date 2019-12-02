package com.example.twotablesinsqlite;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> departmentArrayList;
    private ArrayList<String> parentArrayList;


    private ArrayAdapter<String> arrayAdapter;

    private ArrayList<String> logoList;

    ImageView imageView;

    DepartmentAdapter mAdapter;



    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        GridView gv = findViewById(R.id.department_grid_view);


        departmentArrayList = new ArrayList<String>();
        parentArrayList=new ArrayList<String>();


        logoList = new ArrayList<String>();

        imageView = (ImageView) findViewById(R.id.grid_item_image);


        DatabaseHelper db = new DatabaseHelper(MainActivity.this);

        final List<Parent> parentList =db.getAllParent();
        Log.d("Department","ALL"+ parentList);
        for (Parent parent: parentList) {
            departmentArrayList.add(parent.getParentName());
            logoList.add(parent.getPlogo());

        }
        mAdapter=new DepartmentAdapter();
        gv.setAdapter(mAdapter);

        // *********
        //ADD DATA FROM SQLITE TO ARRAYLIST
        //**********
//        final List<Department> departmentList =db.getAllDepartment();
//        Log.d("Department","ALL"+ departmentList);
//        for (Department department: departmentList) {
//            departmentArrayList.add(department.getDeptName());
//            logoList.add(department.getLogo());
//
//        }
//        mAdapter=new DepartmentAdapter();
//        gv.setAdapter(mAdapter);


//        ***********
//        DEPARTMENT LISTVIEW ONCLICK LISTENER
//        ***********
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent deptIntent=new Intent(MainActivity.this,ContactListActivity.class);
                Intent deptIntent=new Intent(MainActivity.this,DeparmentList.class);
                deptIntent.putExtra("id",parentList.get(position).pid);
                startActivity(deptIntent);
            }
        });

    }



    public void getParentAll() throws Exception {
        progressBar();
        Ion.with(this)
//                .load("http://164.100.124.140:8080/MizoramDirectory/api/departments/")
                .load("http://10.180.243.8:8080/parent/all/")
                .as(new TypeToken<List<Parent>>() {
                })
                .setCallback(new FutureCallback<List<Parent>>() {
                    @Override
                    public void onCompleted(Exception e, final List<Parent> parents) {
                        Parent par;

                        DatabaseHelper db = new DatabaseHelper(MainActivity.this);
                        db.deleteAll();

                        Log.d("SIZE","------xxxxxxx" + parents.size());
                        for (int i = 0; i < parents.size(); i++) {
                            par = new Parent();
                            par.setPid(parents.get(i).pid);
                            Log.d("VALUES","---------------------->>>>>>" +par.pid);
                            par.setParentName(parents.get(i).parentName);
                            Log.d("VALUES","---------------------->>>>>>" +par.parentName);
                            par.setPlogo(parents.get(i).plogo);
                            Log.d("VALUES","---------------------->>>>>>" +par.plogo);

                            Log.d("Cat","ON CREATE "+ db);
                            db.createParent(par);


                        }


                        List<Parent> parentList =db.getAllParent();
                        for (Parent parent: parentList){
                            Log.d("MAIN","ON CREATE "+ parent.getParentName());
                            Log.d("LOGO","ON CREATE "+ parent.getPlogo());
                            parentArrayList.add(parent.getParentName());
                            Log.d("PARENT","LIST-------------------" + parentArrayList);

                        }
                        try {
                            getDepartments();
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                        recreate();
                    }

                });


    }

    public void getDepartments() throws Exception {
        progressBar();
        Ion.with(this)
//                .load("http://164.100.124.140:8080/MizoramDirectory/api/departments/")
                .load("http://10.180.243.8:8080/api/departments/")
                .as(new TypeToken<List<Department>>() {
                })
                .setCallback(new FutureCallback<List<Department>>() {
                    @Override
                    public void onCompleted(Exception e, final List<Department> departments) {
                        Department dept;
                        //int Did;
                        DatabaseHelper db = new DatabaseHelper(MainActivity.this);

                        Log.d("Department", "Test->>>>" + departments.get(0).getDeptName());
                        for (int i = 0; i < departments.size(); i++) {
                            dept = new Department();
                            dept.setId(departments.get(i).id);
                            dept.setDeptName(departments.get(i).deptName);
                            dept.setLogo(departments.get(i).logo);

                            Log.d("DB","TESTUUUUU"+ db);
                            db.createDepartment(dept);
                            Log.d("DB","TESTUUUUU"+ db);

                        }


                        List<Department> departmentList =db.getAllDepartment();
                        Log.d("DEpartment", "DETAILS    ............." + departmentList);
                        for (Department department: departmentList){
                            //departmentArrayList.add(department.getDeptName());
                            Log.d("Dept","TESTUUUUU"+ department);

                            try {
                                getContacts(String.valueOf(department.getId()));
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                        }

                        //recreate();
                    }
                });

    }


    public void getContacts(final String Sid) throws Exception {
        //progressBar();
        Ion.with(this)
//                .load("http://164.100.124.140:8080/MizoramDirectory/api/departments/" + Sid)
                .load("http://10.180.243.8:8080/api/departments/" + Sid)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        Log.d("inside", "ON CREATE " + result);
                        int id = Integer.parseInt(Sid);
                        Log.i("re","RESULT of Dept Name: "+result.get("deptName"));

                        Contacts contacts;
                        DatabaseHelper db=new DatabaseHelper(MainActivity.this);
                        try {
                            ArrayList<String> empName;
                            ArrayList<String> designation;
                            ArrayList<String> mobile;
                            ArrayList<String> landlineOffice;
                            ArrayList<String> landlineRes;
                            ArrayList<String> fax;
                            ArrayList<String> email;
                            ArrayList<String> contactId;
                            Contacts contact;


                            empName=new ArrayList<>();
                            designation=new ArrayList<>();
                            mobile=new ArrayList<>();
                            landlineOffice=new ArrayList<>();
                            landlineRes=new ArrayList<>();
                            fax=new ArrayList<>();
                            email=new ArrayList<>();
                            contactId=new ArrayList<>();



                            String resultInString = result.toString();
                            JSONObject jsonObject = new JSONObject(String.valueOf(resultInString));

                            JSONArray array = jsonObject.getJSONArray("contacts");

                            for(int i =0;i<array.length();i++) {

                                contact=new Contacts();

                                JSONObject object = array.getJSONObject(i);

                                String tempContactId=object.getString("contactId");
                                String tempName = object.getString("empName");
                                String tempDesignation = object.getString("designation");
                                String tempMobile=object.getString("mobile");

                                String tempLandlineOffice = object.getString("landlineOffice");
                                String tempLandlineRes = object.getString("landlineRes");
                                String tempFax=object.getString("fax");
                                String tempEmial = object.getString("email");

                                contact.setContactId(Integer.parseInt(tempContactId));
                                contact.setEmpName(tempName);
                                contact.setDesignation(tempDesignation);
                                contact.setMobile(tempMobile);
                                contact.setLandlineOffice(tempLandlineOffice);
                                contact.setLandlineRes(tempLandlineRes);
                                contact.setFax(tempFax);
                                contact.setEmail(tempEmial);
                                contact.setDept_id(Sid);
                                db.createContact(contact);
                                //Log.i("CON","TESTING AAAA "+contact);
                            }


                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }


                    }

                });
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_update:
                /* DO EDIT */
                try {
                    if (isOnline()) {
                        //getDepartments();
                        getParentAll();

                    } else {
                        Toast.makeText(MainActivity.this, "You are not connected to Internet", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
        return super.onOptionsItemSelected(item);
    }

    class DepartmentAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return departmentArrayList.size();
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

            convertView=getLayoutInflater().inflate(R.layout.department_model,null);
            ImageView mImageView = convertView.findViewById(R.id.grid_item_image);

            TextView textView=(TextView)convertView.findViewById(R.id.deptNameTv);
            System.out.println("KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK"+logoList.get(position));

           // Bitmap bm = StringToBitMap(logoList.get(position)+ ".png");
            mImageView.setImageBitmap(StringToBitMap(logoList.get(position)));

//            textView.setText(departmentArrayList.get(position));
            textView.setText(departmentArrayList.get(position));
            System.out.println("iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
            return convertView;

        }
    }


    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }



    public void progressBar(){

        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Downloading...");
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
        }).start();

            progressDialog.dismiss();


        //progressDialog.show();

    }


    @Override
    protected void onDestroy() {

        progressDialog.dismiss();

        super.onDestroy();
    }


    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }


}

