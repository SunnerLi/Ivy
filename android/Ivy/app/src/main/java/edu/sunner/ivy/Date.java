package edu.sunner.ivy;

import java.util.Calendar;

/**
 * Date structure
 *
 * @author sunner
 * @since 9/4/16.
 */
public class Date {
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

    // ----- Insert method -----

    /**
     * Insert year value.
     *
     * @param value the value want to insert
     * @return if insert successfully
     */
    public boolean setYear(int value) {
        if (value > Constant.YEAR_MAX) {
            return false;
        }
        year = value;
        return true;
    }

    /**
     * Insert month value.
     *
     * @param value the value want to insert
     * @return if insert successfully
     */
    public boolean setMonth(int value) {
        if (value > Constant.MONTH_MAX) {
            return false;
        }
        month = value;
        return true;
    }

    /**
     * Insert day value.
     *
     * @param value the value want to insert
     * @return if insert successfully
     */
    public boolean setDay(int value) {
        if (value > Constant.DAY_MAX) {
            return false;
        }
        day = value;
        return true;
    }

    /**
     * Insert hour value.
     *
     * @param value the value want to insert
     * @return if insert successfully
     */
    public boolean setHour(int value) {
        if (value > Constant.HOUR_MAX) {
            return false;
        }
        hour = value;
        return true;
    }

    /**
     * Insert minute value.
     *
     * @param value the value want to insert
     * @return if insert successfully
     */
    public boolean setMinute(int value) {
        if (value > Constant.MINUTE_MAX) {
            return false;
        }
        minute = value;
        return true;
    }

    // ----- Get method -----
    public int getYear() {
        return year;

    }

    public int getMonth() {
        return month;

    }

    public int getDay() {
        return day;

    }

    public int getHour() {
        return hour;

    }

    public int getMinute() {
        return minute;

    }

    // ----- Other method -----

    /**
     * Set the value according to the current time.
     */
    public void now() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR);
        minute = calendar.get(Calendar.MINUTE);
    }

    /**
     * Word object to the string type.
     *
     * @return the string
     */
    public String toString() {
        String res = "";
        res = res + year + "\n";
        res = res + month + "\n";
        res = res + day + "\n";
        res = res + hour + "\n";
        res = res + minute;
        return res;
    }
}
