/**
 * Author
 * Harpreet Kaur
 * 300910377
 */
package com.example.comp304_003_finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.comp304_003_finalproject.database.EmployeeDAO;
import com.example.comp304_003_finalproject.model.Employee;

public class UserLogin extends AppCompatActivity {

    TextView txtMessage;
    String loginAs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        txtMessage = ((TextView) findViewById(R.id.txtMessage));
        if (getIntent() != null) {
            loginAs = getIntent().getStringExtra("user");
            ((TextView) findViewById(R.id.txtLabel)).setText("Login as " + loginAs);

            String message = getIntent().getStringExtra("Message");
            String user = getIntent().getStringExtra("UserName");

            txtMessage.setText(message);
            ((EditText) findViewById(R.id.txtUserName)).setText(user);
        }
    }

    public void login(View v) {
        String userName = ((EditText) findViewById(R.id.txtUserName)).getText().toString();

        Employee user = authenticateUser(userName);

        if (user != null) {
            SharedPreferences myPreference =
                    getSharedPreferences("RecruitmentSharedPreferences", 0);
            SharedPreferences.Editor prefEditor = myPreference.edit();

            prefEditor.putString("UserId", user.getUserId()); //UserId
            prefEditor.putString("UserRole", loginAs); //UserRole
            prefEditor.commit();

            Intent intent = null;
            if (loginAs.equals("user")) {
                intent = new Intent(UserLogin.this, UserHome.class);
            } else if (loginAs.equals("admin")) {
                intent = new Intent(UserLogin.this, AdminHome.class);
            }
            startActivity(intent);
        } else {
            txtMessage.setText("Incorrect UserName/Password!");
        }
    }

    private Employee authenticateUser(String user){
        String[] valArr = new String[]{user};

        EmployeeDAO dao = new EmployeeDAO();
       Employee empRec = dao.findEmployeeById(valArr);

        if(empRec != null){ //Successful login
            return empRec;
        }
        return null;
    }

}
