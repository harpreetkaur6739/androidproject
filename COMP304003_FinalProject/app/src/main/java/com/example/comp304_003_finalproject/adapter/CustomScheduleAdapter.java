package com.example.comp304_003_finalproject.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.comp304_003_finalproject.R;
import com.example.comp304_003_finalproject.ViewMySchedule;

public class CustomScheduleAdapter extends SimpleCursorAdapter {
    private Context context;
    private int layout;
    private ViewMySchedule viewScheduleFragment;

    public  CustomScheduleAdapter(Context context, int layout, Cursor cursor, String[] from, int[] to, ViewMySchedule viewScheduleFragment){
        super(context, layout, cursor, from, to);
        this.context = context;
        this.layout = layout;
        this.viewScheduleFragment = viewScheduleFragment;
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor){
        int _id = cursor.getInt(cursor.getColumnIndex("_id"));  //Your row id (might need to replace)
        String userId = cursor.getString(cursor.getColumnIndex("_userId"));
        String date = cursor.getString(cursor.getColumnIndex("date"));
        String site = cursor.getString(cursor.getColumnIndex("site"));
        String startTime = cursor.getString(cursor.getColumnIndex("startTime"));
        String endTime = cursor.getString(cursor.getColumnIndex("endTime"));

        Button btnCheckIn = (Button) view.findViewById(R.id.itemBtnCheckIn);
        Button btnCheckout = view.findViewById(R.id.itemBtnCheckOut);
        TextView txtDate = (TextView) view.findViewById(R.id.itemSchedDate);
        TextView txtSite = (TextView)view.findViewById(R.id.itemSite);
        TextView txtStartTime = (TextView) view.findViewById(R.id.itemStartTime);
        TextView txtEndTime = (TextView) view.findViewById(R.id.itemEndTime);

        if(btnCheckIn != null) {
            btnCheckIn.setTag(_id);
            btnCheckIn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){

                    //viewScheduleFragment.checkIn(v,(int)v.getTag());
                }
            });
        }

        if(btnCheckout != null) {
            btnCheckout.setTag(_id);
            btnCheckout.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){

                    //viewScheduleFragment.checkOut(v,(int)v.getTag());
                }
            });
        }

        if(txtDate!= null)  txtDate.setText(date);
        if(txtSite != null) txtSite.setText(site);
        if(txtStartTime != null) txtStartTime.setText(startTime);
        if(txtEndTime != null) txtEndTime.setText(endTime);
    }
}
