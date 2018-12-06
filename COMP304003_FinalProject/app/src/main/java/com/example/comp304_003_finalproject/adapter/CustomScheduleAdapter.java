/**
 * Author
 * Harpreet Kaur
 * 300910377
 */

package com.example.comp304_003_finalproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.comp304_003_finalproject.R;
import com.example.comp304_003_finalproject.ViewMySchedule;
import com.example.comp304_003_finalproject.model.Schedule;

import java.util.List;

public class CustomScheduleAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ViewMySchedule viewScheduleFragment;
    private final List<Schedule> schedules;

    public  CustomScheduleAdapter(Context context, List<Schedule> schedules, ViewMySchedule viewScheduleFragment){
        this.context = context;
        this.schedules = schedules;
        this.viewScheduleFragment = viewScheduleFragment;
    }

    @Override
    public int getCount() {
        return schedules.size();
    }

    @Override
    public Object getItem(int position) {
        return schedules.get(position);
    }

    @Override
    public long getItemId(int position) {
        return schedules.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Schedule schedule = schedules.get(position);


        LayoutInflater inflater = LayoutInflater.from(context);
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.schedule_item_layout, parent, false);
        }

        Button btnCheckIn = (Button) view.findViewById(R.id.itemBtnCheckIn);
        Button btnCheckout = view.findViewById(R.id.itemBtnCheckOut);
        TextView txtDate = (TextView) view.findViewById(R.id.itemSchedDate);
        TextView txtSite = (TextView)view.findViewById(R.id.itemSite);
        TextView txtStartTime = (TextView) view.findViewById(R.id.itemStartTime);
        TextView txtEndTime = (TextView) view.findViewById(R.id.itemEndTime);
        TextView txtCheckInTime = (TextView) view.findViewById(R.id.itemCheckInTime);
        TextView txtCheckOutTime = (TextView) view.findViewById(R.id.itemCheckoutTime);

        if(btnCheckIn != null) {
            btnCheckIn.setTag(schedule.getId());
            btnCheckIn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){

                    viewScheduleFragment.checkIn(v,v.getTag().toString());
                }
            });
        }

        if(btnCheckout != null) {
            btnCheckout.setTag(schedule.getId());
            btnCheckout.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){

                    viewScheduleFragment.checkOut(v,v.getTag().toString());
                }
            });
        }

        if(txtDate!= null)  txtDate.setText(schedule.getDate());
        if(txtSite != null) txtSite.setText(schedule.getSite());
        if(txtStartTime != null) txtStartTime.setText(schedule.getStartTime());
        if(txtEndTime != null) txtEndTime.setText(schedule.getEndTime());
        if(txtCheckInTime != null) {
            txtCheckInTime.setText(schedule.getCheckInTime());
            btnCheckIn.setVisibility(View.INVISIBLE);
        }
        if(txtCheckOutTime != null){
            txtCheckOutTime.setText(schedule.getCheckoutTime());
            btnCheckout.setVisibility(View.INVISIBLE);
        }

        return view;
    }
}
