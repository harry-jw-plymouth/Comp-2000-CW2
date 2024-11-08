package com.example.finalattempt;

import java.util.ArrayList;

public interface HolidayRequestItem {
    void AddItems(String name,int Id,String Status,String StartDate,String EndDate);

    ArrayList<HolidayRequest> getItems();
}
