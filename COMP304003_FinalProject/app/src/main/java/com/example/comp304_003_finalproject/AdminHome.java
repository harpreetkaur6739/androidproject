package com.example.comp304_003_finalproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.provider.Browser;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.comp304_003_finalproject.adapter.EmployeeAdapter;
import com.example.comp304_003_finalproject.database.EmployeeDAO;
import com.example.comp304_003_finalproject.model.Employee;

import java.util.List;

public class AdminHome extends Fragment {

    Button bntAddEmployee,bntMap,bntSMS;
    private ListView listEmployee;
    View viewAdminHome;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewAdminHome = inflater.inflate(R.layout.activity_admin_home, container, false);

        listEmployee = (ListView) viewAdminHome.findViewById(R.id.list_employee);
        listEmployee.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Employee employee = (Employee) listEmployee.getItemAtPosition(position);

                ViewUser viewUserFragment = new ViewUser();
                Bundle args = new Bundle();
                args.putSerializable("employee", employee);
                viewUserFragment.setArguments(args);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, viewUserFragment).commit();

            }
        });

        bntAddEmployee = (Button) viewAdminHome.findViewById(R.id.addEmployee);

        bntAddEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new AddUser()).commit();

            }
        });

        bntMap = (Button) viewAdminHome.findViewById(R.id.map);

        bntMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new MapFragment()).commit();

            }
        });

        bntSMS = (Button) viewAdminHome.findViewById(R.id.sendSMS);

        bntSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new SMSActivity()).commit();

            }
        });

        registerForContextMenu(listEmployee);
        return viewAdminHome;
    }

      private void loadList()  {
        EmployeeDAO dao = new EmployeeDAO();
        List<Employee> employees = dao.findEmployees();
        // List<Employee> employees2 = dao.listEmployess();

        EmployeeAdapter adapter = new EmployeeAdapter(getContext(), employees);
        listEmployee.setAdapter(adapter);
    }

    @Override
    public void onResume(){
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
                if(getActivity().checkSelfPermission(Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE
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


                EmployeeDAO dao = new EmployeeDAO();
                dao.deleta(employee);
               // dao.close();

                loadList();
                PdfDocument doc = new PdfDocument();

                return false;

            }
        });
    }

}
