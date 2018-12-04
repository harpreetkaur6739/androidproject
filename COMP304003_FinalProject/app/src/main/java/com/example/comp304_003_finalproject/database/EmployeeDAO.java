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

public class EmployeeDAO extends SQLiteOpenHelper {


    public EmployeeDAO(Context context) {
        super(context, "EmployeeData", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Employee (id INTEGER PRIMARY KEY, name TEXT NOT NULL, address TEXT, phone TEXT, site TEXT, score REAL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "DROP TABLE IF EXISTS Employee";
        db.execSQL(sql);
        onCreate(db);

    }

    public void insert(Employee employee) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = getContentValuesEmployee(employee);

        db.insert("Employee", null, dados );

    }

    @NonNull
    private ContentValues getContentValuesEmployee(Employee employee) {
        ContentValues dados = new ContentValues();
        dados.put("name", employee.getName());
        dados.put("address", employee.getAddress());
        dados.put("phone", employee.getPhone());
        dados.put("site", employee.getSite());
        dados.put("score", employee.getScore());
        return dados;
    }

    public List<Employee> findEmployees(){


        String sql = "SELECT * FROM Employee;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        List<Employee> employees = new ArrayList<Employee>();
        while (c.moveToNext()) {
            Employee employee = new Employee();
            employee.setId(c.getLong(c.getColumnIndex("id")));
            employee.setName(c.getString(c.getColumnIndex("name")));
            employee.setAddress(c.getString(c.getColumnIndex("address")));
            employee.setPhone(c.getString(c.getColumnIndex("phone")));
            employee.setSite(c.getString(c.getColumnIndex("site")));
            employee.setScore(c.getDouble(c.getColumnIndex("score")));

            employees.add(employee);

        }
        c.close();

        return employees;
       // return null;
    }

    public List<Employee> listEmployess() {

       // String sql = "SELECT * FROM Employee;";
        //SQLiteDatabase db = getReadableDatabase();
       // Cursor c = db.rawQuery("SELECT * FROM Employee;", null);


        return null;
    }

    public void deleta(Employee employee) {

        SQLiteDatabase db = getWritableDatabase();

        String [] params = {String.valueOf(employee.getId())};
        db.delete("Employee", "id = ?", params);
    }

    public void upadate(Employee employee) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados =  getContentValuesEmployee(employee);

        String[] params ={employee.getId().toString()};
        db.update("Employee", dados, "id = ?", params);


    }
}
