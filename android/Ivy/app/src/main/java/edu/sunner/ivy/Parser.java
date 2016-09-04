package edu.sunner.ivy;

import android.content.Intent;
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

import edu.sunner.ivy.ListAdapter.Date;

/**
 * Created by sunner on 9/4/16.
 */
public class Parser {
    List<Word> words = new LinkedList<Word>();
    List<Integer> weakIndexs = new LinkedList<>();

    public void test() {

    }

    public void readWork() {
        // Test the edition
        switch (whichEdition("/sdcard/ivy.pair")) {
            case Constant.VIVIAN_EDITION:
                updateFile();
                break;
            case Constant.IVY_EDITION:
                break;
            default:
                Log.e("??", "異常版本");

        }
        read();
    }

    public int whichEdition(String path) {
        int edition = -1;
        try {
            FileInputStream fis = new FileInputStream(new File(path));
            if (fis != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(fis);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = bufferedReader.readLine();
                edition = (isNumeric(receiveString) ? Constant.IVY_EDITION : Constant.VIVIAN_EDITION);
                fis.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        return edition;
    }

    public static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public void updateFile() {
        // Read the whole data and update
        words = new LinkedList<Word>();
        try {
            FileInputStream fis = new FileInputStream(new File("/sdcard/ivy.pair"));

            if (fis != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(fis);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";

                while (true) {
                    Word word = new Word();

                    receiveString = bufferedReader.readLine();
                    if (receiveString == null)
                        break;
                    word.setEnglish(receiveString);

                    receiveString = bufferedReader.readLine();
                    if (receiveString == null)
                        break;
                    word.setChinese(receiveString);

                    words.add(word);
                    word.show();
                }
                fis.close();
            }
            Log.e("讀檔", "finishㄝ, 長度: " + words.size());

        } catch (FileNotFoundException e) {
            Log.e("?? Parser", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("?? Parser", "Can not read file: " + e.toString());
        }


        // Write to the new file
        try {
            FileOutputStream fos = new FileOutputStream(new File("/sdcard/ivy.pair"));
            if (fos != null) {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fos);

                outputStreamWriter.write((words.size() + "\n"));
                for (int i = 0; i < words.size(); i++) {
                    Word word = words.get(i);
                    Log.d("write: ", word._toString());
                    outputStreamWriter.write(word._toString());
                }
                outputStreamWriter.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("?? Parser", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("?? Parser", "Can not read file: " + e.toString());
        }

    }

    public void read() {
        words = new LinkedList<Word>();
        try {
            FileInputStream fis = new FileInputStream(new File("/sdcard/ivy.pair"));

            if (fis != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(fis);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                int length = Integer.valueOf(bufferedReader.readLine());

                // read data
                for (int i = 0; i < length; i++) {
                    Word word = new Word();
                    word.setEnglish(bufferedReader.readLine());                                     // read english
                    word.setChinese(bufferedReader.readLine());                                     // read chinese

                    Date date = new Date();
                    date.setYear(Integer.valueOf(bufferedReader.readLine()));                       // read Date
                    date.setMonth(Integer.valueOf(bufferedReader.readLine()));
                    date.setDay(Integer.valueOf(bufferedReader.readLine()));
                    date.setHour(Integer.valueOf(bufferedReader.readLine()));
                    date.setMinute(Integer.valueOf(bufferedReader.readLine()));
                    word.setDate(date);

                    word.setNumberOfWrong(Integer.valueOf(bufferedReader.readLine()));              // read number of wrong
                    word.setNumberOfWatch(Integer.valueOf(bufferedReader.readLine()));              // read number of watch
                    words.add(word);
                    word.show();
                }
                fis.close();
            }
            Log.e("讀檔", "finish");

        } catch (FileNotFoundException e) {
            Log.e("?? Parser", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("?? Parser", "Can not read file: " + e.toString());
        }
    }

    public int length() {
        return words.size();
    }

    public String getEnglish(int index) {
        return words.get(index).getEnglish();
    }

    public String getChinese(int index) {
        return words.get(index).getChinese();
    }

    /*
        ----- Strengthen Mode Relative -----
     */
    // Collect the whole index which word is weak
    public void buildWeak(int _max) {
        for (int i = 0; i < words.size(); i++)
            if (words.get(i).getNumberOfWatch() > _max ||
                    words.get(i).getNumberOfWrong() > _max)
                weakIndexs.add(i);
    }

    public int weak_length(){
        return weakIndexs.size();
    }

    public String weak_getEnglish(int index){
        return words.get((int)(weakIndexs.get(index))).getEnglish();
    }

    public String weak_getChinese(int index){
        return words.get((int)(weakIndexs.get(index))).getChinese();
    }
}
