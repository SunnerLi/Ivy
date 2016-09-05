package edu.sunner.ivy;

import android.util.Log;

/**
 * The Word structure
 *
 * @author sunner
 * @since 9/4/16.
 */
public class Word {
    // ----- Content -----
    private String english;
    private String chinese;
    private Date date;
    private int numberOfWrong;
    private int numberOfWatch;

    // ----- Method -----
    /**
     * Constructor.
     */
    public Word() {
        numberOfWatch = 0;
        numberOfWrong = 0;
        date = new Date();
        date.now();
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

    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Set the number of wrong with the specific value.
     *
     * @param value the value want to set
     * @return if successfully sets
     */
    boolean setNumberOfWrong(int value) {
        if (value > 0) {
            numberOfWrong = value;
            return true;
        }
        return false;
    }

    /**
     * Set the number of watch with the specific value.
     *
     * @param value the value want to set
     * @return if successfully sets
     */
    boolean setNumberOfWatch(int value) {
        if (value > 0) {
            numberOfWatch = value;
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

    /**
     * Judge if the string is in english.
     *
     * @param string the string want to examine
     * @return if it's in english
     */
    public boolean isEnglish(String string) {
        if (string.length() < 2) {
            Log.d(Constant.WD_TAG, "The string is too short to judge");
            return false;
        }
        if (Character.getNumericValue(string.charAt(0))
            + Character.getNumericValue(string.charAt(1)) > 58
            && Character.getNumericValue(string.charAt(0))
                + Character.getNumericValue(string.charAt(1)) < 256) {
            return true;
        }
        return false;
    }

    /**
     * Judge if the string is in english.
     * Assume there're only english and chinese two cases
     *
     * @param string the string want to examine
     * @return if it's in chinese
     */
    public boolean isChinese(String string) {
        return !isEnglish(string);
    }

    /**
     * Convert the Word object to string.
     *
     * @return the string
     */
    public String toString() {
        String res = "";
        res = res + english + "\n";
        res = res + chinese + "\n";
        res = res + date.toString() + "\n";
        res = res + numberOfWrong + "\n";
        res = res + numberOfWatch + "\n";
        return res;
    }

    /**
     * Show the Word object contain.
     */
    public void show() {
        Log.d(Constant.WD_TAG, "English: " + english + "\nChinese: " + chinese
            + "\nDate: " + date.toString() + "\nNumber of wrong: " + numberOfWrong
            + "\nNumber of watch: " + numberOfWatch);
    }
}
