package com.example.comp304_003_finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button bntAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    }
}
