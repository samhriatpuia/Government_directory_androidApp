package com.example.twotablesinsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;


    // Database Name
    private static final String DATABASE_NAME = "R2Ddatabase.db";

    // Table Names
    private static final String TABLE_DEPARTMENT = "department";
    private static final String TABLE_CONTACT = "contacts";
    private static final String TABLE_PARENT="parent";


    public static final String KEY_ID="_id";
    public static final String KEY_DEPTNAME="deptName";
    public static final String KEY_HOME="home";
    public static final String KEY_LOGO="logo";


    public static final String KEY_CONTACTID="contactId";
    public static final String KEY_EMPNAME="empName";
    public static final String KEY_DESIGANTION="designation";
    public static final String KEY_MOBILE="mobile";
    public static final String KEY_LANDLINEOFFICE="landlineOffice";
    public static final String KEY_LANDLINERES="landlineRes";
    public static final String KEY_FAX="fax";
    public static final String KEY_EMAIL="email";
    public static final String KEY_DEPTID="dept_id";


    public static final String KEY_PID="id";
    public static final String KEY_PARENTNAME="parentName";
    public static final String KEY_PLOGO="logo";

    public static final String CREATE_DEPARTMENT_TABLE="CREATE TABLE "
            + TABLE_DEPARTMENT + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_DEPTNAME + " TEXT,"
            + KEY_HOME + " TEXT,"
            + KEY_LOGO + " TEXT"
             + ")";

    public static final String CREATE_TABLE_CONTACTS="CREATE TABLE "
            + TABLE_CONTACT + "(" + KEY_CONTACTID + " INTEGER,"
            + KEY_EMPNAME + " TEXT,"
            + KEY_DESIGANTION +" TEXT,"
            + KEY_MOBILE + " TEXT,"
            + KEY_LANDLINEOFFICE + " TEXT,"
            + KEY_LANDLINERES + " TEXT,"
            + KEY_FAX + " TEXT,"
            + KEY_EMAIL+ " TEXT,"
            + KEY_DEPTID + " TEXT" + ")";


    public static final String CREATE_PARENT_TABLE="CREATE TABLE "
            + TABLE_PARENT + "("
            + KEY_PID + " INTEGER PRIMARY KEY,"
            + KEY_PARENTNAME + " TEXT,"
            + KEY_PLOGO + " TEXT"
            + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DEPARTMENT_TABLE);
        db.execSQL(CREATE_TABLE_CONTACTS);
        db.execSQL(CREATE_PARENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEPARTMENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARENT);

        onCreate(db);
    }



    public void createDepartment(Department dept){

        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(KEY_ID,dept.getId());
        values.put(KEY_DEPTNAME,dept.getDeptName());
        values.put(KEY_HOME,dept.getHome());
        values.put(KEY_LOGO,dept.getLogo());



        db.insert(TABLE_DEPARTMENT,null,values);
        db.close();
    }

    public void createParent(Parent parent){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(KEY_PID,parent.getPid());
        values.put(KEY_PARENTNAME,parent.getParentName());
        values.put(KEY_PLOGO,parent.getPlogo());

        db.insert(TABLE_PARENT,null,values);
        db.close();
    }


    public Department getDepartment(int id){
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor=db.query(TABLE_DEPARTMENT,
                new String[]{KEY_ID,KEY_DEPTNAME,KEY_LOGO},
                KEY_ID+"=?",new String[]{String.valueOf(id)},
                null,null,null);

        if (cursor!=null)
            cursor.moveToFirst();

        Department department=new Department();
        department.setId((Integer.parseInt(cursor.getString(0))));
        department.setDeptName((cursor.getString(1)));
        department.setHome((cursor.getString(2)));
        department.setLogo((cursor.getString(3)));

//        cursor.close();
        return department;
    }


    public Parent getParent(int id){
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor=db.query(TABLE_PARENT,
                new String[]{KEY_PID,KEY_PARENTNAME,KEY_PLOGO},
                KEY_PID+"=?",new String[]{String.valueOf(id)},
                null,null,null);

        if (cursor!=null)
            cursor.moveToFirst();

        Parent parent=new Parent();
        parent.setPid(Integer.parseInt(cursor.getString(0)));
        parent.setParentName(cursor.getString(1));
        parent.setPlogo(cursor.getString(2));

//        cursor.close();
        return parent;
    }


    public List<Department> getAllDepartment(){
        List<Department> departmentList=new ArrayList<>();

        SQLiteDatabase db=this.getReadableDatabase();

        String selectAll="SELECT * FROM " + TABLE_DEPARTMENT;
        Cursor cursor=db.rawQuery(selectAll,null);
        if (cursor.moveToFirst()){
            do {
                Department department=new Department();
                department.setId(Integer.parseInt(cursor.getString(0)));
                department.setDeptName(cursor.getString(1));
                department.setHome(cursor.getString(2));
                department.setLogo(cursor.getString(3));

                departmentList.add(department);
            }while(cursor.moveToNext());
        }
        return  departmentList;
    }

    public List<Parent> getAllParent(){
        List<Parent> parentList=new ArrayList<>();

        SQLiteDatabase db=this.getReadableDatabase();

        String selectAll="SELECT * FROM " + TABLE_PARENT;
        Cursor cursor=db.rawQuery(selectAll,null);
        if (cursor.moveToFirst()){
            do {
                Parent parent=new Parent();
                parent.setPid(Integer.parseInt(cursor.getString(0)));
                parent.setParentName(cursor.getString(1));
                parent.setPlogo(cursor.getString(2));

                parentList.add(parent);
            }while(cursor.moveToNext());
        }
        return  parentList;
    }

    //creating contact
    public void createContact(Contacts contacts){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values=new ContentValues();

        values.put(KEY_CONTACTID,contacts.getContactId());
        values.put(KEY_EMPNAME,contacts.getEmpName());
        values.put(KEY_DESIGANTION,contacts.getDesignation());
        values.put(KEY_MOBILE,contacts.getMobile());
        values.put(KEY_LANDLINEOFFICE,contacts.getLandlineOffice());
        values.put(KEY_LANDLINERES,contacts.getLandlineRes());
        values.put(KEY_FAX,contacts.getFax());
        values.put(KEY_EMAIL,contacts.getEmail());
        values.put(KEY_DEPTID,contacts.getDept_id());

        db.insert(TABLE_CONTACT,null,values);
        db.close();
    }

    public Contacts getContact(int id){
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor=db.query(TABLE_CONTACT, new String[]{KEY_CONTACTID,KEY_EMPNAME,KEY_DESIGANTION},
                KEY_CONTACTID+"=?", new String[]{String.valueOf(id)},
                null,null,null);

        if (cursor!=null)
            cursor.moveToFirst();

        Contacts contacts=new Contacts();
        contacts.setContactId((Integer.parseInt(cursor.getString(0))));
        contacts.setEmpName(cursor.getString(1));
        contacts.setDesignation(cursor.getString(2));
        contacts.setMobile(cursor.getString(3));
        contacts.setLandlineOffice(cursor.getString(4));
        contacts.setLandlineRes(cursor.getString(5));
        contacts.setFax(cursor.getString(6));
        contacts.setEmail(cursor.getString(7));
        contacts.setDept_id(cursor.getString(8));

//        cursor.close();
        return contacts;
    }

    //get all Contact
    public  List<Contacts> getAllContacts(int theId){

        List<Contacts> contactsList=new ArrayList<>();

        SQLiteDatabase db=this.getReadableDatabase();

        String selectAll="SELECT * FROM " +TABLE_CONTACT + " WHERE "+ KEY_DEPTID +" =" + theId;

        Cursor cursor=db.rawQuery(selectAll,null);

        if (cursor.moveToFirst()){
            do {
                Contacts contacts=new Contacts();
                contacts.setContactId(Integer.parseInt(cursor.getString(0)));
                contacts.setEmpName(cursor.getString(1));
                contacts.setDesignation(cursor.getString(2));
                contacts.setMobile(cursor.getString(3));
                contacts.setLandlineOffice(cursor.getString(4));
                contacts.setLandlineRes(cursor.getString(5));
                contacts.setFax(cursor.getString(6));
                contacts.setEmail(cursor.getString(7));
                contacts.setDept_id(cursor.getString(8));

                contactsList.add(contacts);

            }while(cursor.moveToNext());
        }

        return contactsList;
    }








    public  void deleteAll(){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_DEPARTMENT);
        db.execSQL("DELETE FROM " + TABLE_CONTACT);
        db.execSQL("DELETE FROM " + TABLE_PARENT);
    }
}
