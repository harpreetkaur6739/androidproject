/**
 * Author
 * Harpreet Kaur
 * 300910377
 */
package com.example.comp304_003_finalproject;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.comp304_003_finalproject.database.EmployeeDAO;
import com.example.comp304_003_finalproject.model.Employee;

import static android.content.Context.MODE_PRIVATE;

public class ViewUser extends Fragment {

    private UserHelper helper;
    View viewUserLayout;
    String userId;
    Employee employee = null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewUserLayout = inflater.inflate(R.layout.activity_view_user, container, false);

        helper = new UserHelper(viewUserLayout);
         if(getArguments() != null) {
            employee = (Employee)getArguments().getSerializable("employee");
         }else{
             SharedPreferences myPref = getActivity().getSharedPreferences("RecruitmentSharedPreferences", MODE_PRIVATE);
             userId = myPref.getString("UserId","");
             employee = fetchPersonalInfo(userId);
         }
            if(employee != null){
                helper.fieldTheEmployee(employee);
            }


        return viewUserLayout;
    }

    private Employee fetchPersonalInfo(String userId){
        String[] user = new String[]{userId};
        Employee emp = new EmployeeDAO().findEmployeeById(user);

       return emp;
    }
}
