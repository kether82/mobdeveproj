package com.mobdeve.s11.lim.ivanjerwin.finalproject;

import java.util.Calendar;

public class Date {

    private int day_in_month, month, year;

    public Date() {
        Calendar c = Calendar.getInstance();

        this.year = c.get(Calendar.YEAR);
        this.day_in_month = c.get(Calendar.DAY_OF_MONTH);
        this.month = c.get(Calendar.MONTH);
    }

    public String toString() {
        return this.month + "/" + this.day_in_month + "/" + this.year;
    }
}
