package edu.sunner.ivy.listadapter;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import edu.sunner.ivy.Constant;
import edu.sunner.ivy.R;

/**
 * This class is the array adapter that would be used in SettingFragment
 *
 * @author sunner
 * @since 9/3/16.
 */
public class SettingListAdapter extends ArrayAdapter<String> {
    // View object
    private View view;

    // Fragment object
    private Fragment fragment;

    // Setting text descriptions
    private String[] settingtexts;

    // String array that store each options in the spinners
    private String[][] arr = {{"Yes", "No"}, {"Yes", "No"}, {"四選一ㄕ", "四刪三"}, {"Yes", "No"}};

    // Spinner object that would be used in the list
    private Spinner spinner;

    /**
     * Constructor.
     *
     * @param view         view object
     * @param fragment     fragment object
     * @param settingTexts the setting text descriptions
     */
    public SettingListAdapter(View view, Fragment fragment, String[] settingTexts) {
        super(view.getContext(), R.layout.list_setting, settingTexts);
        this.fragment = fragment;
        this.view = view;
        this.settingtexts = settingTexts;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = fragment.getActivity().getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_setting, null, true);


        setView(rowView, position);     // Set list view object
        recoverSetting(position);
        setListener(position);          // Set spinner listener

        return rowView;
    }

    /**
     * Recover the previous setting status.
     *
     * @param position the position index of the list
     */
    public void recoverSetting(int position) {
        int prev = -1;
        SharedPreferences settings = view.getContext().getSharedPreferences(Constant.PRE_NAME, 0);
        switch (position) {
            case 0:
                prev = settings.getInt(Constant.SETTING_SHOWTEXT_KEY, Constant.YES);
                if (prev == Constant.YES) {
                    spinner.setSelection(0);
                } else {
                    spinner.setSelection(1);
                }
                break;
            case 1:
                prev = settings.getInt(Constant.SETTING_SPEAKING_KEY, Constant.YES);
                if (prev == Constant.YES) {
                    spinner.setSelection(0);
                } else {
                    spinner.setSelection(1);
                }
                break;
            case 2:
                prev = settings.getInt(Constant.SETTING_MODE_KEY, Constant.FOUR_CHOOSE_ONE);
                if (prev == Constant.FOUR_CHOOSE_ONE) {
                    spinner.setSelection(0);
                } else {
                    spinner.setSelection(1);
                }
                break;
            case 3:
                prev = settings.getInt(Constant.SETTING_SILENT, Constant.YES);
                if (prev == Constant.YES) {
                    spinner.setSelection(0);
                } else {
                    spinner.setSelection(1);
                }
                break;
            default:
                break;
        }
    }

    /**
     * Get view object and set the spinner adapter.
     *
     * @param rowView  the view object
     * @param position the position index of the list
     */
    public void setView(View rowView, int position) {
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        txtTitle.setText(settingtexts[position]);

        spinner = (Spinner) rowView.findViewById(R.id.choose);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
            this.view.getContext(),
            android.R.layout.simple_spinner_item,
            arr[position]);

        // The drop down view
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);
    }

    /**
     * Set the spinner listener.
     *
     * @param position the position index in the list
     */
    public void setListener(final int position) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int index, long val) {
                SharedPreferences settings = view.getContext()
                    .getSharedPreferences(Constant.PRE_NAME, 0);
                int choose;
                switch (position) {
                    case 0:
                        if (val == 0) {
                            choose = Constant.YES;
                        } else {
                            choose = Constant.NO;
                        }
                        settings.edit().putInt(Constant.SETTING_SHOWTEXT_KEY, choose).commit();
                        Log.d(Constant.SLAR_TAG, "show text以修改: " + choose);
                        break;
                    case 1:
                        if (val == 0) {
                            choose = Constant.YES;
                        } else {
                            choose = Constant.NO;
                        }
                        settings.edit().putInt(Constant.SETTING_SPEAKING_KEY, choose).commit();
                        Log.d(Constant.SLAR_TAG, "speaking以修改: " + choose);
                        break;
                    case 2:
                        if (val == 0) {
                            choose = Constant.FOUR_CHOOSE_ONE;
                        } else {
                            choose = Constant.FOUR_DELETE_THREE;
                        }
                        settings.edit().putInt(Constant.SETTING_MODE_KEY, choose).commit();
                        Log.d(Constant.SLAR_TAG, "mode以修改: " + choose);
                        break;
                    case 3:
                        if (val == 0) {
                            choose = Constant.YES;
                        } else {
                            choose = Constant.NO;
                        }
                        settings.edit().putInt(Constant.SETTING_SILENT, choose).commit();
                        Log.d(Constant.SLAR_TAG, "禁音: " + choose);
                        break;
                    default:
                        Log.e(Constant.SLAR_TAG, "未知list row");
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
}
