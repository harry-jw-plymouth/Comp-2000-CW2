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
    public  EmployeeDBHelper(@Nullable Context context){
        super(context,"Employees.db",null,1);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable =
            "CREATE TABLE " + EMPLOYEES + " ("+ USERID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+ EMAIL+ " TEXT, "+ USERNAME+ " TEXT, " + PASSWORD + " TEXT)";

        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

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
        return FName.charAt(0)+LName;
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
                        if(!IDFound){
                            adduser(new UserAccountDataModel(person.getId(), person.getEmail(),GetUserName(person.getFirstname(), person.getLastname()),"123Password"));
                        }
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

                db.update(EMPLOYEES,values,"WHERE "+ USERID +" = " +ID+ ";",null);
                db.close();;
            }
        }


    }
}
