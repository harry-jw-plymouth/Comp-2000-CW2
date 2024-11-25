package com.example.finalattempt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EmployeeDisplayAdapter extends RecyclerView.Adapter<EmployeeDisplayAdapter.EmployeeRecyclerView> {

    private Context mCtx;
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

        holder.Id.setText("ID:"+Employee.getId());
        holder.Name.setText("Name:"+Employee.getFirstname()+" "+Employee.getLastname());
    }

    @Override
    public int getItemCount() {
        return EmployeeList.size();
    }

    class EmployeeRecyclerView extends RecyclerView.ViewHolder{
        TextView Name, Id;
        public EmployeeRecyclerView(@NonNull View itemView) {
            super(itemView);
            Name=itemView.findViewById(R.id.EmpName);
            Id=itemView.findViewById(R.id.EmpID);
        }
    }
}
