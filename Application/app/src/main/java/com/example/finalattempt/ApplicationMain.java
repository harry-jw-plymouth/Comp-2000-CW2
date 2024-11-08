package com.example.finalattempt;

import android.app.Application;
import android.content.Context;

import java.util.ArrayList;

public class ApplicationMain extends Application implements HolidayRequestItem  {
    private static Context context;
    ArrayList<HolidayRequest> HolidayRequests;

    @Override
    public void onCreate(){
        super.onCreate();
        context=getApplicationContext();
        HolidayRequests=new ArrayList<>();
    }
    public static Context getMyContext(){
        return context;
    }
    @Override
    public void AddItems(String name, int Id, String Status, String StartDate, String EndDate) {
        HolidayRequests.add(new HolidayRequest(0,"Harry Watton","Approved"));
        HolidayRequests.add(new HolidayRequest(2,"Alex Crook","Requested"));
        HolidayRequests.add(new HolidayRequest(0,"Harry Watton","Declined"));
        HolidayRequests.add(new HolidayRequest(3,"William Richards","Approved"));
        HolidayRequests.add(new HolidayRequest(4,"Max Waterman","Requested"));

    }
    @Override
    public ArrayList<HolidayRequest> getItems(){
        return HolidayRequests;
    }


}
