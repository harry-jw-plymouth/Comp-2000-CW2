package com.example.finalattempt;


import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class EmployeeService {
    private static final String BASE_URL = "http://10.224.41.11/comp2000";
    private static RequestQueue requestQueue;
    private static final Gson gson = new Gson();
    // Initialize RequestQueue if needed
    private static void initQueue(Context context) {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
    }
    public static void getAllEmployees(Context context) {
    }
    public static Employee getEmployeeById(Context context, int id) {
        initQueue(context);
        String Fname;
        Log.d("Test","Getting employee");
        final Employee Temp=new Employee("","","","","",234F);

        String url = BASE_URL + "/employees/get/" + id;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Test","Getting employee response");
                        Employee employee = gson.fromJson(response.toString(), Employee.class);
                        // onSuccess behavior - Log employee details
                        Temp.setFirstname(employee.getFirstname());
                        Temp.setLastname(employee.getLastname());
                        Temp.department=employee.department;
                        Temp.email=employee.email;
                        Temp.salary=employee.salary;
                        Temp.joiningdate=employee.joiningdate;
                        Log.d("EmployeeInfoFromIdGet", "Firstname: " + Temp.getFirstname() + ", Salary: " + employee.getSalary());
                        //return Temp;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // onError behavior - Log the error
                        Log.e("EmployeeError", "Error retrieving employee by ID: " + error.getMessage());
                    }
                }
        );

        requestQueue.add(request);
        Log.d("Temp before return", "Firstname: " + Temp.getFirstname() + ", Salary: " + Temp.getSalary());
        return Temp;
    }
    public static void addEmployee(Context context, Employee employee) {
        initQueue(context);
        String url = BASE_URL + "/employees/add";
        try {
            JSONObject jsonRequest = new JSONObject(gson.toJson(employee));
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonRequest,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // onSuccess behavior - Log the success message from the API response
                            String message = response.optString("message", "Employee added successfully");
                            Log.d("EmployeeService", message);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // onError behavior - Log the error
                            Log.e("EmployeeError", "Error adding employee: " + error.getMessage());
                        }
                    }
            );
            requestQueue.add(request);
        } catch (JSONException e) {
            Log.e("EmployeeError", "Invalid JSON format: " + e.getMessage());
        }
    }
    public static void deleteEmployee(Context context, int id) {
        initQueue(context);
        String url = BASE_URL + "/employees/delete/" + id;
        StringRequest request = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // onSuccess behavior - Log success message
                        Log.d("EmployeeService", "Employee deleted successfully");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // onError behavior - Log the error
                        Log.e("EmployeeError", "Error deleting employee: " + error.getMessage());
                    }
                }
        );
        requestQueue.add(request);
    }
    public static void updateEmployee(Context context, int id, Employee employee) {
        initQueue(context);
        String url = BASE_URL + "/employees/edit/" + id;
        try {
            JSONObject jsonRequest = new JSONObject(gson.toJson(employee));
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, jsonRequest,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            //function for response
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
// error handling
                        }
                    }
            );
            requestQueue.add(request);
        } catch (JSONException e) {
            Log.e("EmployeeError", "Invalid JSON format: " + e.getMessage());
        }
    }
}



