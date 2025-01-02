package com.example.finalattempt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HolidayRequestAdapterEmployee extends  RecyclerView.Adapter<HolidayRequestAdapterEmployee.HolidayRequestViewHolder>{
    private Context mCtx;
    private List<HolidayRequest> HolidayRequestList;
    public HolidayRequestAdapterEmployee(Context mCtx,List<HolidayRequest>HolidayRequestList){
        this.mCtx=mCtx;
        this.HolidayRequestList=HolidayRequestList;
    }
    @NonNull
    @Override
    public HolidayRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view=inflater.inflate(R.layout.holidayitememployee,null);
        HolidayRequestViewHolder holder = new HolidayRequestViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolidayRequestViewHolder holder, int position) {
        HolidayRequest holidayrequest=HolidayRequestList.get(position);

        holder.Name.setText("Name:"+holidayrequest.GetName());
        holder.Id.setText("Id:"+ Integer.toString(holidayrequest.GetId()));
        holder.Status.setText("Status:"+holidayrequest.GetStatus());
        holder.SDate.setText("From:"+holidayrequest.GetStartDate());
        holder.EDate.setText("To:"+holidayrequest.GetEndDate());
    }

    @Override
    public int getItemCount() {
        return HolidayRequestList.size();
    }

    class HolidayRequestViewHolder extends RecyclerView.ViewHolder{
        TextView Name,Id,Status,SDate,EDate;
        Button Approve,Deny;
        public HolidayRequestViewHolder(View itemView){
            super(itemView);
            Name=itemView.findViewById(R.id.EmpName);
            Id=itemView.findViewById(R.id.HolidayItemEmployeeID);
            Status=itemView.findViewById(R.id.status);
            SDate=itemView.findViewById(R.id.Sdate);
            EDate=itemView.findViewById(R.id.ToDate);
            Approve=itemView.findViewById(R.id.approveButton);
            Deny=itemView.findViewById(R.id.DenyHolidayButton);

        }

    }
}
