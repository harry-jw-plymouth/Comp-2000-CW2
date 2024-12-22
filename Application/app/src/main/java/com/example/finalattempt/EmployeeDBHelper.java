package com.example.finalattempt;

import android.content.Context;
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
    public void UpdateWithNewEmployees(Context context){
        String Url="http://10.224.41.11/comp2000/employees";
        Gson gson=new Gson();
        RequestQueue requestQueue=Volley.newRequestQueue(context);
        StringRequest stringRequest =new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int CurrentID;
                String SelectQuery;

                List<Person> EmployeeList = gson.fromJson(response, new TypeToken<List<Person>>() {}.getType());
                for (Person person:EmployeeList){
                    CurrentID=person.getId();
                    SelectQuery="SELECT EXISTS( SELECT 1 FROM " + EMPLOYEES +" WHERE "+ USERID+"="+CurrentID+");";




                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Result","Error getting: "+error.toString());
            }
        });
    }
    public void ChangePassword(int ID,String NewPass){

    }
}
