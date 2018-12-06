package com.example.comp304_003_finalproject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.comp304_003_finalproject.database.EmployeeDAO;
import com.example.comp304_003_finalproject.model.Employee;

import java.io.File;

public class AddUser extends Fragment {


    public static final int CODIGO_CAMERA = 567;
    private UserHelper helper;
    private String caminhoFoto;
    View viewAddUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewAddUser = inflater.inflate(R.layout.activity_add_user, container, false);
        helper = new UserHelper (viewAddUser);

        Button botaoFoto = (Button) viewAddUser.findViewById(R.id.image_button);
        botaoFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                caminhoFoto = getActivity().getExternalFilesDir(null) + "/"+ System.currentTimeMillis() +".jpg";
                File arquivoFoto = new File(caminhoFoto);
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivoFoto));
                startActivityForResult(intentCamera, CODIGO_CAMERA);

            }
        });


        setHasOptionsMenu(true);
        return viewAddUser;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CODIGO_CAMERA) {
                helper.loadImage(caminhoFoto);
            }
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

         inflater.inflate(R.menu.menu_adduser, menu);

        super.onCreateOptionsMenu(menu, inflater);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_addUser:

                Employee employee = helper.getEmployee();
                EmployeeDAO dao = new EmployeeDAO();

                if (employee.getId() != null)  {
                    dao.upadate(employee);
                } else {
                    dao.insert(employee);
                }

                Toast.makeText(getContext(), "User Added:" + employee.getName(), Toast.LENGTH_SHORT).show();
                break;

        }
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, new AdminHome()).commit();
        return super.onOptionsItemSelected(item);

    }
}
