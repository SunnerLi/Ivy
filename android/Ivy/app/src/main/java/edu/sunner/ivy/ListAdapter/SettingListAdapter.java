package edu.sunner.ivy.ListAdapter;

import android.app.Fragment;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import edu.sunner.ivy.R;

/**
 * Created by sunner on 9/3/16.
 */
public class SettingListAdapter extends ArrayAdapter<String> {
    private View view;
    private Fragment fragment;
    private String[] setting_texts;
    private String[][] arr = {{"Yes", "No"}, {"Yes", "No"}, {"四選一ㄕ", "四刪三"}};

    // Constructor
    public SettingListAdapter(View view, Fragment f, String[] setting_texts) {
        super(view.getContext(), R.layout.list_setting, setting_texts);
        this.fragment = f;
        this.view = view;
        this.setting_texts = setting_texts;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = fragment.getActivity().getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_setting, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        txtTitle.setText(setting_texts[position]);

        Spinner spinner = (Spinner)rowView.findViewById(R.id.choose);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this.view.getContext(), android.R.layout.simple_spinner_item, arr[position]);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinner.setAdapter(spinnerArrayAdapter);

        return rowView;
    }
}
