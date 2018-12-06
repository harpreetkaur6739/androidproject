package com.example.comp304_003_finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.comp304_003_finalproject.database.DatabaseHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserLogin extends AppCompatActivity {

    TextView txtMessage;
    DatabaseHandler dbHandler;
    String TABLE_NAME;
    final String COL_USERNAME = "userId";
    final String COL_PASSWORD = "password";
    String loginAs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        dbHandler = DatabaseHandler.getDbHandlerInstance();
        txtMessage = ((TextView) findViewById(R.id.txtMessage));
        if (getIntent() != null) {
            loginAs = getIntent().getStringExtra("user");
            ((TextView) findViewById(R.id.txtLabel)).setText("Login as " + loginAs);

            TABLE_NAME = getResources().getString(R.string.tbl_users);

            String message = getIntent().getStringExtra("Message");
            String user = getIntent().getStringExtra("UserName");

            txtMessage.setText(message);
            ((EditText) findViewById(R.id.txtUserName)).setText(user);
        }
    }

    public void login(View v) {
        String userName = ((EditText) findViewById(R.id.txtUserName)).getText().toString();
        String password = ((EditText) findViewById(R.id.txtPassword)).getText().toString();

        ArrayList user = authenticateUser(userName, password);

        if (user != null) {
            SharedPreferences myPreference =
                    getSharedPreferences("RecruitmentSharedPreferences", 0);
            SharedPreferences.Editor prefEditor = myPreference.edit();
            prefEditor.putString("UserName", userName);
            Map<String, String> map = new HashMap<String, String>();
            map = (Map) user.get(1);
            prefEditor.putString("UserId", map.get("column_value")); //UserId
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

    private ArrayList authenticateUser(String user, String pass){
        String[] valArr = new String[]{user, pass};
        String[] colArr = new String[]{COL_USERNAME, COL_PASSWORD};

        ArrayList userRec = dbHandler.getRecord(TABLE_NAME, colArr,valArr);

        if(userRec.size() > 0){ //Successful login
            return userRec;
        }
        return null;
    }

}
