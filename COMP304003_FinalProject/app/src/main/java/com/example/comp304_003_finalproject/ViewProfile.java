package com.example.comp304_003_finalproject;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.comp304_003_finalproject.database.DatabaseHandler;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

public class ViewProfile extends Fragment {

    DatabaseHandler dbHandler;
    String TABLE_USERS;
    String fieldsValues[];
    Resources resources;
    View viewProfileLayout;
    TextView txtUserName;
    TextView txtPass;
    EditText txtFName;
    EditText txtLName;
    EditText txtAddress;
    EditText txtCity;
    EditText txtPostalCd;

    TableRow rowBtn;
    Button btnEdit, btnUpdate, btnCancel;

    String record[];
    String userId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //instantiate database values
        dbHandler = DatabaseHandler.getDbHandlerInstance();

        resources = getResources();
        TABLE_USERS = resources.getString(R.string.tbl_users);
        fieldsValues = resources.getStringArray(R.array.fields_user);
        record = new String[fieldsValues.length];

        SharedPreferences myPref = getActivity().getSharedPreferences("RecruitmentSharedPreferences", MODE_PRIVATE);
        userId = myPref.getString("UserId","");

        viewProfileLayout = inflater.inflate(R.layout.activity_view_profile, container, false);

        txtUserName = (TextView)viewProfileLayout.findViewById(R.id.txtUserName);
        txtPass = (TextView)viewProfileLayout.findViewById(R.id.txtPassword);
        txtFName = (EditText)viewProfileLayout.findViewById(R.id.txtFirstName);
        txtLName = (EditText)viewProfileLayout.findViewById(R.id.txtLastName);
        txtAddress = (EditText)viewProfileLayout.findViewById(R.id.txtAddress);
        txtCity = (EditText)viewProfileLayout.findViewById(R.id.txtCity);
        txtPostalCd = (EditText)viewProfileLayout.findViewById(R.id.txtPostalCode);

       rowBtn = (TableRow)viewProfileLayout.findViewById(R.id.row_button);

       /* btnEdit = new Button(new ContextThemeWrapper(getActivity(), R.style.button_style));
        btnUpdate = new Button(new ContextThemeWrapper(getActivity(), R.style.button_style));
        btnCancel = new Button(new ContextThemeWrapper(getActivity(), R.style.button_style));

        btnEdit.setText("Edit");
        btnUpdate.setText("Update");
        btnCancel.setText("Cancel");

        btnEdit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EditFields();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                UpdateFields();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Cancel();
            }
        });

        rowBtn.addView(btnEdit);*/

        fetchPersonalInfo(userId);

        return viewProfileLayout;
    }

    private void fetchPersonalInfo(String userId){
        String keyName[] = {"userId"};
        String values[] = {userId};
        ArrayList user = dbHandler.getRecord(TABLE_USERS, keyName, values);

        if(user != null){

            txtUserName.setText(((HashMap<String, String>) user.get(1)).get("column_value").toString());
            txtPass.setText(((HashMap<String, String>) user.get(2)).get("column_value").toString());
            txtFName.setText(((HashMap<String, String>) user.get(3)).get("column_value").toString());
            txtLName.setText(((HashMap<String, String>) user.get(4)).get("column_value").toString());
            txtAddress.setText(((HashMap<String, String>) user.get(5)).get("column_value").toString());
            txtCity.setText(((HashMap<String, String>) user.get(6)).get("column_value").toString());
            txtPostalCd.setText(((HashMap<String, String>) user.get(7)).get("column_value").toString());

        }

    }
}
