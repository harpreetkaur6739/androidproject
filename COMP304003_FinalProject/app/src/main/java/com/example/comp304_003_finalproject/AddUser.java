package com.example.comp304_003_finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.widget.EditText;
import android.widget.Toast;

import com.example.comp304_003_finalproject.database.EmployeeDAO;
import com.example.comp304_003_finalproject.model.Employee;

public class AddUser extends AppCompatActivity {

    private UserHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        helper = new UserHelper (this);

        Intent intent = getIntent();

        Employee employee = (Employee)  intent.getSerializableExtra("employee");

        if (employee !=null) {
            helper.fieldTheEmployee(employee);
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_adduser, menu);

        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_addUser:

                Employee employee = helper.getEmployee();
                EmployeeDAO dao = new EmployeeDAO(this);

                if (employee.getId() != null)  {
                    dao.upadate(employee);
                } else {
                    dao.insert(employee);
                }


                dao.close();

                Toast.makeText(AddUser.this, "Salve:" + employee.getName(), Toast.LENGTH_SHORT).show();

                finish();
                break;

        }
        return super.onOptionsItemSelected(item);

    }
}
