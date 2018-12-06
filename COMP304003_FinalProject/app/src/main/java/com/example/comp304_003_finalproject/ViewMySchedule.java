package com.example.comp304_003_finalproject;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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
import com.example.comp304_003_finalproject.services.DownloadService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.example.comp304_003_finalproject.services.DownloadService.PERMISSION_REQUEST_CODE;

public class ViewMySchedule extends Fragment {

    DatabaseHandler dbHandler;
    String TABLE_SCHEDULE;
    Resources resources;
    View viewScheduleLayout;
    ListView lstLayout;
    CustomScheduleAdapter cursorAdapter = null;
    String[] fromCols = {"date","site","startTime", "endTime"};
    int[] toCols = {R.id.itemSchedDate, R.id.itemSite, R.id.itemStartTime, R.id.itemEndTime};
    final String COL_USERNAME = "_userId";
    String userId;
    Button btnDownload;
    ArrayList scheduleArrList;
    Cursor scheduleCursor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        resources = getResources();

        dbHandler = DatabaseHandler.getDbHandlerInstance();
        TABLE_SCHEDULE = resources.getString(R.string.tbl_schedule);
        viewScheduleLayout = inflater.inflate(R.layout.activity_view_my_schedule, container, false);
        lstLayout = viewScheduleLayout.findViewById(R.id.lst_schedule);

        cursorAdapter = new CustomScheduleAdapter(getActivity(), R.layout.schedule_item_layout,null, fromCols, toCols, ViewMySchedule.this);

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

        String[] column = new String[]{"_userId"};
        String[] value = new String[]{userId};
        scheduleCursor = dbHandler.getTableCursor(TABLE_SCHEDULE, column, value);

        cursorAdapter.changeCursor(scheduleCursor);

        lstLayout.setAdapter(cursorAdapter);
    }

    private void prepareData() {
        scheduleArrList = new ArrayList();
        if (scheduleCursor.moveToFirst()) {
            do { // for each row
                String[] localList = new String[8];
                for (int count=0,i = 2; i < scheduleCursor.getColumnCount(); i++, count++) {
                    localList[count] = scheduleCursor.getString(i);
                   // localList.add(scheduleCursor.getString(i)); //change the type of details from ArrayList<String> to arrayList<HashMap<String,String>>
                }
                scheduleArrList.add(localList);
            } while (scheduleCursor.moveToNext());
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                Log.d("permission", "Permission denied to Write file - requesting it");
                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions, PERMISSION_REQUEST_CODE);
            }
        }

        Toast.makeText(getContext(), "Downloading...", Toast.LENGTH_SHORT).show();
    }
}
