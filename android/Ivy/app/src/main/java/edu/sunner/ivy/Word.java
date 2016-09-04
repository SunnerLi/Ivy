package edu.sunner.ivy;

import android.content.Intent;
import android.util.Log;

import edu.sunner.ivy.ListAdapter.Date;

/**
 * Created by sunner on 9/4/16.
 */
public class Word {
    // ----- Content -----
    private String english;
    private String chinese;
    private Date date;
    private int numberOfWrong;
    private int numberOfWatch;

    // ----- Method -----
    // Constructor
    public Word() {
        numberOfWatch = 0;
        numberOfWrong = 0;
        date = new Date();
        date.Now();
    }

    public Word(String english, String chinese) {
        this.english = english;
        this.chinese = chinese;
    }

    // Insert method
    public void setEnglish(String eng) {
        english = eng;
    }

    public void setChinese(String chi) {
        chinese = chi;
    }

    public void setDate(Date d) {
        this.date = d;
    }

    boolean setNumberOfWrong(int _) {
        if (_ > 0) {
            numberOfWrong = _;
            return true;
        }
        return false;
    }

    boolean setNumberOfWatch(int _) {
        if (_ > 0) {
            numberOfWatch = _;
            return true;
        }
        return false;
    }

    // Get method
    public String getEnglish() {
        return english;
    }

    public String getChinese() {
        return chinese;
    }

    public Date getDate() {
        return date;
    }

    public int getNumberOfWrong() {
        return numberOfWrong;
    }

    public int getNumberOfWatch() {
        return numberOfWatch;
    }

    // Judge method
    public boolean isEnglish(String _) {
        if (_.length() < 2) {
            Log.d("??", "The string is too short to judge");
            return false;
        }
        if (Character.getNumericValue(_.charAt(0))
                + Character.getNumericValue(_.charAt(1)) > 58 &&
                Character.getNumericValue(_.charAt(0))
                        + Character.getNumericValue(_.charAt(1)) < 256)
            return true;
        return false;
    }

    public boolean isChinese(String _) {
        return !isEnglish(_);
    }

    public String _toString() {
        String res = "";
        res = res + english + "\n";
        res = res + chinese + "\n";
        res = res + date._toString() + "\n";
        res = res + numberOfWrong + "\n";
        res = res + numberOfWatch + "\n";
        return res;
    }

    public void show() {
        Log.d("??", "English: " + english + "\nChinese: " + chinese
                + "\nDate: " + date._toString() + "\nNumber of wrong: " + numberOfWrong
                + "\nNumber of watch: " + numberOfWatch);
    }
}
