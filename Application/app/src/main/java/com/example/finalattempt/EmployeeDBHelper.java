package com.example.finalattempt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDBHelper extends SQLiteOpenHelper {
    public static final String EMPLOYEES = "employees";
    public static final String EMAIL = "email";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "Password";
    public static final String USERID = "Userid";
    public static final String HOLIDAYREQUESTS = "HolidayRequests";
    public static final String REQUESTID ="RequestID";
    public static final String EMPLOYEENAME="EmployeeName";
    public static final String STATUS="Status";
    public static final String STARTDATE="StartDate";
    public static final String ENDDATE="EndDate";
    public static final  String NOTIFICATIONTYPE="Type";
    public static final String EMPLOYEENOTIFICATIONS = "EmployeeNotifications";
    public static final String NOTIFICATIONID = "NotificationID";
    public  EmployeeDBHelper(@Nullable Context context){
        super(context,"Employees.db",null,1);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createEmployeeTable =
            "CREATE TABLE " + EMPLOYEES + " ("+ USERID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+ EMAIL+ " TEXT, "+ USERNAME+ " TEXT, " + PASSWORD + " TEXT)";
        String createHolidayRequestsTable =
                "CREATE TABLE " + HOLIDAYREQUESTS+ " ("+ REQUESTID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+ USERID+" INTEGER, "+EMPLOYEENAME+" TEXT, "+ STATUS+" TEXT, "+STARTDATE+" DATE, "+ENDDATE+" DATE)";
        String CreateEmployeeNotificationTable =
                "CREATE TABLE " + EMPLOYEENOTIFICATIONS+ " ("+  NOTIFICATIONID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+USERID+" INTEGER, " +USERNAME+ " TEXT, " + NOTIFICATIONTYPE+ " TEXT)";



        db.execSQL(createEmployeeTable);
        db.execSQL(createHolidayRequestsTable);
        db.execSQL(CreateEmployeeNotificationTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public  void UpgradeNotificationsTable(SQLiteDatabase db){
        String Drop="DROP TABLE IF EXISTS " + EMPLOYEENOTIFICATIONS;
        String Create ="CREATE TABLE " + EMPLOYEENOTIFICATIONS+ " ("+  NOTIFICATIONID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+USERID+" INTEGER, " +USERNAME+ " TEXT, " + NOTIFICATIONTYPE+ " TEXT)";
        db.execSQL(Drop);
        db.execSQL(Create);
    }
    public void UpgradeRequestsTable(SQLiteDatabase db){
        String dropTable = "DROP TABLE IF EXISTS " + HOLIDAYREQUESTS;

        String createHolidayRequestsTable =
                "CREATE TABLE " + HOLIDAYREQUESTS+ " ("+ REQUESTID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+ USERID+" INTEGER, "+EMPLOYEENAME+" TEXT, "+ STATUS+" TEXT, "+STARTDATE+" DATE, "+ENDDATE+" DATE)";
        db.execSQL(createHolidayRequestsTable);

    }
    public List<HolidayRequestDataModel> getAllHolidayRequestsByID(int IDToGet){
        List<HolidayRequestDataModel> outputList = new ArrayList<>();

        SQLiteDatabase db= this.getReadableDatabase();
        String query = "SELECT * FROM " + HOLIDAYREQUESTS+ " WHERE "+ USERID+ " = "+IDToGet;

        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                int Requestid=cursor.getInt(0);
                int UserID=cursor.getInt(1);
                String Name= cursor.getString(2);
                String Status=cursor.getString(3);
                String StartDate=cursor.getString(4);
                String EndDate=cursor.getString(5);

                HolidayRequestDataModel dataModel = new HolidayRequestDataModel(Requestid,UserID,Name,Status,StartDate,EndDate);
                outputList.add(dataModel);
            }while(cursor.moveToNext());
        }else {

        }
        cursor.close();
        db.close();

        return  outputList;
    }
    public List<NotificationDataModel> getAllNotifications (){
        List<NotificationDataModel> outputList = new ArrayList<>();

        SQLiteDatabase db= this.getReadableDatabase();
        String query = "SELECT * FROM " + EMPLOYEENOTIFICATIONS;

        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                int id=cursor.getInt(0);
                int UserID=cursor.getInt(1);
                String  Uname=cursor.getString(2);
                String Type=cursor.getString(3);

                NotificationDataModel dataModel = new NotificationDataModel(id,UserID,Uname,Type);
                outputList.add(dataModel);
            }while(cursor.moveToNext());
        }else {

        }
        cursor.close();
        db.close();

        return  outputList;
    }
    public List<NotificationDataModel> getAllNotificationsForEmployee (int UserToGet){
        List<NotificationDataModel> outputList = new ArrayList<>();

        SQLiteDatabase db= this.getReadableDatabase();
        String query = "SELECT * FROM " + EMPLOYEENOTIFICATIONS +" WHERE "+USERID+ " = " + UserToGet;

        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                int id=cursor.getInt(0);
                int UserID=cursor.getInt(1);
                String  Uname=cursor.getString(2);
                String Type=cursor.getString(3);

                NotificationDataModel dataModel = new NotificationDataModel(id,UserID,Uname,Type);
                outputList.add(dataModel);
            }while(cursor.moveToNext());
        }else {

        }
        cursor.close();
        db.close();

        return  outputList;
    }
    public List<HolidayRequestDataModel> getAllHolidayRequests(){
        List<HolidayRequestDataModel> outputList = new ArrayList<>();

        SQLiteDatabase db= this.getReadableDatabase();
        String query = "SELECT * FROM " + HOLIDAYREQUESTS;

        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                int Requestid=cursor.getInt(0);
                int UserID=cursor.getInt(1);
                String Name= cursor.getString(2);
                String Status=cursor.getString(3);
                String StartDate=cursor.getString(4);
                String EndDate=cursor.getString(5);

                HolidayRequestDataModel dataModel = new HolidayRequestDataModel(Requestid,UserID,Name,Status,StartDate,EndDate);
                outputList.add(dataModel);
            }while(cursor.moveToNext());
        }else {

        }
        cursor.close();
        db.close();

        return  outputList;
    }

    public List<UserAccountDataModel> getAllEmployees(){
        List<UserAccountDataModel> outputList = new ArrayList<>();

        SQLiteDatabase db= this.getReadableDatabase();
        String query = "SELECT * FROM " + EMPLOYEES;

        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                int id=cursor.getInt(0);
                String email= cursor.getString(1);
                String UserName= cursor.getString(2);
                String PassWord= cursor.getString(3);

                UserAccountDataModel dataModel = new UserAccountDataModel(id,email,UserName,PassWord);
                outputList.add(dataModel);
            }while(cursor.moveToNext());
        }else {

        }
        cursor.close();
        db.close();

        return  outputList;
    }
    public boolean addHolidayRequest(HolidayRequestDataModel dataModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
       // cv.put(REQUESTID,dataModel.getRequestID());
        cv.put(USERID, dataModel.getEmployeeId());
        cv.put(EMPLOYEENAME, dataModel.getEmployeeName());
        cv.put(STATUS, dataModel.getStatus());
        cv.put(STARTDATE, dataModel.getStartDate());
        cv.put(ENDDATE,dataModel.getEndDate());
        long result = db.insert(HOLIDAYREQUESTS, null, cv);
        return result != -1;
    }
    public boolean addNotification(NotificationDataModel dataModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
      //  cv.put(NOTIFICATIONID, dataModel.getNotificationID());
        cv.put(USERID, dataModel.getUserID());
        cv.put(USERNAME, dataModel.getUName());
        cv.put(NOTIFICATIONTYPE,dataModel.getType());
        long result = db.insert(EMPLOYEENOTIFICATIONS, null, cv);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }
    public boolean adduser(UserAccountDataModel dataModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(USERID, dataModel.getUserId());
        cv.put(EMAIL, dataModel.getEmail());
        cv.put(USERNAME, dataModel.getUserName());
        cv.put(PASSWORD,dataModel.getPassWord());
        long result = db.insert(EMPLOYEES, null, cv);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }
    public String GetUserName(String FName,String LName){
        try{
            return FName.charAt(0)+LName;
        }catch (Exception e){
            return FName+LName;
        }

    }
    public void DeleteRemovedEmployees(Context context){
        List<UserAccountDataModel> Users= getAllEmployees();
        Log.d("Status ", "Deleting old employees");
        String Url="http://10.224.41.11/comp2000/employees";
        Gson gson=new Gson();
        int CurrentID=0;
        RequestQueue requestQueue=Volley.newRequestQueue(context);
        SQLiteDatabase db=this.getReadableDatabase();
        //Cursor cursor=db.raw ("SELECT EXISTS( SELECT 1 FROM " + EMPLOYEES +" WHERE "+ USERID+"="+CurrentID+");");


        StringRequest stringRequest =new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String SelectQuery;
                int CurrentID=0;
                Cursor cursor;
                boolean IDFound;
                Log.d("Status", "Opening employees");


                List<Person> EmployeeList = gson.fromJson(response, new TypeToken<List<Person>>() {}.getType());
                IDFound=false;
                cursor=db.rawQuery("SELECT * FROM "+ EMPLOYEES,null);
                if(cursor.moveToFirst()){
                    do{
                        int id=cursor.getInt(0);
                        for(Person person: EmployeeList){
                            CurrentID= person.getId();
                            Log.d("IDS", "DB: " + id+ " API: "+ CurrentID);
                            if (id==CurrentID){
                                Log.d("Status","ID"+ CurrentID+ " found in both api and db");
                                IDFound=true;
                            }

                        }
                        if(!IDFound){
                            deleteUser(id);
                        }

                    }while (cursor.moveToNext());
                    cursor.close();

                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Result","Error getting: "+error.toString());
            }
        });
        requestQueue.add(stringRequest);
    }

    public void UpdateWithNewEmployees(Context context){
        List<UserAccountDataModel> Users= getAllEmployees();
        Log.d("Status ", "Update with new employees");
        String Url="http://10.224.41.11/comp2000/employees";
        Gson gson=new Gson();
        int CurrentID=0;
        RequestQueue requestQueue=Volley.newRequestQueue(context);
        SQLiteDatabase db=this.getReadableDatabase();
        //Cursor cursor=db.raw ("SELECT EXISTS( SELECT 1 FROM " + EMPLOYEES +" WHERE "+ USERID+"="+CurrentID+");");


        StringRequest stringRequest =new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String SelectQuery;
                int CurrentID;
                Cursor cursor;
                boolean IDFound;
                Log.d("Status", "Opening employees");


                List<Person> EmployeeList = gson.fromJson(response, new TypeToken<List<Person>>() {}.getType());
                for (Person person:EmployeeList){
                    CurrentID=person.getId();
                    Log.d("Current", Integer.toString(CurrentID));
                    IDFound=false;
                    cursor=db.rawQuery("SELECT * FROM "+ EMPLOYEES,null);
                    if(cursor.moveToFirst()){
                        do{
                            int id=cursor.getInt(0);
                            Log.d("IDS", "DB: " + id+ " API: "+ CurrentID);
                            if (id==CurrentID){
                                Log.d("Status","ID"+ CurrentID+ " found in both api and db");
                                IDFound=true;
                            }
                        }while (cursor.moveToNext());
                        cursor.close();

                    }
                    if(!IDFound){
                        adduser(new UserAccountDataModel(person.getId(), person.getEmail(),GetUserName(person.getFirstname(), person.getLastname()),"123Password"));
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Result","Error getting: "+error.toString());
            }
        });
        requestQueue.add(stringRequest);

    }
    public void ChangePassword(int ID,String NewPass){
        List<UserAccountDataModel>Users=this.getAllEmployees();
        for (UserAccountDataModel user:Users){
            if(user.getUserId()==ID){
                ContentValues values = new ContentValues();
                SQLiteDatabase db = this.getWritableDatabase();
                values.put(USERID,ID);
                values.put(EMAIL,user.getEmail());
                values.put(USERNAME,user.getUserName());
                values.put(PASSWORD, NewPass);

                db.update(EMPLOYEES,values,USERID + " = " +ID,null);
                db.close();;
            }
        }
    }
    public boolean UpdateHolidayStatus(int RequestId,String NewStatus){
        List<HolidayRequestDataModel>Requests=this.getAllHolidayRequests();
        for(HolidayRequestDataModel holiday:Requests){
            if(holiday.getRequestID()==RequestId){
                ContentValues values= new ContentValues();
                SQLiteDatabase db=this.getWritableDatabase();
                values.put(REQUESTID,RequestId);
                values.put(USERID,holiday.getEmployeeId());
                values.put(EMPLOYEENAME,holiday.getEmployeeName());
                values.put(STATUS,NewStatus);
                values.put(STARTDATE,holiday.getStartDate());
                values.put(ENDDATE,holiday.getEndDate());

                long Result=db.update(HOLIDAYREQUESTS,values,REQUESTID+" = "+RequestId,null);
                return Result != -1;
            }
        }
        return false;
    }
    public void ChangeUserName(int ID,String NewUName){
        List<UserAccountDataModel>Users=this.getAllEmployees();
        for (UserAccountDataModel user:Users){
            if(user.getUserId()==ID){
                ContentValues values = new ContentValues();
                SQLiteDatabase db = this.getWritableDatabase();
                values.put(USERID,ID);
                values.put(EMAIL,user.getEmail());
                values.put(USERNAME,NewUName);
                values.put(PASSWORD, user.getPassWord());

                db.update(EMPLOYEES,values,USERID + " = " +ID,null);
                db.close();;
            }
        }
    }
    public void ChangeEmail(int ID,String NewEmail){
        List<UserAccountDataModel>Users=this.getAllEmployees();
        for (UserAccountDataModel user:Users){
            if(user.getUserId()==ID){
                ContentValues values = new ContentValues();
                SQLiteDatabase db = this.getWritableDatabase();
                values.put(USERID,ID);
                values.put(EMAIL,NewEmail);
                values.put(USERNAME,user.getUserName());
                values.put(PASSWORD, user.getPassWord());

                db.update(EMPLOYEES,values,USERID + " = " +ID,null);
                db.close();;
            }
        }
    }
    public boolean deleteNotification(int ID){
        SQLiteDatabase db = this.getWritableDatabase();
        String query= "DELETE FROM " + EMPLOYEENOTIFICATIONS + " WHERE "+ NOTIFICATIONID + "= " + ID+"";

        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            return false;
        }else{
            return true;
        }
    }
    public boolean deleteUser(int ID){
        SQLiteDatabase db = this.getWritableDatabase();
        String query= "DELETE FROM " + EMPLOYEES + " WHERE "+ USERID + "= " + ID+"";

        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            return false;
        }else{
            return true;
        }
    }
    public boolean deleteRequest(int ID){
        SQLiteDatabase db = this.getWritableDatabase();
        String query= "DELETE FROM " + HOLIDAYREQUESTS + " WHERE "+ REQUESTID + "= " + ID+"";

        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            return false;
        }else{
            return true;
        }
    }
}
