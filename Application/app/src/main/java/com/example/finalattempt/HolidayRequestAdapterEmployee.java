package com.example.finalattempt;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.AbstractList;
import java.util.List;

public class HolidayRequestAdapterEmployee extends  RecyclerView.Adapter<HolidayRequestAdapterEmployee.HolidayRequestViewHolder>{
    private Context mCtx;
    private List<HolidayRequestDataModel> HolidayRequestList;
    public HolidayRequestAdapterEmployee(Context mCtx,List<HolidayRequestDataModel>HolidayRequestList){
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
        HolidayRequestDataModel holidayrequest=HolidayRequestList.get(position);

        holder.Name.setText("Name:"+holidayrequest.GetName());
        holder.Id.setText("UserId:"+ Integer.toString(holidayrequest.GetId()));
        holder.ReqID.setText("RequestID:"+holidayrequest.getRequestID());
        holder.Status.setText("Status:"+holidayrequest.GetStatus());
        holder.SDate.setText("From:"+holidayrequest.GetStartDate());
        holder.EDate.setText("To:"+holidayrequest.GetEndDate());
    }

    @Override
    public int getItemCount() {
        return HolidayRequestList.size();
    }

    class HolidayRequestViewHolder extends RecyclerView.ViewHolder{
        TextView Name,Id,Status,SDate,EDate,ReqID;
        Button Approve,Deny;
        public HolidayRequestViewHolder(View itemView){
            super(itemView);
            Name=itemView.findViewById(R.id.EmpName);
            Id=itemView.findViewById(R.id.HolidayItemEmployeeID);
            ReqID=itemView.findViewById(R.id.ReqID);
            Status=itemView.findViewById(R.id.status);
            SDate=itemView.findViewById(R.id.Sdate);
            EDate=itemView.findViewById(R.id.ToDate);
           // Approve=itemView.findViewById(R.id.approveButton);
            Deny=itemView.findViewById(R.id.DenyHolidayButton);

            itemView.findViewById(R.id.DenyHolidayButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder=new AlertDialog.Builder(mCtx);
                    builder.setTitle("Cancel request?");
                    builder.setMessage("Cancel request from: "+SDate.getText().toString()+" To: "+EDate.getText().toString());
                    builder.setPositiveButton("Keep", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(mCtx,"Holiday not cancelled",Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EmployeeDBHelper EDB = new EmployeeDBHelper(mCtx);
                            int ReqId=GetRequestID(ReqID.getText().toString());
                            if(EDB.deleteRequest(ReqId)){
                                Toast.makeText(mCtx,"Request deleted",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(mCtx, PreviousHolidays.class);
                                String UName=(Name.getText().toString()).replace("Name:","");
                                intent.putExtra("ID",GetRequestID(Id.getText().toString()));
                                intent.putExtra("UName",UName);
                                v.getContext().startActivity(intent);
                            }
                            else {
                                Toast.makeText(mCtx,"Holiday cancelled",Toast.LENGTH_SHORT).show();
                            }

                            //EDB.deleteRequest()
                        }
                    });
                    AlertDialog alertDialog=builder.create();
                    alertDialog.show();
                }
            });

        }
        public int GetRequestID(String id){
            id=id.replaceAll("[^\\d.]","");
            Log.d("New id",id);
            return Integer.parseInt(id);
        }

    }
}
