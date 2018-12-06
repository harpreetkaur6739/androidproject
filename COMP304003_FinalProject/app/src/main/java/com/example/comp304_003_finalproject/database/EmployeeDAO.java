package com.example.comp304_003_finalproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.comp304_003_finalproject.model.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    DatabaseHandler dbHandler;
    String TABLE_EMPLOYEE = "Employee";

    public EmployeeDAO(){
        dbHandler = DatabaseHandler.getDbHandlerInstance();
    }

    public void insert(Employee employee) {

         ContentValues dados = getContentValuesEmployee(employee);

         Long rowId = dbHandler.addRecord(dados, TABLE_EMPLOYEE);
    }

    @NonNull
    private ContentValues getContentValuesEmployee(Employee employee) {
        ContentValues dados = new ContentValues();
        dados.put("userId", employee.getUserId());
        dados.put("name", employee.getName());
        dados.put("address", employee.getAddress());
        dados.put("phone", employee.getPhone());
        dados.put("site", employee.getSite());
        dados.put("score", employee.getScore());
        dados.put("photo", employee.getPhoto());
        return dados;
    }

    public List<Employee> findEmployees(){

        Cursor c = dbHandler.getTableCursor(TABLE_EMPLOYEE, null, null);

        List<Employee> employees = new ArrayList<Employee>();
        while (c.moveToNext()) {
            Employee employee = new Employee();
            employee.setId(c.getLong(c.getColumnIndex("id")));
            employee.setUserId(c.getString(c.getColumnIndex("userId")));
            employee.setName(c.getString(c.getColumnIndex("name")));
            employee.setAddress(c.getString(c.getColumnIndex("address")));
            employee.setPhone(c.getString(c.getColumnIndex("phone")));
            employee.setSite(c.getString(c.getColumnIndex("site")));
            employee.setScore(c.getDouble(c.getColumnIndex("score")));
            employee.setPhoto(c.getString(c.getColumnIndex("photo")));


            employees.add(employee);

        }
        c.close();

        return employees;
       // return null;
    }

    public Employee findEmployeeById(String[] userId){
        String[] key = new String[]{"userId"};

        Cursor c = dbHandler.getTableCursor(TABLE_EMPLOYEE, key, userId);

        Employee employee = null;
        while (c.moveToNext()) {
            employee = new Employee();
            employee.setId(c.getLong(c.getColumnIndex("id")));
            employee.setUserId(c.getString(c.getColumnIndex("userId")));
            employee.setName(c.getString(c.getColumnIndex("name")));
            employee.setAddress(c.getString(c.getColumnIndex("address")));
            employee.setPhone(c.getString(c.getColumnIndex("phone")));
            employee.setSite(c.getString(c.getColumnIndex("site")));
            employee.setScore(c.getDouble(c.getColumnIndex("score")));
            employee.setPhoto(c.getString(c.getColumnIndex("photo")));

        }
        c.close();

        return employee;
        // return null;
    }

    public List<Employee> listEmployess() {

       // String sql = "SELECT * FROM Employee;";
        //SQLiteDatabase db = getReadableDatabase();
       // Cursor c = db.rawQuery("SELECT * FROM Employee;", null);


        return null;
    }

    public void deleta(Employee employee) {
        String key = "id";
        String params = employee.getId().toString();
        dbHandler.deleteRecord(TABLE_EMPLOYEE, key, params);
    }

    public void upadate(Employee employee) {

        ContentValues dados =  getContentValuesEmployee(employee);
        String[] key = new String []{"id"};
        String[] params ={employee.getId().toString()};
        dbHandler.updateRecord(dados, TABLE_EMPLOYEE, key, params);
        //db.update("Employee", dados, "id = ?", params);


    }


}
