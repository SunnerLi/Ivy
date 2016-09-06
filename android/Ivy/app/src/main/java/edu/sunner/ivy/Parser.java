package edu.sunner.ivy;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.List;

/**
 * The main structure that deal with the file and the list of the Word object
 *
 * @author sunner
 * @since 9/4/16.
 */
public class Parser {
    // List object to store the Word object
    private List<Word> words = new LinkedList<Word>();

    // List object to store the index of the Word object which has record of watch or being wrong
    private List<Integer> weakIndexs = new LinkedList<>();

    // Default file path
    private static final String PATH = "/sdcard/ivy.pair";

    /**
     * The program should check the edition at first.
     * The user might put the old edition .pair file while launch the APP
     */
    public void readWork() {
        // Test the edition
        switch (whichEdition(PATH)) {
            case Constant.VIVIAN:
                updateFile(PATH);
                break;
            case Constant.IVY:
                break;
            default:
                Log.e(Constant.PR_TAG, "異常版本");

        }
        read(PATH);
    }

    /**
     * Check if the source is in the new edition.
     * New:    Ivy edition
     * Old:    Vivian edition
     *
     * @param path the path want to deal with
     * @return the edition constant
     */
    public int whichEdition(String path) {
        int edition = -1;
        try {
            FileInputStream fis = new FileInputStream(new File(path));
            if (fis != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(fis);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = bufferedReader.readLine();
                edition = (isNumeric(receiveString) ? Constant.IVY : Constant.VIVIAN);
                fis.close();
            }
        } catch (FileNotFoundException err) {
            err.printStackTrace();
        } catch (IOException err) {
            Log.e("login activity", "Can not read file: " + err.toString());
        }
        return edition;
    }

    /**
     * Check if the string is numberic.
     *
     * @param str the string want to examine
     * @return if it's numerical
     */
    public static boolean isNumeric(String str) {
        try {
            double doub = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     * Update the .pair file from the old edition.
     * Notice You should check if it's in the old edition
     *
     * @param path the string want to examine
     */
    public void updateFile(String path) {
        // Read the whole data and update
        words = new LinkedList<Word>();
        try {
            FileInputStream fis = new FileInputStream(new File(path));

            if (fis != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(fis);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                while (true) {
                    // Create a new default Word object
                    String receiveString = "";
                    Word word = new Word();

                    // Read English and set
                    receiveString = bufferedReader.readLine();
                    if (receiveString == null) {
                        break;
                    }
                    word.setEnglish(receiveString);

                    // Read Chinese and set
                    receiveString = bufferedReader.readLine();
                    if (receiveString == null) {
                        break;
                    }
                    word.setChinese(receiveString);

                    // Add into the list
                    words.add(word);
                    word.show();
                }
                fis.close();
            }
            Log.e("讀檔", "finishㄝ, 長度: " + words.size());

        } catch (FileNotFoundException err) {
            Log.e(Constant.PR_TAG, "File not found: " + err.toString());
        } catch (IOException err) {
            Log.e(Constant.PR_TAG, "Can not read file: " + err.toString());
        }

        // Write to the new file
        write(PATH);
    }

    /**
     * Main function to write the whole data in words list into the file.
     *
     * @param path the string want to examine
     */
    public void write(String path) {
        try {
            FileOutputStream fos = new FileOutputStream(new File(path));
            if (fos != null) {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fos);

                outputStreamWriter.write((words.size() + "\n"));
                for (int i = 0; i < words.size(); i++) {
                    Word word = words.get(i);
                    Log.d("write: ", word.toString());
                    outputStreamWriter.write(word.toString());
                }
                outputStreamWriter.close();
            }
            Log.d(Constant.PR_TAG, "寫入完成");
        } catch (FileNotFoundException err) {
            Log.e(Constant.PR_TAG, "File not found: " + err.toString());
        } catch (IOException err) {
            Log.e(Constant.PR_TAG, "Can not read file: " + err.toString());
        }
    }

    /**
     * The write function with default path.
     */
    public void write() {
        write(PATH);
    }

    /**
     * Main function to read the whole data in file.
     *
     * @param path the string want to examine
     */
    public void read(String path) {
        words = new LinkedList<Word>();
        try {
            FileInputStream fis = new FileInputStream(new File(path));

            if (fis != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(fis);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                int length = Integer.valueOf(bufferedReader.readLine());

                // read data
                for (int i = 0; i < length; i++) {
                    Word word = new Word();

                    // read english
                    word.setEnglish(bufferedReader.readLine());

                    // read chinese
                    word.setChinese(bufferedReader.readLine());

                    // read Date
                    Date date = new Date();
                    date.setYear(Integer.valueOf(bufferedReader.readLine()));
                    date.setMonth(Integer.valueOf(bufferedReader.readLine()));
                    date.setDay(Integer.valueOf(bufferedReader.readLine()));
                    date.setHour(Integer.valueOf(bufferedReader.readLine()));
                    date.setMinute(Integer.valueOf(bufferedReader.readLine()));
                    word.setDate(date);

                    // read number of wrong
                    word.setNumberOfWrong(Integer.valueOf(bufferedReader.readLine()));

                    // read number of watch
                    word.setNumberOfWatch(Integer.valueOf(bufferedReader.readLine()));
                    words.add(word);
                    // word.show();
                }
                fis.close();
            }
            Log.e("讀檔", "finish");

        } catch (FileNotFoundException err) {
            Log.e(Constant.PR_TAG, "File not found: " + err.toString());
        } catch (IOException err) {
            Log.e(Constant.PR_TAG, "Can not read file: " + err.toString());
        }
    }

    /**
     * Get the number of the whole Word objects.
     *
     * @return number
     */
    public int length() {
        return words.size();
    }

    /**
     * Get the english in the specific position.
     *
     * @param index the position number
     * @return the english string
     */
    public String getEnglish(int index) {
        return words.get(index).getEnglish();
    }

    /**
     * Get the chinese in the specific position.
     *
     * @param index the position number
     * @return the chinese string
     */
    public String getChinese(int index) {
        return words.get(index).getChinese();
    }

    /*
        ----- Strengthen Mode Relative -----
     */

    /**
     * Collect the whole index which word is weak.
     *
     * @param max the threshold number
     */
    public void buildWeak(int max) {
        weakIndexs = new LinkedList<>();
        for (int i = 0; i < words.size(); i++) {
            if (words.get(i).getNumberOfWatch() > max
                || words.get(i).getNumberOfWrong() > max) {
                weakIndexs.add(i);
            }
        }
    }

    /**
     * Get the number of the Word objects that should be strengthen.
     *
     * @return number
     */
    public int weaklength() {
        return weakIndexs.size();
    }

    /**
     * Get the english that should be strengthen in the specific position.
     *
     * @param index the position number
     * @return the english string
     */
    public String weakgetEnglish(int index) {
        return words.get((int) (weakIndexs.get(index))).getEnglish();
    }

    /**
     * Get the chinese that should be strengthen in the specific position.
     *
     * @param index the position number
     * @return the chinese string
     */
    public String weakgetChinese(int index) {
        return words.get((int) (weakIndexs.get(index))).getChinese();
    }

    /**
     * Increase the number of watch in the specific position.
     *
     * @param index the position want to deal with in the list
     */
    public void increaseNumberOfWatch(int index) {
        Word word = words.get(index);
        word.setNumberOfWatch(word.getNumberOfWatch() + 1);
        words.set(index, word);
    }

    /**
     * Increase the number of wrong in the specific position.
     *
     * @param index the position want to deal with in the list
     */
    public void increaseNumberOfWrong(int index) {
        Word word = words.get(index);
        word.setNumberOfWrong(word.getNumberOfWrong() + 1);
        words.set(index, word);
    }

    /**
     * Decrease the number of watch in the specific position.
     *
     * @param index the position want to deal with in the list
     */
    public void decreaseNumberOfWatch(int index) {
        index = weakIndexs.get(index);
        Word word = words.get(index);
        if (Math.signum(word.getNumberOfWatch()) == -1.0) {
            word.setNumberOfWatch(0);
        } else {
            word.setNumberOfWatch(word.getNumberOfWatch() - 1);
        }
        words.set(index, word);
    }

    /**
     * Decrease the number of wrong in the specific position.
     *
     * @param index the position want to deal with in the list
     */
    public void decreaseNumberOfWrong(int index) {
        index = weakIndexs.get(index);
        Word word = words.get(index);
        if (Math.signum(word.getNumberOfWrong()) == -1.0) {
            word.setNumberOfWrong(0);
        } else {
            word.setNumberOfWrong(word.getNumberOfWrong() - 1);
        }
        words.set(index, word);
    }

    /**
     * Testing function.
     */
    public void showWeakListN() {
        for (int i = 0; i < weaklength(); i++) {
            Log.d(Constant.PR_TAG, "Word: " + words.get(weakIndexs.get(i)).getEnglish()
                + "numberOfWatch: " + words.get(weakIndexs.get(i)).getNumberOfWatch()
                + "numberOfWrong: " + words.get(weakIndexs.get(i)).getNumberOfWrong());
        }
    }
}
