package com.example.finalattempt;

import static androidx.core.content.ContextCompat.startActivity;

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
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EmployeeDisplayAdapter extends RecyclerView.Adapter<EmployeeDisplayAdapter.EmployeeRecyclerView> {

    private Context mCtx;
    private Person CurrentPerson;
    private List<Person> EmployeeList;
    public EmployeeDisplayAdapter(Context mCtx, List<Person> EmployeeList){
        this.mCtx=mCtx;
        this.EmployeeList=EmployeeList;
    }
    @NonNull
    @Override
    public EmployeeDisplayAdapter.EmployeeRecyclerView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view=inflater.inflate(R.layout.employeeitem,null);
        EmployeeDisplayAdapter.EmployeeRecyclerView holder = new EmployeeDisplayAdapter.EmployeeRecyclerView(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeDisplayAdapter.EmployeeRecyclerView holder, int position) {
        Person Employee=EmployeeList.get(position);
        CurrentPerson=Employee;

        holder.Id.setText("ID:"+Employee.getId());
        holder.Name.setText("Name:"+Employee.getFirstname()+" "+Employee.getLastname());
        holder.Employee=Employee;
    }

    @Override
    public int getItemCount() {
        return EmployeeList.size();
    }

    class EmployeeRecyclerView extends RecyclerView.ViewHolder{
        TextView Name, Id;
        Person Employee;
        Button DeleteButton;
        public EmployeeRecyclerView(@NonNull View itemView) {
            super(itemView);
            Name=itemView.findViewById(R.id.EmpName);
            Id=itemView.findViewById(R.id.EmpID);

            itemView.findViewById(R.id.OpenEmployeeDetails).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Button Test","Open employee "+Id.getText().toString());
                }
            });
            itemView.findViewById(R.id.DeleteEmployee).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Button test","Deleting employee "+ Id.getText().toString());
                    AlertDialog.Builder builder=new AlertDialog.Builder(mCtx);
                    builder.setTitle("Confirm Employee deletion");
                    builder.setMessage("Please confirm you would like to delete employee " + Id.getText().toString());
                    builder.setCancelable(false);
                    // above line prevents alert from closing when area outside box clicked

                    builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //EmployeeService.deleteEmployee(mCtx,163);

                            Toast.makeText(mCtx, "Employee"+CurrentPerson.getId()+" deleted",Toast.LENGTH_SHORT).show();
                            EmployeeService.deleteEmployee(mCtx, CurrentPerson.getId());
                            Intent intent=new Intent(mCtx,admin_Main_Refresh.class);
                            v.getContext().startActivity(intent);

                        }
                    });
                    builder.setNegativeButton("Dont delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(mCtx,"Employee not deleted",Toast.LENGTH_SHORT).show();

                        }
                    });
                    AlertDialog alertDialog= builder.create();
                    alertDialog.show();

                }
            });
        }
    }
}
