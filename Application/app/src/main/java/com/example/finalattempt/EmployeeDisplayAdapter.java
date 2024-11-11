package com.example.finalattempt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EmployeeDisplayAdapter extends RecyclerView.Adapter<EmployeeDisplayAdapter.EmployeeRecyclerView> {

    private Context mCtx;
    private List<EmployeeDetails> EmployeeList;
    public EmployeeDisplayAdapter(Context mCtx, List<EmployeeDetails> EmployeeList){
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
        EmployeeDetails Employee=EmployeeList.get(position);
    }

    @Override
    public int getItemCount() {
        return EmployeeList.size();
    }

    class EmployeeRecyclerView extends RecyclerView.ViewHolder{

        public EmployeeRecyclerView(@NonNull View itemView) {
            super(itemView);
        }
    }
}
