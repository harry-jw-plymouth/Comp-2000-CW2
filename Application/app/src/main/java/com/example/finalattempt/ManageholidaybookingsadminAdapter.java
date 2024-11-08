package com.example.finalattempt;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ManageholidaybookingsadminAdapter extends RecyclerView.Adapter<ManageholidaybookingsadminAdapter.HolidayRequestViewHolder> {
    private ArrayList<HolidayRequest> HolidayRequests;
    Activity activity;
    public ManageholidaybookingsadminAdapter(Activity activity){
        this.activity=activity;
        HolidayRequests=((HolidayRequestItem)ApplicationMain.getMyContext()).getItems();
    }
    public class HolidayRequestViewHolder extends RecyclerView.ViewHolder {
        TextView name,Status,Id;
        Button Approve,Deny;

        public HolidayRequestViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.EmpName);
            Status=itemView.findViewById(R.id.status);
            Id=itemView.findViewById(R.id.HolidayItemEmployeeID);
            Deny=itemView.findViewById(R.id.DenyHolidayButton);
            Approve=itemView.findViewById(R.id.approveButton);
            // at 16:00 in video
        }
    }
    @NonNull
    @Override
    public ManageholidaybookingsadminAdapter.HolidayRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.holidayitem,parent,false);
        HolidayRequestViewHolder holidayViewholder=new HolidayRequestViewHolder(view);
        return holidayViewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull ManageholidaybookingsadminAdapter.HolidayRequestViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
