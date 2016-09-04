package edu.sunner.ivy.ListAdapter;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import edu.sunner.ivy.Constant;
import edu.sunner.ivy.R;

/**
 * Created by sunner on 9/3/16.
 */
public class SettingListAdapter extends ArrayAdapter<String> {
    private View view;
    private Fragment fragment;
    private String[] setting_texts;
    private String[][] arr = {{"Yes", "No"}, {"Yes", "No"}, {"四選一ㄕ", "四刪三"}};

    Spinner spinner;

    // Constructor
    public SettingListAdapter(View view, Fragment f, String[] setting_texts) {
        super(view.getContext(), R.layout.list_setting, setting_texts);
        this.fragment = f;
        this.view = view;
        this.setting_texts = setting_texts;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = fragment.getActivity().getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_setting, null, true);


        setView(rowView, position);                                                                 // Set list view object
        recoverSetting(position);
        setListener(position);                                                                      // Set spinner listener

        return rowView;
    }

    public void recoverSetting(int position) {
        int prev = -1;
        SharedPreferences settings = view.getContext().getSharedPreferences(Constant.PRE_NAME, 0);
        switch (position) {
            case 0:
                prev = settings.getInt(Constant.SETTING_SHOWTEXT_KEY, Constant.YES);
                if (prev == Constant.YES)
                    spinner.setSelection(0);
                else
                    spinner.setSelection(1);
                break;
            case 1:
                prev = settings.getInt(Constant.SETTING_SPEAKING_KEY, Constant.YES);
                if (prev == Constant.YES)
                    spinner.setSelection(0);
                else
                    spinner.setSelection(1);
                break;
            case 2:
                prev = settings.getInt(Constant.SETTING_MODE_KEY, Constant.FOUR_CHOOSE_ONE);
                if (prev == Constant.FOUR_CHOOSE_ONE)
                    spinner.setSelection(0);
                else
                    spinner.setSelection(1);
                break;
        }
    }

    public void setView(View rowView, int position) {
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        txtTitle.setText(setting_texts[position]);

        spinner = (Spinner) rowView.findViewById(R.id.choose);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this.view.getContext(), android.R.layout.simple_spinner_item, arr[position]);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinner.setAdapter(spinnerArrayAdapter);
    }

    public void setListener(final int position) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SharedPreferences settings = view.getContext().getSharedPreferences(Constant.PRE_NAME, 0);
                switch (position) {
                    case 0:
                        int _;
                        if (l == 0)
                            _ = Constant.YES;
                        else
                            _ = Constant.NO;
                        settings.edit().putInt(Constant.SETTING_SHOWTEXT_KEY, _).commit();
                        Log.d("??", "show text以修改: " + _);
                        break;
                    case 1:
                        int __;
                        if (l == 0)
                            __ = Constant.YES;
                        else
                            __ = Constant.NO;
                        settings.edit().putInt(Constant.SETTING_SPEAKING_KEY, __).commit();
                        Log.d("??", "speaking以修改: " + __);
                        break;
                    case 2:
                        int ___;
                        if (l == 0)
                            ___ = Constant.FOUR_CHOOSE_ONE;
                        else
                            ___ = Constant.FOUR_DELETE_THREE;
                        settings.edit().putInt(Constant.SETTING_MODE_KEY, ___).commit();
                        Log.d("??", "mode以修改: " + ___);
                        break;
                    default:
                        Log.e("??", "未知list row");
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
}
