package com.example.finalattempt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public static final String ADMINS = "admins";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "Password";
    public static final String ID = "id";
    public static final  String NOTIFICATIONTYPE="Type";
    public static final String EMPLOYEENOTIFICATIONS = "EmployeeNotifications";
    public static final String NOTIFICATIONID = "NotificationID";
    public static final String USERID = "Userid";

    public DBHelper(@Nullable Context context) {
        super(context,"user.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable =
                "CREATE TABLE " + ADMINS + " ("+ ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+ USERNAME+ " TEXT, " + PASSWORD + " TEXT)";
        String CreateEmployeeNotificationTable =
                "CREATE TABLE " + EMPLOYEENOTIFICATIONS+ " ("+  NOTIFICATIONID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+USERID+" INTEGER, " +USERNAME+ " TEXT, " + NOTIFICATIONTYPE+ " TEXT)";

        db.execSQL(CreateEmployeeNotificationTable);

        db.execSQL(createTable);
    }
    public  void UpgradeNotificationsTable(SQLiteDatabase db){
        String Drop="DROP TABLE IF EXISTS " + EMPLOYEENOTIFICATIONS;
        String Create ="CREATE TABLE " + EMPLOYEENOTIFICATIONS+ " ("+  NOTIFICATIONID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+USERID+" INTEGER, " +USERNAME+ " TEXT, " + NOTIFICATIONTYPE+ " TEXT)";
        db.execSQL(Drop);
        db.execSQL(Create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTable = "DROP TABLE IF EXISTS " + ADMINS;
        db.execSQL(dropTable);
        onCreate(db);
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
    public  boolean addAdmin(AdminAccountDataModel DataModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(USERNAME, DataModel.getUserName());
        cv.put(PASSWORD, DataModel.getPassword());

        long result = db.insert(ADMINS, null, cv);
        if(result == -1) {
            return  false;
        }else{
            return true;
        }
    }
    public List<AdminAccountDataModel> getAllAdmins(){
        List<AdminAccountDataModel> outputList = new ArrayList<>();

        SQLiteDatabase db= this.getReadableDatabase();
        String query = "SELECT * FROM " + ADMINS;

        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                int id=cursor.getInt(0);
                String UserName= cursor.getString(1);
                String Password= cursor.getString(2);

                AdminAccountDataModel dataModel = new AdminAccountDataModel(UserName,Password);
                outputList.add(dataModel);
            }while(cursor.moveToNext());
        }else {

        }
        cursor.close();
        db.close();

        return  outputList;
    }
}
