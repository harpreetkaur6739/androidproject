package com.example.comp304_003_finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

public class UserHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        drawer = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.toolBar);
//        setSupportActionBar(toolbar);

        NavigationView navView = findViewById(R.id.navView);
        navView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        SharedPreferences myPref = getSharedPreferences("RecruitmentSharedPreferences", MODE_PRIVATE);

        ((TextView)navView.getHeaderView(0).findViewById(R.id.txtUserHeader)).setText( "Hello, " + myPref.getString("UserName",""));

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ViewProfile()).commit();
            navView.setCheckedItem(R.id.navMyProfile);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.navMyProfile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ViewProfile ()).commit();
                break;

           case R.id.navMySchedule:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ViewMySchedule ()).commit();
                break;

           /* case R.id.navAllJobs:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ViewJobDetail ()).commit();
                break;

            case R.id.navMyJobs:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ViewMyJob ()).commit();
                break;
*/
            case R.id.navLogout:
                Intent intent = new Intent(UserHome.this, MainActivity.class);
                startActivity(intent);
                break;
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed(){
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }
}
