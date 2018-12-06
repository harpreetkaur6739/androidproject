/**
 * Author
 * Harpreet Kaur
 * 300910377
 */

package com.example.comp304_003_finalproject.model;

import java.io.Serializable;

public class Schedule implements Serializable {

    private int id;
    private String userId;
    private String date;
    private String site;
    private String startTime;
    private String endTime;
    private String checkInTime;
    private String checkoutTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }

    public String getCheckoutTime() {
        return checkoutTime;
    }

    public void setCheckoutTime(String checkoutTime) {
        this.checkoutTime = checkoutTime;
    }

    public String[] toPrint(){
        String[] scheduleArr = new String[]{
                date, site, startTime, endTime, checkInTime, checkoutTime
        };

        return scheduleArr;
    }

}

