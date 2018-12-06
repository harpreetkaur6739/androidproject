/**
 * Author
 * Harpreet Kaur
 * 300910377
 */
package com.example.comp304_003_finalproject.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.example.comp304_003_finalproject.model.Schedule;

import java.util.ArrayList;

public class ScheduleDAO {
    DatabaseHandler dbHandler;
    String TABLE_SCHEDULE = "tbl_schedule";

    public ScheduleDAO(){
        dbHandler = DatabaseHandler.getDbHandlerInstance();
    }

    public Schedule findScheduleById(String scheduleId){
        Schedule sched = null;
        String[] key = new String[]{"id"};
        String[] val = new String[]{scheduleId};
        Cursor c = dbHandler.getTableCursor(TABLE_SCHEDULE, key, val);

        while (c.moveToNext()) {
            sched = new Schedule();
            sched.setId(c.getInt(c.getColumnIndex("id")));
            sched.setUserId(c.getString(c.getColumnIndex("userId")));
            sched.setDate(c.getString(c.getColumnIndex("date")));
            sched.setSite(c.getString(c.getColumnIndex("site")));
            sched.setStartTime(c.getString(c.getColumnIndex("startTime")));
            sched.setEndTime(c.getString(c.getColumnIndex("endTime")));
            sched.setCheckInTime(c.getString(c.getColumnIndex("checkInTime")));
            sched.setCheckoutTime(c.getString(c.getColumnIndex("checkOutTime")));
        }

        return sched;
    }

    public ArrayList<Schedule> getSchedule(String userId){
        String[] key = new String[]{"userId"};
        String[] val = new String[]{userId};
        Cursor c = dbHandler.getTableCursor(TABLE_SCHEDULE, key, val);

        ArrayList<Schedule> schedule = new ArrayList<Schedule>();
        while(c.moveToNext()){
            Schedule sched = new Schedule();
            sched.setId(c.getInt(c.getColumnIndex("id")));
            sched.setUserId(c.getString(c.getColumnIndex("userId")));
            sched.setDate(c.getString(c.getColumnIndex("date")));
            sched.setSite(c.getString(c.getColumnIndex("site")));
            sched.setStartTime(c.getString(c.getColumnIndex("startTime")));
            sched.setEndTime(c.getString(c.getColumnIndex("endTime")));
            sched.setCheckInTime(c.getString(c.getColumnIndex("checkInTime")));
            sched.setCheckoutTime(c.getString(c.getColumnIndex("checkOutTime")));

            schedule.add(sched);
        }
        c.close();

        return schedule;
    }

    public void update(Schedule schedule) {

        ContentValues values =  getContentValuesSchedule(schedule);
        String[] key = new String []{"id"};
        String[] params =new String[]{schedule.getId()+""};
        dbHandler.updateRecord(values, TABLE_SCHEDULE, key, params);
    }



    @NonNull
    public ContentValues getContentValuesSchedule(Schedule schedule) {
        ContentValues val = new ContentValues();
        val.put("id", schedule.getId());
        val.put("userId", schedule.getUserId());
        val.put("date", schedule.getDate());
        val.put("site", schedule.getSite());
        val.put("startTime", schedule.getStartTime());
        val.put("endTime", schedule.getEndTime());
        val.put("checkInTime", schedule.getCheckInTime());
        val.put("checkOutTime", schedule.getCheckoutTime());
        return val;
    }
}
