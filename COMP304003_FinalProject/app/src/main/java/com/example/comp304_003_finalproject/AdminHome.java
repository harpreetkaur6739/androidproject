package com.example.comp304_003_finalproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Browser;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.comp304_003_finalproject.adapter.EmployeeAdapter;
import com.example.comp304_003_finalproject.database.EmployeeDAO;
import com.example.comp304_003_finalproject.model.Employee;

import java.util.List;

public class AdminHome extends AppCompatActivity {

    Button bntAddEmployee;
    private ListView listEmployee;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

       listEmployee = (ListView) findViewById(R.id.list_employee);


        listEmployee.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Employee employee = (Employee) listEmployee.getItemAtPosition(position);
                Intent intentGoAddUser = new Intent(AdminHome.this, AddUser.class);
                intentGoAddUser.putExtra("employee", employee);
                startActivity(intentGoAddUser);


            }
        });



        bntAddEmployee = (Button) findViewById(R.id.addEmployee);

        bntAddEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Intent intent=new Intent(AdminHome.this, AddUser.class);

                startActivity(intent);

            }
        });

        registerForContextMenu(listEmployee);



    }


    private void loadList()  {
        EmployeeDAO dao = new EmployeeDAO(this);
        List<Employee> employees = dao.findEmployees();
        // List<Employee> employees2 = dao.listEmployess();
        dao.close();


        EmployeeAdapter adapter = new EmployeeAdapter(this, employees);
        listEmployee.setAdapter(adapter);
    }

    @Override
    protected void onResume(){
        super.onResume();
        loadList();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo)  {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Employee employee = (Employee) listEmployee.getItemAtPosition(info.position);


        MenuItem itemLigar = menu.add("Call");
        itemLigar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(ActivityCompat.checkSelfPermission(AdminHome.this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(AdminHome.this, new String[]{Manifest.permission.CALL_PHONE
                    },123);

                }else{
                    Intent intentLigar = new Intent(Intent.ACTION_CALL);
                    intentLigar.setData(Uri.parse("Phone:" + employee.getPhone()));
                    startActivity(intentLigar);
                }
                return false;
            }
        });


        MenuItem itemSite = menu.add("Go Person Site");
        Intent intentSite = new Intent(Intent.ACTION_VIEW);

        String site = employee.getSite();
        if (!site.startsWith("https://")) {
            site = "https://" + site;
        }

        intentSite.setData(Uri.parse(site));
        itemSite.setIntent(intentSite);


        MenuItem itemSMS = menu.add("Send SMS");
        Intent intentSMS = new Intent(Intent.ACTION_VIEW) ;
        intentSMS.setData(Uri.parse("sms:" + employee.getPhone()));
        itemSMS.setIntent(intentSMS);



        MenuItem itemMap = menu.add("Location In Map");
        Intent intentMap = new Intent(Intent.ACTION_VIEW);
        intentMap.setData(Uri.parse("geo:0,0?q=" + employee.getAddress()));
        itemMap.setIntent(intentMap);


        MenuItem deletar = menu.add("Delete");
            deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {


                EmployeeDAO dao = new EmployeeDAO(AdminHome.this);
                dao.deleta(employee);
                dao.close();

                loadList();

                return false;

            }
        });
    }
}
