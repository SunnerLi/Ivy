package edu.sunner.ivy.ListAdapter;

import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import edu.sunner.ivy.R;

/**
 * Created by sunner on 9/3/16.
 */
public class ListenListAdapter extends ArrayAdapter {
    private View view;
    private Fragment fragment;
    private String[] setting_texts;
    private String[][] arr = {{"   Yes   ", "   No   "}, {"Normal", "Slower"}};

    // Constructor
    public ListenListAdapter(View view, Fragment f, String[] setting_texts) {
        super(view.getContext(), R.layout.list_listen, setting_texts);
        this.fragment = f;
        this.view = view;
        this.setting_texts = setting_texts;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = fragment.getActivity().getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_listen, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        txtTitle.setText(setting_texts[position]);

        Switch switch1 = (Switch) rowView.findViewById(R.id.switch1);
        if (switch1 == null)
            Log.e("??", "switch為空");
        switch1.setTextOn(arr[position][0]);
        switch1.setTextOff(arr[position][1]);

        return rowView;
    }
}
