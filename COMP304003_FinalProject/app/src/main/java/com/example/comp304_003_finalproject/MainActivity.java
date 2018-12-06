package com.example.comp304_003_finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.comp304_003_finalproject.database.DatabaseHandler;

public class MainActivity extends AppCompatActivity {

    Button bntAdmin;
    DatabaseHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHandler = new DatabaseHandler(this);

        bntAdmin = (Button) findViewById(R.id.btnAdminLogin);

        bntAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Intent intent=new Intent(MainActivity.this, AdminHome.class);

                startActivity(intent);

            }
        });

    }

    protected void loginAsUser(View v){
        Intent intent = new Intent(MainActivity.this, UserLogin.class);
        intent.putExtra("user","user");
        startActivity(intent);
    }
}
