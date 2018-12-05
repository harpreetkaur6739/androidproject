package com.example.comp304_003_finalproject.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.comp304_003_finalproject.AdminHome;
import com.example.comp304_003_finalproject.R;
import com.example.comp304_003_finalproject.model.Employee;

import java.util.List;

public class EmployeeAdapter extends BaseAdapter {
    private final List<Employee> employees;
    private final Context context;

    public EmployeeAdapter(Context context, List<Employee> employees) {
        this.context = context;
        this.employees = employees;
    }

    @Override
    public int getCount() {
        return employees.size();
    }

    @Override
    public Object getItem(int position) {
        return employees.get(position);
    }

    @Override
    public long getItemId(int position) {
        return employees.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Employee employee = employees.get(position);


        LayoutInflater inflater = LayoutInflater.from(context);
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.list_item, parent, false);
        }

        TextView campName = (TextView) view.findViewById(R.id.item_name);
        campName.setText(employee.getName());

        TextView campPhone = (TextView) view.findViewById(R.id.item_phone);
        campPhone.setText(employee.getPhone());

        ImageView campPhoto = (ImageView) view.findViewById(R.id.item_photo);
        String caminhoFoto = employee.getPhoto();
        if (caminhoFoto != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
            campPhoto.setImageBitmap(bitmapReduzido);
            campPhoto.setScaleType(ImageView.ScaleType.FIT_XY);
            campPhoto.setTag(caminhoFoto);
        }

        return view;
    }
}
