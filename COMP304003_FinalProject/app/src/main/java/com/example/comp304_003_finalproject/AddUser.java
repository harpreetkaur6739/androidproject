package com.example.comp304_003_finalproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.comp304_003_finalproject.database.EmployeeDAO;
import com.example.comp304_003_finalproject.model.Employee;

import java.io.File;

public class AddUser extends AppCompatActivity {


    public static final int CODIGO_CAMERA = 567;
    private UserHelper helper;
    private String caminhoFoto;

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

        Button botaoFoto = (Button) findViewById(R.id.image_button);
        botaoFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                caminhoFoto = getExternalFilesDir(null) + "/"+ System.currentTimeMillis() +".jpg";
                File arquivoFoto = new File(caminhoFoto);
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivoFoto));
                startActivityForResult(intentCamera, CODIGO_CAMERA);

            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CODIGO_CAMERA) {
                helper.loadImage(caminhoFoto);
            }
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
