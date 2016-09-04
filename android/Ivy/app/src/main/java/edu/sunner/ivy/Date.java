package edu.sunner.ivy.ListAdapter;

import java.util.Calendar;

import edu.sunner.ivy.Constant;

/**
 * Created by sunner on 9/4/16.
 */
public class Date {
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

    // ----- Insert method -----
    public boolean setYear(int _) {
        if (_ > Constant.YEAR_MAX)
            return false;
        year = _;
        return true;
    }

    public boolean setMonth(int _) {
        if (_ > Constant.MONTH_MAX)
            return false;
        month = _;
        return true;
    }

    public boolean setDay(int _) {
        if (_ > Constant.DAY_MAX)
            return false;
        day = _;
        return true;
    }

    public boolean setHour(int _) {
        if (_ > Constant.HOUR_MAX)
            return false;
        hour = _;
        return true;
    }

    public boolean setMinute(int _) {
        if (_ > Constant.MINUTE_MAX)
            return false;
        minute = _;
        return true;
    }

    // ----- Get method -----
    int getYear() {
        return year;

    }

    int getMonth() {
        return month;

    }

    int getDay() {
        return day;

    }

    int getHour() {
        return hour;

    }

    int getMinute() {
        return minute;

    }

    // ----- Other method -----
    public void Now() {
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH)+1;
        day = c.get(Calendar.DAY_OF_MONTH);
        hour = c.get(Calendar.HOUR);
        minute = c.get(Calendar.MINUTE);
    }

    public String _toString(){
        String res = "";
        res = res + year + "\n";
        res = res + month + "\n";
        res = res + day + "\n";
        res = res + hour + "\n";
        res = res + minute;
        return res;
    }
}
