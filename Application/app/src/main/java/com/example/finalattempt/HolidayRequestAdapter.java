package com.example.finalattempt;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Currency;
import java.util.List;

public class HolidayRequestAdapter extends  RecyclerView.Adapter<HolidayRequestAdapter.HolidayRequestViewHolder>{
    private Context mCtx;
    private List<HolidayRequestDataModel> HolidayRequestList;
    public HolidayRequestAdapter(Context mCtx,List<HolidayRequestDataModel>HolidayRequestList){
        this.mCtx=mCtx;
        this.HolidayRequestList=HolidayRequestList;
    }
    @NonNull
    @Override
    public HolidayRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view=inflater.inflate(R.layout.holidayitem,null);
        HolidayRequestViewHolder holder = new HolidayRequestViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolidayRequestViewHolder holder, int position) {
        HolidayRequestDataModel holidayrequest=HolidayRequestList.get(position);

        holder.Name.setText("Name:"+holidayrequest.GetName());
        holder.Id.setText("Id:"+ Integer.toString(holidayrequest.GetId()));
        holder.Status.setText("Status:"+holidayrequest.GetStatus());
        holder.SDate.setText("From:"+holidayrequest.GetStartDate());
        holder.EDate.setText("To:"+holidayrequest.GetEndDate());
        holder.ReqID.setText("RequestID:"+holidayrequest.getRequestID());

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
            Status=itemView.findViewById(R.id.status);
            SDate=itemView.findViewById(R.id.Sdate);
            EDate=itemView.findViewById(R.id.ToDate);
            Approve=itemView.findViewById(R.id.approveButton);
           // Deny=itemView.findViewById(R.id.DenyHolidayButton);
            ReqID=itemView.findViewById(R.id.ReqID);
            itemView.findViewById(R.id.approveButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EmployeeDBHelper EDB = new EmployeeDBHelper(mCtx);
                    String Stat=(Status.getText().toString()).replace("Status:","");
                    if(Stat.equals("Requested")){
                        Log.d("Status",Stat);
                        AlertDialog.Builder builder=new AlertDialog.Builder(mCtx);
                        builder.setTitle("Approve request?");
                        builder.setMessage("Approve "+ GetRawName(Name.getText().toString()) +"'s request from "+SDate.getText().toString()+" to "+ EDate.getText().toString());
                        builder.setPositiveButton("Approve", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("Status","Approved");
                                if (EDB.UpdateHolidayStatus(GetRequestID(ReqID.getText().toString()),"Approved"))
                                {
                                    EDB.addNotification(new NotificationDataModel(0,GetRequestID(Id.getText().toString()),GetRawName(Name.getText().toString()),"HolidayRequestUpdate"));
                                    Status.setText("Status:Approved");
                                    Toast.makeText( mCtx,"Successfully updated status",Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText( mCtx,"Failed to update status",Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                        builder.setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("Status","Denied");
                                if (EDB.UpdateHolidayStatus(GetRequestID(ReqID.getText().toString()),"Denied"))
                                {
                                    Status.setText("Status:Denied");
                                    Toast.makeText( mCtx,"Successfully updated status",Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText( mCtx,"Failed to update status",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        AlertDialog alertDialog= builder.create();
                        alertDialog.show();

                    }
                    else if (Stat.equals("Approved")){
                        Log.d("Status",Stat);
                        AlertDialog.Builder builder=new AlertDialog.Builder(mCtx);
                        builder.setTitle("Cancel approved request?");
                        builder.setMessage("Cancel "+ GetRawName(Name.getText().toString()) +"'s approved request from "+SDate.getText().toString()+" to "+ EDate.getText().toString());
                        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("","Cancelled");
                                if (EDB.UpdateHolidayStatus(GetRequestID(ReqID.getText().toString()),"Denied"))
                                {
                                    Status.setText("Status:Denied");
                                    EDB.addNotification(new NotificationDataModel(0,GetRequestID(Id.getText().toString()),GetRawName(Name.getText().toString()),"HolidayRequestUpdate"));
                                    Toast.makeText( mCtx,"Successfully updated status",Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText( mCtx,"Failed to update status",Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                        builder.setNegativeButton("Allow", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("Status","Allowed");
                                Toast.makeText(mCtx,"Holiday allowed",Toast.LENGTH_SHORT).show();
                            }
                        });
                        AlertDialog alertDialog= builder.create();
                        alertDialog.show();
                    }
                    else if (Stat.equals("Denied")){
                        Log.d("Status",Stat);
                        AlertDialog.Builder builder=new AlertDialog.Builder(mCtx);
                        builder.setTitle("Approve cancelled request?");
                        builder.setMessage("Approve "+ GetRawName(Name.getText().toString()) +"'s approved request from "+SDate.getText().toString()+" to "+ EDate.getText().toString());
                        builder.setPositiveButton("Approve", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("","Approved");
                                if (EDB.UpdateHolidayStatus(GetRequestID(ReqID.getText().toString()),"Approved"))
                                {
                                    Status.setText("Status:Approved");

                                    Toast.makeText( mCtx,"Successfully updated status",Toast.LENGTH_SHORT).show();
                                    EDB.addNotification(new NotificationDataModel(0,GetRequestID(Id.getText().toString()),GetRawName(Name.getText().toString()),"HolidayRequestUpdate"));
                                }else{
                                    Toast.makeText( mCtx,"Failed to update status",Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                        builder.setNegativeButton("Leave", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("Status","Allowed");
                                Toast.makeText(mCtx,"Holiday not allowed",Toast.LENGTH_SHORT).show();
                            }
                        });
                        AlertDialog alertDialog= builder.create();
                        alertDialog.show();
                    }
                }
            });


        }
        public String GetRawName(String Name){
            return Name.replace("Name:","");
        }
        public int GetRequestID(String id){
            id=id.replaceAll("[^\\d.]","");
            Log.d("New id",id);
            return Integer.parseInt(id);
        }



    }
}
