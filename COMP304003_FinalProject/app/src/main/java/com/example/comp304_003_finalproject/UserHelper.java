package com.example.comp304_003_finalproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.example.comp304_003_finalproject.model.Employee;

public class UserHelper {

    private final EditText empName;
    private final EditText empAddress;
    private final EditText empPhone;
    private final EditText empSite;
    private final RatingBar empScore;
    private final ImageView empPhoto;


    private Employee employee;

    public UserHelper(AddUser activity)  {
         empName = (EditText) activity.findViewById(R.id.employee_name);
         empAddress = (EditText) activity.findViewById(R.id.employee_address);
         empPhone = (EditText) activity.findViewById(R.id.employee_phone);
         empSite = (EditText) activity.findViewById(R.id.employee_site);
         empScore = (RatingBar) activity.findViewById(R.id.employee_score);
         empPhoto = (ImageView) activity.findViewById(R.id.image_photo);
        employee = new Employee();
    }

    public Employee getEmployee() {

        Employee employee = new Employee();
        employee.setName(empName.getText().toString());
        employee.setAddress(empAddress.getText().toString());
        employee.setPhone(empPhone.getText().toString());
        employee.setSite(empSite.getText().toString());
        employee.setScore(Double.valueOf(empScore.getProgress()));
        employee.setPhoto((String) empPhoto.getTag());
        return employee;

    }

    public void fieldTheEmployee(Employee employee) {
        empName.setText(employee.getName());
        empAddress.setText(employee.getAddress());
        empPhone.setText(employee.getPhone());
        empSite.setText(employee.getSite());
        empScore.setProgress(employee.getScore().intValue());
        loadImage(employee.getPhoto());

        this.employee = employee;
    }

    public void loadImage(String caminhoFoto) {

        if (caminhoFoto != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
            empPhoto.setImageBitmap(bitmapReduzido);
            empPhoto.setScaleType(ImageView.ScaleType.FIT_XY);
            empPhoto.setTag(caminhoFoto);
        }
    }
}
