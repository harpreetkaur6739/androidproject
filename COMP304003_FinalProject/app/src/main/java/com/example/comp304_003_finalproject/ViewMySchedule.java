/**
 * Author
 * Harpreet Kaur
 * 300910377
 */
package com.example.comp304_003_finalproject;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comp304_003_finalproject.adapter.CustomScheduleAdapter;
import com.example.comp304_003_finalproject.database.DatabaseHandler;
import com.example.comp304_003_finalproject.database.ScheduleDAO;
import com.example.comp304_003_finalproject.model.Schedule;
import com.example.comp304_003_finalproject.services.DownloadService;
import com.example.comp304_003_finalproject.services.MessageSender;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.example.comp304_003_finalproject.services.DownloadService.PERMISSION_REQUEST_CODE;

public class ViewMySchedule extends Fragment {

    DatabaseHandler dbHandler;
    String TABLE_SCHEDULE;
    Resources resources;
    View viewScheduleLayout;
    ListView lstLayout;
    String userId;
    Button btnDownload;
    ArrayList scheduleArrList;
    List<Schedule> schedules;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        resources = getResources();

        dbHandler = DatabaseHandler.getDbHandlerInstance();
        TABLE_SCHEDULE = resources.getString(R.string.tbl_schedule);
        viewScheduleLayout = inflater.inflate(R.layout.activity_view_my_schedule, container, false);
        lstLayout = viewScheduleLayout.findViewById(R.id.lst_schedule);

        //cursorAdapter = new CustomScheduleAdapter(getActivity(), R.layout.schedule_item_layout,null, fromCols, toCols, ViewMySchedule.this);

        SharedPreferences myPref = getActivity().getSharedPreferences("RecruitmentSharedPreferences", MODE_PRIVATE);
        userId = myPref.getString("UserId","");

        populateSchedule();

        btnDownload = (Button) viewScheduleLayout.findViewById(R.id.btnDownloadSched);

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent downloadService = new Intent(getActivity().getBaseContext(),DownloadService.class);
                prepareData();
                downloadService.putExtra("data", scheduleArrList);
                downloadService.putExtra("userId",userId);
               getActivity().startService(downloadService);

            }
        });

        return viewScheduleLayout;
    }

    private void populateSchedule(){
        ScheduleDAO dao = new ScheduleDAO();
        schedules = dao.getSchedule(userId);

        CustomScheduleAdapter adapter = new CustomScheduleAdapter(getContext(), schedules, this);
        lstLayout.setAdapter(adapter);
    }

    private void prepareData() {
        if(schedules.size() > 0){
            scheduleArrList = new ArrayList();
            for(int i=0; i<schedules.size(); i++){
                scheduleArrList.add(schedules.get(i).toPrint());
            }
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if(getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                    Log.d("permission", "Permission denied to Write file - requesting it");
                    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    requestPermissions(permissions, PERMISSION_REQUEST_CODE);
                }
            }

            Toast.makeText(getContext(), "Downloading...", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getContext(), "Nothing to Download!", Toast.LENGTH_SHORT).show();
        }

    }

    public void checkIn(View v, String scheduleId){
        Date currentTime = Calendar.getInstance().getTime();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        ScheduleDAO dao = new ScheduleDAO();
        Schedule sched = dao.findScheduleById(scheduleId);
        if(sched != null){
            sched.setCheckInTime(format.format(currentTime));
            dao.update(sched);
            getFragmentManager().beginTransaction().replace(R.id.fragment_container, new ViewMySchedule()).commit();

            //send SMS
            String message = "Employee - " + userId + " - checked in at Site - " + sched.getSite() + " - on " + sched.getCheckInTime();
            sendSMS(message);
        }

    }

    private void sendSMS(String message){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(getActivity().checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_DENIED){
                Log.d("permission", "Permission denied to SEND_SMS - requesting it");
                String[] permissions = {Manifest.permission.SEND_SMS};
                requestPermissions(permissions, PERMISSION_REQUEST_CODE);
            }
        }

        Intent msgSenderService = new Intent(getActivity().getBaseContext(),MessageSender.class);
        msgSenderService.putExtra("smsMsg",message);
        getActivity().startService(msgSenderService);


    }

    public void checkOut(View v, String scheduleId){
        Date currentTime = Calendar.getInstance().getTime();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        ScheduleDAO dao = new ScheduleDAO();
        Schedule sched = dao.findScheduleById(scheduleId);
        if(sched != null){
            sched.setCheckoutTime(format.format(currentTime));
            dao.update(sched);
            getFragmentManager().beginTransaction().replace(R.id.fragment_container, new ViewMySchedule()).commit();

            //send SMS
            String message = "Employee - " + userId + " - checked out from Site - " + sched.getSite() + " - on " + sched.getCheckInTime();
            sendSMS(message);
        }
    }
}
